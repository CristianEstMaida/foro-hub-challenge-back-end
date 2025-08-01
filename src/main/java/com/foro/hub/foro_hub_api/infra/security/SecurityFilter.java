package com.foro.hub.foro_hub_api.infra.security;

import com.foro.hub.foro_hub_api.domain.user.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException  {

        var authHeader = request.getHeader("Authorization");

        if(authHeader != null) {
            var token = authHeader.replace("Bearer ", "");

            try {
                var username = tokenService.getSubject(token);
                if (username != null) {

                    var user = userRepository.findByLogin(username);
                    //System.out.println(user);
                    var authentication = new UsernamePasswordAuthenticationToken(
                            user,
                            null,
                            user.get().getAuthorities()
                    );
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("{\"error\": \"Invalid or expired Token.\"}");
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}
