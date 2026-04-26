package com.umidade.temperatura.controllers;

import com.umidade.temperatura.dto.UsuarioDto;
import com.umidade.temperatura.dto.UsuarioSessaoDto;
import com.umidade.temperatura.models.UsuarioModel;
import com.umidade.temperatura.services.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    private UsuarioService usuarioService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

}
