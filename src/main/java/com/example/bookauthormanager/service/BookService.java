package com.example.bookauthormanager.service;

import com.example.bookauthormanager.entity.Author;
import com.example.bookauthormanager.entity.Book;
import com.example.bookauthormanager.repository.AuthorRepository;
import com.example.bookauthormanager.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Transactional
    public Book createBook(Book book, Long authorId) {
        validateBook(book);
        book.setAuthor(resolveAuthorForBook(book.getAuthor(), authorId));
        return bookRepository.save(book);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAllBooksWithAuthors();
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + id));
    }

    @Transactional
    public Book updateBook(Long id, Book updatedBook, Long authorId) {
        validateBook(updatedBook);
        Book existingBook = getBookById(id);
        existingBook.setTitle(updatedBook.getTitle());
        existingBook.setIsbn(updatedBook.getIsbn());
        existingBook.setPublicationYear(updatedBook.getPublicationYear());
        existingBook.setAuthor(resolveAuthorForBook(updatedBook.getAuthor(), authorId));
        return bookRepository.save(existingBook);
    }

    private Author resolveAuthorForBook(Author providedAuthor, Long authorId) {
        if (authorId != null) {
            return authorRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("Author not found with id: " + authorId));
        }
        if (providedAuthor == null) {
            throw new IllegalArgumentException("Author information is required");
        }
        if (providedAuthor.getId() != null) {
            return authorRepository.findById(providedAuthor.getId())
                .orElseThrow(() -> new EntityNotFoundException("Author not found with id: " + providedAuthor.getId()));
        }
        validateAuthorForBookCreation(providedAuthor);
        return authorRepository.save(providedAuthor);
    }

    private void validateBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book is required");
        }
        if (book.getTitle() == null || book.getTitle().isBlank()) {
            throw new IllegalArgumentException("Book title is required");
        }
        if (book.getIsbn() == null || book.getIsbn().isBlank()) {
            throw new IllegalArgumentException("Book ISBN is required");
        }
        if (book.getPublicationYear() == null || book.getPublicationYear() < 1000) {
            throw new IllegalArgumentException("Publication year is invalid");
        }
    }

    private void validateAuthorForBookCreation(Author author) {
        if (author.getName() == null || author.getName().isBlank()) {
            throw new IllegalArgumentException("New author name is required");
        }
        if (author.getEmail() == null || author.getEmail().isBlank()) {
            throw new IllegalArgumentException("New author email is required");
        }
        if (author.getAge() == null || author.getAge() < 18) {
            throw new IllegalArgumentException("New author age must be at least 18");
        }
    }
}
