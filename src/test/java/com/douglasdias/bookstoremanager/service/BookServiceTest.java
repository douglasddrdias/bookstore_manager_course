package com.douglasdias.bookstoremanager.service;

import com.douglasdias.bookstoremanager.dto.BookDTO;
import com.douglasdias.bookstoremanager.entity.Book;
import com.douglasdias.bookstoremanager.exception.BookNotFoundException;
import com.douglasdias.bookstoremanager.repository.BookRepository;
import com.douglasdias.bookstoremanager.utils.BookUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.douglasdias.bookstoremanager.utils.BookUtils.createFakeBook;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    void whenGivenExistingIdThenReturnThisBook() {
        Book expectedFoundBook = createFakeBook();
        when(bookRepository.findById(expectedFoundBook.getId())).thenReturn(Optional.of(expectedFoundBook));
        BookDTO bookDTO = null;
        try {
            bookDTO = bookService.findById(expectedFoundBook.getId());
            assertEquals(expectedFoundBook.getName(), bookDTO.getName());
            assertEquals(expectedFoundBook.getIsbn(), bookDTO.getIsbn());
            assertEquals(expectedFoundBook.getPublisherName(), bookDTO.getPublisherName());
        } catch (BookNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    void whenGivenUnexistingIdThenNotFoundThrowAnException() {
        var invalidId = 10L;
        when(bookRepository.findById(invalidId)).thenReturn(Optional.ofNullable(any(Book.class)));
        assertThrows(BookNotFoundException.class, () -> bookService.findById(invalidId));
    }
}
