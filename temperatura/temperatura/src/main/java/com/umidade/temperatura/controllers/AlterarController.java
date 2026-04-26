package com.umidade.temperatura.controllers;

import com.umidade.temperatura.dto.UsuarioDto;
import com.umidade.temperatura.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AlterarController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/usuarios/alterar")
    public String telaEditarUsuarios(Model model) {

        List<UsuarioDto> usuarios = usuarioService.listaUsuario();

        model.addAttribute("usuarios", usuarios);

        return "alterar"; // nome do seu HTML
    }

    @PostMapping("/usuarios/alterar")
    public String alterarUsuario(
            @RequestParam Long id,
            @RequestParam String email,
            @RequestParam(required = false) String senha,
            Model model
    ) {

        UsuarioDto dto = new UsuarioDto();
        dto.setEmail(email);
        dto.setSenha(senha);

        boolean sucesso = usuarioService.editaUsuario(dto, id);

        if (sucesso) {
            model.addAttribute("sucesso", "Usuário atualizado com sucesso!");
        } else {
            model.addAttribute("erro", "Erro ao atualizar usuário (email já existe ou inválido)");
        }

        // recarrega lista
        model.addAttribute("usuarios", usuarioService.listaUsuario());

        return "alterar"; // volta pra mesma tela
    }
}
