package com.foro.hub.foro_hub_api.domain.topic.validaciones;

import com.foro.hub.foro_hub_api.domain.ValidationException;
import com.foro.hub.foro_hub_api.domain.topic.TopicCancelamientoDTO;
import com.foro.hub.foro_hub_api.domain.topic.TopicDTO;
import com.foro.hub.foro_hub_api.domain.topic.TopicFormDTO;
import com.foro.hub.foro_hub_api.domain.topic.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component("ValidadorDeTopicReserva")
public class ValidadorTopic implements ValidadorDeTopic{
    @Autowired
    private TopicRepository topicRepository;

    @Override
    public void validar(TopicFormDTO datos) {
        if (datos.title() == null || datos.message() == null){
            return;
        }

        if (topicRepository.existsByTitleAndMessage(datos.title(), datos.message())){
            throw new ValidationException("El tópico " + datos.title() + " ya existe");
        }
    }
}
