#include <xc.h>
#include "config.h"

void delay_ms(int time){
    int i;
    for(i=0;i<time;i++){
        __delay_ms(1);
    }
}
void delay_us(int time){
    int i;
    for(i=0;i<time;i++){
        __delay_us(1);
    }
}