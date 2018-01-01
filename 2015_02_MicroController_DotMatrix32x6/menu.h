/*
 * File:   menu.h
 * Author: SKHU 2015 Microcontroller Project Team1(Seo Dong Hyeong, Kim Min Kyung, Kim Hye Jin, Kim Ji Hyeon)
 *
 * Date 2015. 05. 25 ~ 2015. 05. 28
 */

#ifndef MENU_H
#define	MENU_H

#define LIMIT 100

char i;
unsigned char flag;
unsigned long mask1 = 0x00000003, mask2 = 0x0000000F;

void moving_glow_left_right_data(void);
unsigned long moving_data(char i);
void moving_up_down(void);

/*scroll funtions*/                                                             /* Option1 */
void scroll_left_right(void);
void disappear_scroll_left_right(void);
void disappear_scroll_right_left(void);
void appear_scroll_left_right(void);
void appear_scroll_right_left(void);

void divide_out(void);
void divide_in(void);
void divide_out_in(void);

void toggle(void);                                                             /* Option2 */
void opposition(void);

void wave_led(void);                                                           /* Option3 */

/*Led's ON and OFF*/
void led_on(void);
void led_off(void);       /* Option6 */

void toggle_speed_two(void);

#endif	/* MENU_H */