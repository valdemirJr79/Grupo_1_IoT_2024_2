package com.umidade.temperatura;

import com.umidade.temperatura.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.integration.config.EnableIntegration;

@EnableIntegration
@SpringBootApplication(scanBasePackages = "com.umidade.temperatura")
public class TemperaturaApplication {

	public static void main(String[] args) {
		SpringApplication.run(TemperaturaApplication.class, args);
	}

}
