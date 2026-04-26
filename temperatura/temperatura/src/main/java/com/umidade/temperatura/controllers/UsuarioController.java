package com.umidade.temperatura.controllers;

import com.umidade.temperatura.models.UsuarioModel;
import com.umidade.temperatura.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // abrir tela de cadastro
    @GetMapping("/cadastro")
    public String cadastro() {
        return "cadastro";
    }

    // salvar usuário
    @PostMapping("/cadastro")
    public String cadastrar(@RequestParam String email,
                            @RequestParam String senha,
                            Model model) {

        // verifica se já existe
        if (usuarioRepository.findByEmail(email).isPresent()) {
            model.addAttribute("erro", "Email já cadastrado!");
            return "cadastro";
        }

        UsuarioModel usuario = new UsuarioModel();
        usuario.setEmail(email);
        usuario.setSenha(passwordEncoder.encode(senha)); // 🔥 criptografia
        usuario.setRole("USER"); // padrão

        usuarioRepository.save(usuario);

        return "redirect:/login";
    }
}
