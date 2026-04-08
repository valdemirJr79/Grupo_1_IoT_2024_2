package com.umidade.temperatura.controllers;

import com.umidade.temperatura.dto.UsuarioDto;
import com.umidade.temperatura.services.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    private UsuarioService usuarioService;

    public LoginController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/login")
    public String viewLogin(Model model){

        UsuarioDto usuarioDto = new UsuarioDto();

        model.addAttribute("usuarioDto", usuarioDto);

        return "login";
    }

    @PostMapping("/login")
    public String autenticar(@RequestParam String email,
                             @RequestParam String senha,
                             Model model) {

        if(email.equals("admin@email.com") && senha.equals("123")) {
            return "redirect:/home";
        }

        model.addAttribute("erro", "Email ou senha inválidos!");
        return "login";
    }

}
