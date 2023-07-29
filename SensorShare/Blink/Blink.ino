int ileriGit=3;
int geriGit=2;
int sagDon=4;
int far=5;

void setup() {
  pinMode(ileriGit, OUTPUT);
  pinMode(geriGit, OUTPUT);
  pinMode(sagDon, OUTPUT);
  pinMode(far, OUTPUT);
  Serial.begin(9600);
}
int data=0;
void loop() {
  if(Serial.available()>0){
    data = Serial.read();
    if(data == '1'){
      digitalWrite(ileriGit,HIGH);
      delay(1000);
      digitalWrite(ileriGit,LOW);
    }
    else if(data == '2'){
      digitalWrite(geriGit,HIGH);
      delay(1000);
      digitalWrite(geriGit,LOW);
    }else if(data == '3'){
      digitalWrite(sagDon,HIGH);
      delay(1000);
      digitalWrite(sagDon,LOW);
    }else if(data == '4'){
      digitalWrite(far,HIGH);
    }else if(data == '5'){
      digitalWrite(far,LOW);
    }
  }
}
