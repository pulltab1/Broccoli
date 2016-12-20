#ifndef CONFIG_H
#define	CONFIG_H

#define _XTAL_FREQ 8000000

// コンフィギュレーション１の設定
#pragma config FOSC     = INTOSC   // 内部ｸﾛｯｸ使用する(INTOSC)
#pragma config WDTE     = OFF      // ｳｵｯﾁﾄﾞｯｸﾞﾀｲﾏｰ無し(OFF)
#pragma config PWRTE    = ON       // 電源ONから64ms後にﾌﾟﾛｸﾞﾗﾑを開始する(ON)
#pragma config MCLRE    = OFF      // 外部ﾘｾｯﾄ信号は使用せずにﾃﾞｼﾞﾀﾙ入力(RA3)ﾋﾟﾝとする(OFF)
#pragma config CP       = OFF      // ﾌﾟﾛｸﾞﾗﾑﾒﾓﾘｰを保護しない(OFF)
#pragma config CPD      = OFF      // ﾃﾞｰﾀﾒﾓﾘｰを保護しない(OFF)
#pragma config BOREN    = ON       // 電源電圧降下常時監視機能ON(ON)
#pragma config CLKOUTEN = OFF      // CLKOUTﾋﾟﾝをRA4ﾋﾟﾝで使用する(OFF)
#pragma config IESO     = OFF      // 外部・内部ｸﾛｯｸの切替えでの起動はなし(OFF)
#pragma config FCMEN    = OFF      // 外部ｸﾛｯｸ監視しない(OFF)

// コンフィギュレーション２の設定
#pragma config WRT    = OFF        // Flashﾒﾓﾘｰを保護しない(OFF)
#pragma config PLLEN  = OFF        // 動作クロックを32MHzでは動作させない(OFF)
#pragma config STVREN = ON         // スタックがオーバフローやアンダーフローしたらリセットをする(ON)
#pragma config BORV   = HI         // 電源電圧降下常時監視電圧(2.5V)設定(HI)
#pragma config LVP    = OFF        // 低電圧プログラミング機能使用しない(OFF)

#endif

