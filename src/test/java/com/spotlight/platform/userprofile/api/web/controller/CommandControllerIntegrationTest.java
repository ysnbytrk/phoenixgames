package com.spotlight.platform.userprofile.api.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spotlight.platform.userprofile.api.core.commands.Command;
import com.spotlight.platform.userprofile.api.core.commands.CommandData;
import com.spotlight.platform.userprofile.api.core.commands.CommandType;
import com.spotlight.platform.userprofile.api.core.commands.executer.CommandExecutorRegistry;
import com.spotlight.platform.userprofile.api.core.profile.IUserProfileService;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserId;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfileFixtures;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Collections;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CommandControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private IUserProfileService profileService;

    @Mock
    private CommandExecutorRegistry executorRegistry;


    @BeforeEach
    void setUp() {
        // Initialize the mock objects
        MockitoAnnotations.openMocks(this);
        profileService.update(UserProfileFixtures.USER_PROFILE);
    }

    @Test
    void handleCommands_ValidCommandData_ExecutesCommand() throws Exception {

        // Create a valid CommandData
        CommandData commandData = UserProfileFixtures.REPLACE_COMMAND_DATA;

        // Convert CommandData to JSON
        String json = objectMapper.writeValueAsString(Collections.singletonList(commandData));

        // Perform the POST request
        MvcResult result = mockMvc.perform(post("/commands")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();


        // Assert the response status
        assertThat(result.getResponse().getStatus()).isEqualTo(200);
    }

    @Test
    void handleCommands_InvalidCommandType_ReturnsBadRequest() throws Exception {
        // Create an invalid CommandData with an unsupported command type
        CommandData commandData = UserProfileFixtures.UNSUPPORTED_COMMAND_DATA;

        // Convert CommandData to JSON
        String json = objectMapper.writeValueAsString(Collections.singletonList(commandData));

        // Perform the POST request
        mockMvc.perform(post("/commands")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        // Verify that the execute method was not called
        verifyNoInteractions(executorRegistry);
    }

    @Test
    void handleCommands_EmptyCommandData_ReturnsSuccessResponse() throws Exception {
        // Perform the POST request with an empty list of command data
        mockMvc.perform(post("/commands")
                        .content("[]")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Verify that the execute method was not called
        verifyNoInteractions(executorRegistry);
    }

    @Test
    void handleCommands_InvalidCommandData_ReturnsBadRequest() throws Exception {
        // Create an invalid CommandData with missing required fields
        CommandData commandData = new CommandData(UserId.valueOf(""), CommandType.UNSUPPORTED, Map.of());

        // Convert CommandData to JSON
        String json = objectMapper.writeValueAsString(Collections.singletonList(commandData));

        // Perform the POST request
        mockMvc.perform(post("/commands")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        // Verify that the execute method was not called
        verifyNoInteractions(executorRegistry);
    }

}
