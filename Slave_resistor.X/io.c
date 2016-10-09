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
            ADCON1 = 0b10010000 ;
            ADCON0 = 0x01 | ((pin<<2));
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
unsigned short analogRead(){
    unsigned short temp;

     GO_nDONE = 1 ;         // PIC�ɃA�i���O�l�ǎ��J�n���w��
     while(GO_nDONE) ;      // PIC���ǎ�芮������܂ő҂�
     temp = ADRESH ;        // PIC�͓ǎ�����l��ADRESH��ADRESL�̃��W�X�^�[�ɃZ�b�g����
     temp = ( temp << 8 ) | ADRESL ;  // 10�r�b�g�̕���\�͂ł�

     return temp ;
}