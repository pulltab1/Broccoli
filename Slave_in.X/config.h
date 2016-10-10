#ifndef CONFIG_H
#define	CONFIG_H

#define _XTAL_FREQ 8000000

// �R���t�B�M�����[�V�����P�̐ݒ�
#pragma config FOSC     = INTOSC   // �����ۯ��g�p����(INTOSC)
#pragma config WDTE     = OFF      // �����ޯ����ϰ����(OFF)
#pragma config PWRTE    = ON       // �d��ON����64ms�����۸��т��J�n����(ON)
#pragma config MCLRE    = OFF      // �O��ؾ�ĐM���͎g�p�������޼��ٓ���(RA3)��݂Ƃ���(OFF)
#pragma config CP       = OFF      // ��۸�����ذ��ی삵�Ȃ�(OFF)
#pragma config CPD      = OFF      // �ް���ذ��ی삵�Ȃ�(OFF)
#pragma config BOREN    = ON       // �d���d���~���펞�Ď��@�\ON(ON)
#pragma config CLKOUTEN = OFF      // CLKOUT��݂�RA4��݂Ŏg�p����(OFF)
#pragma config IESO     = OFF      // �O���E�����ۯ��̐ؑւ��ł̋N���͂Ȃ�(OFF)
#pragma config FCMEN    = OFF      // �O���ۯ��Ď����Ȃ�(OFF)

// �R���t�B�M�����[�V�����Q�̐ݒ�
#pragma config WRT    = OFF        // Flash��ذ��ی삵�Ȃ�(OFF)
#pragma config PLLEN  = OFF        // ����N���b�N��32MHz�ł͓��삳���Ȃ�(OFF)
#pragma config STVREN = ON         // �X�^�b�N���I�[�o�t���[��A���_�[�t���[�����烊�Z�b�g������(ON)
#pragma config BORV   = HI         // �d���d���~���펞�Ď��d��(2.5V)�ݒ�(HI)
#pragma config LVP    = OFF        // ��d���v���O���~���O�@�\�g�p���Ȃ�(OFF)

#endif

