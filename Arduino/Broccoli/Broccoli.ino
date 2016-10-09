#include <Wire.h>

#define BROADCAST_ADDRESS 0x00

byte Mode;

void InitBlopic(){
  //リセット処理
  pinMode(9,OUTPUT);
  digitalWrite(9,LOW);
  delay(500);
  digitalWrite(9,HIGH);
  Serial.end();
  Wire.end();

  //起動処理
  Serial.begin(9600);
  while(!Serial)
  Wire.begin();
  Serial.print("start@");

  //初期状態に
  Mode=0; 
}

void EntryRequest(){
  byte buf=0x00;
  byte count=1;
  while(buf!=0xff){
    Wire.requestFrom(BROADCAST_ADDRESS,1);
    if(Wire.available()>0){
      buf = Wire.read();
      Wire.beginTransmission(BROADCAST_ADDRESS);
      Wire.write(count);
      Wire.endTransmission();
      Serial.print(String(count, DEC)+":"+String(buf, DEC)+"@");
      count++;
    }
  }
}

void CommandRequest(){
  byte addr;
  byte buf;
  byte len;

  len=Serial.available();
  if(len){
    addr=Serial.read();
    if(addr==0xff){InitBlopic();EntryRequest();return;}//終了処理
    for(int i=1;i<len;i++){//アドレスとセットでコマンドを受信した場合
      buf=Serial.read();
      Wire.beginTransmission(addr);
      Wire.write(buf);
      Wire.endTransmission();
      Wire.requestFrom(addr,1);
      if(Wire.available()>0){
        Serial.print(String(Wire.read(), DEC)+"@");
      }
    }
  }
}

void setup() {
  InitBlopic();
  EntryRequest();
}



void loop() {
  CommandRequest();
}
