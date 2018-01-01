/*
 * File:   main.c
 * Author: SKHU 2015 Microcontroller Project Team1(Seo Dong Hyeong, Kim Min Kyung, Kim Hye Jin, Kim Ji Hyeon)
 *
 * Date 2015. 05. 25 ~ 2015. 05. 28
 */

#include "main.h"

init_switch(void)
{
    //1.Make PORTB as input
    TRISB = 0xFF;
    //2.Make PORTB as digital port
    ANSELH = 0x00;
}

//read switch value
unsigned short read_switch(void)
{
    unsigned short i;
    if (RB0 == 1)	// If RB0 is pressed,
    {
        for (i = 0; i < 30; i++);
        return 2;	// return 2 (recognize RB0 as 2, according to switch name of our EmxPICM02 board)
    }
    if (RB1 == 1)	// If RB1 is pressed,
    {
        for (i = 0; i < 30; i++);
        return 3;	// return 3 
    }
    if (RB2 == 1)	// If RB2 is pressed,
    {
        for (i = 0; i < 30; i++);
        return 4;	// return 4
    }
    if (RB3 == 1)	// If RB3 is pressed,
    {
        for (i = 0; i < 30; i++);
        return 5;	// return 5
    }
    if (RB4 == 1)	// If RB4 is pressed,
    {
        for (i = 0; i < 30; i++);
        return 6;	// return 6
    }
    if (RB5 == 1)	// If RB5 is pressed,
    {
        for (i = 0; i < 30; i++);
        return 7;	// return 7
    }
}