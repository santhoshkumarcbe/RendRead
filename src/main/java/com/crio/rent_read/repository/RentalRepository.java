package com.crio.rent_read.repository;

import com.crio.rent_read.entity.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RentalRepository extends JpaRepository<Rental, Long> {

    List<Rental> findByUserIdAndReturnDateIsNull(Long userId);

    long countByUserIdAndReturnDateIsNull(Long userId);

    boolean existsByBookIdAndReturnDateIsNull(Long bookId);

    @Query("SELECT r FROM Rental r WHERE r.id = :rentalId AND r.returnDate IS NULL")
    Rental findActiveRentalById(Long rentalId);
}
