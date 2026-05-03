package com.umidade.temperatura.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SensorDto {
    private Double temperatura;
    private Double umidade;
    private String timestamp;
}

