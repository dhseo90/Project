#include "address_book.h"
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <stdio_ext.h>

int delete_contact_menu(abk_t *p_address_book)
{
	int index = 0;
	int i, j, k;
	int size = p_address_book->size;
	char cho;
	char target[NAME_SIZE];
	char null[PHONE_COUNT] = {'\0'};


	while(1)
	{
		printf("===================================================\n");
		printf("< Seach Delete Menu >\n");
		printf("	N : Seach by name\n");
		printf("	P : Seach by phone number\n");
		printf("	E : Search by email address\n");
		printf("	L : list all contacts\n");
		printf("	X : exit application\n");
		printf("===================================================\n\n");
		printf("Enter The Seach Delete Menu : ");
		__fpurge(stdin);
		scanf("%c", &cho);
		printf("\n");

		switch (cho)
		{
			case 'N':
			case 'n': index = 1;
				  if(search_component_menu(p_address_book, &index) == 0)
				  {
					  printf("Name is none...\n");
				  }
				  else
				  {
					  for(i = index; i < size; i++)
					  {
						  strcpy(p_address_book->contact_list[i].name, p_address_book->contact_list[i + 1].name);

						  for(j = 0; j < PHONE_COUNT; j++)
						  {
							  strcpy(p_address_book->contact_list[i].phone[j], p_address_book->contact_list[i+1].phone[j]);
						  }

						  for(j = 0; j < EMAIL_COUNT; j++)
						  {
							  strcpy(p_address_book->contact_list[i].email[j], p_address_book->contact_list[i+1].email[j]);
						  }

					  }
					  p_address_book->size -= 1;
					  size -= 1;
				  }
				  break;

			case 'P':
			case 'p': index = 2;
				  if(search_component_menu(p_address_book, &index) == 0)
				  {
					  printf("Phone number is none...\n");
				  }
				  else
				  {
					  for(i = index; i < size; i++)
					  {
						  strcpy(p_address_book->contact_list[i].name, p_address_book->contact_list[i + 1].name);

						  for(j = 0; j < PHONE_COUNT; j++)
						  {
							  strcpy(p_address_book->contact_list[i].phone[j], p_address_book->contact_list[i+1].phone[j]);
						  }

						  for(j = 0; j < EMAIL_COUNT; j++)
						  {
							  strcpy(p_address_book->contact_list[i].email[j], p_address_book->contact_list[i+1].email[j]);
						  }
					  }

					  p_address_book->size -= 1;
					  size -= 1;
				  }
				  break;
			case 'E':
			case 'e': index = 3;
				  if(search_component_menu(p_address_book, &index) == 0)
				  {
					  printf("Email is none...\n");
				  }
				  else
				  {
					  for(i = index; i < size; i++)
					  {
						  strcpy(p_address_book->contact_list[i].name, p_address_book->contact_list[i + 1].name);

						  for(j = 0; j < PHONE_COUNT; j++)
						  {
							  strcpy(p_address_book->contact_list[i].phone[j], p_address_book->contact_list[i+1].phone[j]);
						  }

						  for(j = 0; j < EMAIL_COUNT; j++)
						  {
							  strcpy(p_address_book->contact_list[i].email[j], p_address_book->contact_list[i+1].email[j]);
						  }

						  p_address_book->contact_list[i] = p_address_book->contact_list[i + 1];
					  }
					  p_address_book->size -= 1;
					  size -= 1;
				  }
				  break;
			case 'L':
			case 'l': list_all_menu(p_address_book);
				  break;

			case 'X':
			case 'x': main_menu(p_address_book);
				  break;
			default: printf("You entered wrong option\n");
		}
	}
}

