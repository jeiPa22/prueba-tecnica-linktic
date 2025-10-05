package com.acme.products.project.infraestructure.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ApiKeyFilterTest {

    @AfterEach
    void cleanup() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void shouldNotFilter_known_paths() {
        var f = new ApiKeyFilter("X-API-KEY", "expected");
        var req = mock(HttpServletRequest.class);
        when(req.getRequestURI()).thenReturn("/v3/api-docs/foo");
        assertTrue(f.shouldNotFilter(req));

        when(req.getRequestURI()).thenReturn("/swagger-ui/index.html");
        assertTrue(f.shouldNotFilter(req));

        when(req.getRequestURI()).thenReturn("/actuator/health");
        assertTrue(f.shouldNotFilter(req));
    }

    @Test
    void doFilterInternal_unauthorized_writes_error() throws Exception {
        var f = new ApiKeyFilter("X-API-KEY", "expected");
        var req = mock(HttpServletRequest.class);
        var res = mock(HttpServletResponse.class);
        var chain = mock(FilterChain.class);

        when(req.getHeader("X-API-KEY")).thenReturn("bad");

        var sw = new StringWriter();
        var pw = new PrintWriter(sw);
        when(res.getWriter()).thenReturn(pw);

        f.doFilterInternal(req, res, chain);

        verify(res).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        pw.flush();
        var body = sw.toString();
        assertTrue(body.contains("No autorizado") || body.contains("Clave API"));
        verify(chain, never()).doFilter(req, res);
    }

    @Test
    void doFilterInternal_authorized_calls_chain_and_sets_auth() throws Exception {
        var f = new ApiKeyFilter("X-API-KEY", "expected");
        var req = mock(HttpServletRequest.class);
        var res = mock(HttpServletResponse.class);
        var chain = mock(FilterChain.class);

        when(req.getHeader("X-API-KEY")).thenReturn("expected");

        f.doFilterInternal(req, res, chain);

        verify(chain).doFilter(req, res);
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
    }
}
