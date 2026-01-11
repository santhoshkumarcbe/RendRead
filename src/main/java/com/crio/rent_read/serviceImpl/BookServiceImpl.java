package com.crio.rent_read.serviceImpl;

import com.crio.rent_read.dto.request.BookRequest;
import com.crio.rent_read.dto.response.BookResponse;
import com.crio.rent_read.entity.Book;
import com.crio.rent_read.entity.enums.AvailabilityStatus;
import com.crio.rent_read.exception.ResourceNotFoundException;
import com.crio.rent_read.repository.BookRepository;
import com.crio.rent_read.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public BookResponse createBook(BookRequest request) {

        log.info("Creating new book: {}", request.getTitle());

        Book book = Book.builder()
                .title(request.getTitle())
                .author(request.getAuthor())
                .genre(request.getGenre())
                .availabilityStatus(request.getAvailabilityStatus())
                .build();

        return mapToResponse(bookRepository.save(book));
    }

    @Override
    public BookResponse updateBook(Long bookId, BookRequest request) {

        log.info("Updating book id: {}", bookId);

        Book book = bookRepository.findById(bookId)
                .orElseThrow();

        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setGenre(request.getGenre());
        book.setAvailabilityStatus(request.getAvailabilityStatus());

        return mapToResponse(bookRepository.save(book));
    }

    @Override
    public void deleteBook(Long bookId) {

        log.warn("Deleting book id: {}", bookId);

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));

        bookRepository.delete(book);
    }

    @Override
    public List<BookResponse> getAllBooks() {
        return bookRepository.findAll().stream().map(this::mapToResponse).toList();
    }

    @Override
    public List<BookResponse> getAvailableBooks() {
        return bookRepository.findByAvailabilityStatus(AvailabilityStatus.AVAILABLE)
                .stream().map(this::mapToResponse).toList();
    }

    private BookResponse mapToResponse(Book book) {
        return BookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .genre(book.getGenre())
                .availabilityStatus(book.getAvailabilityStatus())
                .build();
    }
}
