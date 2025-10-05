package com.acme.inventory.project.infraestructure.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.acme.inventory.project.infraestructure.config.InventorySecurityProperties;

import jakarta.servlet.http.HttpServletResponse;

/**
 * Configuración de seguridad para la aplicación.
 * Define un filtro de seguridad que valida la clave API en las solicitudes
 * entrantes.
 */
@Configuration
public class SecurityConfig {
        /**
         * Configura la cadena de filtros de seguridad.
         * Deshabilita CSRF, permite el acceso a la documentación de la API
         * y protege todas las demás rutas con autenticación basada en clave API.
         *
         * @param http     El objeto HttpSecurity para configurar la seguridad web
         * @param header   El nombre del encabezado donde se espera la clave API
         * @param expected El valor esperado de la clave API
         * @return La cadena de filtros de seguridad configurada
         * @throws Exception Si ocurre un error durante la configuración
         */
        @Bean
        SecurityFilterChain filterChain(HttpSecurity http, InventorySecurityProperties props) throws Exception {
                return http
                                .csrf(csrf -> csrf.disable())
                                .authorizeHttpRequests(reg -> reg
                                                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**",
                                                                "/swagger-ui.html")
                                                .permitAll()
                                                .requestMatchers("/actuator/health/**", "/actuator/info").permitAll()
                                                .anyRequest().authenticated())
                                .exceptionHandling(e -> e.authenticationEntryPoint(
                                                (req, res, ex) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED)))
                                .addFilterBefore(new ApiKeyFilter(props.header(), props.expectedKey()),
                                                UsernamePasswordAuthenticationFilter.class)
                                .build();
        }
}
