package com.crio.rent_read.dto.request;

import lombok.Data;

@Data
public class SignupRequest {

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String role;
}