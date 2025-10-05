package com.acme.products.project.infraestructure.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.util.Objects;

/**
 * Filtro de seguridad para validar la clave API en las solicitudes entrantes.
 * Este filtro verifica que la clave API proporcionada en el encabezado
 * coincida con la clave esperada. Si no coincide, responde con un error 401.
 */
public class ApiKeyFilter extends OncePerRequestFilter {
    private final String headerName;
    private final String expected;

    public ApiKeyFilter(String headerName, String expected) {
        this.headerName = headerName;
        this.expected = expected;
    }

    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) {
        String p = request.getRequestURI();
        return p.startsWith("/v3/api-docs") || p.startsWith("/swagger-ui") || p.equals("/swagger-ui.html")
                || p.startsWith("/actuator/health") || p.startsWith("/actuator/info");
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
            @NonNull FilterChain chain)
            throws ServletException, IOException {

        String provided = request.getHeader(headerName);

        if (!Objects.equals(provided, expected)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/vnd.api+json");
            response.getWriter().write(
                    "{\"errors\":[{\"status\":\"401\",\"title\":\"No autorizado\",\"detail\":\"Clave API inválida o ausente\"}]}");
            return;
        }

        Authentication auth = new UsernamePasswordAuthenticationToken("api-key", null, java.util.List.of());
        var context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(auth);
        SecurityContextHolder.setContext(context);

        chain.doFilter(request, response);
    }
}