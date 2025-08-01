package com.foro.hub.foro_hub_api.domain.user;

import jakarta.validation.constraints.NotBlank;

public record UserAuthenticationData(@NotBlank String login, @NotBlank  String password) {
}
