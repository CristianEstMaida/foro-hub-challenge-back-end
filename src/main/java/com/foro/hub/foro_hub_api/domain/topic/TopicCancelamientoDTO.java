package com.foro.hub.foro_hub_api.domain.topic;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record TopicCancelamientoDTO(@NotNull Long id/*,
                                    @NotNull String title,
                                    @NotNull String message,
                                    @NotNull LocalDateTime creationDate*/) {
}
