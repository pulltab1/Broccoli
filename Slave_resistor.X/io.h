#ifndef IO_H
#define IO_H

#include <xc.h>
#define INPUT           0x01
#define INPUT_PULLUP    0x02
#define INPUT_ANALOG    0x03
#define OUTPUT          0x04
#define OUTPUT_PWM      0x05

#define LOW 0
#define HIGH 1

union{
   unsigned short HL;
   unsigned char H;
   unsigned char L;
}HL;
void pinMode(unsigned char pin,unsigned char mode);//ピンのモードを設定
void digitalWrite(unsigned char pin,char output);//ピン出力の設定
char digitalRead(unsigned char pin);//ピン入力の設定
void analogWrite(unsigned char pin,unsigned char output);
unsigned short analogRead();

#endif