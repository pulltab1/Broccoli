#ifndef INIT_H
#define	INIT_H

//OSCCON設定時にHF/MFの時、MFの周波数を使うかどうか。
#define USEMF FALSE

//初期化
void initOSCCON(void);

//省電力モード設定
void setOSCIDLE(int idle);

#endif
