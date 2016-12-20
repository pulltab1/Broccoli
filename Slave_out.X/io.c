#include "delay.h"
#include "io.h"

void pinMode(unsigned char pin, unsigned char mode) {
    LATA = LATA & ~(1 << pin);
    switch (mode) {
        case INPUT:
            TRISA = TRISA | (1 << pin);
            ANSELA = ANSELA & (~(1 << pin));
            break;
        case OUTPUT:
            TRISA = TRISA & (~(1 << pin));
            ANSELA = ANSELA & (~(1 << pin));
            break;
        case INPUT_ANALOG:
            TRISA = TRISA | (1 << pin);
            ANSELA = ANSELA | ((1 << pin));
            ADCON1 = 0b00010011 ;
            ADCON0 = ADCON0 | 0x01 | ((pin<<3)) ;
            PORTA = PORTA & (~(1 << pin));
            delay_us(5);
            break;
    }
}

void digitalWrite(unsigned char pin, char value) {
    if (value) {
        LATA = LATA | (1 << pin);
    } else {
        LATA = LATA & ~(1 << pin);
    }
}

char digitalRead(unsigned char pin) {
    return ( PORTA & (1 << pin));
}
void analogWrite(unsigned char pin, unsigned char value){

}
unsigned short analogRead(unsigned char pin){
    unsigned short temp;

     GO_nDONE = 1;
     while(GO_nDONE);
     temp = ADRESH;
     temp = ( temp << 8 ) | ADRESL;

     return temp ;
}