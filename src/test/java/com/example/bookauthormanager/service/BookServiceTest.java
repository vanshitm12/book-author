package com.example.bookauthormanager.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.bookauthormanager.entity.Author;
import com.example.bookauthormanager.entity.Book;
import com.example.bookauthormanager.repository.AuthorRepository;
import com.example.bookauthormanager.repository.BookRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    private BookService bookService;

    @BeforeEach
    void setup() {
        bookService = new BookService(bookRepository, authorRepository);
    }

    @Test
    void createBook_shouldUseExistingAuthorWhenAuthorIdProvided() {
        Author author = new Author();
        author.setId(1L);
        author.setName("Existing Author");
        author.setEmail("existing@example.com");
        author.setAge(40);

        Book book = new Book();
        book.setTitle("Service Book");
        book.setIsbn("ISBN-SERVICE");
        book.setPublicationYear(2022);

        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(bookRepository.save(any(Book.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Book saved = bookService.createBook(book, 1L);

        assertThat(saved.getAuthor().getName()).isEqualTo("Existing Author");
        verify(bookRepository).save(book);
    }

    @Test
    void updateBook_shouldUpdateBookDetails() {
        Author author = new Author();
        author.setId(2L);
        author.setName("Updated Author");
        author.setEmail("updated@example.com");
        author.setAge(38);

        Book existingBook = new Book();
        existingBook.setId(1L);
        existingBook.setTitle("Old");
        existingBook.setIsbn("OLD-1");
        existingBook.setPublicationYear(2018);

        Book incoming = new Book();
        incoming.setTitle("New");
        incoming.setIsbn("NEW-1");
        incoming.setPublicationYear(2024);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(existingBook));
        when(authorRepository.findById(2L)).thenReturn(Optional.of(author));
        when(bookRepository.save(any(Book.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Book updated = bookService.updateBook(1L, incoming, 2L);

        assertThat(updated.getTitle()).isEqualTo("New");
        assertThat(updated.getAuthor().getId()).isEqualTo(2L);
    }

    @Test
    void createBook_shouldThrowWhenInvalidBook() {
        Book invalid = new Book();
        invalid.setTitle("");
        invalid.setIsbn("X");
        invalid.setPublicationYear(2020);

        assertThatThrownBy(() -> bookService.createBook(invalid, null))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
