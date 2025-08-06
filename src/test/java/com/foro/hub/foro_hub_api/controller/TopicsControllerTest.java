package com.foro.hub.foro_hub_api.controller;

import com.foro.hub.foro_hub_api.domain.course.Course;
import com.foro.hub.foro_hub_api.domain.course.CourseRepository;
import com.foro.hub.foro_hub_api.domain.topic.TopicDetailsDTO;
import com.foro.hub.foro_hub_api.domain.topic.TopicFormDTO;
import com.foro.hub.foro_hub_api.domain.topic.TopicRepository;
import com.foro.hub.foro_hub_api.domain.topic.TopicStatus;
import com.foro.hub.foro_hub_api.domain.user.User;
import com.foro.hub.foro_hub_api.domain.user.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@Transactional
class TopicsControllerTest {

@Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private TopicRepository topicRepository;

    @BeforeEach
    void prepararDatos() {
        topicRepository.deleteAll();
        courseRepository.deleteAll();
        userRepository.deleteAll();
        if (!userRepository.existsByLogin("autor@email.com")) {

            userRepository.save(new User(null, "Autor de prueba", "autor@email.com", "123456"));
        }
        courseRepository.save(new Course(null, "Curso de prueba", "Spring Boot"));
    }

    @Autowired
    private JacksonTester<TopicFormDTO> datosReservaTopicJson;
    @Autowired
    private JacksonTester<TopicDetailsDTO> datosDetalleTopicJson;
    @Autowired
    private MockMvc mvc;
    @Test
    @DisplayName("Deberia devolver http 400 cuando la request no tenga datos")
    @WithMockUser
    void registrar_escenario1() throws Exception {
        var response = mvc.perform(post("/topics"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Debería devolver http 201 cuando la request reciba un json válido")
    @WithMockUser
    void registrar_escenario2() throws Exception {
        var dto = new TopicFormDTO(
                "Título de prueba",
                "Mensaje de prueba",
                "Autor de prueba",
                "Curso de prueba"
        );

        var jsonRequest = datosReservaTopicJson.write(dto).getJson();

        var response = mvc.perform(post("/topics")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());

        var respuestaDto = datosDetalleTopicJson.parseObject(response.getContentAsString());

        assertThat(respuestaDto.title()).isEqualTo("Título de prueba");
        assertThat(respuestaDto.message()).isEqualTo("Mensaje de prueba");
        assertThat(respuestaDto.authorName()).isEqualTo("Autor de prueba");
        assertThat(respuestaDto.courseName()).isEqualTo("Curso de prueba");
        assertThat(respuestaDto.status()).isEqualTo(TopicStatus.UNANSWERED);
        assertThat(respuestaDto.id()).isNotNull();
        assertThat(respuestaDto.creationDate()).isNotNull();
    }
}