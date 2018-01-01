/*
 * File:   menu.c
 * Author: SKHU 2015 Microcontroller Project Team1(Seo Dong Hyeong, Kim Min Kyung, Kim Hye Jin, Kim Ji Hyeon)
 *
 * Date 2015. 05. 25 ~ 2015. 05. 28
 */

#include "main.h"

extern unsigned short global_count;
extern unsigned long data[6];

void moving_glow_data(void)/* left to right & right to left */
{
    unsigned char i = 0;
    unsigned long mask = 0xFFFFFFFE;

    /* right to left off */
    while (i < 32)
    {
        T0IE = 1;
        if (global_count != TIME5) /* during global_count ups till 50, this is working */
        {
            display(data[0] & mask, 0);
            display(data[1] & mask, 1);
            display(data[2] & mask, 2);
            display(data[3] & mask, 3);
            display(data[4] & mask, 4);
            display(data[5] & mask, 5);
        }

        if (global_count == TIME5)
        {
            i++;
            mask <<= 1; /* shift left data 1 bit */
            global_count = 0;/* initialize global_count */
        }
    }

    i = 0;
    mask = 0x00000001;

    /* right to left on */
    while (i < 31)
    {
        T0IE = 1;
        if (global_count != TIME5) /* during global_count ups till 50, this is working */
        {
            display(data[0] & mask, 0);
            display(data[1] & mask, 1);
            display(data[2] & mask, 2);
            display(data[3] & mask, 3);
            display(data[4] & mask, 4);
            display(data[5] & mask, 5);
        }

        if (global_count == TIME5)
        {
            i++;
            mask = (mask << 1) + 1;/* shift left data 1 bit and plus 1*/
            global_count = 0;/* initialize global_count */
        }
    }

    i = 0;
    mask = 0x7FFFFFFF; /* initialize mask */

    /**/
    while (i < 32)
    {
        T0IE = 1;
        if (global_count != TIME5) /* during global_count ups till 50, this is working */
        {
            display(data[0] & mask, 0);
            display(data[1] & mask, 1);
            display(data[2] & mask, 2);
            display(data[3] & mask, 3);
            display(data[4] & mask, 4);
            display(data[5] & mask, 5);
        }

        if (global_count == TIME5)
        {
            i++;
            mask >>= 1;
            global_count = 0;
        }
    }

    i = 0;
    mask = 0x80000000;

    /* left to right on */
    while (i < 31)
    {
        T0IE = 1;
        if (global_count != TIME5) /* during global_count ups till 50, this is working */
        {
			display(data[0] & mask, 0);
            display(data[1] & mask, 1);
            display(data[2] & mask, 2);
            display(data[3] & mask, 3);
            display(data[4] & mask, 4);
            display(data[5] & mask, 5);
        }

        if (global_count == TIME5)
        {
            i++;
            mask = (mask >> 1) + 0x80000000;
            global_count = 0;
        }
    }
}

unsigned long moving_data(short i)
{
    switch (i)
    {
		/* according to passed i variable, 
		   it appears always starting with data[0] */ 
        case 0:
        case 1:
        case 2:
        case 3:
        case 4:
            return 0x00000000;
            break;

        case 5:
            return data[0];
            break;
        case 6:
            return data[1];
            break;
        case 7:
            return data[2];
            break;
        case 8:
            return data[3];
            break;
        case 9:
            return data[4];
            break;
        case 10:
            return data[5];
            break;
        case 11:
        case 12:
        case 13:
        case 14:
        case 15:
            return 0x00000000;
            break;
    }
}

void moving_up_down(void)
{
    short i = 0;

    while (i != 10) /* DOWN to UP */
    {
        T0IE = 1;
        if (global_count != TIME2)
        {
			/* while TIME2, Go up 1 row using moving_data function */ 
            display(moving_data(i), 0);
            display(moving_data(i + 1), 1);
            display(moving_data(i + 2), 2);
            display(moving_data(i + 3), 3);
            display(moving_data(i + 4), 4);
            display(moving_data(i + 5), 5);
        }
        if (global_count == TIME2)
        {
            i++;
            global_count = 0;
        }
    }

    while (i != -1)	/* DOWN to UP */
    {
        T0IE = 1;
        if (global_count != TIME2)
        {
			/* while TIME2, Go down 1 row using moving_data function */ 
            display(moving_data(i), 0);
            display(moving_data(i + 1), 1);
            display(moving_data(i + 2), 2);
            display(moving_data(i + 3), 3);
            display(moving_data(i + 4), 4);
            display(moving_data(i + 5), 5);
        }
        if (global_count == TIME2)
        {
            i--;
            global_count = 0;
        }
    }
}

void disappear_scroll_left_right(void)
{
    short i = 0;

    while (i != 32)
    {
        T0IE = 1;
        if (global_count != TIME1)
        {
			/* while TIME1, shift 1 righdt */ 
            display((data[0] >> i), 0);
            display((data[1] >> i), 1);
            display((data[2] >> i), 2);
            display((data[3] >> i), 3);
            display((data[4] >> i), 4);
            display((data[5] >> i), 5);
        }
        else if (global_count == TIME1)
        {
            i++;
            global_count = 0;
        }
    }
}

void disappear_scroll_right_left(void)
{
    short i = 0;

    while (i != 32)
    {
        T0IE = 1;
        if (global_count != TIME1)
        {
			/* while TIME1, shift 1 left */ 
            display((data[0] << i), 0);
            display((data[1] << i), 1);
            display((data[2] << i), 2);
            display((data[3] << i), 3);
            display((data[4] << i), 4);
            display((data[5] << i), 5);
        }
        else if (global_count == TIME1)
        {
            i++;
            global_count = 0;
        }
    }
}

void appear_scroll_right_left(void)
{
    short i = 30;

    while (i != -1)
    {
        T0IE = 1;
        if (global_count != TIME1)
        {
			/* while TIME1, shift 1 right */ 
            display((data[0] >> i), 0);
            display((data[1] >> i), 1);
            display((data[2] >> i), 2);
            display((data[3] >> i), 3);
            display((data[4] >> i), 4);
            display((data[5] >> i), 5);
        }
        else if (global_count == TIME1)
        {
            i--;
            global_count = 0;
        }
    }
}

void appear_scroll_left_right(void)
{
    short i = 30;

    while (i != -1)
    {
        T0IE = 1;
        if (global_count != TIME1)
        {
			/* while TIME1, shift 1 left */ 
            display((data[0] << i), 0);
            display((data[1] << i), 1);
            display((data[2] << i), 2);
            display((data[3] << i), 3);
            display((data[4] << i), 4);
            display((data[5] << i), 5);
        }
        else if (global_count == TIME1)
        {
            i--;
            global_count = 0;
        }
    }
}

void scroll_left_right(void)
{
	/* First moving from right end, And then dissapear */
    appear_scroll_right_left();		
    disappear_scroll_right_left();
	/* First moving from left end, And then dissapear */
    appear_scroll_left_right();
    disappear_scroll_left_right();
}

void dim_led(unsigned char duty)
{
    unsigned short count = 0;
    unsigned char i;

    for (i = 0; i < 6; i++)
    {
        display(data[i], i);    /* During duty time, LED ON */
    }
    while (count++ <= duty);

    for (i = 0; i < 6; i++)
    {
        display(0, i);  /* During 100-duty time, LED OFF */
    }
    while (count++ <= FULL_BRIGHT);
}

void wave_led(void)
{
    unsigned char i;
    static unsigned short count = 0;

    T0IE = 1;
    if (global_count != TIME0)
    {
        for (i = 0; i < 6; i++)
        {
            display(data[i], i); /* During duty time, 10% LED ON */
        }
        while (count++ <= LOW_BRIGHT);

        for (i = 0; i < 6; i++)
        {
            display(data[i] & mask2, i); /* During duty time, 40% LED ON */
        }
        while (count++ <= MID_BRIGHT);

        for (i = 0; i < 6; i++)
        {
            display(data[i] & mask1, i); /* 100% LED ON */
        }
        while (count++ <= FULL_BRIGHT);
        /* Exection of mask1 starting position */
        if (mask1 == 3)
        {
            mask2 = 0x0000000F; 
        }
        if (mask1 == 6)
        {
            mask2 = 0x0000001F;
        }
        if (mask1 == 0x0000000C)
        {
            mask2 = 0x0000003F;
        }
    }
    if (global_count == TIME0)
    {
		/* After TIME0 flew, Shift left mask1,2 */
        mask1 <<= 1;
        if (mask1 == 0)
        {
            mask1 = 0x00000003;
        }
        mask2 <<= 1;
        if (mask2 == 0)
        {
            mask2 = 0x0000000F;
        }
        global_count = 0;
    }
}

void led_on(void)
{
	/* Normally display a string */
    display(data[0], 0);
    display(data[1], 1);
    display(data[2], 2);
    display(data[3], 3);
    display(data[4], 4);
    display(data[5], 5);
}

void led_off(void)
{
	/* Normally all LED OFF */
    display(0x00000000, 0);
    display(0x00000000, 1);
    display(0x00000000, 2);
    display(0x00000000, 3);
    display(0x00000000, 4);
    display(0x00000000, 5);
}

void led_opposition(void)
{
    unsigned short flag = 1;

	/* First Background LED ON, All characters LED OFF because of initiallizing flag = 1 */
    while (flag == 1)
    {
        T0IE = 1;
        if (global_count != TIME5)
        {
            display(~data[0], 0);
            display(~data[1], 1);
            display(~data[2], 2);
            display(~data[3], 3);
            display(~data[4], 4);
            display(~data[5], 5);
        }
        else if (global_count == TIME5)
        {
            flag = 2;
            global_count = 0;
        }
    }
	/* Alternately All characters LED ON, Background OFF Because of flag setting */
    while (flag == 2)
    {
        T0IE = 1;
        if (global_count != TIME5)
        {
            display(data[0], 0);
            display(data[1], 1);
            display(data[2], 2);
            display(data[3], 3);
            display(data[4], 4);
            display(data[5], 5);
        }
        else if (global_count == TIME5)
        {
            flag = 1;
            global_count = 0;
        }
    }
}

void divide_out(void)
{
    unsigned long temp1 = 0xFFFF0000; /* half left LED is ON*/
    unsigned long temp2 = 0x0000FFFF; /* half right LED is ON */
    unsigned long
    short i = 1, j;

    while (i < 16)
    {
        T0IE = 1;
        if (global_count != TIME2)
        {
			/* Doing OR operation with each temp1,2 half and half, and Shift(spread) to the both ends */
            display(((temp1 & ((data[0] & temp1) << i)) | (temp2 & ((data[0] & temp2) >> i))), 0);
            display(((temp1 & ((data[1] & temp1) << i)) | (temp2 & ((data[1] & temp2) >> i))), 1);
            display(((temp1 & ((data[2] & temp1) << i)) | (temp2 & ((data[2] & temp2) >> i))), 2);
            display(((temp1 & ((data[3] & temp1) << i)) | (temp2 & ((data[3] & temp2) >> i))), 3);
            display(((temp1 & ((data[4] & temp1) << i)) | (temp2 & ((data[4] & temp2) >> i))), 4);
            display(((temp1 & ((data[5] & temp1) << i)) | (temp2 & ((data[5] & temp2) >> i))), 5);
        }
        if (global_count == TIME2)
        {
            i++;
            global_count = 0;
        }
    }
}

void divide_in(void)
{
    unsigned long temp1 = 0xFFFF0000;
    unsigned long temp2 = 0x0000FFFF;
    unsigned long
    short i = 15;

    while (i >= 0)
    {
        T0IE = 1;
        if (global_count != TIME2)
        {
			/* Doing OR operation with each temp1,2 half and half, and Shift(gather) to the center */
            display(((temp1 & ((data[0] & temp1) << i)) | (temp2 & ((data[0] & temp2) >> i))), 0);
            display(((temp1 & ((data[1] & temp1) << i)) | (temp2 & ((data[1] & temp2) >> i))), 1);
            display(((temp1 & ((data[2] & temp1) << i)) | (temp2 & ((data[2] & temp2) >> i))), 2);
            display(((temp1 & ((data[3] & temp1) << i)) | (temp2 & ((data[3] & temp2) >> i))), 3);
            display(((temp1 & ((data[4] & temp1) << i)) | (temp2 & ((data[4] & temp2) >> i))), 4);
            display(((temp1 & ((data[5] & temp1) << i)) | (temp2 & ((data[5] & temp2) >> i))), 5);
        }

        if (global_count == TIME2)
        {
            i--;
            global_count = 0;
        }
    }
}

void divide_out_in(void)
{
    divide_out();	/* Divide from middle to both ends */
    divide_in();	/* Combine from middle to both ends */
}

void toggle_speed_one(void)
{
    unsigned short flag = 0;

    while (flag != 1)	/* when flag = 0, it working */
    {
        T0IE = 1;
		/* while global_count < TIME8, All Characters ON */
        if (global_count != TIME8)
        {
            display(data[0], 0);
            display(data[1], 1);
            display(data[2], 2);
            display(data[3], 3);
            display(data[4], 4);
            display(data[5], 5);
        }
        else if (global_count == TIME8)
        {
            flag = 1;
            global_count = 0;
        }
    }
    while (flag != 2)	/* when flag = 1, it working */
    {
        T0IE = 1;
		/* while global_count < TIME5, All LEDs OFF */
        if (global_count != TIME8)
        {
            display(0x00000000, 0);
            display(0x00000000, 1);
            display(0x00000000, 2);
            display(0x00000000, 3);
            display(0x00000000, 4);
            display(0x00000000, 5);
        }
        else if (global_count == TIME8)
        {
            flag = 2;
            global_count = 0;
        }
    }
}

void toggle_speed_two(void)
{
    unsigned short flag = 0;

    while (flag != 1)	/* when flag = 0, it working */
    {
        T0IE = 1;
		/* while global_count < TIME5, All Characters ON */
        if (global_count != TIME5)
        {
            display(data[0], 0);
            display(data[1], 1);
            display(data[2], 2);
            display(data[3], 3);
            display(data[4], 4);
            display(data[5], 5);
        }
        else if (global_count == TIME5)
        {
            flag = 1;
            global_count = 0;
        }
    }
    while (flag != 2)	/* when flag = 1, it working */
    {
        T0IE = 1;
		/* while global_count < TIME5, All LEDs OFF */
        if (global_count != TIME5)
        {
            display(0x00000000, 0);
            display(0x00000000, 1);
            display(0x00000000, 2);
            display(0x00000000, 3);
            display(0x00000000, 4);
            display(0x00000000, 5);
        }
        else if (global_count == TIME5)
        {
            flag = 2;
            global_count = 0;
        }
    }
}

void toggle_speed_three(void)
{
    unsigned short flag = 0;

    while (flag != 1) /* when flag = 0, it working */
    {
        T0IE = 1;
		/* while global_count < TIME3, All Characters ON */
        if (global_count != TIME3)
        {
            display(data[0], 0);
            display(data[1], 1);
            display(data[2], 2);
            display(data[3], 3);
            display(data[4], 4);
            display(data[5], 5);
        }
        else if (global_count == TIME3)
		{
            flag = 1; /* make flag as 1 */
            global_count = 0; /* initialize global_count */
        }
    }
    while (flag != 2) /* when flag = 1, it working */
    {
        T0IE = 1;
		/* while global_count < TIME3, All LEDs OFF */
		if (global_count != TIME3)
        {
            display(0x00000000, 0);
            display(0x00000000, 1);
            display(0x00000000, 2);
            display(0x00000000, 3);
            display(0x00000000, 4);
            display(0x00000000, 5);
        }
        else if (global_count == TIME3)
        {
            flag = 2;
            global_count = 0;
        }
    }
}

void toggle_speed(void)
{
    toggle_speed_one();
    toggle_speed_two()
    toggle_speed_three();
}

void toggle(void)
{
    unsigned long mask = 0x3E03E03E;
    unsigned short flag = 0;

    while (flag != 1)
    {
        T0IE = 1;
        if (global_count != TIME1) /* during global_count ups till 10, this working */
        {
            /* LED located in odd position ON & even position OFF */
            display(data[0] & mask, 0);
            display(data[1] & mask, 1);
            display(data[2] & mask, 2);
            display(data[3] & mask, 3);
            display(data[4] & mask, 4);
            display(data[5] & mask, 5);
        }
        if (global_count == TIME1)
        {
            flag = 1;
            global_count = 0;
        }
    }
    while (flag != 2)
    {
        T0IE = 1;
        if (global_count != TIME1) /* during global_count ups till 10, this is working */
        {
            /* LED located in even position ON & odd position OFF */
            display(data[0] & ~mask, 0);
            display(data[1] & ~mask, 1);
            display(data[2] & ~mask, 2);
            display(data[3] & ~mask, 3);
            display(data[4] & ~mask, 4);
            display(data[5] & ~mask, 5);
        }
        if (global_count == TIME1)
        {
            flag = 2;
            global_count = 0;
        }
    }
}

