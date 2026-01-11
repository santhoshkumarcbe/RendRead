package com.crio.rent_read.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ErrorResponse {

    private String message;
    private String httpStatus;
    private LocalDateTime localDateTime;
}

