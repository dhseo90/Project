/*
 * motor.c.c
 *
 * Created: 2017-05-18 ���� 4:33:25
 * Author : Administrator
 */ 

#define  F_CPU 8000000L  // ���ʹ� �̰� ���� �߿� ���߸��� ����
#include <avr/io.h>
#include <util/delay.h>


//���͸� 1���� ȸ����Ű�� ���� ������
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

	return step_data1[step_index1];// �迭 ����

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
		 
			
			for(int i = 0; i < 1500 ; i++){//200���� ����

			a = stepForward1();

			if (a == 0x01)      PORTC = ((1 << 0) | ( 1 << 7));//���Ͱ� ���ֺ���  ���ʹ� �ݴ�������� ���ÿ� ȸ��
			else if ( a==0x02) PORTC = ((1 << 1) | ( 1 << 6));
			else if ( a==0x04) PORTC = ((1 << 2) | ( 1 << 5));
			else if ( a==0x08) PORTC = ((1 << 3) | ( 1 << 4));
			
			_delay_ms(5); //���ڱ� �ݴ���� ȸ�� ����
		    }
		}
		
		

		//_delay_ms(1000);
	
	
	  else if( num == '2'){

			for(int i = 0; i < 1500 ; i++){//200���� ����

			a = stepBackward1();

			if (a == 0x08)     PORTC = ((1 << 3) | ( 1 << 4));//���Ͱ� ���ֺ���  ���ʹ� �ݴ�������� ���ÿ� ȸ��
			else if ( a==0x04) PORTC = ((1 << 2) | ( 1 << 5));
			else if ( a==0x02) PORTC = ((1 << 1) | ( 1 << 6));
			else if ( a==0x01) PORTC = ((1 << 0) | ( 1 << 7));
			
			_delay_ms(5); //���ڱ� �ݴ���� ȸ�� ����
			}
		 

		}

 
		//_delay_ms(1000);
	
}





