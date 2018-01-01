#include "address_book.h"
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <stdio_ext.h>

int edit_contact_menu(abk_t *p_address_book)
{
	int index; /* find index */
	int i, j; /* for use for loop */
	char cho; /* choice work */
	int size = p_address_book -> size;
	char target[NAME_SIZE];
	
	/* print Edit Contact Menu */
	while(1)
	{
		printf("===================================================\n");
		printf("< Edit Contact Menu >\n");
		printf("\tN : Seach by name\n");
		printf("\tP : Seach by phone number\n");
		printf("\tE : Search by email address\n");
		printf("\tL : list all contacts\n");
		printf("\tX : exit application\n");
		printf("===================================================\n\n");
		printf("Enter the Search Edit menu : ");
		__fpurge(stdin);
		scanf("%c", &cho);

		switch (cho)
		{
			case 'n':
			case 'N': index = 1; /* Using -> Search_by_name */ 
				  if (search_component_menu(p_address_book, &index) == 0)
				  {
					  printf("Name is none...\n");
				  }
				  else
				  {
					  /* check -> find information */
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
					 
					  /* if -> find success -> call modify function */
					  modification_menu(p_address_book, index);
				  }

				  break;

			case 'p':
			case 'P': index = 2; /* Using -> Search_by_phone nunber */
				  if (search_component_menu(p_address_book, &index) == 0)
				  {
					  printf("Phone number is none...\n");
				  }
				  else
				  { 
					  /* check -> find information */
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
					  
					  /* if -> find success -> call modify function */
					  modification_menu(p_address_book, index);
				  }

				  break;

			case 'e':
			case 'E': index = 3; /* using -> search_by_email address */
				  if (search_component_menu(p_address_book, &index) == 0)
				  {
					  printf("Email is none...\n");
				  }
				  else
				  { 
					  /* check -> find information */
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
					  /* if -> find success -> call modify function */
					  modification_menu(p_address_book, index);
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

int modification_menu(abk_t *p_address_book, int index)
{
	char cho;
	
	/* print modification_menu */
	while(1)
	{
		printf("===================================================\n");
		printf("The Print menu\n");
		printf("\t C : Modify contact name\n");
		printf("\t P : Add, modify or delete phone number\n");
		printf("\t E : Add, modify or delete email address\n");
		printf("\t X : Exit to edit contact menu\n");
		printf("===================================================\n\n");
		printf("Enter the Print menu : ");
		__fpurge(stdin);
		scanf("%c", &cho);
		
		/* choice -> modify work */
		switch(cho)
		{	
			case 'c' :
			case 'C' : modify_contact(p_address_book, index);
				   break;
			case 'p' :
			case 'P' : modify_phone(p_address_book, index);
				   break;
			case 'e' :
			case 'E' : modify_email_address(p_address_book, index);
				   break;
			case 'X' :
			case 'x' : return 0;
			default : printf("You entered wrong option\n");
		}		
	}
}

int modify_phone(abk_t *p_address_book, int index)
{
	while(1)
	{
	        /* print modify_phone menu*/
		char cho;
		printf("===================================================\n");
		printf("The Print choice : \n");
		printf("\tA : Add phone number\n");
		printf("\tM : Modify phone number\n");
		printf("\tD : Delete phone number\n");
		printf("\tX : Exite to modification menu\n");
		printf("===================================================\n\n");
		printf("Enter The Print choice menu : ");
		__fpurge(stdin);
		scanf("%c", &cho);
		
		/* choice add, modify, delete */
		switch(cho)
		{
			case 'a' :
			case 'A' : phone_add(p_address_book, index);
				   break;
			case 'm' :
			case 'M' : phone_modi(p_address_book, index);
				   break;
			case 'd' :
			case 'D' : phone_del(p_address_book, index);
				   break;
			case 'x' :
			case 'X' : return 0;
				   break;
			default : printf("You entered wrong option\n");
		}
	}
}

int phone_add(abk_t *p_address_book, int index)
{
	char add[PHONE_NUMBER_SIZE];
	int opt;
	int i;
	
	/* input -> change number */
	printf("add new phone number to add : ");
	__fpurge(stdin);
	scanf("%s", add);
	printf("\n"); 	

	opt = 2;

	/* check -> Exist -> same number */
	if (validate(p_address_book, opt, add) == 1)
	{
		printf("[ WARNING ] :: %s is already exist!\n\n", add);
		return 0;		
	}
	
	/* if phone_count is full */
	if(strcmp(p_address_book -> contact_list[index].phone[PHONE_COUNT - 1],"\0") != 0)
	{
		printf("Memory is full...\n\n");
		return 0;
	}
	
	/* add -> phone number */
	for(i = 0; i < PHONE_COUNT; i++)
	{
		if(strcmp(p_address_book -> contact_list[index].phone[i],"\0") == 0)
		{
			strcpy(p_address_book -> contact_list[index].phone[i], add);
			break;
		}


	}

	/* check -> add print */
	printf("\nbefore add index : %s\n", p_address_book -> contact_list[index].phone[i-1]);
	printf("add index : %s\n", p_address_book -> contact_list[index].phone[i]);
}

int phone_modi(abk_t *p_address_book, int index)
{
	int i, opt;

	/* if phone number is not exist */
	if(strcmp(p_address_book -> contact_list[index].phone[0],"\0") == 0)
	{
		printf("Phone number is none...\n");
		return 0;
	}
	
	/* print phone number */
	for(i = 0; i < PHONE_COUNT; i++)
	{	

		if(strcmp(p_address_book -> contact_list[index].phone[i],"\0") != 0)
		{
			printf("phone[%d] : %s\n",i, p_address_book -> contact_list[index].phone[i]);
		}
	}

	char modi[PHONE_NUMBER_SIZE];
	int record;
	printf("\nEnter the current phone record : ");
	__fpurge(stdin);
	scanf("%d", &record);
	printf("\n");


	printf("Enter the change phone number : ");
	__fpurge(stdin);
	scanf("%s", modi);
	printf("\n");

	opt = 2;

	/* check -> Exist -> same information */
	if (validate(p_address_book, opt, modi) == 1)
	{
		printf("[ WARNING ] :: %s is already exist!\n\n", modi);
		return 0;		
	}

	printf("before modi = %s\n", p_address_book -> contact_list[index].phone[record]);

	strcpy(p_address_book -> contact_list[index].phone[record], modi);
	printf("after modi = %s\n", p_address_book -> contact_list[index].phone[record]);
}

int phone_del(abk_t *p_address_book, int index)
{
	/* if phone number is not exist */
	if(strcmp(p_address_book -> contact_list[index].phone[0], "\0") == 0)
	{
		printf("Phone number is none...\n");
		return 0;
	}

	int i;

	/* print phone nunmber */
	for(i = 0; i < PHONE_COUNT; i++)
	{
		if(strcmp(p_address_book -> contact_list[index].phone[i], "\0") != 0)
		{
			printf("phone[%d] : %s\n", i, p_address_book -> contact_list[index].phone[i]);
		}
	}

	int record;
	printf("\nEnter the delete phone record: : ");
	__fpurge(stdin);
	scanf("%d", &record);

	/* phone number delete */
	if (record == PHONE_COUNT -1)
	{
		strcpy(p_address_book -> contact_list[index].phone[PHONE_COUNT-1], "\0");
	}
	else
	{
		/* pull the phone number */
		for(i = record; i < PHONE_COUNT; i++)
		{

			strcpy(p_address_book -> contact_list[index].phone[i], p_address_book -> contact_list[index].phone[i+1]);
		}
		
		/* last array is garbage value so, change -> '\0' */
		strcpy(p_address_book -> contact_list[index].phone[PHONE_COUNT-1], "\0");
	}

	printf("\nAfter delete \n");
	for(i = 0; i < PHONE_COUNT; i++)
	{
		if(strcmp(p_address_book -> contact_list[index].phone[i], "\0") != 0)
		{
			printf("%d : %s\n", i, p_address_book -> contact_list[index].phone[i]);
		}
	}

}

int modify_email_address(abk_t *p_address_book, int index)
{
	char cho;
	while(1)
	{
		/* print -> modify email menu */
		printf("===================================================\n");
		printf("The Print menu \n");
		printf("\tA : Add email address\n");
		printf("\tM : Modify email address\n");
		printf("\tD : Delete email address\n");
		printf("\tX : Exite to modification menu\n");
		printf("===================================================\n\n");
		printf("Enter The Print menu : ");
		__fpurge(stdin);
		scanf("%c", &cho);
		
		/* choice -> add, modify, delete */
		switch(cho)
		{
			case 'a' :
			case 'A' : email_add(p_address_book, index);
				   break;
			case 'm' :
			case 'M' : email_modi(p_address_book, index);
				   break;
			case 'd' :
			case 'D' : email_del(p_address_book, index);
				   break;
			case 'x' :
			case 'X' : return 0;
				   break;		
			default : printf("You entered wrong option\n");
		}	
	}	
}

int email_add(abk_t *p_address_book, int index)
{
	char add[EMAIL_ADDRESS_SIZE];
	int opt;

	printf("\nadd new email to add : ");
	__fpurge(stdin);
	scanf("%s", add);
	printf("\n");

	int i;

	opt = 3;

	/* check -> Exist -> same information */
	if (validate(p_address_book, opt, add) == 1)
	{
		printf("[ WARNING ] :: %s is already exist!\n\n", add);
		return 0;		
	}

	/* if email_count is full */
	if(strcmp(p_address_book -> contact_list[index].email[EMAIL_COUNT - 1],"\0") != 0)
	{
		printf("Memory is full...\n\n");
		return 0;
	}
	
	/* add address */
	for(i = 0; i < EMAIL_COUNT; i++)
	{
		if(strcmp(p_address_book -> contact_list[index].email[i],"\0") == 0)
		{
			strcpy(p_address_book -> contact_list[index].email[i], add);
			break;
		}


	}
	printf("\nbefore add index : %s\n", p_address_book -> contact_list[index].email[i-1]);
	printf("add index : %s\n", p_address_book -> contact_list[index].email[i]);
}

int email_modi(abk_t *p_address_book, int index)
{
	int i, opt;

	/* if email is not exist */
	if(strcmp(p_address_book -> contact_list[index].email[0],"\0") == 0)
	{
		printf("Phone number is none...\n");
		return 0;
	}

	/* print all email address */
	for(i = 0; i < EMAIL_COUNT; i++)
	{	

		if(strcmp(p_address_book -> contact_list[index].email[i],"\0") != 0)
		{
			printf("email[%d] : %s\n",i, p_address_book -> contact_list[index].email[i]);
		}
	}

	char modi[EMAIL_ADDRESS_SIZE];
	int record;
	printf("\nEnter the current email record : ");
	__fpurge(stdin);
	scanf("%d", &record);

	printf("Enter the change email address : ");
	__fpurge(stdin);
	scanf("%s", modi);
	opt = 3;

	/* check -> Exist -> same information */
	if (validate(p_address_book, opt, modi) == 1)
	{
		printf("[ WARNING ] :: %s is already exist!\n\n", modi);
		return 0;		
	}

	printf("\nbefore modi = %s\n", p_address_book -> contact_list[index].email[record]);

	strcpy(p_address_book -> contact_list[index].email[record], modi);
	printf("after modi = %s\n", p_address_book -> contact_list[index].email[record]);
}


int email_del(abk_t *p_address_book, int index)
{
	/* if email is not exist */
	if(strcmp(p_address_book -> contact_list[index].phone[0], "\0") == 0)
	{
		printf("Phone number is none...\n");
		return 0;
	}

	int i;

	/* print -> exist -> email address */
	for(i = 0; i < EMAIL_COUNT; i++)
	{
		if(strcmp(p_address_book -> contact_list[index].email[i], "\0") != 0)
		{
			printf("email[%d] : %s\n",i, p_address_book -> contact_list[index].email[i]);
		}
	}

	int record;
	printf("\nEnter the delete email record : ");
	__fpurge(stdin);
	scanf("%d", &record);

	/* delete email address */
	if (record == EMAIL_COUNT -1)
	{
		strcpy(p_address_book -> contact_list[index].email[EMAIL_COUNT-1], "\0");
	}
	else
	{
		for(i = record; i < EMAIL_COUNT; i++)
		{

			strcpy(p_address_book -> contact_list[index].email[i], p_address_book -> contact_list[index].email[i+1]);
		}

		strcpy(p_address_book -> contact_list[index].email[EMAIL_COUNT-1], "\0");
	}
	
	/* check -> after delete */
	printf("\nAfter delete \n");
	for(i = 0; i < EMAIL_COUNT; i++)
	{
		if(strcmp(p_address_book -> contact_list[index].email[i], "\0") != 0)
		{
			printf("%d : %s\n", i, p_address_book -> contact_list[index].email[i]);
		}
	}
}

int modify_contact(abk_t *p_address_book, int index)
{
	char c_name[NAME_SIZE];
	int opt;
	__fpurge(stdin);
	printf("\nbeforename = %s\n", p_address_book -> contact_list[index].name);
	printf("Enter the change name : ");
	__fpurge(stdin);
	scanf("%s", c_name);
	printf("\n");

	opt = 1;

	/* check -> Exist -> same information */
	if (validate(p_address_book, opt, c_name) == 1)
	{
		printf("[ WARNING ] :: %s is already exist!\n\n", c_name);
		return 0;		
	}
	
	/* change -> name */
	strcpy(p_address_book -> contact_list[index].name , c_name);

	printf("change name = %s\n", p_address_book -> contact_list[index].name);
}

