package com.foro.hub.foro_hub_api.domain.topic;

import java.time.LocalDateTime;

public record TopicDetailsDTO(
        Long id,
        String title,
        String message,
        LocalDateTime creationDate,
        String courseName,
        TopicStatus status,
        String authorName
) {
    public TopicDetailsDTO(Topic topic) {
        this(
                topic.getId(),
                topic.getTitle(),
                topic.getMessage(),
                topic.getCreationDate(),
                topic.getCourse().getName(),
                topic.getStatus(),
                topic.getUser().getName()
        );
    }
}
