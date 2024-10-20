package com.japanese.lessons.service.Auth;

import com.japanese.lessons.dtos.request.LoginRequest;
import com.japanese.lessons.dtos.request.RegisterRequest;
import com.japanese.lessons.dtos.response.AuthResponse;
import com.japanese.lessons.models.User.ERole;
import com.japanese.lessons.models.User.User;
import com.japanese.lessons.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        String token = jwtService.getTokenService(user);

        return AuthResponse.builder()
                .token(token)
                .role(user.getRole())
                .build();
    }

    public AuthResponse register(RegisterRequest registerRequest) {
        ERole role = (registerRequest.getRole() != null) ? registerRequest.getRole() : ERole.USER;

        User user = User.builder()
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(role)
                .build();

        userRepository.save(user);
        return AuthResponse.builder().token(jwtService.getTokenService(user)).role(role).build();
    }
}
