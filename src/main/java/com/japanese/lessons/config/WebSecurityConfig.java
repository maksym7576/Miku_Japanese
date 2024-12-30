package com.japanese.lessons.config;

import com.japanese.lessons.jwt.AuthTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final AuthenticationProvider authenticationProvider;
    private final AuthTokenFilter authTokenFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/auth/**",
                                "/api/manga/**",
                                "/api/manga/create",
                                "/api/images/**",
                                "/api/text/**",
                                "/api/question/**",
                                "/api/answer/**",
                                "/api/lesson/**",
                                "/api/user/**",
                                "/api/exercise/**",
                                "/api/url/**",
                                "/api/level/**"

                        ).permitAll()

                        .requestMatchers(
                                "/api/user/delete"
                        ).hasAnyAuthority("ROLE_USER")

                        .requestMatchers(
                                "/api/wallet/user/addMoney/{userId}"
                        ).hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")

                        .requestMatchers(
                                "/api/flight/**"
                        ).hasRole("ADMIN")

                        .requestMatchers(
                                "/api/test/user"
                        ).hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
                        .requestMatchers("/api/v1/passengers/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:8081")); // Дозволяємо цей домен
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-Requested-With"));
        configuration.setAllowCredentials(true); // Дозволяємо авторизацію
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Дозволяємо для всіх маршрутів
        return source;
    }

}