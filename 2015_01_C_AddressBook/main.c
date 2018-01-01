#include "address_book.h"
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <stdio_ext.h>

int main()
{
	int i;
	char cho;

	abk_t *address = malloc(sizeof(abk_t));
	load_file(address);
	main_menu(address);

	return 0;
}

