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
    public String autenticar(@ModelAttribute UsuarioDto usuarioDto, Model model) {
        System.out.println(usuarioDto.getEmail());
        System.out.println(usuarioDto.getSenha());

        UsuarioModel usuarioModel = usuarioService.validaUsuario(usuarioDto);

        if (usuarioModel != null){
            System.out.println(usuarioModel.getEmail());
            System.out.println(usuarioModel.getSenha());
            System.out.println("chegou no if do logincontroller");
            UsuarioSessaoDto usuarioSessaoDto = new UsuarioSessaoDto(usuarioModel.getId(),usuarioModel.getEmail(), usuarioModel.getSenha());
            return "redirect:/home";
        }

        model.addAttribute("erro", "Email ou senha inválidos!");
        return "login";
    }

}
