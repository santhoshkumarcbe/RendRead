package com.crio.rent_read.dto.request;

import com.crio.rent_read.entity.enums.AvailabilityStatus;
import com.crio.rent_read.entity.enums.Genre;
import lombok.Data;

@Data
public class BookRequest {

    private String title;
    private String author;
    private Genre genre;
    private AvailabilityStatus availabilityStatus;
}
