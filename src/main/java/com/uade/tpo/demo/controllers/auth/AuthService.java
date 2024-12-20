package com.uade.tpo.demo.controllers.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.uade.tpo.demo.controllers.Jwt.JwtService;
import com.uade.tpo.demo.entity.User;
import com.uade.tpo.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        User user=userRepository.findByUsername(request.getUsername()).orElseThrow();
        String token=jwtService.generateToken(user);
        return AuthResponse.builder()
            .token(token)
            .role(user.getRole())
            .userId(user.getUserId())
            .email(user.getEmail())
            .build();

    }

    public AuthResponse register(RegisterRequest request) {
        User user = User.builder()
            .username(request.getUsername())
            .password(passwordEncoder.encode( request.getPassword()))
            .email(request.getEmail())
            .firstName(request.getFirstname())
            .lastName(request.getLastname())
            .country(request.getCountry())
            .role(request.getRole())
            .build();

        userRepository.save(user);

        return AuthResponse.builder()
            .token(jwtService.generateToken(user))
            .build();
        
    }

}