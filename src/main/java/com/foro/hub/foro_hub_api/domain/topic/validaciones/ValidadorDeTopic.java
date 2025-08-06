package com.foro.hub.foro_hub_api.domain.topic.validaciones;

import com.foro.hub.foro_hub_api.domain.topic.TopicFormDTO;

public interface ValidadorDeTopic {
    void validar(TopicFormDTO datos);
}
