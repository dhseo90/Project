#include "address_book.h"
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <stdio_ext.h>

int main_menu(abk_t *p_address_book)
{
	char cho;
	char file_name[20] = "addressbook.txt";
	char *filename = file_name;

	while(1)
	{
		printf("===================================================\n");
		__fpurge(stdin);
		printf("< Main menu >\n");
		printf("\tA : add contact\n");
		printf("\tE : edit contact\n");
		printf("\tS : search contact\n");
		printf("\tL : list all contacts\n");
		printf("\tD : delete contact\n");
		printf("\tV : save contact information\n");
		printf("\tX : exit application\n");
		printf("===================================================\n\n");
		printf("Enter the Main menu : ");
		scanf("%c", &cho);

		/*Call each function*/
		switch (cho)	
		{
			case 'A':
			case 'a': add_contact_menu(p_address_book);
				  break;		
			case 'E':
			case 'e': edit_contact_menu(p_address_book);
				  break;	
			case 'S':
			case 's': search_contact_menu(p_address_book);
				  break;	
			case 'L':
			case 'l': list_all_menu(p_address_book);
				  break;
			case 'D':
			case 'd': delete_contact_menu(p_address_book);
				  break;
			case 'V':
			case 'v': save_file(filename, p_address_book);
				  break;
			case 'X':
			case 'x': call_exit(filename, p_address_book);
			default: printf("You entered wrong option\n");
		}
	}
}

int load_file(abk_t *p_address_book)
{
	char w_temp[100];
	char temp[100];
	char *p;
	int i, num;
	char null[3] = {'\0'};

	/*file open*/
	Addressbook = fopen("addressbook.txt", "a+");

	/*call the file contents*/
	fscanf(Addressbook, "%s", w_temp);

	if (strcmp(w_temp, "\0") == 0)
	{
		return 0;
	}

	/*store the file size*/
	p = strtok(w_temp, "#");
	strcpy(temp, p);
	p_address_book->size = atoi(temp);

	num = 0;

	while(num < p_address_book->size)
	{
		/*store the name*/
		fscanf(Addressbook, "%s", w_temp);
		p = strtok(w_temp, ";");

		strcpy(p_address_book->contact_list[num].name, p);
		p = strtok(NULL, ":");
		strcpy(temp, p);

		/*store the phone numbers*/
		if (strcmp(temp, "PHONE") == 0)
		{
			while (1)
			{
				p = strtok(NULL, ";");
				if (p == '\0')
				{
					break;
				}
				strcpy(temp, p);

				i = 0;
				while (1)
				{
					if (p == '\0')
					{
						break;
					}
					strcpy(p_address_book->contact_list[num].phone[i], p);
					i++;

					p = strtok(NULL, ";");
				}

			}
		}


		fscanf(Addressbook, "%s", w_temp);
		p = strtok(w_temp, ";");
		p = strtok(NULL, ":");
		strcpy(temp, p);

		/*store the email addresses*/
		if (strcmp(temp, "EMAIL") == 0)
		{
			while (1)
			{
				p = strtok(NULL, ";");
				if (p == '\0')
				{
					break;
				}
				strcpy(temp, p);

				i = 0;
				while (1)
				{
					if (p == '\0')
					{
						break;
					}
					strcpy(p_address_book->contact_list[num].email[i], p);
					i++;

					p = strtok(NULL, ";");
				}
			}
		}
		num++;
	}
	return 0;
}

