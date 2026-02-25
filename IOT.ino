#define verde 21
#define amarelo 22
#define vermelho 23

void setup() {
  pinMode(verde, OUTPUT);
  pinMode(amarelo, OUTPUT);
  pinMode(vermelho, OUTPUT);
}

void loop() {
  digitalWrite(verde, HIGH);  // turn the LED on (HIGH is the voltage level)
  //delay(1000);                      // wait for a second
  //digitalWrite(verde, LOW);   // turn the LED off by making the voltage LOW
  //delay(1000);   
  
  digitalWrite(amarelo, HIGH);  // turn the LED on (HIGH is the voltage level)
  //delay(1000);                      // wait for a second
  //digitalWrite(amarelo, LOW);   // turn the LED off by making the voltage LOW
  //delay(1000); 
  
  digitalWrite(vermelho, HIGH);  // turn the LED on (HIGH is the voltage level)
  //delay(1000);                      // wait for a second
  //digitalWrite(vermelho, LOW);   // turn the LED off by making the voltage LOW
  //delay(1000);                 // wait for a second
}
