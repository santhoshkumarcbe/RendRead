package com.crio.rent_read.service;

import com.crio.rent_read.entity.Book;
import com.crio.rent_read.entity.User;
import com.crio.rent_read.entity.enums.AvailabilityStatus;
import com.crio.rent_read.exception.BadRequestException;
import com.crio.rent_read.repository.BookRepository;
import com.crio.rent_read.repository.RentalRepository;
import com.crio.rent_read.repository.UserRepository;
import com.crio.rent_read.serviceImpl.RentalServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

class RentalServiceTest {

    private final RentalRepository rentalRepository = Mockito.mock(RentalRepository.class);
    private final UserRepository userRepository = Mockito.mock(UserRepository.class);
    private final BookRepository bookRepository = Mockito.mock(BookRepository.class);

    private final RentalService rentalService =
            new RentalServiceImpl(rentalRepository, userRepository, bookRepository);

    @Test
    void shouldThrowErrorWhenUserHasTwoActiveRentals() {

        User user = User.builder().id(1L).build();
        Book book = Book.builder().id(1L).availabilityStatus(AvailabilityStatus.AVAILABLE).build();

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        Mockito.when(rentalRepository.countByUserIdAndReturnDateIsNull(1L)).thenReturn(2L);

        Assertions.assertThrows(BadRequestException.class, () -> {
            rentalService.rentBook(1L, 1L);
        });
    }
}
