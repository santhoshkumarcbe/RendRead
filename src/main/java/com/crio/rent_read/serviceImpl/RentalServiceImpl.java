package com.crio.rent_read.serviceImpl;

import com.crio.rent_read.dto.response.BookResponse;
import com.crio.rent_read.dto.response.RentalResponse;
import com.crio.rent_read.entity.Book;
import com.crio.rent_read.entity.Rental;
import com.crio.rent_read.entity.User;
import com.crio.rent_read.entity.enums.AvailabilityStatus;
import com.crio.rent_read.exception.BadRequestException;
import com.crio.rent_read.exception.ResourceNotFoundException;
import com.crio.rent_read.repository.BookRepository;
import com.crio.rent_read.repository.RentalRepository;
import com.crio.rent_read.repository.UserRepository;
import com.crio.rent_read.service.RentalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RentalServiceImpl implements RentalService {

    private final RentalRepository rentalRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    @Override
    @Transactional
    public RentalResponse rentBook(Long userId, Long bookId) {

        log.info("User {} attempting to rent book {}", userId, bookId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));

        if (book.getAvailabilityStatus() == AvailabilityStatus.NOT_AVAILABLE) {
            throw new BadRequestException("Book is not available for rent");
        }

        long activeRentals = rentalRepository.countByUserIdAndReturnDateIsNull(userId);
        if (activeRentals >= 2) {
            throw new BadRequestException("User has already reached maximum book rental limit!");
        }

        Rental rental = Rental.builder()
                .user(user)
                .book(book)
                .rentedAt(LocalDate.now())
                .build();

        book.setAvailabilityStatus(AvailabilityStatus.NOT_AVAILABLE);

        rentalRepository.save(rental);
        bookRepository.save(book);

        return mapToResponse(rental);
    }

    @Override
    public List<RentalResponse> getActiveRentals(Long userId) {

        log.info("Fetching active rentals for user {}", userId);

        return rentalRepository.findByUserIdAndReturnDateIsNull(userId)
                .stream().map(this::mapToResponse).toList();
    }

    @Override
    @Transactional
    public void returnBook(Long rentalId) {

        log.info("Returning rental id {}", rentalId);

        Rental rental = rentalRepository.findActiveRentalById(rentalId);

        if (rental == null) {
            throw new BadRequestException("No active rental found for this id");
        }

        rental.setReturnDate(LocalDate.now());

        Book book = rental.getBook();
        book.setAvailabilityStatus(AvailabilityStatus.AVAILABLE);

        rentalRepository.save(rental);
        bookRepository.save(book);
    }

    private RentalResponse mapToResponse(Rental rental) {

        Book book = rental.getBook();

        BookResponse bookResponse = BookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .genre(book.getGenre())
                .availabilityStatus(book.getAvailabilityStatus())
                .build();

        return RentalResponse.builder()
                .id(rental.getId())
                .book(bookResponse)
                .rentedAt(rental.getRentedAt())
                .returnDate(rental.getReturnDate())
                .build();
    }
}
