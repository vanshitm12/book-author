package com.example.bookauthormanager.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.bookauthormanager.entity.Author;
import com.example.bookauthormanager.repository.AuthorRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    private AuthorService authorService;

    @BeforeEach
    void setup() {
        authorService = new AuthorService(authorRepository);
    }

    @Test
    void createAuthor_shouldSaveValidAuthor() {
        Author author = new Author();
        author.setName("Service Author");
        author.setEmail("service.author@example.com");
        author.setAge(41);

        when(authorRepository.save(any(Author.class))).thenReturn(author);

        Author saved = authorService.createAuthor(author);

        assertThat(saved.getName()).isEqualTo("Service Author");
        verify(authorRepository).save(author);
    }

    @Test
    void updateAuthor_shouldUpdateFields() {
        Author existing = new Author();
        existing.setId(1L);
        existing.setName("Old Name");
        existing.setEmail("old@example.com");
        existing.setAge(28);

        Author incoming = new Author();
        incoming.setName("New Name");
        incoming.setEmail("new@example.com");
        incoming.setAge(35);

        when(authorRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(authorRepository.save(any(Author.class))).thenReturn(existing);

        Author updated = authorService.updateAuthor(1L, incoming);

        assertThat(updated.getName()).isEqualTo("New Name");
        assertThat(updated.getEmail()).isEqualTo("new@example.com");
        assertThat(updated.getAge()).isEqualTo(35);
    }

    @Test
    void createAuthor_shouldThrowWhenInvalid() {
        Author invalid = new Author();
        invalid.setName("");
        invalid.setEmail("bad@example.com");
        invalid.setAge(22);

        assertThatThrownBy(() -> authorService.createAuthor(invalid))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
