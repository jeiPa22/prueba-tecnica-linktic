package com.acme.inventory.project.infraestructure.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

class ApiKeyFilterTest {

    @Test
    void shouldNotFilter_pathsExcluded() {
        var f = new ApiKeyFilter("x", "v");
        var req = mock(HttpServletRequest.class);
        when(req.getRequestURI()).thenReturn("/v3/api-docs/index");
        assertThat(f.shouldNotFilter(req)).isTrue();
    }

    @Test
    void shouldNotFilter_otherDocsPaths() {
        var f = new ApiKeyFilter("x", "v");
        var req1 = mock(HttpServletRequest.class);
        when(req1.getRequestURI()).thenReturn("/swagger-ui/test");
        var req2 = mock(HttpServletRequest.class);
        when(req2.getRequestURI()).thenReturn("/swagger-ui.html");
        var req3 = mock(HttpServletRequest.class);
        when(req3.getRequestURI()).thenReturn("/actuator/health/check");
        var req4 = mock(HttpServletRequest.class);
        when(req4.getRequestURI()).thenReturn("/actuator/info/other");

        assertThat(f.shouldNotFilter(req1)).isTrue();
        assertThat(f.shouldNotFilter(req2)).isTrue();
        assertThat(f.shouldNotFilter(req3)).isTrue();
        assertThat(f.shouldNotFilter(req4)).isTrue();
    }

    @Test
    void unauthorized_when_missingOrBadKey() throws Exception {
        var f = new ApiKeyFilter("x", "secret");
        var req = mock(HttpServletRequest.class);
        var res = mock(HttpServletResponse.class);
        var chain = mock(FilterChain.class);

        when(req.getRequestURI()).thenReturn("/api/ok");
        when(req.getHeader("x")).thenReturn(null);
        var writer = mock(PrintWriter.class);
        when(res.getWriter()).thenReturn(writer);

        f.doFilterInternal(req, res, chain);

        verify(res).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(res).setContentType("application/vnd.api+json");
        verify(writer).write(anyString());
        verify(chain, never()).doFilter(req, res);
    }

    @Test
    void authorized_when_keyMatches() throws Exception {
        var f = new ApiKeyFilter("x-api", "abc");
        var req = mock(HttpServletRequest.class);
        var res = mock(HttpServletResponse.class);
        var chain = mock(FilterChain.class);

        when(req.getRequestURI()).thenReturn("/api/ok");
        when(req.getHeader("x-api")).thenReturn("abc");

        f.doFilterInternal(req, res, chain);

        verify(chain).doFilter(req, res);
    }
}
