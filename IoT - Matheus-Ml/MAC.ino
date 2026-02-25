#include <WiFi.h>
#define verde 21

void setup() {
  pinMode(verde, OUTPUT);
  Serial.begin(115200);
  Serial.println("Obtendo endereço MAC...");
  Serial.print("MAC address: ");
  Serial.println(WiFi.macAddress());
}

void loop(){
  digitalWrite(verde, HIGH);
}