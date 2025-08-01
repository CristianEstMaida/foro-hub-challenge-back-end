package com.foro.hub.foro_hub_api.domain.topic;

import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

public record TopicDTO(Long id,
                       String title,
                       String message,
                       LocalDateTime creationDate) {
    public TopicDTO(Topic topic) {
        this(topic.getId(), topic.getTitle(), topic.getMessage(), topic.getCreationDate());
    }

    public static Page<TopicDTO> convert(Page<Topic> topics) {
        return topics.map(TopicDTO::new);
    }
}
