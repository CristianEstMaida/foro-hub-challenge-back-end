package com.foro.hub.foro_hub_api.domain.user;

import jakarta.validation.constraints.NotBlank;

public record UserFormDTO(@NotBlank String name, @NotBlank String login, @NotBlank  String password) {
    public UserFormDTO(User user) {
        this( user.getName(), user.getLogin(), user.getPassword());
    }
}