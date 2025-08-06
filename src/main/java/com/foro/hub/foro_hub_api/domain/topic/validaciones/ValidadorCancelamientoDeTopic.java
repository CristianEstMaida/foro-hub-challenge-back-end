package com.foro.hub.foro_hub_api.domain.topic.validaciones;

import com.foro.hub.foro_hub_api.domain.topic.TopicCancelamientoDTO;

public interface ValidadorCancelamientoDeTopic {
    void validar(TopicCancelamientoDTO datos);
}
