#include "delay.h"
#include "io.h"
#include "config.h"
#include "osccon.h"

#include "i2c.h"

//通信用定数
#define BROADCAST_ADDRESS (0x00)
#define UNIQUE_ADDRESS (0x02)
#define DELAY_OF_STARTUP 500

//リクエスト用定数
#define WALL_PIN (RA0)
#define SWITCH_PIN (RA4)

void main()
{
     int Mode = 0;

     initOSCCON();

     delay_ms(DELAY_OF_STARTUP);

     pinMode(WALL_PIN,OUTPUT);
     pinMode(SWITCH_PIN,INPUT);
     i2cInit(BROADCAST_ADDRESS);
     snd[0] = UNIQUE_ADDRESS;

     while(1) {
          if (i2cReceiveCheck() != 0) {
              GIE = 0;//i2c処理があるので割り込み禁止
              switch(Mode){
                  case 0x00:
                      SSP1ADD = rcv[0]<<1;
                      digitalWrite(WALL_PIN,HIGH);
                      Mode=1;
                      break;
                  case 0x01:
                      switch(rcv[0]){
                      case 0x00:
                         if(digitalRead(SWITCH_PIN))
                             snd[0]=1;
                         else
                             snd[0]=0;
                         break;
                      default:
                          snd[0]=-1;
                          break;
                      }
                      break;
              }
              GIE = 1;
          }
     }
}