package com.foro.hub.foro_hub_api.controller;

import com.foro.hub.foro_hub_api.domain.answer.Answer;
import com.foro.hub.foro_hub_api.domain.answer.AnswerDetailsDTO;
import com.foro.hub.foro_hub_api.domain.answer.AnswerFormDTO;
import com.foro.hub.foro_hub_api.domain.answer.AnswerRepository;
import com.foro.hub.foro_hub_api.domain.topic.Topic;
import com.foro.hub.foro_hub_api.domain.topic.TopicRepository;
import com.foro.hub.foro_hub_api.domain.user.User;
import com.foro.hub.foro_hub_api.domain.user.UserRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/respuestas")
@SecurityRequirement(name = "bearer-key")
public class AnswerController {

    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TopicRepository topicRepository;

    @PostMapping
    public ResponseEntity<AnswerDetailsDTO> crear(@RequestBody @Valid AnswerFormDTO datos, UriComponentsBuilder uriBuilder) {
        User autor = userRepository.findById(datos.autorId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Autor no encontrado"));

        Topic topico = topicRepository.findById(datos.topicoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tópico no encontrado"));

        Answer answer = new Answer();
        answer.setMessage(datos.mensaje());
        answer.setAuthor(autor);
        answer.setTopic(topico);
        answer.setCreationDate(LocalDateTime.now());
        answerRepository.save(answer);
        URI uri = uriBuilder.path("/respuestas/{id}").buildAndExpand(answer.getId()).toUri();
        return ResponseEntity.created(uri).body(new AnswerDetailsDTO(answer));
    }

    @GetMapping
    public ResponseEntity<List<AnswerDetailsDTO>> listar() {
        List<AnswerDetailsDTO> respuestas = answerRepository.findAll()
                .stream()
                .map(AnswerDetailsDTO::new)
                .toList();

        return ResponseEntity.ok(respuestas);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnswerDetailsDTO> actualizar(@PathVariable Long id, @RequestBody @Valid AnswerFormDTO datos) {
        Answer answer = answerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Respuesta no encontrada"));

        answer.setMessage(datos.mensaje());
        answerRepository.save(answer);

        return ResponseEntity.ok(new AnswerDetailsDTO(answer));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!answerRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Respuesta no encontrada");
        }

        answerRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

