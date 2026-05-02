package com.example.bookauthormanager.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.bookauthormanager.entity.Author;
import com.example.bookauthormanager.entity.Book;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    void saveAndFindAll_shouldPersistBook() {
        Author author = new Author();
        author.setName("Repo Author");
        author.setEmail("repo.author@example.com");
        author.setAge(44);
        Author savedAuthor = authorRepository.save(author);

        Book book = new Book();
        book.setTitle("Repo Book");
        book.setIsbn("ISBN-REPO-1");
        book.setPublicationYear(2020);
        book.setAuthor(savedAuthor);

        bookRepository.save(book);

        List<Book> books = bookRepository.findAll();
        assertThat(books).isNotEmpty();
        assertThat(books).extracting(Book::getTitle).contains("Repo Book");
    }

    @Test
    void findAllBooksWithAuthors_shouldReturnBooksWithAuthors() {
        Author author = new Author();
        author.setName("Join Author");
        author.setEmail("join.author@example.com");
        author.setAge(39);
        Author savedAuthor = authorRepository.save(author);

        Book book = new Book();
        book.setTitle("Join Book");
        book.setIsbn("ISBN-JOIN-1");
        book.setPublicationYear(2021);
        book.setAuthor(savedAuthor);
        bookRepository.save(book);

        List<Book> joinedBooks = bookRepository.findAllBooksWithAuthors();

        assertThat(joinedBooks).isNotEmpty();
        assertThat(joinedBooks.get(0).getAuthor()).isNotNull();
    }
}
