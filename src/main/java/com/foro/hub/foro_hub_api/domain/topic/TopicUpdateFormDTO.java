package com.foro.hub.foro_hub_api.domain.topic;

import com.foro.hub.foro_hub_api.domain.course.Course;
import com.foro.hub.foro_hub_api.domain.course.CourseRepository;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TopicUpdateFormDTO(
        @NotNull Long id,
        @NotBlank String message,
        @NotBlank String courseName,
        @NotBlank String title
        ){
}