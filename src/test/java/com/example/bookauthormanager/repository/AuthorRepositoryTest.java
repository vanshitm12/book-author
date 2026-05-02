package com.example.bookauthormanager.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.bookauthormanager.entity.Author;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    void saveAndFindAll_shouldPersistAuthor() {
        Author author = new Author();
        author.setName("Test Author");
        author.setEmail("test.author@example.com");
        author.setAge(35);

        authorRepository.save(author);

        List<Author> authors = authorRepository.findAll();
        assertThat(authors).isNotEmpty();
        assertThat(authors).extracting(Author::getEmail).contains("test.author@example.com");
    }
}
