package com.acme.inventory.src.infraestructure.security;

import java.io.IOException;
import java.util.Objects;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.Authentication;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ApiKeyFilter extends OncePerRequestFilter {
    /**
     * El nombre del encabezado HTTP donde se espera la clave API
     */
    private final String headerName;
    /**
     * La clave API esperada para autenticación
     */
    private final String expected;

    /**
     * Constructor que inicializa el filtro con el nombre del encabezado y la clave
     * esperada
     *
     * @param headerName El nombre del encabezado HTTP
     * @param expected   La clave API esperada
     */
    public ApiKeyFilter(String headerName, String expected) {
        this.headerName = headerName;
        this.expected = expected;
    }

    /**
     * Filtra las solicitudes entrantes para verificar la clave API
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        var provided = request.getHeader(headerName);
        if (!Objects.equals(provided, expected)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/vnd.api+json");
            response.getWriter().write("""
                    {"errors":[{"status":"401","title":"No autorizado","detail":"Clave API inválida"}]}
                    """);
            return;
        }
        Authentication auth = new AbstractAuthenticationToken(AuthorityUtils.NO_AUTHORITIES) {
            @Override
            public Object getCredentials() {
                return provided;
            }

            @Override
            public Object getPrincipal() {
                return "api-key";
            }
        };
        auth.setAuthenticated(true);
        filterChain.doFilter(request, response);
    }
}
