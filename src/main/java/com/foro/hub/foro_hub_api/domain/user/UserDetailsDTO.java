package com.foro.hub.foro_hub_api.domain.user;

public record UserDetailsDTO(Long id,
                             String name,
                             String login
) {
    public UserDetailsDTO(User user) {
        this(user.getId(), user.getName(), user.getLogin());
    }
}