#ifndef IO_H
#define IO_H

#include <xc.h>
#define INPUT           0x01
#define INPUT_PULLUP    0x02
#define INPUT_ANALOG    0x03
#define OUTPUT          0x04
#define OUTPUT_PWM      0x05

#define RA0 0
#define RA1 1
#define RA2 2
#define RA3 3
#define RA4 4
#define RA5 5

#define AN0 0
#define AN1 1
#define AN2 2
#define AN3 3
#define LOW 0
#define HIGH 1

void pinMode(unsigned char pin,unsigned char mode);//ピンのモードを設定
void digitalWrite(unsigned char pin,char value);//ピン出力の設定
char digitalRead(unsigned char pin);//ピン入力の設定
void analogWrite(unsigned char pin,unsigned char value);
unsigned short analogRead();

#endif