char segment[10] = {0xfc,0x60,0xda,0xf2,0x66,0xb6,0xbe,0xe0,0xfe,0xf6};

const int analogInPin = A0;  // Analog input pin that the potentiometer is attached to
const int analogOutPin = 9; // Analog output pin that the LED is attached to
float arus = 0;
int sensorValue = 0;        // value read from the pot
static char outstr[8];
bool RelayState = true;

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  pinMode(4, OUTPUT);
  pinMode(5, OUTPUT);
  pinMode(6, OUTPUT);
  pinMode(7, OUTPUT);
  
  pinMode(8, OUTPUT);
  pinMode(9, OUTPUT);
  pinMode(10, OUTPUT);
  pinMode(11, OUTPUT);
  pinMode(13, OUTPUT);
  digitalWrite(10,RelayState);
  digitalWrite(13,!RelayState);
  
  pinMode(2, INPUT);
  digitalWrite(2, HIGH);
  digitalWrite(11, HIGH);
  delay(2000);
  digitalWrite(11, LOW);
  attachInterrupt(0,PushButton,LOW);
}

void loop() {
  // put your main code here, to run repeatedly:
    int jumlah = 0;
    int value = ((int) (arus*220*10))%10000;
    int angka[4];
    angka[0] = value/1000;
    value %= 1000;
    angka[1] = value/100;
    value %= 100;
    angka[2] = value/10;
    value %= 10;
    angka[3] = value;
    Serial.print(angka[0]);
    Serial.print(angka[1]);
    Serial.print(angka[2]);
    Serial.print('.');
    Serial.print(angka[3]);
    for (int i=1;i<=60;i++){
      if (Serial.available() > 0) {
        byte input = Serial.read();
        if (input == '0'){
          RelayState = false;
          digitalWrite(10,RelayState);
          digitalWrite(13,!RelayState);
        }else if (input == '1'){
          RelayState = true;
          digitalWrite(10,RelayState);
          digitalWrite(13,!RelayState);
        }else if (input == '2'){
          digitalWrite(11,HIGH);
        }else if (input == '3'){
          digitalWrite(11,LOW);
        }
      }
      jumlah += abs(analogRead(analogInPin)-511);
      tampil_angka(arus*220);
    }
    sensorValue = jumlah/60;
    arus = sensorValue*0.026;
}

void tampil_angka(float val)
{
  int value = ((int) (val*10))%10000;
  int angka[4];
  angka[0] = value/1000;
  value %= 1000;
  angka[1] = value/100;
  value %= 100;
  angka[2] = value/10;
  value %= 10;
  angka[3] = value;
  for (int i=0;i<4;i++)
  {
      char hex = segment[angka[i]];
      if (i == 2)
        hex = hex | 1;
      for (int j=0;j<8;j++)
      {
        bool clock = (hex >> j)&0x1;
        digitalWrite(8,!clock);
        digitalWrite(9,HIGH);
        delayMicroseconds(1);
        digitalWrite(9,LOW);
        delayMicroseconds(1);
    
      }
      int digit = 4 + i;
      for (int k=4;k<=7;k++)
      {
        if (k == digit)
          digitalWrite(k,HIGH);
        else
          digitalWrite(k,LOW);  
      }
      delay(4);  
  }
}

void PushButton()
{
  RelayState = !RelayState;
  digitalWrite(10,RelayState);
  digitalWrite(13,!RelayState);
  while(digitalRead(2)==LOW);
}
