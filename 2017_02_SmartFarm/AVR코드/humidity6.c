/*
 * TempSensor.c
 *
 * Created: 2017-05-11 오후 2:48:52
 * Author : Administrator
 */ 

    
    #include <avr/io.h>
	
   


    void ADC_init(unsigned char channel)
    {

	    ADMUX |= (1 << REFS0); //AVCC를 기준 전압으로 선택

	    ADCSRA |= 0x07; //분주비 설정
	    ADCSRA |= (1 << ADEN); //ADC 활성화
	    ADCSRA |= (1 << ADFR); // 프리러닝 모드

	    ADMUX |= ((ADMUX & 0xE0) | channel); //채널 선택
	    ADCSRA |= (1 << ADSC); //변환 시작
    }

    int read_ADC(void)
    {
	    while(!(ADCSRA & (1 << ADIF))); //변환 종료 대기

	    return  ADC; //10비트 값을 반환
    }


