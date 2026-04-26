package com.umidade.temperatura.services;

import com.umidade.temperatura.dto.UsuarioDto;
import com.umidade.temperatura.models.UsuarioModel;
import com.umidade.temperatura.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final PasswordEncoder passwordEncoder;
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder){
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void cadastrarUsuario(String email, String senha) {

        if (usuarioRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email já cadastrado!");
        }

        UsuarioModel usuario = new UsuarioModel();
        usuario.setEmail(email);
        usuario.setSenha(passwordEncoder.encode(senha)); // 🔥 regra importante
        usuario.setRole("USER");

        usuarioRepository.save(usuario);
    }

    public Boolean editaUsuario(UsuarioDto dados, Long id){

        Optional<UsuarioModel> usuarioOP = usuarioRepository.findById(id);

        if (usuarioOP.isEmpty()) {
            return false;
        }

        UsuarioModel usuario = usuarioOP.get();

        // 🔥 verifica se email já existe EM OUTRO usuário
        Optional<UsuarioModel> testaEmail = usuarioRepository.findByEmail(dados.getEmail());

        if (testaEmail.isPresent() && !testaEmail.get().getId().equals(id)) {
            return false;
        }

        usuario.setEmail(dados.getEmail());

        // 🔥 só altera senha se foi preenchida
        if (dados.getSenha() != null && !dados.getSenha().isEmpty()) {
            usuario.setSenha(passwordEncoder.encode(dados.getSenha()));
        }

        usuarioRepository.save(usuario);
        return true;
    }

    public Boolean excluirUsuario(Long id){
        Optional<UsuarioModel> usuarioOP = usuarioRepository.findById(id);

        if (usuarioOP.isPresent()){

            usuarioRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public void testeSenha() {
        String senhaDigitada = "123"; // coloque a senha que você usa no login
        String senhaBanco = "$2a$10$lriiIg9ynLqt1xixeMyb2urzzR53G5CDtAWsCS.Y4d6h2GiDYwQOW"; // cole exatamente do banco

        boolean resultado = passwordEncoder.matches(senhaDigitada, senhaBanco);

        System.out.println("Senha bate? " + resultado);
    }

    public List<UsuarioDto> listaUsuario(){
        List<UsuarioDto> listaUsuarioDto = new ArrayList<>();
        List<UsuarioModel> listaUsuario = usuarioRepository.findAll();

        for (UsuarioModel usuario : listaUsuario){
            UsuarioDto usuarioDto = new UsuarioDto();

            usuarioDto.setId(usuario.getId());
            usuarioDto.setEmail(usuario.getEmail());
            usuarioDto.setSenha(usuario.getEmail());
            usuarioDto.setRole(usuario.getRole());

            listaUsuarioDto.add(usuarioDto);
        }
        return listaUsuarioDto;
    }
}
