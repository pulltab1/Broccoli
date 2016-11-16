#include <Wire.h>

#define BROADCAST_ADDRESS (int)(0x00)
#define ENTRY_TIMEOUT (10)

/*
 * 初期化命令
 */
void InitBlopic() {
  Serial.end();
  //起動処理
  Serial.begin(9600);
  while (!Serial)
  Wire.begin();
}

/*
 * 新規に接続されたブロックがあるか確認し、ある場合は上位側に通知する。
 */
void EntryRequest(){
  int count = 0;//タイマー用
  Serial.print("begin@");
  while (count < ENTRY_TIMEOUT) {
    Wire.requestFrom(BROADCAST_ADDRESS, 1);
    if (Wire.available() > 0) {
      byte buf = Wire.read();
      Wire.beginTransmission(BROADCAST_ADDRESS);
      Wire.write(count);
      if(Wire.endTransmission()==0){
        Serial.print(String(buf, DEC) + "@");//上位側に接続されたブロックの種類を通知する
        count = 0;
      }
    }
    delay(1);
    count++;
  }
  Serial.print("end@");
}
/*
 * マスタ側からの命令に従ってデータの送受信を行う
 */
void CommandRequest() {
  byte len = Serial.available();
  if (len>0) {
    byte addr = Serial.read();
    for (int i = 1; i < len; i++) { //アドレスとセットでコマンドを受信した場合
      byte buf = Serial.read();
      Wire.beginTransmission(addr);
      Wire.write(buf);
      if(Wire.endTransmission()==0){
        Wire.requestFrom(addr, 1);
        if (Wire.available() > 0) {
          byte data = Wire.read();
          Serial.print(String(data, DEC) + "@");
        }
      }
    }
  }
}

void setup() {
  InitBlopic();
}

void loop() {
  EntryRequest();
  CommandRequest();
}
