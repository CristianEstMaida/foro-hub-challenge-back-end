package com.foro.hub.foro_hub_api.domain.answer;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class AnswerDTO {

    private Long id;
    private String message;
    private LocalDateTime creationDate;
    private String authorName;

    public AnswerDTO(Answer answer) {
        this.id = answer.getId();
        this.message = answer.getMessage();
        this.creationDate = answer.getCreationDate();
        this.authorName = answer.getAuthor().getName();
    }
}
