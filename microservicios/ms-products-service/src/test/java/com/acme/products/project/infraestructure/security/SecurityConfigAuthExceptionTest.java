package com.acme.products.project.infraestructure.security;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.AuthenticationException;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

class SecurityConfigAuthExceptionTest {

    @Test
    void invokeAuthExceptionHandler_lambda_executes() throws Exception {
        SecurityConfig cfg = new SecurityConfig();
        // locate the synthetic lambda method by name
        Method[] methods = SecurityConfig.class.getDeclaredMethods();
        Method target = null;
        for (Method m : methods) {
            if (m.getName().contains("lambda$4")) {
                target = m;
                break;
            }
        }
        assertNotNull(target, "auth-exception lambda not found");

        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse resp = new MockHttpServletResponse();

        // invoke the lambda with (HttpServletRequest, HttpServletResponse, AuthenticationException)
        target.setAccessible(true);
        // pass a dummy AuthenticationException
        AuthenticationException ae = new AuthenticationException("boom"){};
        // method is static-like synthetic; invoke with cfg instance and args
        target.invoke(cfg, req, resp, ae);

        // after invocation, response should have an error status set
        int status = resp.getStatus();
        // handler may set 401 or 403; assert it's a 4xx client error
        assertTrue(status >= 400 && status < 500, "expected 4xx status, was " + status);
    }
}
