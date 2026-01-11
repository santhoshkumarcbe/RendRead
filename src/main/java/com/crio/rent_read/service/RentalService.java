package com.crio.rent_read.service;

import com.crio.rent_read.dto.response.RentalResponse;

import java.util.List;

public interface RentalService {

    RentalResponse rentBook(Long userId, Long bookId);

    List<RentalResponse> getActiveRentals(Long userId);

    void returnBook(Long rentalId);
}

