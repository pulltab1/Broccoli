#include <xc.h>
#include "i2c.h"
#include "config.h"

#ifdef I2C_MASTER
int AckCheck;

void i2cIdleCheck(char mask) {
    while ((SSP1CON2 & 0x1F) | (SSP1STAT & mask));
}

void interrupt i2cInterrupt(void) {
    if (SSP1IF == 1) { // SSP(I2C)割り込み発生か？
        if (AckCheck == 1) AckCheck = 0;
        SSP1IF = 0; // フラグクリア
    }
    if (BCL1IF == 1) { // MSSP(I2C)バス衝突割り込み発生か？
        BCL1IF = 0; // 今回はフラグのみクリアする(無処理)
    }
}

void i2cInit() {
    ANSELA &= 0b11111001;
    TRISA |= 0b00000110;
    PORTA &= 0b11111001;
    SSP1STAT = 0b10000000; // 標準速度モードに設定する(100kHz)
    SSP1CON1 = 0b00101000; // SDA/SCLピンはI2Cで使用し、マスターモードとする
    SSP1ADD = (_XTAL_FREQ / 4) / 100000 - 1;
    SSP1IE = 1; // SSP(I2C)割り込みを許可する
    BCL1IE = 1; // MSSP(I2C)バス衝突割り込みを許可する
    PEIE = 1; // 周辺装置割り込みを許可する
    GIE = 1; // 全割り込み処理を許可する
    SSP1IF = 0; // SSP(I2C)割り込みフラグをクリアする
    BCL1IF = 0; // MSSP(I2C)バス衝突割り込みフラグをクリアする
}

int i2cSend(unsigned char adrs, int len, char *buf) {
    int i, ans;

    // スタート(START CONDITION)
    i2cIdleCheck(0x5);
    SSP1CON2bits.SEN = 1;
    // [スレーブのアドレス+スレーブは受信を要求]を送信する
    i2cIdleCheck(0x5);
    AckCheck = 1;
    SSP1BUF = (char) (adrs << 1); // アドレスを送信 R/W=0
    while (AckCheck); // 相手からのACK返答を待つ
    ans = SSP1CON2bits.ACKSTAT;
    if (ans == 0) {
        // [データ]を送信する
        for (i = 0; i < len; i++) {
            i2cIdleCheck(0x5);
            AckCheck = 1;
            SSP1BUF = (char) *buf; // データを送信
            buf++;
            while (AckCheck); // 相手からのACK返答を待つ
            ans = SSP1CON2bits.ACKSTAT;
            if (ans != 0) {
                ans = 2; // 相手がNOACKを返した
                break;
            }
        }
    }
    // ストップ(STOP CONDITION)
    i2cIdleCheck(0x5);
    SSP1CON2bits.PEN = 1;
    return ans;
}

int i2cReceive(unsigned char adrs, int len, char *buf) {
    int i, ans;

    // スタート(START CONDITION)
    i2cIdleCheck(0x5);
    SSP1CON2bits.SEN = 1;
    // [スレーブのアドレス+スレーブへデータ要求]を送信する
    i2cIdleCheck(0x5);
    AckCheck = 1;
    SSP1BUF = (char) ((adrs << 1) + 1); // アドレスを送信 R/W=1
    while (AckCheck); // 相手からのACK返答を待つ
    ans = SSP1CON2bits.ACKSTAT;
    if (ans == 0) {
        for (i = 1; i <= len; i++) {
            // [データ]を受信する
            i2cIdleCheck(0x5);
            SSP1CON2bits.RCEN = 1; // 受信を許可する
            i2cIdleCheck(0x4);
            *buf = SSP1BUF; // 受信
            buf++;
            i2cIdleCheck(0x5);
            if (i = len) SSP1CON2bits.ACKDT = 1; // ACKデータはNOACK
            else SSP1CON2bits.ACKDT = 0; // ACKデータはACK
            SSP1CON2bits.ACKEN = 1; // ACKデータを返す
        }
    }
    // ストップ(STOP CONDITION)
    i2cIdleCheck(0x5);
    SSP1CON2bits.PEN = 1;
    return ans;
}

#endif
#ifdef I2C_SLAVE

unsigned int rcv_flg; // 受信情報(受信データの個数を格納)
unsigned char *Sdtp; // 送信データバッファのアドレスポインター
unsigned char *Rdtp; // 受信データバッファのアドレスポインター

void interrupt i2cInterrupt(void) {
    char x;

    /*********************************************************************
     * SSP(I2C)割り込み発生時の処理                                       *
     *********************************************************************/
    if (SSP1IF == 1) { //
        if (SSP1STATbits.R_nW == 0) {
            /******* マスターからの書き込み要求(スレーブは受信) *******/
            if (SSP1STATbits.D_nA == 0) {
                // 受信バイトはアドレス
                Rdtp = (char *) rcv;
                x = SSP1BUF; // アドレスデータを空読みする
                rcv_flg = 0;
            } else {
                // 受信バイトはデータ
                *Rdtp = SSP1BUF; // データを読込む(ACKはPICが自動的に返す)
                Rdtp++;
                rcv_flg++;
            }
            SSP1IF = 0; // 割込みフラグクリア
            SSP1CON1bits.CKP = 1; // SCLラインを開放する(通信の再開)
        } else {
            /******* マスターからの読み出し要求(スレーブは送信) *******/
            if (SSP1STATbits.BF == 1) {
                // アドレス受信後の割り込みと判断する
                Sdtp = (char *) snd;
                x = SSP1BUF; // アドレスデータを空読みする
                while ((SSP1CON1bits.CKP) | (SSP1STATbits.BF));
                SSP1BUF = *Sdtp; // 送信データのセット
                Sdtp++;
                SSP1IF = 0; // 割込みフラグクリア
                SSP1CON1bits.CKP = 1; // SCLラインを開放する(通信の再開)
            } else {
                // データの送信後のACK受け取りによる割り込みと判断する
                if (SSP1CON2bits.ACKSTAT == 0) {
                    // マスターからACK応答なら次のデータを送信する
                    while ((SSP1CON1bits.CKP) | (SSP1STATbits.BF));
                    SSP1BUF = *Sdtp; // 送信データのセット
                    Sdtp++;
                    SSP1IF = 0; // 割込みフラグクリア
                    SSP1CON1bits.CKP = 1; // SCLラインを開放する(通信の再開)
                } else {
                    // マスターからはNOACKで応答された時
                    SSP1IF = 0; // 割込みフラグクリア
                }
            }
        }
    }
    /*********************************************************************
     * MSSP(I2C)バス衝突割り込発生時の処理                                *
     *********************************************************************/
    if (BCL1IF == 1) {
        BCL1IF = 0; // 今回はフラグのみクリアする(無処理)
    }
}

void i2cInit(int address) {
    SSP1STAT = 0b10000000; // 標準速度モードに設定する(100kHz)
    SSP1CON1 = 0b00100110; // SDA/SCLピンはI2Cで使用し、スレーブモードとする
    SSP1CON2bits.SEN = 1; // SCL制御(クロックストレッチ)を行う
    //  SSP1CON2bits.GCEN = 1;   // 一括呼び出しの同報通知を有効にする
    SSP1ADD = address << 1; // マイアドレスの設定
    SSP1MSK = 0b11111110; // アドレス比較用マスクデータ
    SSP1IE = 1; // SSP(I2C)割り込みを許可する
    BCL1IE = 1; // MSSP(I2C)バス衝突割り込みを許可する
    PEIE = 1; // 周辺装置割り込みを許可する
    GIE = 1; // 全割り込み処理を許可する
    SSP1IF = 0; // SSP(I2C)割り込みフラグをクリアする
    BCL1IF = 0; // MSSP(I2C)バス衝突割り込みフラグをクリアする
    rcv_flg = 0;
    ANSELA &= 0b11111001;
    TRISA |= 0b00000110;
    PORTA &= 0b11111001;
}

int i2cReceiveCheck() {
    int ans;

    ans = 0;
    if (rcv_flg != 0) { // 受信したぞぉ
        if ((SSP1STATbits.S == 0) && (SSP1STATbits.P == 1)) { // ｽﾄｯﾌﾟｺﾝﾃﾞｨｼｮﾝ発行されたぞぉ
            ans = rcv_flg;
            rcv_flg = 0;
        }
    }
    return (ans);
}


#endif