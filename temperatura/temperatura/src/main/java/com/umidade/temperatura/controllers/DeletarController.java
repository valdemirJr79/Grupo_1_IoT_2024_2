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
public class DeletarController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/usuarios/deletar")
    public String telaDeletarUsuarios(Model model) {

        List<UsuarioDto> usuarios = usuarioService.listaUsuario();

        model.addAttribute("usuarios", usuarios);

        return "deletar";
    }

    @PostMapping("/usuarios/deletar")
    public String deletarUsuario(
            @RequestParam Long id,
            Model model
    ) {

        boolean sucesso = usuarioService.excluirUsuario(id);

        if (sucesso) {
            model.addAttribute("sucesso", "Usuário deletado com sucesso.");
        } else {
            model.addAttribute("erro", "Usuário não foi possível excluir.");
        }

        model.addAttribute("usuarios", usuarioService.listaUsuario());

        return "deletar";
    }
}
