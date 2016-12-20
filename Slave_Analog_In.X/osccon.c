#include <xc.h>
#include "osccon.h"
#include "config.h"

void initOSCCON(void){
//内部クロックの場合、__XTAL_FREQの値に応じてOSCCONを設定する。
#if FOSC==INTOOSC
    #if _XTAL_FREQ == 16000000
        OSCCON|=0b01111010;
    #elif _XTAL_FREQ == 8000000
        OSCCON|=0b01110010;
    #elif _XTAL_FREQ == 4000000
        OSCCON|=0b01101010;
    #elif _XTAL_FREQ == 2000000
        OSCCON|=0b01100010;
    #elif _XTAL_FREQ == 1000000
        OSCCON|=0b01011010;
    #else
        #if USEMF==TRUE
            #if _XTAL_FREQ == 500000
                OSCCON|=0b00111010;
            #elif _XTAL_FREQ == 250000
                OSCCON|=0b00110010;
            #elif _XTAL_FREQ == 125000
                OSCCON|=0b00101010;
            #elif _XTAL_FREQ == 62500
                OSCCON|=0b00100010;
            #elif _XTAL_FREQ == 31200
                OSCCON|=0b00010010;
            #endif
        #else
            #if _XTAL_FREQ == 500000
                OSCCON|=0b01010010;
            #elif _XTAL_FREQ == 250000
                OSCCON|=0b01001010;
            #elif _XTAL_FREQ == 125000
                OSCCON|=0b01000010;
            #elif _XTAL_FREQ == 31200
                OSCCON|=0b00011010;
            #endif
        #endif
    #endif
#endif
}
void setOSCIDLE(int idle){
    if(idle)
        OSCCON|=0x80;
    else
        OSCCON&=0x7f;
}