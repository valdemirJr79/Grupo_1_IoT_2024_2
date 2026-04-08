
#include <ArduinoOTA.h>
#include <WiFi.h>
#include "DHT.h"

#define DHTPIN 2 // Define o pino do LED embutido
#define DHTTYPE DHT11

DHT dht(DHTPIN, DHTTYPE);

const char* ssid = "REDEWORK";
const char* password = "Acessonet05";

void setup() {
  dht.begin(); 
  Serial.begin(115200);
  WiFi.begin(ssid, password);
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  Serial.println("Conectado à rede Wi-Fi");
  Serial.print("Endereço IP: ");
  Serial.println(WiFi.localIP());

  ArduinoOTA.onStart([]() {
    String type;
    if (ArduinoOTA.getCommand() == U_FLASH) {
      type = "sketch";
    } else {
      type = "filesystem";
    }
    Serial.println("Iniciando atualização de " + type);
  });
  ArduinoOTA.onEnd([]() {
    Serial.println("\nFim");
  });
  ArduinoOTA.onProgress([](unsigned int progress, unsigned int total) {
    Serial.printf("Progresso: %u%%\r", (progress / (total / 100)));
  });
  ArduinoOTA.onError([](ota_error_t error) {
    Serial.printf("Erro [%u]: ", error);
    if (error == OTA_AUTH_ERROR) Serial.println("Erro de autenticação");
    else if (error == OTA_BEGIN_ERROR) Serial.println("Erro ao iniciar");
    else if (error == OTA_CONNECT_ERROR) Serial.println("Erro de conexão");
    else if (error == OTA_RECEIVE_ERROR) Serial.println("Erro ao receber");
    else if (error == OTA_END_ERROR) Serial.println("Erro ao finalizar");
  });
  ArduinoOTA.begin();
  Serial.println("Pronto para OTA");



}

void loop() {
  ArduinoOTA.handle();
  delay(2000);

  float umidade = dht.readHumidity();
  float temperatura = dht.readTemperature();

  if (isnan(umidade) || isnan(temperatura)) {
    Serial.println("{\"erro\":\"falha_leitura\"}");
    return;
  }

  // Envia no formato JSON
  Serial.print("{\"temperatura\":");
  Serial.print(temperatura);
  Serial.print(",\"umidade\":");
  Serial.print(umidade);
  Serial.println("}");

}