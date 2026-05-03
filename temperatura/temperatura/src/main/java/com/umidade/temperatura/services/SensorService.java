package com.umidade.temperatura.services;

import com.umidade.temperatura.dto.SensorDto;
import com.umidade.temperatura.models.SensorData;
import com.umidade.temperatura.repositories.SensorDataRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SensorService {

    private final SensorDataRepository sensorDataRepository;

    public SensorService(SensorDataRepository sensorDataRepository){
        this.sensorDataRepository = sensorDataRepository;
    }

    public void salvarSensorData(SensorDto dados){

        SensorData sensorData = new SensorData();

        if (dados!=null){
            sensorData.setTemperatura(dados.getTemperatura());
            System.out.println(dados.getTemperatura());

            sensorData.setUmidade(dados.getUmidade());
            System.out.println(dados.getUmidade());

            sensorData.setTimestamp(java.time.OffsetDateTime.parse(dados.getTimestamp()).toLocalDateTime());
            System.out.println(dados.getTimestamp());

            sensorDataRepository.save(sensorData);
        }

    }

}
