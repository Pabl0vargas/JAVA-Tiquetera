package com.tiquetera.infrastructure.adapters.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiquetera.infrastructure.adapters.in.web.dto.EventDTO;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser; // De la HU-5
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
@ActiveProfiles("test")
@Disabled("Test de integración deshabilitado en entorno local Windows por compatibilidad de Docker. Funcional en CI/CD Linux.")
class EventControllerIntegrationTest {

    @Container
    @ServiceConnection
    static MySQLContainer<?> mySQLContainer = new MySQLContainer<>(DockerImageName.parse("mysql:8.0"));

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"}) // Seguridad (HU-5)
    void shouldCreateEvent_WhenValidRequest() throws Exception {
        EventDTO dto = new EventDTO();
        dto.setName("Integration Fest Mergeado");
        dto.setEventDate(LocalDateTime.now().plusDays(10));
        dto.setCategory("Music");
        dto.setVenueId(1L);

        mockMvc.perform(post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Integration Fest Mergeado"));
    }
}