/*
 * File:   delay.c
 * Author: SKHU 2015 Microcontroller Project Team1(Seo Dong Hyeong, Kim Min Kyung, Kim Hye Jin, Kim Ji Hyeon)
 *
 * Date 2015. 05. 25 ~ 2015. 05. 28
 */

#include "main.h"

void delay_ms(unsigned short cnt) /* Making delay */
{
	unsigned short i, j;

	for(i = cnt; i--; )
	{
		for(j = 50; j--; );
	}
}