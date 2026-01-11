package com.crio.rent_read.controller;


import com.crio.rent_read.dto.response.RentalResponse;
import com.crio.rent_read.service.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rentals")
@RequiredArgsConstructor
public class RentalController {

    private final RentalService rentalService;

    @PostMapping("/users/{userId}/books/{bookId}")
    public ResponseEntity<RentalResponse> rentBook(
            @PathVariable Long userId,
            @PathVariable Long bookId) {

        RentalResponse response = rentalService.rentBook(userId, bookId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/active-rentals/users/{userId}")
    public ResponseEntity<List<RentalResponse>> getActiveRentals(@PathVariable Long userId) {
        return ResponseEntity.ok(rentalService.getActiveRentals(userId));
    }

    @PutMapping("/{rentalId}")
    public ResponseEntity<Void> returnBook(@PathVariable Long rentalId) {
        rentalService.returnBook(rentalId);
        return ResponseEntity.noContent().build();
    }
}
