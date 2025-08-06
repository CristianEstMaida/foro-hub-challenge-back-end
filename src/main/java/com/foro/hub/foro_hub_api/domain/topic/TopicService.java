package com.foro.hub.foro_hub_api.domain.topic;

import com.foro.hub.foro_hub_api.domain.ValidationException;
import com.foro.hub.foro_hub_api.domain.course.Course;
import com.foro.hub.foro_hub_api.domain.course.CourseRepository;
import com.foro.hub.foro_hub_api.domain.topic.validaciones.ValidadorCancelamientoDeTopic;
import com.foro.hub.foro_hub_api.domain.topic.validaciones.ValidadorTopic;
import com.foro.hub.foro_hub_api.domain.user.UserRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicService {
    private final TopicRepository topicRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final List<ValidadorTopic> validadores;
    private final List<ValidadorCancelamientoDeTopic> validadoresCancelamiento;

    @Autowired
    public TopicService(TopicRepository topicRepository, CourseRepository courseRepository,
                        UserRepository userRepository,
                        List<ValidadorTopic> validadores,
                        List<ValidadorCancelamientoDeTopic> validadoresCancelamiento) {
        this.topicRepository = topicRepository;
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.validadores = validadores;
        this.validadoresCancelamiento = validadoresCancelamiento;
    }

    public Topic update(TopicUpdateFormDTO form) {
        Topic topic = topicRepository.findById(form.id())
                .orElseThrow(() -> new ValidationException("Topic no encontrado"));

        topic.setTitle(form.title());
        topic.setMessage(form.message());
        topic.setCourse(getValidCourse(form.courseName()));

        return topic;
    }

    private Course getValidCourse(@NotBlank String courseName) {
        return courseRepository.findByName(courseName)
                .orElseThrow(() -> new ValidationException("Curso no existe"));
    }

    public TopicDetailsDTO register(@Valid TopicFormDTO datos) {
        validadores.forEach(v -> v.validar(datos));
        Topic topic = datos.convert(courseRepository, userRepository);
        topicRepository.save(topic);
        return new TopicDetailsDTO(topic);
    }

    public void delete(TopicCancelamientoDTO datos) {
        validadoresCancelamiento.forEach(v -> v.validar(datos));
        topicRepository.deleteById(datos.id());
    }

    public Page<TopicDetailsDTO> list(String courseName, Integer year, Pageable pagination) {
        Page<Topic> topics;

        if (courseName != null && year != null) {
            topics = topicRepository.findByCourseNameAndYear(courseName, year, pagination);
        } else if (courseName != null) {
            topics = topicRepository.findByCourseName(courseName, pagination);
        } else {
            topics = topicRepository.findAll(pagination);
        }
        return topics.map(TopicDetailsDTO::new);
    }

    public TopicDetailsDTO getDetails(Long id) {
        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new ValidationException("El tópico con ID " + id + " no fue encontrado"));
        return new TopicDetailsDTO(topic);

    }
}
