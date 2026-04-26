package com.umidade.temperatura.controllers;

import com.umidade.temperatura.models.UsuarioModel;
import com.umidade.temperatura.repositories.UsuarioRepository;
import com.umidade.temperatura.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/cadastro")
    public String cadastro() {
        usuarioService.testeSenha();
        return "cadastro";
    }

    @PostMapping("/cadastro")
    public String cadastrar(@RequestParam String email,
                            @RequestParam String senha,
                            Model model) {

        try {
            usuarioService.cadastrarUsuario(email, senha);
            return "redirect:/login";

        } catch (RuntimeException e) {
            model.addAttribute("erro", e.getMessage());
            return "cadastro";
        }
    }
}
