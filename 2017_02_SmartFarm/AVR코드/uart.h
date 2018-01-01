/*
 * UART1.h
 *
 * Created: 2017-04-09 ?¤í›„ 2:45:48
 *  Author: USER
 */ 


#ifndef UART_H_
#define UART_H_

void UART1_init(void);
void UART1_transmit(char data);
unsigned char UART1_receive(void);
void UART1_print_string(char *str);
void UART1_print_1_byte_number(uint8_t n);


#endif /* UART1_H_ */
