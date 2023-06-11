package com.spotlight.platform.userprofile.api.web.controller;

import com.spotlight.platform.userprofile.api.core.profile.persistence.IUserProfileDao;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserId;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfileFixtures;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserProfileControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IUserProfileDao IUserProfileDao;

    @BeforeEach
    void beforeEach() {
        MockitoAnnotations.openMocks(this);
        reset(IUserProfileDao);
    }

    @Nested
    @DisplayName("getUserProfile")
    class GetUserProfile {
        private static final String USER_ID_PATH_PARAM = "userId";
        private static final String URL = "/users/{%s}/profile";

        @Test
        void existingUser_correctObjectIsReturned() throws Exception {
            when(IUserProfileDao.get(any(UserId.class))).thenReturn(Optional.of(UserProfileFixtures.USER_PROFILE));

            mockMvc.perform(get(String.format(URL, USER_ID_PATH_PARAM), UserProfileFixtures.USER_ID)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(result -> assertThatJson(result.getResponse().getContentAsString()).isEqualTo(UserProfileFixtures.SERIALIZED_USER_PROFILE));
        }

        @Test
        void nonExistingUser_returns404() throws Exception {
            when(IUserProfileDao.get(any(UserId.class))).thenReturn(Optional.empty());

            mockMvc.perform(get(String.format(URL + "sssssss", USER_ID_PATH_PARAM), UserProfileFixtures.USER_ID)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        }

        @Test
        void validationFailed_returns400() throws Exception {
            mockMvc.perform(get(String.format(URL, USER_ID_PATH_PARAM), UserProfileFixtures.INVALID_USER_ID)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        }

        @Test
        void unhandledExceptionOccured_returns400() throws Exception {
            when(IUserProfileDao.get(any(UserId.class))).thenThrow(new RuntimeException("Some unhandled exception"));

            mockMvc.perform(get(String.format(URL, USER_ID_PATH_PARAM), UserProfileFixtures.USER_ID)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        }
    }
}
