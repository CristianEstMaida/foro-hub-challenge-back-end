package com.foro.hub.foro_hub_api.domain.user;

import com.foro.hub.foro_hub_api.domain.perfil.Perfil;
import com.foro.hub.foro_hub_api.domain.perfil.PerfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PerfilRepository perfilRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository usuarioRepository, PerfilRepository perfilRepository) {
        this.userRepository = usuarioRepository;
        this.perfilRepository = perfilRepository;
    }

    public List<UserDetailsDTO> listarTodos() {
        return userRepository.findAll()
                .stream()
                .map(usuario -> new UserDetailsDTO(usuario))
                .collect(Collectors.toList());
    }


    public User registrar(UserFormDTO datos) {
        if (userRepository.existsByLogin(datos.login())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El login ya está registrado");
        }

        Perfil perfil = perfilRepository.findByNombre("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Perfil 'ROLE_USER' no encontrado"));


        User user = new User(null, datos.name(), datos.login(), passwordEncoder.encode(datos.password()), Set.of(perfil));
        return userRepository.save(user);
    }


}
