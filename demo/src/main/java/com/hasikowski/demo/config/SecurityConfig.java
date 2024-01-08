package com.hasikowski.demo.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserAuthProvider userAuthProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws  Exception{
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(new JwtAuthFilter(userAuthProvider), BasicAuthenticationFilter.class)
                .sessionManagement(customizer -> customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((requests) ->
                        requests.requestMatchers(HttpMethod.POST,  "login").permitAll())
                .authorizeHttpRequests((request) -> request.requestMatchers(HttpMethod.GET, "paged").permitAll())
                .authorizeHttpRequests((request) -> request.requestMatchers(HttpMethod.POST, "/games/recommend").permitAll())
                .authorizeHttpRequests((request) -> request.requestMatchers(HttpMethod.GET, "/genres").permitAll())
                .authorizeHttpRequests((request) -> request.requestMatchers(HttpMethod.POST, "/register").permitAll())
                .authorizeHttpRequests((request) -> request.requestMatchers(HttpMethod.GET, "/games/{id}").permitAll())
                .authorizeHttpRequests((request) -> request.requestMatchers(HttpMethod.GET, "/count").permitAll())
                .authorizeHttpRequests((request) -> request.requestMatchers(HttpMethod.POST, "/game").permitAll())
                .authorizeHttpRequests((request) -> request.requestMatchers(HttpMethod.PUT, "/games/").permitAll()
                                .anyRequest().authenticated());
        return httpSecurity.build();
    }
}
