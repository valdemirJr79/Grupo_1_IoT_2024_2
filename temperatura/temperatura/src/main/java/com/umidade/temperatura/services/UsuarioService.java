package com.umidade.temperatura.services;

import com.umidade.temperatura.dto.UsuarioDto;
import com.umidade.temperatura.models.UsuarioModel;
import com.umidade.temperatura.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    private UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }

    public Boolean criarUsuario(UsuarioDto dados){
        UsuarioModel usuarioModel = new UsuarioModel();

        usuarioModel.setEmail(dados.getEmail());
        usuarioModel.setSenha(dados.getSenha());
        usuarioRepository.save(usuarioModel);

        return true;
    }

    public UsuarioModel validaUsuario(UsuarioDto dados){

        System.out.println("EMAIL: " + dados.getEmail());
        System.out.println("SENHA: " + dados.getSenha());

        Optional<UsuarioModel> usuarioOP =
                usuarioRepository.findByEmailAndSenha(
                        dados.getEmail().trim(),
                        dados.getSenha().trim()
                );

        System.out.println("Encontrou? " + usuarioOP.isPresent());

        return usuarioOP.orElse(null);
    }
}
