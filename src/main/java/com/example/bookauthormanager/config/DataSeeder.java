package com.example.bookauthormanager.config;

import com.example.bookauthormanager.entity.Author;
import com.example.bookauthormanager.entity.Book;
import com.example.bookauthormanager.repository.AuthorRepository;
import com.example.bookauthormanager.repository.BookRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedData(AuthorRepository authorRepository, BookRepository bookRepository) {
        return args -> {
            if (authorRepository.count() > 0 || bookRepository.count() > 0) {
                return;
            }

            List<Author> authors = new ArrayList<>();
            for (int i = 1; i <= 10; i++) {
                Author author = new Author();
                author.setName("Author " + i);
                author.setEmail("author" + i + "@example.com");
                author.setAge(30 + i);
                authors.add(author);
            }
            authors = authorRepository.saveAll(authors);

            List<Book> books = new ArrayList<>();
            for (int i = 1; i <= 10; i++) {
                Book book = new Book();
                book.setTitle("Sample Book " + i);
                book.setIsbn("ISBN-1000" + i);
                book.setPublicationYear(2010 + i);
                book.setAuthor(authors.get((i - 1) % authors.size()));
                books.add(book);
            }
            bookRepository.saveAll(books);
        };
    }
}
