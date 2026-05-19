#include <ArduinoOTA.h>
#include <WiFi.h>
#include "DHT.h"
#include <PubSubClient.h>

#define DHTPIN 2
#define DHTTYPE DHT11

DHT dht(DHTPIN, DHTTYPE);

// WiFi
const char* ssid = "REDEWORK";
const char* password = "Acessonet05";

// MQTT (HiveMQ público)
const char* mqtt_server = "192.168.1.8";
const int mqtt_port = 1883;
const char* mqtt_topic = "esp32/sensor";

WiFiClient espClient;
PubSubClient client(espClient);

void reconnectMQTT() {
  while (!client.connected()) {
    Serial.print("Conectando ao MQTT...");

    String clientId = "ESP32-" + WiFi.macAddress();

    if (client.connect(clientId.c_str())) {
      Serial.println("Conectado!");
    } else {
      Serial.print("Falha, rc=");
      Serial.print(client.state());
      Serial.println(" tentando novamente em 5s");
      delay(5000);
    }
  }
}

void setup() {
  dht.begin(); 
  Serial.begin(115200);

  WiFi.begin(ssid, password);
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }

  Serial.println("\nConectado ao Wi-Fi");
  Serial.println(WiFi.localIP());
  Serial.println(WiFi.macAddress());

  client.setServer(mqtt_server, mqtt_port);

  // OTA (igual ao seu)
  ArduinoOTA.begin();
}

void loop() {
  ArduinoOTA.handle();

  if (!client.connected()) {
    reconnectMQTT();
  }
  client.loop();

  float umidade = dht.readHumidity();
  float temperatura = dht.readTemperature();

  if (isnan(umidade) || isnan(temperatura)) {
    Serial.println("{\"erro\":\"falha_leitura\"}");
    return;
  }

  // Monta JSON
  String payload = "{";
  payload += "\"temperatura\":" + String(temperatura) + ",";
  payload += "\"umidade\":" + String(umidade);
  payload += "}";

  // Envia MQTT
  client.publish(mqtt_topic, payload.c_str());

  // Debug serial
  Serial.println(payload);

  delay(2000);
}