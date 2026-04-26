package com.umidade.temperatura.services;

import com.umidade.temperatura.dto.UsuarioDto;
import com.umidade.temperatura.models.UsuarioModel;
import com.umidade.temperatura.repositories.UsuarioRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class UsuarioService {

    private final PasswordEncoder passwordEncoder;
    private UsuarioRepository usuarioRepository;

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

        if (usuarioOP.isPresent()){
            Optional<UsuarioModel> testaEmail = usuarioRepository.findByEmail(dados.getEmail());

            if (testaEmail.isPresent()){
                return false;
            }

            UsuarioModel usuario = usuarioOP.get();

            usuario.setEmail(dados.getEmail());
            usuario.setSenha(dados.getSenha());
            usuarioRepository.save(usuario);
            return true;
        } else {
            return false;
        }
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

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> usuarioRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }
}
