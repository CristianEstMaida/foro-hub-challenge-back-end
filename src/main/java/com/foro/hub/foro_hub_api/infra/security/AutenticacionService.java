package com.foro.hub.foro_hub_api.infra.security;

import com.foro.hub.foro_hub_api.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutenticacionService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return userRepository
                .findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + login));
    }
}
