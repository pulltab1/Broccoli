#include <xc.h>
#include "i2c.h"
#include "config.h"

#ifdef I2C_MASTER
int AckCheck;

void i2cIdleCheck(char mask) {
    while ((SSP1CON2 & 0x1F) | (SSP1STAT & mask));
}

void interrupt i2cInterrupt(void) {
    if (SSP1IF == 1) { // SSP(I2C)���荞�ݔ������H
        if (AckCheck == 1) AckCheck = 0;
        SSP1IF = 0; // �t���O�N���A
    }
    if (BCL1IF == 1) { // MSSP(I2C)�o�X�Փˊ��荞�ݔ������H
        BCL1IF = 0; // ����̓t���O�̂݃N���A����(������)
    }
}

void i2cInit() {
    ANSELA &= 0b11111001;
    TRISA |= 0b00000110;
    PORTA &= 0b11111001;
    SSP1STAT = 0b10000000; // �W�����x���[�h�ɐݒ肷��(100kHz)
    SSP1CON1 = 0b00101000; // SDA/SCL�s����I2C�Ŏg�p���A�}�X�^�[���[�h�Ƃ���
    SSP1ADD = (_XTAL_FREQ / 4) / 100000 - 1;
    SSP1IE = 1; // SSP(I2C)���荞�݂�������
    BCL1IE = 1; // MSSP(I2C)�o�X�Փˊ��荞�݂�������
    PEIE = 1; // ���ӑ��u���荞�݂�������
    GIE = 1; // �S���荞�ݏ�����������
    SSP1IF = 0; // SSP(I2C)���荞�݃t���O���N���A����
    BCL1IF = 0; // MSSP(I2C)�o�X�Փˊ��荞�݃t���O���N���A����
}

int i2cSend(unsigned char adrs, int len, char *buf) {
    int i, ans;

    // �X�^�[�g(START CONDITION)
    i2cIdleCheck(0x5);
    SSP1CON2bits.SEN = 1;
    // [�X���[�u�̃A�h���X+�X���[�u�͎�M��v��]�𑗐M����
    i2cIdleCheck(0x5);
    AckCheck = 1;
    SSP1BUF = (char) (adrs << 1); // �A�h���X�𑗐M R/W=0
    while (AckCheck); // ���肩���ACK�ԓ���҂�
    ans = SSP1CON2bits.ACKSTAT;
    if (ans == 0) {
        // [�f�[�^]�𑗐M����
        for (i = 0; i < len; i++) {
            i2cIdleCheck(0x5);
            AckCheck = 1;
            SSP1BUF = (char) *buf; // �f�[�^�𑗐M
            buf++;
            while (AckCheck); // ���肩���ACK�ԓ���҂�
            ans = SSP1CON2bits.ACKSTAT;
            if (ans != 0) {
                ans = 2; // ���肪NOACK��Ԃ���
                break;
            }
        }
    }
    // �X�g�b�v(STOP CONDITION)
    i2cIdleCheck(0x5);
    SSP1CON2bits.PEN = 1;
    return ans;
}

int i2cReceive(unsigned char adrs, int len, char *buf) {
    int i, ans;

    // �X�^�[�g(START CONDITION)
    i2cIdleCheck(0x5);
    SSP1CON2bits.SEN = 1;
    // [�X���[�u�̃A�h���X+�X���[�u�փf�[�^�v��]�𑗐M����
    i2cIdleCheck(0x5);
    AckCheck = 1;
    SSP1BUF = (char) ((adrs << 1) + 1); // �A�h���X�𑗐M R/W=1
    while (AckCheck); // ���肩���ACK�ԓ���҂�
    ans = SSP1CON2bits.ACKSTAT;
    if (ans == 0) {
        for (i = 1; i <= len; i++) {
            // [�f�[�^]����M����
            i2cIdleCheck(0x5);
            SSP1CON2bits.RCEN = 1; // ��M��������
            i2cIdleCheck(0x4);
            *buf = SSP1BUF; // ��M
            buf++;
            i2cIdleCheck(0x5);
            if (i = len) SSP1CON2bits.ACKDT = 1; // ACK�f�[�^��NOACK
            else SSP1CON2bits.ACKDT = 0; // ACK�f�[�^��ACK
            SSP1CON2bits.ACKEN = 1; // ACK�f�[�^��Ԃ�
        }
    }
    // �X�g�b�v(STOP CONDITION)
    i2cIdleCheck(0x5);
    SSP1CON2bits.PEN = 1;
    return ans;
}

#endif
#ifdef I2C_SLAVE

unsigned int rcv_flg; // ��M���(��M�f�[�^�̌����i�[)
unsigned char *Sdtp; // ���M�f�[�^�o�b�t�@�̃A�h���X�|�C���^�[
unsigned char *Rdtp; // ��M�f�[�^�o�b�t�@�̃A�h���X�|�C���^�[

void interrupt i2cInterrupt(void) {
    char x;

    /*********************************************************************
     * SSP(I2C)���荞�ݔ������̏���                                       *
     *********************************************************************/
    if (SSP1IF == 1) { //
        if (SSP1STATbits.R_nW == 0) {
            /******* �}�X�^�[����̏������ݗv��(�X���[�u�͎�M) *******/
            if (SSP1STATbits.D_nA == 0) {
                // ��M�o�C�g�̓A�h���X
                Rdtp = (char *) rcv;
                x = SSP1BUF; // �A�h���X�f�[�^����ǂ݂���
                rcv_flg = 0;
            } else {
                // ��M�o�C�g�̓f�[�^
                *Rdtp = SSP1BUF; // �f�[�^��Ǎ���(ACK��PIC�������I�ɕԂ�)
                Rdtp++;
                rcv_flg++;
            }
            SSP1IF = 0; // �����݃t���O�N���A
            SSP1CON1bits.CKP = 1; // SCL���C�����J������(�ʐM�̍ĊJ)
        } else {
            /******* �}�X�^�[����̓ǂݏo���v��(�X���[�u�͑��M) *******/
            if (SSP1STATbits.BF == 1) {
                // �A�h���X��M��̊��荞�݂Ɣ��f����
                Sdtp = (char *) snd;
                x = SSP1BUF; // �A�h���X�f�[�^����ǂ݂���
                while ((SSP1CON1bits.CKP) | (SSP1STATbits.BF));
                SSP1BUF = *Sdtp; // ���M�f�[�^�̃Z�b�g
                Sdtp++;
                SSP1IF = 0; // �����݃t���O�N���A
                SSP1CON1bits.CKP = 1; // SCL���C�����J������(�ʐM�̍ĊJ)
            } else {
                // �f�[�^�̑��M���ACK�󂯎��ɂ�銄�荞�݂Ɣ��f����
                if (SSP1CON2bits.ACKSTAT == 0) {
                    // �}�X�^�[����ACK�����Ȃ玟�̃f�[�^�𑗐M����
                    while ((SSP1CON1bits.CKP) | (SSP1STATbits.BF));
                    SSP1BUF = *Sdtp; // ���M�f�[�^�̃Z�b�g
                    Sdtp++;
                    SSP1IF = 0; // �����݃t���O�N���A
                    SSP1CON1bits.CKP = 1; // SCL���C�����J������(�ʐM�̍ĊJ)
                } else {
                    // �}�X�^�[�����NOACK�ŉ������ꂽ��
                    SSP1IF = 0; // �����݃t���O�N���A
                }
            }
        }
    }
    /*********************************************************************
     * MSSP(I2C)�o�X�Փˊ��荞�������̏���                                *
     *********************************************************************/
    if (BCL1IF == 1) {
        BCL1IF = 0; // ����̓t���O�̂݃N���A����(������)
    }
}

void i2cInit(int address) {
    SSP1STAT = 0b10000000; // �W�����x���[�h�ɐݒ肷��(100kHz)
    SSP1CON1 = 0b00100110; // SDA/SCL�s����I2C�Ŏg�p���A�X���[�u���[�h�Ƃ���
    SSP1CON2bits.SEN = 1; // SCL����(�N���b�N�X�g���b�`)���s��
    //  SSP1CON2bits.GCEN = 1;   // �ꊇ�Ăяo���̓���ʒm��L���ɂ���
    SSP1ADD = address << 1; // �}�C�A�h���X�̐ݒ�
    SSP1MSK = 0b11111110; // �A�h���X��r�p�}�X�N�f�[�^
    SSP1IE = 1; // SSP(I2C)���荞�݂�������
    BCL1IE = 1; // MSSP(I2C)�o�X�Փˊ��荞�݂�������
    PEIE = 1; // ���ӑ��u���荞�݂�������
    GIE = 1; // �S���荞�ݏ�����������
    SSP1IF = 0; // SSP(I2C)���荞�݃t���O���N���A����
    BCL1IF = 0; // MSSP(I2C)�o�X�Փˊ��荞�݃t���O���N���A����
    rcv_flg = 0;
    ANSELA &= 0b11111001;
    TRISA |= 0b00000110;
    PORTA &= 0b11111001;
}

int i2cReceiveCheck() {
    int ans;

    ans = 0;
    if (rcv_flg != 0) { // ��M��������
        if ((SSP1STATbits.S == 0) && (SSP1STATbits.P == 1)) { // �į�ߺ��ި��ݔ��s���ꂽ����
            ans = rcv_flg;
            rcv_flg = 0;
        }
    }
    return (ans);
}


#endif