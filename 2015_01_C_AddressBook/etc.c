#include "address_book.h"
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <stdio_ext.h>

int save_file(char *abk_filename, abk_t *p_address_book)
{
	int i, j, num; 

	num = p_address_book->size;

	/*file open*/
	Addressbook = fopen("address_book.txt", "a+");

	/*move the pointer*/
	fseek(Addressbook, 0, SEEK_SET);

	/*save the size*/
	fprintf(Addressbook, "%d#\n", p_address_book->size);
	for (i = 0; i < num; i++)
	{	
		/*save the name + phone number*/ 
		fprintf(Addressbook, "%s;PHONE:", p_address_book->contact_list[i].name);
		for (j = 0; j < PHONE_COUNT; j++)
		{
			fprintf(Addressbook, "%s;", p_address_book->contact_list[i].phone[j]);
		}
		fprintf(Addressbook, "\n");

		/*save the name + email address*/
		fprintf(Addressbook, "%s;EMAIL:", p_address_book->contact_list[i].name);
		for (j = 0; j < EMAIL_COUNT; j++)
		{
			fprintf(Addressbook, "%s;", p_address_book->contact_list[i].email[j]);
		}
		fprintf(Addressbook, "\n");
	}

	/*save the contents*/
	remove(abk_filename);
	rename("address_book.txt", abk_filename);
	return 0;	
}

int list_all_menu(abk_t *p_address_book)
{
	int i, j;
	int size = p_address_book->size;

	/*print the contents*/
	printf("***************************************************\n");
	printf("Total size : %d\n\n", size);
	for(i = 0; i < size; i++)
	{
		printf("#NO %d\n", (i + 1));
		printf("%s;PHONE:", p_address_book->contact_list[i].name );

		for(j = 0; j < PHONE_COUNT; j++)
		{
			printf("%s;", p_address_book->contact_list[i].phone[j]);
		}
		printf("\n");

		printf("%s;EMAIL:", p_address_book->contact_list[i].name);
		for(j = 0; j < EMAIL_COUNT; j++)
		{
			printf("%s;", p_address_book->contact_list[i].email[j]);
		}
		printf("\n");
	}
	printf("***************************************************\n\n");
	return 0;
}

int call_exit(char *abk_filename, abk_t *p_address_book)
{
	char cho;

	while(1)
	{
		__fpurge(stdin);
		printf("===================================================\n");
		printf("< Exit menu >\n");
		printf("	S : save changes and exit\n");
		printf("	D : discard changes\n");
		printf("	C : cancel\n");
		printf("===================================================\n\n");
		printf("Enter The Exit menu : ");
		__fpurge(stdin);
		scanf("%c", &cho);

		switch (cho)
		{
			case 'S':
			case 's': save_file(abk_filename, p_address_book);
				  free(p_address_book);
				  exit(0);

			case 'D':
			case 'd': free(p_address_book);
				  exit(1);

			case 'C':
			case 'c': return 0;

			default : printf("You entered wrong option\n");
		}
	}
}

int validate(abk_t *p_address_book, int opt, char *temp)
{

	int i,j;
	int size = p_address_book->size;

	switch (opt)
	{
		case 1: /*validating the name*/
			for(i = 0; i < size; i++)
			{
				if (strcmp(p_address_book->contact_list[i].name, temp) == 0)
				{
					return 1;
				}
			}
			return 0;

		case 2: /*validating the phone number*/
			for(i = 0; i < size; i++)
			{
				for(j = 0; j < PHONE_COUNT; j++)
				{
					if (strcmp(p_address_book->contact_list[i].phone[j], temp) == 0)
					{
						return 1;
					}
				}
			}
			return 0;

		case 3: /*validating the email address*/
			for(i = 0; i < size; i++)
			{
				for(j = 0; j < EMAIL_COUNT; j++)
				{
					if (strcmp(p_address_book->contact_list[i].email[j], temp) == 0)
					{
						return 1;
					}
				}
			}
			return 0;
	}

}
