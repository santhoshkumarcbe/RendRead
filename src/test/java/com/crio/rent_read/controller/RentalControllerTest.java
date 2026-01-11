package com.crio.rent_read.controller;

import com.crio.rent_read.dto.response.RentalResponse;
import com.crio.rent_read.service.RentalService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RentalController.class)
class RentalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private RentalService rentalService;

    @Test
    void shouldRentBook() throws Exception {

        RentalResponse response = RentalResponse.builder()
                .id(1L)
                .rentedAt(LocalDate.now())
                .build();

        Mockito.when(rentalService.rentBook(1L, 1L)).thenReturn(response);

        mockMvc.perform(post("/rentals/users/1/books/1"))
                .andExpect(status().isCreated());
    }
}
