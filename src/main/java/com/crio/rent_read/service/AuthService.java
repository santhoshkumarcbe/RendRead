package com.crio.rent_read.service;


import com.crio.rent_read.dto.request.LoginRequest;
import com.crio.rent_read.dto.request.SignupRequest;
import com.crio.rent_read.dto.response.UserResponse;

public interface AuthService {

    UserResponse signup(SignupRequest request);

    UserResponse login(LoginRequest request);
}
