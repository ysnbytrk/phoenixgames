package com.spotlight.platform.userprofile.api.web.healthchecks;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class HealthCheckControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void healthCheck_ReturnsHealthyStatus() throws Exception {
        // Perform the GET request for the health check endpoint
        MvcResult result = mockMvc.perform(get("/actuator/health")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Assert the response status
        assertThat(result.getResponse().getStatus()).isEqualTo(200);

        // Assert the response body contains the "status" field with value "UP"
        assertThat(result.getResponse().getContentAsString()).contains("\"status\":\"UP\"");
    }
}
