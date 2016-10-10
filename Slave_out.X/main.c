#include "delay.h"
#include "io.h"
#include "config.h"
#include "osccon.h"

#include "i2c.h"

//通信用定数
#define BROADCAST_ADDRESS (0x00)
#define UNIQUE_ADDRESS (0x01)
#define DELAY_OF_ADDRESS_CHANGE (100)

//リクエスト用定数
#define WALL_PIN (RA0)
#define LED_PIN (RA4)

void main()
{
     int Mode=0;

     initOSCCON();

     pinMode(WALL_PIN,OUTPUT);
     pinMode(LED_PIN,OUTPUT);
     digitalWrite(WALL_PIN,LOW);
     digitalWrite(LED_PIN,LOW);
     i2cInit(BROADCAST_ADDRESS);
     snd[0]=UNIQUE_ADDRESS;

     while(1) {
          if (i2cReceiveCheck() != 0) {
              GIE=0;//i2c処理があるので割り込み禁止
              switch(Mode){
                  case 0:
                      SSP1ADD = rcv[0]<<1;
                      digitalWrite(WALL_PIN,HIGH);
                      Mode=1;
                      break;
                  case 1:
                      snd[0]=0xff;
                      switch(rcv[0]){
                      case 0:
                          digitalWrite(LED_PIN,LOW);
                          break;
                      case 1:
                          digitalWrite(LED_PIN,HIGH);
                          break;
                      default:
                          snd[0]=-1;
                          break;
                      }
                      break;
              }
              GIE=1;
          }
     }
}