package com.crio.rent_read.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.crio.rent_read.dto.request.BookRequest;
import com.crio.rent_read.dto.response.BookResponse;
import com.crio.rent_read.entity.enums.AvailabilityStatus;
import com.crio.rent_read.entity.enums.Genre;
import com.crio.rent_read.service.BookService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateBook() throws Exception {

        BookRequest request = new BookRequest();
        request.setTitle("Test Book");
        request.setAuthor("Test Author");
        request.setGenre(Genre.FICTION);
        request.setAvailabilityStatus(AvailabilityStatus.AVAILABLE);

        BookResponse response = BookResponse.builder()
                .id(1L)
                .title("Test Book")
                .author("Test Author")
                .genre(Genre.FICTION)
                .availabilityStatus(AvailabilityStatus.AVAILABLE)
                .build();

        Mockito.when(bookService.createBook(Mockito.any())).thenReturn(response);

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }
}
