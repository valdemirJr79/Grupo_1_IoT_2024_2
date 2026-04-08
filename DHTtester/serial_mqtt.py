import serial
import paho.mqtt.client as mqtt
import time
import json

# Configurações da porta serial
serial_port = 'COM4'
baud_rate = 115200

# Configurações do broker MQTT
broker_address = "broker.hivemq.com"
broker_port = 1883
topic = "SensorDHT11"
client_id = "SensorDHT11"

# Cria um cliente MQTT
client = mqtt.Client(client_id=client_id, protocol=mqtt.MQTTv311)

def on_connect(client, userdata, flags, rc):
    if rc == 0:
        print("Conectado ao broker MQTT!")
    else:
        print(f"Falha na conexão, código de retorno: {rc}")

def on_disconnect(client, userdata, rc):
    print("Desconectado do broker MQTT!")

client.on_connect = on_connect
client.on_disconnect = on_disconnect

# Conecta ao broker MQTT
client.connect(broker_address, broker_port, 60)
client.loop_start()

try:
    # Abre a porta serial
    ser = serial.Serial(serial_port, baud_rate, timeout=1)
    print(f"Porta serial {serial_port} aberta. Lendo dados...")

    while True:
        # Lê uma linha da porta serial
        if ser.in_waiting > 0:
            line = ser.readline().decode('utf-8', errors='ignore').strip()

            if line:
                print(f"Dados recebidos da serial: {line}")

                try:
                    # 🔥 Garante que começa no JSON (evita lixo da serial)
                    if "{" in line:
                        line = line[line.find("{"):]

                    # Converte JSON vindo do ESP32
                    data = json.loads(line)

                    temperatura = data.get("temperatura")
                    umidade = data.get("umidade")

                    print(f"Temperatura: {temperatura} °C")
                    print(f"Umidade: {umidade} %")

                    # Publica no MQTT (mantém JSON)
                    result = client.publish(topic, json.dumps(data))
                    result.wait_for_publish()

                    if result.is_published():
                        print("Dados publicados com sucesso!")
                    else:
                        print(f"Falha ao publicar os dados. Erro: {result.rc}")

                except json.JSONDecodeError:
                    print("Erro ao decodificar JSON (linha inválida)")
                    print(f"RAW: {repr(line)}")
                except Exception as e:
                    print(f"Erro ao processar dados: {e}")

        time.sleep(0.1)

except serial.SerialException as e:
    print(f"Erro ao abrir a porta serial: {e}")

except KeyboardInterrupt:
    print("Programa encerrado pelo usuário.")

finally:
    client.loop_stop()
    client.disconnect()

    if 'ser' in locals() and ser.is_open:
        ser.close()

    print("Conexão MQTT e porta serial fechadas.")