package com.foro.hub.foro_hub_api.domain.topic;

import com.foro.hub.foro_hub_api.domain.answer.Answer;
import com.foro.hub.foro_hub_api.domain.course.Course;
import com.foro.hub.foro_hub_api.domain.user.User;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Topic")
@Table(name = "topics")
@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode(of = "id")
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "titulo")
    private String title;
    @Column(name = "mensaje")
    private String message;
    @Column(name = "fecha_creacion")
    private LocalDateTime creationDate = LocalDateTime.now();
    @Enumerated(EnumType.STRING)
    private TopicStatus status = TopicStatus.UNANSWERED;
    @ManyToOne
    @JoinColumn(name = "autor")
    private User user;
    @ManyToOne
    @JoinColumn(name = "curso")
    private Course course;
    @OneToMany(mappedBy = "topic")
    private List<Answer> answers = new ArrayList<>();

    public Topic(String title, String message, Course course, User user) {
        this.title = title;
        this.message = message;
        this.course = course;
        this.user = user;
    }
}
