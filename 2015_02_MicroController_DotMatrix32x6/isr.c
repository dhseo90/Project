/*
 * File:   isr.c
 * Author: SKHU 2015 Microcontroller Project Team1(Seo Dong Hyeong, Kim Min Kyung, Kim Hye Jin, Kim Ji Hyeon)
 *
 * Date 2015. 05. 25 ~ 2015. 05. 28
 */

#include "main.h"

void interrupt timer(void)
{
    /*timer0*/
    if (T0IF)
    {
        global_count++;
        TMR0 = 6;
        T0IF = 0;
        T0IE = 0;
    }

    /*timer1*/
//    if(TMR1IF)
//    {
//        TMR1H = 0xFF;
//        TMR1L = 0x06;  // Load Timer1 Register with Initial value
//        TMR1IF = 0;
//    }
//
    /*timer2*/
//    if(TMR2IF)
//    {
//        TMR2 = 6;  // Load Timer2 Register with Initial value
//        TMR2IF = 0;
//    }
}
