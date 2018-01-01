/*
 * File:   led.h
 * Author: SKHU 2015 Microcontroller Project Team1(Seo Dong Hyeong, Kim Min Kyung, Kim Hye Jin, Kim Ji Hyeon)
 *
 * Date 2015. 05. 25 ~ 2015. 05. 28
 */

#ifndef LED_H
#define	LED_H

void init_led(void);
void change_to_char(char data);
void change_to_timer(char data);
void display(long value, char row);
void input_data_to_led(const char *data, short i);

#endif	/* LED_H */

