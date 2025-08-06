package com.foro.hub.foro_hub_api.domain.topic;

import com.foro.hub.foro_hub_api.domain.course.Course;
import com.foro.hub.foro_hub_api.domain.course.CourseRepository;
import com.foro.hub.foro_hub_api.domain.user.User;
import com.foro.hub.foro_hub_api.domain.user.UserRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(
        replace = AutoConfigureTestDatabase.Replace.NONE
)
@ActiveProfiles({"test"})
class TopicRepositoryTest {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager em;

    @Test
    @DisplayName("Debe devolver true si el tópico con título y mensaje ya existe")
    void existsByTitleAndMessage_shouldReturnTrueWhenTopicExists() {
        // GIVEN:
        Course course = courseRepository.save(new Course(null, "Algebra", "Spring Boot"));
        User author = userRepository.save(new User(null, "cristian", "cristian@email.com", "123456"));
        em.persist(course);
        em.persist(author);

        // AND:
        Topic topic = new Topic("Duplicado", "Este mensaje ya existe", course, author);
        em.persist(topic);

        // WHEN:
        boolean exists = topicRepository.existsByTitleAndMessage("Duplicado", "Este mensaje ya existe");

        // THEN:
        assertTrue(exists);
    }

    @Test
    @DisplayName("Debe devolver false si no hay coincidencia entre título y mensaje")

    void existsByTitleAndMessage_shouldReturnFalseWhenNoMatch() {
        // GIVEN:
        // WHEN:
        boolean exists = topicRepository.existsByTitleAndMessage("Inexistente", "Mensaje único");
        // THEN:
        assertFalse(exists);
    }
}