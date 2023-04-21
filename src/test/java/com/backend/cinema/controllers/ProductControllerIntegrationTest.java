package com.awbd.lab5.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @WithMockUser(username = "admin", password = "12345", roles = "ADMIN")
    public void showByIdMvc() throws Exception {

        mockMvc.perform(get("/products/{id}", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("productDetails"));
    }

    @Test
    @WithMockUser(username = "admin", password = "12345", roles = "ADMIN")
    public void showByIdNotFound() throws Exception {

        mockMvc.perform(get("/products/{id}", "17"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("notFoundException"));
    }
}
