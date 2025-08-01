package com.foro.hub.foro_hub_api.domain.topic;

import com.foro.hub.foro_hub_api.domain.course.Course;
import com.foro.hub.foro_hub_api.domain.course.CourseRepository;
import com.foro.hub.foro_hub_api.domain.user.User;
import com.foro.hub.foro_hub_api.domain.user.UserRepository;
import jakarta.validation.constraints.NotBlank;

public record TopicForm (
        @NotBlank String title,
        @NotBlank String message,
        @NotBlank String authorName,
        @NotBlank String courseName
){
    public Topic convert(CourseRepository courseRepository, UserRepository userRepository) {
        User author = userRepository.findByName(authorName).orElseThrow(() -> new IllegalArgumentException("User not found"));
        Course course = courseRepository.findByName(courseName).orElseThrow(() -> new IllegalArgumentException("Course not found"));
        return new Topic(title, message, course, author);
    }
}
