package com.crio.rent_read.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class RentalResponse {

    private Long id;
    private BookResponse book;
    private LocalDate rentedAt;
    private LocalDate returnDate;
}
