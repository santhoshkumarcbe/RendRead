package com.crio.rent_read.dto.response;

import com.crio.rent_read.entity.enums.AvailabilityStatus;
import com.crio.rent_read.entity.enums.Genre;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookResponse {

    private Long id;
    private String title;
    private String author;
    private Genre genre;
    private AvailabilityStatus availabilityStatus;
}

