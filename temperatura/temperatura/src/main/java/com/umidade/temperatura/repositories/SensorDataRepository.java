package com.umidade.temperatura.repositories;


import com.umidade.temperatura.models.SensorData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorDataRepository extends JpaRepository<SensorData, Long> {
}
