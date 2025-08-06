package com.foro.hub.foro_hub_api.domain.answer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AnswerFormDTO(
        @NotBlank String mensaje,
        @NotNull Long topicoId,
        @NotNull Long autorId
) {}

