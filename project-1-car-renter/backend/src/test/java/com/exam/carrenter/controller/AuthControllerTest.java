package com.exam.carrenter.controller;

import com.exam.carrenter.model.User;
import com.exam.carrenter.model.interfaces.UserRepository;
import com.exam.carrenter.services.AuthService;
import com.exam.carrenter.services.JwtService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {


    @Autowired MockMvc mvc;
    @MockBean AuthService authService;
    @MockBean JwtService jwtService;


    @MockBean private UserRepository userRepository;



    @Test
    void register_returns201_withoutBody_onSuccess() throws Exception {
        User user = new User();
        user.setEmail("a@b.c");
        user.setPasswordHash("hash");
        user.setRole("USER");

        Mockito.when(authService.register("a@b.c", "pw"))
                .thenReturn(user);


        mvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"a@b.c\",\"password\":\"pw\"}")
                        .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isCreated())
                .andExpect(content().string("")); // AuthController 201 üres body-val
    }


    @Test
    void register_duplicateEmail_returns409_withMessage() throws Exception {
        Mockito.when(authService.register(anyString(), anyString()))
                .thenThrow(new IllegalArgumentException("Email already exists"));


        mvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"dup@b.c\",\"password\":\"pw\"}")
                        .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isConflict())
                .andExpect(content().string("Email already exists"));
    }


    @Test
    void login_returnsAuthResponse_withAccessToken() throws Exception {
        User u = new User();
        u.setEmail("a@b.c");
        u.setPasswordHash("hash");
        u.setRole("USER");

        Mockito.when(authService.authenticate("a@b.c", "pw")).thenReturn(u);
        Mockito.when(jwtService.generate(u)).thenReturn("JWT-TOKEN");


        mvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"a@b.c\",\"password\":\"pw\"}")
                        .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.accessToken").value("JWT-TOKEN"));
    }
}
