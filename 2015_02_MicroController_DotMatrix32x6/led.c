/*
 * File:   led.c
 * Author: SKHU 2015 Microcontroller Project Team1(Seo Dong Hyeong, Kim Min Kyung, Kim Hye Jin, Kim Ji Hyeon)
 *
 * Date 2015. 05. 25 ~ 2015. 05. 28
 */

#include "main.h"

void init_led(void)
{
    TRISD0 = 0;		/* Set output for RESET 4017 pin */
    TRISD1 = 0;		/* Set output for CLOCK 4017 pin */

    TRISD2 = 0;		/* Set output for SRCCK 74HC595 pin */
    TRISD3 = 0;		/* Set output for DATA 74HC595 pin */
    TRISD4 = 0;		/* Set output for RCLK 74HC595 pin */

    RESET = 1;
    RESET = 0;
}

/*Make the form to display the char for time and date (4-width)*/
void change_to_timer(char data)
{
    switch (data)
    {
        case '(':
            temp[0] = 0x00000002;
            temp[1] = 0x00000004;
            temp[2] = 0x00000004;
            temp[3] = 0x00000004;
            temp[4] = 0x00000004;
            temp[5] = 0x00000002;
            break;
            
        case ')':
            temp[0] = 0x00000004;
            temp[1] = 0x00000002;
            temp[2] = 0x00000002;
            temp[3] = 0x00000002;
            temp[4] = 0x00000002;
            temp[5] = 0x00000004;
            break;

        case '/':
            temp[0] = 0x00000001;
            temp[1] = 0x00000001;
            temp[2] = 0x00000002;
            temp[3] = 0x00000002;
            temp[4] = 0x00000004;
            temp[5] = 0x00000004;
            break;

        case ':':
            temp[0] = 0x00000000;
            temp[1] = 0x00000002;
            temp[2] = 0x00000000;
            temp[3] = 0x00000000;
            temp[4] = 0x00000002;
            temp[5] = 0x00000000;
            break;

        case '0':
            temp[0] = 0x00000002;
            temp[1] = 0x00000005;
            temp[2] = 0x00000005;
            temp[3] = 0x00000005;
            temp[4] = 0x00000005;
            temp[5] = 0x00000002;
            break;

        case '1':
            temp[0] = 0x00000002;
            temp[1] = 0x00000006;
            temp[2] = 0x00000002;
            temp[3] = 0x00000002;
            temp[4] = 0x00000002;
            temp[5] = 0x00000007;
            break;

        case '2':
            temp[0] = 0x00000006;
            temp[1] = 0x00000001;
            temp[2] = 0x00000001;
            temp[3] = 0x00000006;
            temp[4] = 0x00000004;
            temp[5] = 0x00000007;
            break;

        case '3':
            temp[0] = 0x00000007;
            temp[1] = 0x00000001;
            temp[2] = 0x00000007;
            temp[3] = 0x00000001;
            temp[4] = 0x00000001;
            temp[5] = 0x00000007;
            break;

        case '4':
            temp[0] = 0x00000001;
            temp[1] = 0x00000003;
            temp[2] = 0x00000005;
            temp[3] = 0x00000007;
            temp[4] = 0x00000001;
            temp[5] = 0x00000001;
            break;

        case '5':
            temp[0] = 0x00000007;
            temp[1] = 0x00000004;
            temp[2] = 0x00000006;
            temp[3] = 0x00000001;
            temp[4] = 0x00000001;
            temp[5] = 0x00000006;
            break;

        case '6':
            temp[0] = 0x00000007;
            temp[1] = 0x00000004;
            temp[2] = 0x00000004;
            temp[3] = 0x00000007;
            temp[4] = 0x00000005;
            temp[5] = 0x00000007;
            break;

        case '7':
            temp[0] = 0x00000007;
            temp[1] = 0x00000001;
            temp[2] = 0x00000001;
            temp[3] = 0x00000001;
            temp[4] = 0x00000001;
            temp[5] = 0x00000001;
            break;

        case '8':
            temp[0] = 0x00000007;
            temp[1] = 0x00000005;
            temp[2] = 0x00000007;
            temp[3] = 0x00000005;
            temp[4] = 0x00000005;
            temp[5] = 0x00000007;
            break;

        case '9':
            temp[0] = 0x00000007;
            temp[1] = 0x00000005;
            temp[2] = 0x00000007;
            temp[3] = 0x00000001;
            temp[4] = 0x00000001;
            temp[5] = 0x00000007;
            break;
    }
}

/* Make the form to display the char (5-width) */
void change_to_char(char data)
{
    switch (data)
    {
        case 'A':
            temp[0] = 0x0000000E;
            temp[1] = 0x00000011;
            temp[2] = 0x00000011;
            temp[3] = 0x0000001F;
            temp[4] = 0x00000011;
            temp[5] = 0x00000011;
            break;

        case 'B':
            temp[0] = 0x0000001E;
            temp[1] = 0x00000011;
            temp[2] = 0x0000001E;
            temp[3] = 0x00000011;
            temp[4] = 0x00000011;
            temp[5] = 0x0000001E;
            break;

        case 'C':
            temp[0] = 0x0000000E;
            temp[1] = 0x00000011;
            temp[2] = 0x00000010;
            temp[3] = 0x00000010;
            temp[4] = 0x00000011;
            temp[5] = 0x0000000E;
            break;

        case 'D':
            temp[0] = 0x0000001E;
            temp[1] = 0x00000011;
            temp[2] = 0x00000011;
            temp[3] = 0x00000011;
            temp[4] = 0x00000011;
            temp[5] = 0x0000001E;
            break;

        case 'E':
            temp[0] = 0x0000001F;
            temp[1] = 0x00000010;
            temp[2] = 0x0000001E;
            temp[3] = 0x00000010;
            temp[4] = 0x00000010;
            temp[5] = 0x0000001F;
            break;

        case 'F':
            temp[0] = 0x0000001F;
            temp[1] = 0x00000010;
            temp[2] = 0x0000001E;
            temp[3] = 0x00000010;
            temp[4] = 0x00000010;
            temp[5] = 0x00000010;
            break;

        case 'G':
            temp[0] = 0x0000000E;
            temp[1] = 0x00000011;
            temp[2] = 0x00000010;
            temp[3] = 0x00000017;
            temp[4] = 0x00000011;
            temp[5] = 0x0000000E;
            break;

        case 'H':
            temp[0] = 0x00000011;
            temp[1] = 0x00000011;
            temp[2] = 0x0000001F;
            temp[3] = 0x00000011;
            temp[4] = 0x00000011;
            temp[5] = 0x00000011;
            break;

        case 'I':
            temp[0] = 0x0000001F;
            temp[1] = 0x00000004;
            temp[2] = 0x00000004;
            temp[3] = 0x00000004;
            temp[4] = 0x00000004;
            temp[5] = 0x0000001F;
            break;

        case 'J':
            temp[0] = 0x0000001F;
            temp[1] = 0x00000002;
            temp[2] = 0x00000002;
            temp[3] = 0x00000012;
            temp[4] = 0x00000012;
            temp[5] = 0x0000000E;
            break;

        case 'K':
            temp[0] = 0x00000011;
            temp[1] = 0x00000012;
            temp[2] = 0x0000001C;
            temp[3] = 0x00000012;
            temp[4] = 0x00000012;
            temp[5] = 0x00000011;
            break;

        case 'L':
            temp[0] = 0x00000010;
            temp[1] = 0x00000010;
            temp[2] = 0x00000010;
            temp[3] = 0x00000010;
            temp[4] = 0x00000010;
            temp[5] = 0x0000001F;
            break;

        case 'M':
            temp[0] = 0x00000011;
            temp[1] = 0x0000001B;
            temp[2] = 0x00000015;
            temp[3] = 0x00000015;
            temp[4] = 0x00000015;
            temp[5] = 0x00000015;
            break;

        case 'N':
            temp[0] = 0x00000011;
            temp[1] = 0x00000019;
            temp[2] = 0x00000015;
            temp[3] = 0x00000015;
            temp[4] = 0x00000013;
            temp[5] = 0x00000011;
            break;

        case 'O':
            temp[0] = 0x0000000E;
            temp[1] = 0x00000011;
            temp[2] = 0x00000011;
            temp[3] = 0x00000011;
            temp[4] = 0x00000011;
            temp[5] = 0x0000000E;
            break;

        case 'P':
            temp[0] = 0x0000001E;
            temp[1] = 0x00000011;
            temp[2] = 0x00000011;
            temp[3] = 0x0000001E;
            temp[4] = 0x00000010;
            temp[5] = 0x00000010;
            break;

        case 'Q':
            temp[0] = 0x0000000E;
            temp[1] = 0x00000011;
            temp[2] = 0x00000011;
            temp[3] = 0x00000015;
            temp[4] = 0x00000012;
            temp[5] = 0x0000000D;
            break;

        case 'R':
            temp[0] = 0x0000001E;
            temp[1] = 0x00000011;
            temp[2] = 0x0000001F;
            temp[3] = 0x00000012;
            temp[4] = 0x00000011;
            temp[5] = 0x00000011;
            break;

        case 'S':
            temp[0] = 0x0000000F;
            temp[1] = 0x00000010;
            temp[2] = 0x0000000E;
            temp[3] = 0x00000001;
            temp[4] = 0x00000011;
            temp[5] = 0x0000000E;
            break;

        case 'T':
            temp[0] = 0x0000001F;
            temp[1] = 0x00000004;
            temp[2] = 0x00000004;
            temp[3] = 0x00000004;
            temp[4] = 0x00000004;
            temp[5] = 0x00000004;
            break;

        case 'U':
            temp[0] = 0x00000011;
            temp[1] = 0x00000011;
            temp[2] = 0x00000011;
            temp[3] = 0x00000011;
            temp[4] = 0x00000011;
            temp[5] = 0x0000000E;
            break;

        case 'V':
            temp[0] = 0x00000011;
            temp[1] = 0x00000011;
            temp[2] = 0x00000011;
            temp[3] = 0x00000011;
            temp[4] = 0x0000000A;
            temp[5] = 0x00000004;
            break;

        case 'W':
            temp[0] = 0x00000015;
            temp[1] = 0x00000015;
            temp[2] = 0x00000015;
            temp[3] = 0x00000015;
            temp[4] = 0x00000015;
            temp[5] = 0x0000000A;
            break;

        case 'X':
            temp[0] = 0x00000011;
            temp[1] = 0x0000000A;
            temp[2] = 0x00000004;
            temp[3] = 0x00000004;
            temp[4] = 0x0000000A;
            temp[5] = 0x00000011;
            break;

        case 'Y':
            temp[0] = 0x00000011;
            temp[1] = 0x00000011;
            temp[2] = 0x0000000A;
            temp[3] = 0x00000004;
            temp[4] = 0x00000004;
            temp[5] = 0x00000004;
            break;

        case 'Z':
            temp[0] = 0x0000001F;
            temp[1] = 0x00000001;
            temp[2] = 0x00000002;
            temp[3] = 0x00000004;
            temp[4] = 0x00000008;
            temp[5] = 0x0000001F;
            break;

        case '0':
            temp[0] = 0x0000000E;
            temp[1] = 0x00000011;
            temp[2] = 0x00000011;
            temp[3] = 0x00000011;
            temp[4] = 0x00000011;
            temp[5] = 0x0000000E;
            break;

        case '1':
            temp[0] = 0x00000004;
            temp[1] = 0x0000000C;
            temp[2] = 0x00000014;
            temp[3] = 0x00000004;
            temp[4] = 0x00000004;
            temp[5] = 0x0000001F;
            break;

        case '2':
            temp[0] = 0x0000000E;
            temp[1] = 0x00000011;
            temp[2] = 0x00000001;
            temp[3] = 0x0000001E;
            temp[4] = 0x00000010;
            temp[5] = 0x0000001F;
            break;

        case '3':
            temp[0] = 0x0000001E;
            temp[1] = 0x00000001;
            temp[2] = 0x0000000E;
            temp[3] = 0x00000001;
            temp[4] = 0x00000001;
            temp[5] = 0x0000001E;
            break;

        case '4':
            temp[0] = 0x00000002;
            temp[1] = 0x00000006;
            temp[2] = 0x0000000A;
            temp[3] = 0x00000012;
            temp[4] = 0x0000001F;
            temp[5] = 0x00000002;
            break;

        case '5':
            temp[0] = 0x0000001F;
            temp[1] = 0x00000010;
            temp[2] = 0x0000001E;
            temp[3] = 0x00000001;
            temp[4] = 0x00000001;
            temp[5] = 0x0000001E;
            break;

        case '6':
            temp[0] = 0x0000001F;
            temp[1] = 0x00000010;
            temp[2] = 0x0000001F;
            temp[3] = 0x00000011;
            temp[4] = 0x00000011;
            temp[5] = 0x0000001F;
            break;

        case '7':
            temp[0] = 0x0000001F;
            temp[1] = 0x00000001;
            temp[2] = 0x00000001;
            temp[3] = 0x00000001;
            temp[4] = 0x00000001;
            temp[5] = 0x00000001;
            break;

        case '8':
            temp[0] = 0x0000000E;
            temp[1] = 0x00000011;
            temp[2] = 0x0000000E;
            temp[3] = 0x00000011;
            temp[4] = 0x00000011;
            temp[5] = 0x0000000E;
            break;

        case '9':
            temp[0] = 0x0000001F;
            temp[1] = 0x00000011;
            temp[2] = 0x0000001F;
            temp[3] = 0x00000001;
            temp[4] = 0x00000001;
            temp[5] = 0x0000001F;
            break;
    }
}

/*Display to LED circuit*/
void display(long value, char row)
{
	unsigned char data_bit, i;

	RCLK = LOW; /* Starting store the data bit */
	for (data_bit = 0; data_bit < 32; data_bit++)
	{
            /* DATA bit storing meanwhile SRCLK bit is present form low to high */
            SRCLK = LOW;
            DATA  = ((value >> data_bit) & 0x01);
            SRCLK = HIGH;
	}
	RCLK = HIGH; /* After storing 32 bits, Release the data bits */

        /* Reset */
	RESET = HIGH;
	RESET = LOW;

	for (i = 0; i < row; i++)
	{
            CLOCK = LOW;    /* Until it reaches 'row'th clock, */
            CLOCK = HIGH;   /* From Low to high, high to low on and on */
	}
}

/*Input data to data value*/
void input_data_to_led(const char *str, short j)
{
    unsigned char i;

    while(*str)
    {
        change_to_char(*str++); // Change character to led display character using change_to_char function

        for(i = 0; i < 6; i++)  /* Display new character and remain before display data */
        {
            data[i] <<= (SIZE + 1);
            data[i] = data[i] | temp[i];
        }
    }

    for(i = 0; i < 6; i++) /* Shift the data */
    {
        data[i] <<= 3;
    }

    for(i = 0; i < 6; i++) /* Shift the data */
    {
        data[i] >>= j;
    }
}