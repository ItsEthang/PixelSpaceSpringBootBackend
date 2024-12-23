package com.pixel.PixelSpace;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pixel.PixelSpace.Controllers.UserController;
import com.pixel.PixelSpace.Models.User;
import com.pixel.PixelSpace.Models.RequestBodies.UserFollowRequest;
import com.pixel.PixelSpace.Models.ResponseEntities.UserResponse;
import com.pixel.PixelSpace.Services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import javax.naming.AuthenticationException;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private User testUser;

    @BeforeEach
    void setUp() {
        // Prepare a dummy user for testing
        testUser = new User();
        testUser.setUserId(1);
        testUser.setUsername("testUser");
        testUser.setPassword("password123");
        testUser.setName("Test User");
        testUser.setProfileImg("profile.png");
        testUser.setBio("This is a test bio.");
    }

    @Test
    void testUserRegister_Success() throws Exception {
        // Given: A user registration request with valid data
        when(userService.getAllUsers()).thenReturn(List.of());
        doNothing().when(userService).userRegister(any(User.class));

        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(status().isCreated())
                .andExpect(content().string("New user registered"));

        // Verify that userService.userRegister was called
        verify(userService, times(1)).userRegister(any(User.class));
    }

    @Test
    void testUserRegister_UserAlreadyExists() throws Exception {
        // Given: A user registration request with an existing username
        when(userService.getAllUsers()).thenReturn(List.of(testUser));

        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(status().isConflict())
                .andExpect(content().string("User already exists"));

        // Verify that userService.userRegister was not called
        verify(userService, times(0)).userRegister(any(User.class));
    }

    @Test
    void testUserLogin_Success() throws Exception {
        // Given: A user login request with valid credentials
        when(userService.userLogin(testUser.getUsername(), testUser.getPassword())).thenReturn(testUser);

        mockMvc.perform(post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(status().isOk())
                .andExpect(header().exists("userId"));

        // Verify that userService.userLogin was called
        verify(userService, times(1)).userLogin(testUser.getUsername(), testUser.getPassword());
    }

    @Test
    void testUserLogin_Unauthorized() throws Exception {
        // Given: A user login request with invalid credentials
        when(userService.userLogin(testUser.getUsername(), testUser.getPassword()))
                .thenThrow(new AuthenticationException("Invalid credentials"));

        mockMvc.perform(post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Invalid credentials"));

        // Verify that userService.userLogin was called
        verify(userService, times(1)).userLogin(testUser.getUsername(), testUser.getPassword());
    }

    @Test
    void testUserFollow_Success() throws Exception {
        // Given: A valid user follows another user
        UserFollowRequest request = new UserFollowRequest();
        request.setUserId2(2); // User is following user with ID 2
        doNothing().when(userService).createFriendship(1, 2);

        mockMvc.perform(post("/user/follow")
                .header("userId", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("User made friend with user 2! How lucky :)"));

        // Verify that userService.createFriendship was called
        verify(userService, times(1)).createFriendship(1, 2);
    }

}
