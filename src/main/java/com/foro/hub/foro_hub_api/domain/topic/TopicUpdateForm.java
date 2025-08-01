package com.foro.hub.foro_hub_api.domain.topic;

import com.foro.hub.foro_hub_api.domain.course.Course;
import com.foro.hub.foro_hub_api.domain.course.CourseRepository;
import jakarta.validation.constraints.NotBlank;

public record TopicUpdateForm (
        @NotBlank String title,
        @NotBlank String message,
        @NotBlank String courseName
){
    public Topic update(Long id, TopicRepository topicRepository, CourseRepository courseRepository) {
        Topic topic = topicRepository.getReferenceById(id);
        topic.setTitle(this.title);
        topic.setMessage(this.message);
        Course course = courseRepository.findByName(this.courseName)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));
        topic.setCourse(course);
        return topic;
    }
}