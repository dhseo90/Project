/*
 * TempSensor.c
 *
 * Created: 2017-05-11 ?ㅽ썑 2:48:52
 * Author : Administrator
 */ 

 #define F_CPU 8000000L
    #include <avr/io.h>
    #include <util/delay.h>
    #include <stdio.h>
    #include "UART.h"
    #include "humidity.h"
	#include "temparature.h"
	#include "motor.h"
	#include "cdsCellSen.h"


    FILE OUTPUT = FDEV_SETUP_STREAM(UART1_transmit, NULL, _FDEV_SETUP_WRITE);
    FILE INPUT = FDEV_SETUP_STREAM(NULL, UART1_receive, _FDEV_SETUP_READ);

   
    int main(void)
    {
		char temp = '\0';
		
	    int  read_humidity;
		int  read_temparature;
		int  read_cdsCellSen;
	
		int  humidity;
		int  temparature;
		int  cdsCellSen;

	    float inputvoltage_humidity;
		float inputvoltage_temparature;
		float inputvoltage_cdsCellSen;
		
	    stdout = &OUTPUT;
	    stdin = &INPUT;

	    UART1_init(); //UART 통신 초기화
	    ADC_humidity_init(2);  //AD변환기 초기화, 채널 3사용
        ADC_temparature_init(3); //AD변환기 초기화, 채널 2사용
		ADC_cdsCellSen_init(1); //AD변환기 초기화, 채널 1사용 
		Motor_init();
		
	    while(1)
	    {
		/*
			read_humidity = read_humidity_ADC(); //ADC 값 읽기
		    //0에서 1,023 사이의 값을 0V에서 2.7V 사이 값으로 변환 한다.
		    inputvoltage_humidity = read_humidity * 2.7 / 1023.0;
		    //10mV에 1도이므로 100을 곱해서 현재 온도를 얻는다 .
		    humidity = (int)(inputvoltage_humidity * 70.0);
		    //printf("humidity=%d\r\n", humidity);
		    //_delay_ms(1000); //1초 한 번 읽음
    
		    read_temparature = read_temparature_ADC(); //ADC 값 읽기
		    //0에서 1,023 사이의 값을 0V에서 2.7V 사이 값으로 변환 한다.
		    inputvoltage_temparature = read_temparature * 5.0 / 1023.0;
		    //10mV에 1도이므로 100을 곱해서 현재 온도를 얻는다 
			temparature = (int)(inputvoltage_temparature * 100.0); 
		    // printf("temparature=%d\r\n", temparature);
		    //_delay_ms(1000); //1초 한 번 읽음

			read_cdsCellSen= read_cdsCellSen_ADC(); //ADC 값 읽기
			//printf("cdsCellsensor=%d\r\n", read_cdsCellSen);
			//_delay_ms(1000); //1초 한 번 읽음
			printf("%d/%d/%d", humidity, temparature, read_cdsCellSen);
			//printf("\n\r");
			_delay_ms(1000); //1초 한 번 읽음
		*/	
			temp = UART1_receive();	
			printf("temp:%c\n",temp);
			if(temp == '1')
			{
				printf("one\n\r");
				motor_driver('1');				
			}
			else if(temp == '2') 
			{
				printf("two\n\r");
				motor_driver('2');	
			}
			else if(temp == '3')
			{
				printf("three\n\r");
				read_humidity = read_humidity_ADC(); //ADC 값 읽기
			    //0에서 1,023 사이의 값을 0V에서 2.7V 사이 값으로 변환 한다.
			    inputvoltage_humidity = read_humidity * 2.7 / 1023.0;
			    //10mV에 1도이므로 100을 곱해서 현재 온도를 얻는다 .
			    humidity = (int)(inputvoltage_humidity * 70.0);
			    //printf("humidity=%d\r\n", humidity);
			    //_delay_ms(1000); //1초 한 번 읽음
    
			    read_temparature = read_temparature_ADC(); //ADC 값 읽기
			    //0에서 1,023 사이의 값을 0V에서 2.7V 사이 값으로 변환 한다.
			    inputvoltage_temparature = read_temparature * 5.0 / 1023.0;
			    //10mV에 1도이므로 100을 곱해서 현재 온도를 얻는다 
				temparature = (int)(inputvoltage_temparature * 100.0); 
			    // printf("temparature=%d\r\n", temparature);
			    //_delay_ms(1000); //1초 한 번 읽음

				read_cdsCellSen= read_cdsCellSen_ADC(); //ADC 값 읽기
				//printf("cdsCellsensor=%d\r\n", read_cdsCellSen);
				//_delay_ms(1000); //1초 한 번 읽음
				printf("%d/%d/%d", humidity, temparature, read_cdsCellSen);
				//printf("\n\r");
				_delay_ms(1000); //1초 한 번 읽음
				//continue;
			}
		}
	    return 0;
   }





