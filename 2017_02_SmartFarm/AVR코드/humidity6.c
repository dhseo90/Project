/*
 * TempSensor.c
 *
 * Created: 2017-05-11 ���� 2:48:52
 * Author : Administrator
 */ 

    
    #include <avr/io.h>
	
   


    void ADC_init(unsigned char channel)
    {

	    ADMUX |= (1 << REFS0); //AVCC�� ���� �������� ����

	    ADCSRA |= 0x07; //���ֺ� ����
	    ADCSRA |= (1 << ADEN); //ADC Ȱ��ȭ
	    ADCSRA |= (1 << ADFR); // �������� ���

	    ADMUX |= ((ADMUX & 0xE0) | channel); //ä�� ����
	    ADCSRA |= (1 << ADSC); //��ȯ ����
    }

    int read_ADC(void)
    {
	    while(!(ADCSRA & (1 << ADIF))); //��ȯ ���� ���

	    return  ADC; //10��Ʈ ���� ��ȯ
    }


