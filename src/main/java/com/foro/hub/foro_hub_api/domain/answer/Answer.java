package com.foro.hub.foro_hub_api.domain.answer;

import com.foro.hub.foro_hub_api.domain.topic.Topic;
import com.foro.hub.foro_hub_api.domain.user.User;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity(name = "Answer")
@Table(name = "answers")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Setter
@Getter
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;

    @ManyToOne
    private Topic topic;
    private LocalDateTime creationDate = LocalDateTime.now();

    @ManyToOne
    private User author = new User();
    private Boolean isSolution = false;
}
