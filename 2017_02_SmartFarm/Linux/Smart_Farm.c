#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/poll.h>
#include <termios.h>
#include <fcntl.h>
#include <gtk/gtk.h>
#include <pthread.h>

GtkWidget *window;
GtkWidget *openBtn, *closeBtn, *autoBtn, *manualBtn;
GtkWidget *label1, *label2, *label3, *label4, *label5, *now;
GtkWidget *data[3];
GtkWidget *fixed, *entry;
GtkWidget *bgImg;

int upperStandard[3] = {15, 28, 60}; // 습도, 온도, 조도 순서
int underStandard[3] = {5, 24, 45};
int integerData[3] = {0};

char ctlFlag = 0;
char modeFlag = 0;

gchar *nowCtl[2] = { "현재 비닐하우스가 닫혀있습니다.",  "현재 비닐하우스가 열려있습니다." };
gchar *nowMode[2] = { "자동 모드", "수동 모드" };

void init_setting_gtk();
void autoMode(GtkWidget *frame, GdkEventButton *event);
void manualMode(GtkWidget *frame, GdkEventButton *event);
void openHouse(GtkWidget *frame, GdkEventButton *event);
void closeHouse(GtkWidget *frame, GdkEventButton *event);
void controlFunction(GtkWidget *frame, GdkEventButton *event);
GtkWidget *xpm_label_box(gchar * xpm_filename, gchar * label_text);
void changeMsg();
int recv();
void updateData();
void refresh_win();

char buf[1024];
int send_flag = 0;
pthread_t thread;
pthread_mutex_t mutex = PTHREAD_MUTEX_INITIALIZER;

int main(int argc, char* argv[])
{
    pthread_create(&thread, NULL, (void*(*)(void*))recv, NULL);

    gtk_init(&argc, &argv);
    window = gtk_window_new(GTK_WINDOW_TOPLEVEL);
    gtk_widget_set_size_request(window, 800, 480);   

    init_setting_gtk();

    g_signal_connect(G_OBJECT(window), "destroy", G_CALLBACK(gtk_main_quit), NULL);
    g_signal_connect(G_OBJECT(window), "delete_event", G_CALLBACK(gtk_main_quit), NULL);

    gtk_widget_show_all(window);
    gtk_main();
    return 0;
}

void refresh_win()
{
    while(gtk_events_pending())
        gtk_main_iteration_do(1);

}

int recv()
{
    int fd;
    struct termios newtio;
    struct pollfd poll_events;
    int poll_state;
    int i;

    fd = open("/dev/ttyS0", O_RDWR | O_NOCTTY | O_NONBLOCK);

    if (0 > fd)
    {
        printf("open error\n");
        return -1;
    }

    memset(&newtio, 0, sizeof(newtio));
    newtio.c_cflag = B9600 | CS8 | CLOCAL | CREAD;
    newtio.c_oflag = 0;
    newtio.c_lflag = 0;
    newtio.c_cc[VTIME] = 0;
    newtio.c_cc[VMIN] = 1;

    tcflush(fd, TCIFLUSH);
    tcsetattr(fd, TCSANOW, &newtio);
    fcntl(fd, F_SETFL, FNDELAY);

    poll_events.fd = fd;
    poll_events.events = POLLIN | POLLERR | POLLOUT;
    poll_events.revents = 0;

    write(fd, "3", 1);

    while (1)
    {
        poll_state = poll((struct pollfd*)&poll_events, 1, 1000);
        if (0 < poll_state)
        {
            if (poll_events.revents & POLLIN)
            {
                memset(buf, '\0', 1024);
                read(fd, buf, 1024);
                updateData();
                refresh_win();
                write(fd, "3", 1);
            }

            if (poll_events.revents & POLLOUT)
            {
                if(send_flag == 1)
                {
                    printf("send_flag : %d\n", send_flag);
                    pthread_mutex_lock(&mutex);
                    write(fd, "1", 1);
                    send_flag = 0;
                    pthread_mutex_unlock(&mutex);
                }
                else if(send_flag == 2)
                {
                    printf("send_flag : %d\n", send_flag);
                    pthread_mutex_lock(&mutex);
                    write(fd, "2", 1);
                    send_flag = 0;
                    pthread_mutex_unlock(&mutex);
                }

            }

            if (poll_events.revents & POLLERR)
            {
                printf("통신 라인에 에러가 발생, 프로그램 종료");
                break;
            }
        }
    }
    close(fd);
    return 0;
}

void init_setting_gtk()
{
    entry = gtk_entry_new();
    fixed = gtk_fixed_new();

    gtk_container_add(GTK_CONTAINER(window), fixed);
    gtk_widget_set_size_request(entry, 150, -1);
    gtk_widget_show(entry);

    gtk_widget_set_uposition(window, 1, 1);

    label3 = gtk_label_new("습도");
    gtk_widget_set_size_request(label3, 85, 40);
    gtk_fixed_put(GTK_FIXED(fixed), label3, 560, 35);

    label4 = gtk_label_new("온도");
    gtk_widget_set_size_request(label4, 85, 40);
    gtk_fixed_put(GTK_FIXED(fixed), label4, 560, 135);

    label5 = gtk_label_new("조도");
    gtk_widget_set_size_request(label5, 85, 40);
    gtk_fixed_put(GTK_FIXED(fixed), label5, 560, 235);

    data[0] = gtk_label_new("0000");
    gtk_widget_set_size_request(data[0], 100, 40);
    gtk_fixed_put(GTK_FIXED(fixed), data[0], 655, 35);

    data[1] = gtk_label_new("0001");
    gtk_widget_set_size_request(data[1], 100, 40);
    gtk_fixed_put(GTK_FIXED(fixed), data[1], 655, 135);

    data[2] = gtk_label_new("0002");
    gtk_widget_set_size_request(data[2], 100, 40);
    gtk_fixed_put(GTK_FIXED(fixed), data[2], 655, 235);

    label1 = gtk_label_new("Mode");
    gtk_widget_set_size_request(label1, 100, 40);
    gtk_fixed_put(GTK_FIXED(fixed), label1, 15, 375);

    autoBtn = gtk_button_new_with_mnemonic("Auto");
    gtk_widget_set_size_request(autoBtn, 100, 40);
    gtk_fixed_put(GTK_FIXED(fixed), autoBtn, 125, 375);
    g_signal_connect(G_OBJECT(autoBtn), "clicked", G_CALLBACK(autoMode), NULL);

    manualBtn = gtk_button_new_with_mnemonic("Manual");
    gtk_widget_set_size_request(manualBtn, 100, 40);
    gtk_fixed_put(GTK_FIXED(fixed), manualBtn, 235, 375);
    g_signal_connect(G_OBJECT(manualBtn), "clicked", G_CALLBACK(manualMode), NULL);

    label2 = gtk_label_new("Control");
    gtk_widget_set_size_request(label2, 100, 40);
    gtk_fixed_put(GTK_FIXED(fixed), label2, 435, 370);

    openBtn = gtk_button_new_with_mnemonic("Open");
    gtk_widget_set_size_request(openBtn, 100, 40);
    gtk_fixed_put(GTK_FIXED(fixed), openBtn, 545, 375);
    g_signal_connect(G_OBJECT(openBtn), "clicked", G_CALLBACK(openHouse), NULL);

    closeBtn = gtk_button_new_with_mnemonic("Close");
    gtk_widget_set_size_request(closeBtn, 100, 40);
    gtk_fixed_put(GTK_FIXED(fixed), closeBtn, 655, 375);
    g_signal_connect(G_OBJECT(closeBtn), "clicked", G_CALLBACK(closeHouse), NULL);

    bgImg = xpm_label_box("house.png", NULL);
    gtk_widget_show(bgImg);
    gtk_container_add(GTK_CONTAINER(window), bgImg);
    gtk_widget_set_size_request(bgImg, 500, 260);
    gtk_fixed_put(GTK_FIXED(fixed), bgImg, 15, 15);

    gchar msg[1024];
    sprintf(msg, "%s\t/\t%s", nowCtl[ctlFlag], nowMode[modeFlag]);
    now = gtk_label_new(msg);
    gtk_widget_set_size_request(now, 500, 40);
    gtk_fixed_put(GTK_FIXED(fixed), now, 15, 280);
}

GtkWidget *xpm_label_box(gchar * xpm_filename, gchar * label_text)
{
    GtkWidget *box;
    GtkWidget *label;
    GtkWidget *image;

    box = gtk_hbox_new(FALSE, 0);
    gtk_container_set_border_width(GTK_CONTAINER(box), 2);

    image = gtk_image_new_from_file(xpm_filename);
    label = gtk_label_new(label_text);

    gtk_box_pack_start(GTK_BOX(box), image, FALSE, FALSE, 3);
    gtk_box_pack_start(GTK_BOX(box), label, FALSE, FALSE, 3);

    gtk_widget_show(image);
    gtk_widget_show(label);

    return box;
}

void autoMode(GtkWidget *frame, GdkEventButton *event)
{
    int index = 0;
    int flag = 0;

    for(index = 0; index < 3; index++)
    {
        if(integerData[index] > upperStandard[index] || integerData[index] < underStandard[index])
        {
            flag = 1;
            break;
        }
    }

    if(flag)
    {
        pthread_mutex_lock(&mutex);
        send_flag = 1;
        pthread_mutex_unlock(&mutex);
        ctlFlag = 1;
    }
    else
    {
        pthread_mutex_lock(&mutex);
        send_flag = 2;
        pthread_mutex_unlock(&mutex);
        ctlFlag = 0;
    }
    modeFlag = 0;

    changeMsg();
}

void manualMode(GtkWidget *frame, GdkEventButton *event)
{
    modeFlag = 1;
    changeMsg();
}

void openHouse(GtkWidget *frame, GdkEventButton *event)
{
    if (modeFlag == 0)
    {
        GtkWidget *dialog;
        dialog = gtk_message_dialog_new(GTK_WINDOW(window), GTK_DIALOG_MODAL, GTK_MESSAGE_INFO, GTK_BUTTONS_OK, "자동 모드에서는 작동 할 수 없습니다.");
        gtk_window_set_title(GTK_WINDOW(dialog), "Information");
        gtk_dialog_run(GTK_DIALOG(dialog));
        gtk_widget_destroy(dialog);

        return;
    }
    else
    {
        printf("clicked open\n");
        pthread_mutex_lock(&mutex);
        send_flag = 1;
        pthread_mutex_unlock(&mutex);
        ctlFlag = 1;
        changeMsg();
    }
}

void closeHouse(GtkWidget *frame, GdkEventButton *event)
{
    if (modeFlag == 0)
    {
        GtkWidget *dialog;
        dialog = gtk_message_dialog_new(GTK_WINDOW(window), GTK_DIALOG_MODAL, GTK_MESSAGE_INFO, GTK_BUTTONS_OK, "자동 모드에서는 작동 할 수 없습니다.");
        gtk_window_set_title(GTK_WINDOW(dialog), "Information");
        gtk_dialog_run(GTK_DIALOG(dialog));
        gtk_widget_destroy(dialog);

        return;
    }
    else
    {
        printf("clicked close\n");
        pthread_mutex_lock(&mutex);
        send_flag = 2;
        pthread_mutex_unlock(&mutex);
        ctlFlag = 0;
        changeMsg();
    }
}

void updateData()
{
    char* temp = NULL;
    gchar output[32] = { '\0' };
    int index = 0;

    temp = strtok(buf, "/");
    while(temp != NULL)
    {
        integerData[index] = atoi(temp);
        sprintf(output, "%s", temp);
        printf("%s\t",output);
        printf("index : %d\n",index);
        gtk_label_set_text((GtkLabel*)data[index++], output);
        temp = strtok(NULL, "/");
    }
}

void changeMsg()
{
    gchar msg[1024];
    sprintf(msg, "%s\t/\t%s", nowCtl[ctlFlag], nowMode[modeFlag]);
    gtk_label_set_text((GtkLabel*)now, msg);
} 
