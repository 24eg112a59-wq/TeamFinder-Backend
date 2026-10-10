package com.example.teamfinder.config;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.cors.CorsConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SecurityConfigCorsTest {

    @Test
    void allowsProjectPreviewOriginsWithCredentialedPostRequests() {
        String previewOrigin = "https://team-finder-frontend-ly01zy6xl-snehith5.vercel.app";
        MockHttpServletRequest request = new MockHttpServletRequest("OPTIONS", "/api/team-requests");
        CorsConfiguration configuration = new SecurityConfig()
            .corsConfigurationSource()
            .getCorsConfiguration(request);

        assertEquals(previewOrigin, configuration.checkOrigin(previewOrigin));
        assertTrue(configuration.getAllowCredentials());
        assertTrue(configuration.checkHttpMethod(HttpMethod.POST) != null);
        assertTrue(configuration.checkHeaders(java.util.List.of("content-type")) != null);
    }

    @Test
    void rejectsOriginsOutsideTheFrontendDeploymentPattern() {
        MockHttpServletRequest request = new MockHttpServletRequest("OPTIONS", "/api/team-requests");
        CorsConfiguration configuration = new SecurityConfig()
            .corsConfigurationSource()
            .getCorsConfiguration(request);

        assertNull(configuration.checkOrigin("https://attacker.example"));
    }
}
