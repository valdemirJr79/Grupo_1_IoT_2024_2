package com.umidade.temperatura.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioSessaoDto {
    private Long id;
    private String email;
    private String senha;
}
