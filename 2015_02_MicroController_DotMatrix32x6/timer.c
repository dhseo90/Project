/*
 * File:   timer.c
 * Author: SKHU 2015 Microcontroller Project Team1(Seo Dong Hyeong, Kim Min Kyung, Kim Hye Jin, Kim Ji Hyeon)
 *
 * Date 2015. 05. 25 ~ 2015. 05. 28
 */

#include "main.h"

void init_timer0(void)
{
    T0IE = 1; /* Enable Timer 0 Interrupt */
    T0CS = 0; /* Timer 0 Clock Source select bit */
    PSA = 0;  /* Assign prescalar to Timer0 */
    PS0 = 0;  /* Select 1:2 prescalar value */
    PS1 = 0;
    PS2 = 0;
    T0IF = 0; /* Initially, no interrupt */
    GIE = 1;  /* Enable global Interrupt */
//    PEIE = 1;
}

void init_timer1(void)
{
    TMR1H = 0xFF; // Timer1 clock source select bit (16bit TMR1H : TMR1L)
    TMR1L = 0x06;
    TMR1CS = 0;   // Timer1 Clock Source Select bit -> Internal clock (FOSC/4)
    T1CKPS1 = 0;  // Timer1 Input Clock Prescale Select bits -> 01 = 1:2 Prescale Value
    T1CKPS0 = 1;
    TMR1IF = 0;   // TMR1IF bit should be cleared before enabling interrupts
    TMR1IE = 1;   // TMR1IE bit of the PIE1 register must be set
    TMR1ON = 1;   // TMR1ON bit of the T1CON register must be set ->  Enables Timer1
    PEIE = 1;     // PEIE bit of the INTCON register must be set
    GIE = 1;      // Enable global Interrupt
}

void init_timer2(void)
{
    T2CKPS0 = 0;    // Prescaler is 1
    T2CKPS1 = 0;
    TMR2ON = 1;     // Timer2 On bit
    PEIE = 1;       // PEIE bit of the INTCON register must be set
    GIE = 1;        // Enable global Interrupt
}