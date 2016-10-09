#ifndef I2C_H
#define	I2C_H

#define I2C_SLAVE

#define I2C_DATA_LEN 8                  // データバッファのサイズ

#ifdef I2C_MASTER
void interrupt i2cInterrupt( void ) ;
void i2cInit() ;
int  i2cSend(unsigned char adrs,int len,char *buf) ;
int  i2cReceive(unsigned char adrs,int len,char *buf) ;

#endif
#ifdef I2C_SLAVE

unsigned char rcv[I2C_DATA_LEN];
unsigned char snd[I2C_DATA_LEN];

void interrupt i2cInterrupt( void ) ;
void i2cInit(int address) ;
int i2cReceiveCheck() ;
void i2csetSendData(char *buf);
void setRecvData(char *buf);

#endif


#endif	/* LCD_I2C_LIB_H */