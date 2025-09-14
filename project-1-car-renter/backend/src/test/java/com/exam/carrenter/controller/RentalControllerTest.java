package com.exam.carrenter.controller;
import com.exam.carrenter.filter.JwtAuthFilter;
import com.exam.carrenter.model.Rental;
import com.exam.carrenter.model.User;
import com.exam.carrenter.model.interfaces.RentalRepository;
import com.exam.carrenter.model.interfaces.UserRepository;
import com.exam.carrenter.services.RentalService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(RentalController.class)
@AutoConfigureMockMvc(addFilters = false)
class RentalControllerTest {

    @Autowired MockMvc mvc;
    @MockBean RentalRepository rentalRepo;
    @MockBean
    JwtAuthFilter jwtAuthFilter;
    @MockBean UserRepository users;
    @MockBean RentalService service;
    @Test
    void listInRange_filtersByDates() throws Exception {
        Mockito.when(rentalRepo.findInRange(any(), any())).thenReturn(List.of(new Rental(), new Rental()));


        mvc.perform(get("/api/rentals")
                        .param("from", LocalDateTime.now().minusDays(1).toString())
                        .param("to", LocalDateTime.now().plusDays(1).toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").exists())
                .andExpect(jsonPath("$[1]").exists());
    }


    @Test
    void create_assignsUserFromAuthPrincipal() throws Exception {
        String email = "user@exam.com";
        UUID uid = UUID.randomUUID();

        User user = new User();
        user.setId(uid);
        user.setEmail(email);
        user.setPasswordHash("hash");
        user.setRole("USER");

        Mockito.when(users.findByEmail(email)).thenReturn(Optional.of(user));


        Rental saved = new Rental();
        saved.setId(UUID.randomUUID());
        Mockito.when(service.create(any(Rental.class))).thenReturn(saved);


        Authentication auth = Mockito.mock(Authentication.class);
        Mockito.when(auth.getPrincipal()).thenReturn(email);


        mvc.perform(post("/api/rentals").principal(auth)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"carId\":\"" + UUID.randomUUID() + "\",\"start\":\"2030-01-01T10:00:00\",\"end\":\"2030-01-02T10:00:00\"}")
                        .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());
    }


    @Test
    void create_onOverlap_returns409_withMessage() throws Exception {
        String email = "user@exam.com";

        User user = new User();
        user.setId(UUID.randomUUID());
        user.setEmail(email);
        user.setPasswordHash("hash");
        user.setRole("USER");


        Mockito.when(users.findByEmail(email)).thenReturn(Optional.of(user));
        Mockito.when(service.create(any(Rental.class))).thenThrow(new IllegalArgumentException("Overlaps"));


        Authentication auth = Mockito.mock(Authentication.class);
        Mockito.when(auth.getPrincipal()).thenReturn(email);


        mvc.perform(post("/api/rentals").principal(auth)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"carId\":\"" + UUID.randomUUID() + "\",\"start\":\"2030-01-01T10:00:00\",\"end\":\"2030-01-01T12:00:00\"}")
                        .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isConflict())
                .andExpect(content().string("Overlaps"));
    }
}
