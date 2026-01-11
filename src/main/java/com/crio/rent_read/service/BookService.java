package com.crio.rent_read.service;

import com.crio.rent_read.dto.request.BookRequest;
import com.crio.rent_read.dto.response.BookResponse;

import java.util.List;

public interface BookService {

    BookResponse createBook(BookRequest request);

    BookResponse updateBook(Long bookId, BookRequest request);

    void deleteBook(Long bookId);

    List<BookResponse> getAllBooks();

    List<BookResponse> getAvailableBooks();
}
