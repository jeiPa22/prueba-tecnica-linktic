package com.acme.inventory.src.infraestructure.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.acme.inventory.src.infraestructure.config.InventorySecurityProperties;

/**
 * Configuración de seguridad para el servicio de inventario.
 */
@Configuration
public class SecurityConfig {
        /**
         * Configura la cadena de filtros de seguridad.
         * 
         * @param http  La configuración de seguridad HTTP.
         * @param props Las propiedades de seguridad.
         * @return La cadena de filtros de seguridad.
         * @throws Exception Si ocurre un error al configurar la seguridad.
         */
        @Bean
        SecurityFilterChain filterChain(
                        HttpSecurity http,
                        InventorySecurityProperties props) throws Exception {
                return http
                                .csrf(csrf -> csrf.disable())
                                .authorizeHttpRequests(reg -> reg
                                                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**").permitAll()
                                                .anyRequest().authenticated())
                                .addFilterBefore(new ApiKeyFilter(props.header(), props.expectedKey()),
                                                UsernamePasswordAuthenticationFilter.class)
                                .build();
        }
}
