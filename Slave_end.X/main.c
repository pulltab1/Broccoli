#include "delay.h"
#include "io.h"
#include "config.h"
#include "osccon.h"

#include "i2c.h"

//�ʐM�p�萔
#define BROADCAST_ADDRESS (0x00)
#define UNIQUE_ADDRESS (0xff)
#define DELAY_OF_ADDRESS_CHANGE (100)

//���N�G�X�g�p�萔
#define WALL_PIN (0)

void main()
{
     int Mode=0;

     initOSCCON();

     pinMode(WALL_PIN,OUTPUT);
     digitalWrite(WALL_PIN,LOW);
     i2cInit(BROADCAST_ADDRESS) ;
     snd[0]=UNIQUE_ADDRESS;

     while(1) {
          if (i2cReceiveCheck() != 0) {
              GIE=0;//i2c����������̂Ŋ��荞�݋֎~
              switch(Mode){
                  case 0:
                      SSP1ADD = rcv[0]<<1 ;
                      digitalWrite(0,HIGH);
                      Mode=1;
                      break;
                  case 1:
                      snd[0]=0xff;
                      break;
              }
              GIE=1;
          }
     }
}