package com.umidade.temperatura.controllers;

import com.umidade.temperatura.models.SensorData;
import com.umidade.temperatura.repositories.SensorDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/sensores")
public class SensorController {

    @Autowired
    private SensorDataRepository repository;

    @PostMapping
    public SensorData salvar(@RequestBody SensorData data) {
        return repository.save(data);
    }
}