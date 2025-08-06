package com.foro.hub.foro_hub_api.controller;

import com.foro.hub.foro_hub_api.domain.course.CourseRepository;
import com.foro.hub.foro_hub_api.domain.topic.*;
import com.foro.hub.foro_hub_api.domain.user.UserRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/topics")
@SecurityRequirement(name = "bearer-key")
public class TopicsController {


    private final TopicService topicService;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    public TopicsController(TopicService topicService) {
        this.topicService = topicService;
    }

    @GetMapping
    @CacheEvict(value = "topicsList")
    public ResponseEntity<Page<TopicDetailsDTO>> list(@RequestParam(required = false) String courseName,
                               @RequestParam(required = false) Integer year,
                               @PageableDefault(sort = "creationDate", direction = Sort.Direction.ASC, page = 0, size = 10) Pageable pagination) {
        var response = topicService.list(courseName, year, pagination);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Transactional
    @CacheEvict(value = "topicsList", allEntries = true)
    public ResponseEntity<TopicDetailsDTO> register(@RequestBody @Valid TopicFormDTO form, UriComponentsBuilder uriBuilder) {
        TopicDetailsDTO topicDTO = topicService.register(form);

        URI uri = uriBuilder.path("/topics/{id}").buildAndExpand(topicDTO.id()).toUri();
        return ResponseEntity.created(uri).body(topicDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicDetailsDTO> detail(@PathVariable Long id) {
        TopicDetailsDTO dto = topicService.getDetails(id); // Maneja lógica y errores
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<TopicDetailsDTO> update(@PathVariable Long id, @RequestBody @Valid TopicUpdateFormDTO form) {
        var response = topicService.update(form);
        return ResponseEntity.ok(new TopicDetailsDTO(response));
    }

    @DeleteMapping("/{id}")
    @Transactional
    @CacheEvict(value = "topicsList", allEntries = true)
    public ResponseEntity<?> delete(@PathVariable TopicCancelamientoDTO datos) {
        topicService.delete(datos);
        return ResponseEntity.noContent().build();
    }
}
