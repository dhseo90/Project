/*
 * motor.c.c
 *
 * Created: 2017-05-18 오후 4:33:25
 * Author : Administrator
 */ 

#define  F_CPU 8000000L  // 모터는 이것 아주 중요 빠뜨리지 말것
#include <avr/io.h>
#include <util/delay.h>


//모터를 1스텝 회전시키기 위한 데이터
uint8_t step_data1[] =  {0x01,0x02,0x04,0x08};
int step_index1 = -1;

void Motor_init(void)
{

		DDRC = 0XFF;
		PORTC = 0X00;
}


uint8_t stepForward1(void)
{
	step_index1++;
	if(step_index1 >=4) step_index1 = 0;

	return step_data1[step_index1];// 배열 원소

}


uint8_t stepBackward1(void)
{
	step_index1--;
	if(step_index1 < 0) step_index1 = 3;

	return step_data1[step_index1];

}

void motor_driver(char num)
{
	/* Replace with your application code */
	

	int a=0;
	

		if (num == '1' ){
		 
			
			for(int i = 0; i < 1500 ; i++){//200스텝 진행

			a = stepForward1();

			if (a == 0x01)      PORTC = ((1 << 0) | ( 1 << 7));//모터가 마주보고  상대와는 반대방향으로 동시에 회전
			else if ( a==0x02) PORTC = ((1 << 1) | ( 1 << 6));
			else if ( a==0x04) PORTC = ((1 << 2) | ( 1 << 5));
			else if ( a==0x08) PORTC = ((1 << 3) | ( 1 << 4));
			
			_delay_ms(5); //값자기 반대방향 회전 방지
		    }
		}
		
		

		//_delay_ms(1000);
	
	
	  else if( num == '2'){

			for(int i = 0; i < 1500 ; i++){//200스텝 진행

			a = stepBackward1();

			if (a == 0x08)     PORTC = ((1 << 3) | ( 1 << 4));//모터가 마주보고  상대와는 반대방향으로 동시에 회전
			else if ( a==0x04) PORTC = ((1 << 2) | ( 1 << 5));
			else if ( a==0x02) PORTC = ((1 << 1) | ( 1 << 6));
			else if ( a==0x01) PORTC = ((1 << 0) | ( 1 << 7));
			
			_delay_ms(5); //값자기 반대방향 회전 방지
			}
		 

		}

 
		//_delay_ms(1000);
	
}





