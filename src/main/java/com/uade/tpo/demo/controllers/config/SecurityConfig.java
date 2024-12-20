package com.uade.tpo.demo.controllers.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.uade.tpo.demo.controllers.Jwt.JwtAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

        private final JwtAuthenticationFilter jwtAuthFilter;
        private final AuthenticationProvider authenticationProvider;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .csrf(AbstractHttpConfigurer::disable)
                                .authorizeHttpRequests(req -> req.requestMatchers("/api/v1/auth/**").permitAll()
                                                .requestMatchers("/error/**").permitAll()
                                                .requestMatchers("/auth/**").permitAll()
                                                .requestMatchers("/users/**").hasAuthority("ADMIN")
                                                .requestMatchers(HttpMethod.POST, "/movies").hasAnyAuthority("ADMIN")
                                                .requestMatchers(HttpMethod.DELETE, "/movies/**").hasAuthority("ADMIN")
                                                .requestMatchers(HttpMethod.PUT, "/movies/**").hasAuthority("ADMIN")
                                                .requestMatchers(HttpMethod.GET, "/movies/**").permitAll()
                                                .requestMatchers("/genres/**").hasAuthority("ADMIN")
                                                .requestMatchers("/directors/**").hasAuthority("ADMIN")
                                                .requestMatchers(HttpMethod.POST, "/orders").hasAnyAuthority("USER", "ADMIN") // Permitir crear órdenes para todos
                                                .requestMatchers(HttpMethod.DELETE, "/orders/**").hasAuthority("ADMIN") // Restringir DELETE a ADMIN
                                                .requestMatchers(HttpMethod.PUT, "/orders/**").hasAuthority("ADMIN") // Restringir PUT a ADMIN
                                                .requestMatchers(HttpMethod.GET, "/orders/**").hasAnyAuthority("USER", "ADMIN") // Permitir ver órdenes para todos
                                                .anyRequest()
                                                .authenticated())
                                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                                .authenticationProvider(authenticationProvider)
                                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }
}
