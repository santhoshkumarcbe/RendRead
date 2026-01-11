package com.crio.rent_read.repository;

import com.crio.rent_read.entity.Book;
import com.crio.rent_read.entity.enums.AvailabilityStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByAvailabilityStatus(AvailabilityStatus availabilityStatus);
}

