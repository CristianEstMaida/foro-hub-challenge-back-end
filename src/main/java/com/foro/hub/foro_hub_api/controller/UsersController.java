package com.foro.hub.foro_hub_api.controller;

import com.foro.hub.foro_hub_api.domain.perfil.Perfil;
import com.foro.hub.foro_hub_api.domain.perfil.PerfilRepository;
import com.foro.hub.foro_hub_api.domain.user.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/usuario")
@SecurityRequirement(name = "bearer-key")
public class UsersController {

    private final UserService usuarioService;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UsersController(UserService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Transactional
    @PostMapping
    public ResponseEntity<UserDetailsDTO> registrarUsuario(@RequestBody @Valid UserFormDTO datos, UriComponentsBuilder uriBuilder) {
        User user = usuarioService.registrar(datos);

        URI uri = uriBuilder.path("/usuario/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(new UserDetailsDTO(user));
    }

    @GetMapping
    public ResponseEntity<List<UserDetailsDTO>> listarTodos() {
        List<UserDetailsDTO> usuarios = usuarioService.listarTodos();
        return ResponseEntity.ok(usuarios);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDetailsDTO> actualizarUsuario(@PathVariable Long id, @RequestBody @Valid UserFormDTO datos) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        user.setName(datos.name());
        user.setLogin(datos.login());
        user.setPassword(passwordEncoder.encode(datos.password()));

        userRepository.save(user);

        return ResponseEntity.ok(new UserDetailsDTO(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
        }

        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}

