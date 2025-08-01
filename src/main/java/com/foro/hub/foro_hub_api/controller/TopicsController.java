package com.foro.hub.foro_hub_api.controller;

import com.foro.hub.foro_hub_api.domain.course.CourseRepository;
import com.foro.hub.foro_hub_api.domain.topic.*;
import com.foro.hub.foro_hub_api.domain.user.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static org.springframework.data.domain.Sort.Direction.DESC;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;
@RestController
@RequestMapping("/topics")
public class TopicsController {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    @CacheEvict(value = "topicsList")
    public Page<TopicDTO> list(@RequestParam(required = false) String courseName,
                               @RequestParam(required = false) Integer year,
                               @PageableDefault(sort = "creationDate", direction = Sort.Direction.ASC, page = 0, size = 10) Pageable pagination) {

        Page<Topic> topics;
        if (courseName != null && year != null) {
            topics = topicRepository.findByCourseNameAndYear(courseName, year, pagination);

        }
        else if (courseName != null) {
            topics = topicRepository.findByCourseName(courseName, pagination);
        } else {
            topics = topicRepository.findAll(pagination);
        }
        System.out.println(topics.getTotalElements());
        return TopicDTO.convert(topics);
    }

    @PostMapping
    @Transactional
    @CacheEvict(value = "topicsList", allEntries = true)
    public ResponseEntity<TopicDTO> register(@RequestBody @Valid TopicForm form, UriComponentsBuilder uriBuilder) {
        if (topicRepository.existsByTitleAndMessage(form.title(), form.message())){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .build();
        }

        Topic topic = form.convert(courseRepository, userRepository);
        topicRepository.save(topic);

        URI uri = uriBuilder.path("/topics/{id}").buildAndExpand(topic.getId()).toUri();
        return ResponseEntity.created(uri).body(new TopicDTO(topic));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicDetailsDTO> detail(@PathVariable Long id) {
        Optional<Topic> topic = topicRepository.findById(id);
        if (topic.isPresent()) {
            return ResponseEntity.ok(new TopicDetailsDTO(topic.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<TopicDTO> update(@PathVariable Long id, @RequestBody @Valid TopicUpdateForm form) {
        Optional<Topic> optional = topicRepository.findById(id);
        if (optional.isPresent()) {
            Topic topic = form.update(id, topicRepository, courseRepository);
            return ResponseEntity.ok(new TopicDTO(topic));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    @CacheEvict(value = "topicsList", allEntries = true)
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<Topic> topic = topicRepository.findById(id);
        if (topic.isPresent()) {
            topicRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
