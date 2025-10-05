package com.acme.inventory.src.infraestructure.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    /**
     * Configura la cadena de filtros de seguridad, incluyendo la autenticación por
     * clave API
     *
     * @param http        El objeto HttpSecurity para configurar la seguridad web
     * @param header      El nombre del encabezado HTTP donde se espera la clave API
     * @param expectedKey La clave API esperada para autenticación
     * @return La cadena de filtros de seguridad configurada
     * @throws Exception Si ocurre un error durante la configuración
     */
    @Bean
    SecurityFilterChain filterChain(
            HttpSecurity http,
            @Value("${inventory.security.header}") String header,
            @Value("${inventory.security.expectedKey}") String expectedKey) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(reg -> reg
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**").permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(new ApiKeyFilter(header, expectedKey), UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
