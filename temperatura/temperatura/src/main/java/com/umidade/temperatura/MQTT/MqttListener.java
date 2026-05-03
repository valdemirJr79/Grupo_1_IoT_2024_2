package com.umidade.temperatura.MQTT;

import com.umidade.temperatura.dto.SensorDto;
import com.umidade.temperatura.models.SensorData;
import com.umidade.temperatura.services.SensorService;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
public class MqttListener {

    private final ObjectMapper mapper = new ObjectMapper();
    private final SensorService sensorService;

    public MqttListener(SensorService sensorService){
        this.sensorService = sensorService;
    }

    @ServiceActivator(inputChannel = "mqttInputChannel")
    public void receberMensagem(Message<?> message) {
        try {
            String payload = message.getPayload().toString();
            System.out.println(payload);
            SensorDto dto = mapper.readValue(payload, SensorDto.class);

            sensorService.salvarSensorData(dto);

            System.out.println("Salvo no banco: " + payload);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
