package com.exam.carrenter.controller;


import com.exam.carrenter.filter.JwtAuthFilter;
import com.exam.carrenter.model.Car;
import com.exam.carrenter.model.interfaces.CarRepository;
import com.exam.carrenter.model.interfaces.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


import java.util.List;
import java.util.UUID;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(CarController.class)
@AutoConfigureMockMvc(addFilters = false)
class CarControllerTest {


    @Autowired MockMvc mvc;
    @MockBean
    JwtAuthFilter jwtAuthFilter;
    @MockBean
    UserRepository userRepoForSecurity;
    @MockBean CarRepository repo;


    @Test
    void list_returnsAll() throws Exception {
        Mockito.when(repo.findAll()).thenReturn(List.of(new Car(), new Car()));
        mvc.perform(get("/api/cars")
                .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").exists())
                .andExpect(jsonPath("$[1]").exists());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void create_requiresAdmin_returns201_withBody() throws Exception {
        Car saved = new Car(); saved.setId(UUID.randomUUID());
        Mockito.when(repo.save(any(Car.class))).thenReturn(saved);


        mvc.perform(post("/api/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"brand\":\"VW\",\"model\":\"Golf\"}")
                        .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(saved.getId().toString()));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void update_requiresAdmin_returns200() throws Exception {
        UUID id = UUID.randomUUID();
        Car updated = new Car(); updated.setId(id);
        Mockito.when(repo.save(any(Car.class))).thenReturn(updated);


        mvc.perform(put("/api/cars/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"brand\":\"VW\"}")
                        .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()));
    }


    @Test
    @WithMockUser(roles = {"ADMIN"})
    void delete_whenNotFound_returns404() throws Exception {
        UUID id = UUID.randomUUID();
        Mockito.when(repo.existsById(eq(id))).thenReturn(false);


        mvc.perform(delete("/api/cars/" + id)
                        .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isNotFound());
    }

}
