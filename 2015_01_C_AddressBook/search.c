#include "address_book.h"
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <stdio_ext.h>

int search_contact_menu(abk_t *p_address_book)
{
	int index;
	int i, j;
	char cho;
	int size = p_address_book->size;
	char target[NAME_SIZE];

	while(1)
	{
		__fpurge(stdin);
		printf("===================================================\n");
		printf("< Seach Component Menu >\n");
		printf("\tN : Seach by name\n");
		printf("\tP : Seach by phone number\n");
		printf("\tE : Search by email address\n");
		printf("\tL : list all contacts\n");
		printf("\tX : exit application\n");
		printf("===================================================\n\n");
		printf("Enter The Seach Component Menu : ");
		__fpurge(stdin);
		scanf("%c", &cho);

		switch (cho)
		{
			case 'n':
			case 'N': index = 1;
				  if (search_component_menu(p_address_book, &index) == 0)
				  {
					  printf("Name is none...\n");
				  }
				  else
				  {
					  printf("Success!!\n");
					  printf("***************************************************\n");
					  printf("#Person information : NO.%d\n", (index+1));

					  printf("%s;PHONE:", p_address_book->contact_list[index].name );
					  for(j = 0; j < PHONE_COUNT; j++)
					  {
						  printf("%s;", p_address_book->contact_list[index].phone[j]);
					  }
					  printf("\n");

					  printf("%s;EMAIL:", p_address_book->contact_list[index].name);
					  for(j = 0; j < EMAIL_COUNT; j++)
					  {
						  printf("%s;", p_address_book->contact_list[index].email[j]);
					  }
					  printf("\n");
					  printf("***************************************************\n");
				  }
				  break;

			case 'p':
			case 'P': index = 2;
				  if (search_component_menu(p_address_book, &index) == 0)
				  {
					  printf("Phone number is none...\n");
				  }
				  else
				  { 
					  printf("Success!!\n");
					  printf("***************************************************\n");
					  printf("#Person information : NO.%d\n", (index+1));

					  printf("%s;PHONE:", p_address_book->contact_list[index].name );
					  for(j = 0; j < PHONE_COUNT; j++)
					  {
						  printf("%s;", p_address_book->contact_list[index].phone[j]);
					  }
					  printf("\n");

					  printf("%s;EMAIL:", p_address_book->contact_list[index].name);
					  for(j = 0; j < EMAIL_COUNT; j++)
					  {
						  printf("%s;", p_address_book->contact_list[index].email[j]);
					  }
					  printf("\n");
					  printf("***************************************************\n");
				  }
				  break;

			case 'e':
			case 'E': index = 3;
				  if (search_component_menu(p_address_book, &index) == 0)
				  {
					  printf("Email is none...\n");
				  }
				  else
				  { 
					  printf("Success!!\n");
					  printf("***************************************************\n");
					  printf("#Person information : NO.%d\n", (index+1));

					  printf("%s;PHONE:", p_address_book->contact_list[index].name );
					  for(j = 0; j < PHONE_COUNT; j++)
					  {
						  printf("%s;", p_address_book->contact_list[index].phone[j]);
					  }
					  printf("\n");

					  printf("%s;EMAIL:", p_address_book->contact_list[index].name);
					  for(j = 0; j < EMAIL_COUNT; j++)
					  {
						  printf("%s;", p_address_book->contact_list[index].email[j]);
					  }
					  printf("\n");
					  printf("***************************************************\n");
				  }
				  break;
			case 'L':
			case 'l': list_all_menu(p_address_book);
				  break;

			case 'X':
			case 'x': main_menu(p_address_book);
				  break;
			default : printf("You entered wrong option\n");
		}
	}
}

int search_component_menu(abk_t *p_address_book, int *index)
{
	int i,j;
	char target[100];
	int size = p_address_book->size;

	switch (*index)
	{
		case 1: printf("Enter the name : ");
			__fpurge(stdin);
			scanf("%s", target);
			printf("\n");
			for(i = 0; i < size; i++)
			{
				if (strcmp(p_address_book->contact_list[i].name, target) == 0)
				{
					*index = i;
					return 1;
				}
			}
			return 0;

		case 2: printf("Enter the phone number : ");
			__fpurge(stdin);
			scanf("%s", target);
			printf("\n");

			for(i = 0; i < size; i++)
			{
				for(j = 0; j < PHONE_COUNT; j++)
				{
					if (strcmp(p_address_book->contact_list[i].phone[j], target) == 0)
					{
						*index = i;
						return 1;
					}
				}
			}
			return 0;

		case 3: printf("Enter the email address : ");
			__fpurge(stdin);
			scanf("%s", target);
			printf("\n");

			for(i = 0; i < size; i++)
			{
				for(j = 0; j < EMAIL_COUNT; j++)
				{
					if (strcmp(p_address_book->contact_list[i].email[j], target) == 0)
					{
						*index = i;
						return 1;
					}
				}
			}
			return 0;
	}
}

