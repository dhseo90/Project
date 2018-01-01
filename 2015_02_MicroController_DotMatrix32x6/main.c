/*
 * File:   main.c
 * Author: SKHU 2015 Microcontroller Project Team1(Seo Dong Hyeong, Kim Min Kyung, Kim Hye Jin, Kim Ji Hyeon)
 *
 * Date 2015. 05. 25 ~ 2015. 05. 28
 */

#include "main.h"

__CONFIG( DEBUG_OFF & LVP_OFF & FCMEN_OFF & IESO_OFF & BOREN_OFF & CPD_OFF & CP_OFF & MCLRE_ON & PWRTE_ON & WDTE_OFF & FOSC_HS);
__CONFIG(WRT_OFF & BOR4V_BOR40V);

void init_config(void)
{
        init_switch();        /*Init for switch*/
        init_led();           /*Init for LED*/
        init_timer0();        /*Init for timer0*/
}

void main(void)
{
    init_config();

    static unsigned short SW, val;
    input_data_to_led("SATYA", 1);

    while(1)
    {
        SW = read_switch();
        if (SW == SWITCH2) // Double check switch is pressed or not
        {
            val =  2;
        }
        if (SW == SWITCH3)
        {
            val = 3;
        }
        if (SW == SWITCH4)
        {
            val = 4;
        }
        if (SW == SWITCH5)
        {
            val = 5;
        }
        if (SW == SWITCH6)
        {
            val = 6;
        }
        if (SW == SWITCH7)
        {
            val = 7;
        }

        if (val == 2)
        {
            divide_out();
        }
        if (val == 3)
        {
            moving_glow_left_right_data();
        }
        if (val == 4)
        {
            toggle();
        }
        if (val == 5)
        {
            opposition();
        }
        if (val == 6)
        {
            wave_led();
        }
        if (val == 7)
        {
            led_off();
        }
        else
        {
            led_on();
        }
    }
}