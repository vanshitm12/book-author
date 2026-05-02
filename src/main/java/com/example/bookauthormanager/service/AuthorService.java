package com.example.bookauthormanager.service;

import com.example.bookauthormanager.entity.Author;
import com.example.bookauthormanager.repository.AuthorRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Transactional
    public Author createAuthor(Author author) {
        validateAuthor(author);
        return authorRepository.save(author);
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Author getAuthorById(Long id) {
        return authorRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Author not found with id: " + id));
    }

    @Transactional
    public Author updateAuthor(Long id, Author updatedAuthor) {
        validateAuthor(updatedAuthor);
        Author existingAuthor = getAuthorById(id);
        existingAuthor.setName(updatedAuthor.getName());
        existingAuthor.setEmail(updatedAuthor.getEmail());
        existingAuthor.setAge(updatedAuthor.getAge());
        return authorRepository.save(existingAuthor);
    }

    private void validateAuthor(Author author) {
        if (author == null) {
            throw new IllegalArgumentException("Author is required");
        }
        if (author.getName() == null || author.getName().isBlank()) {
            throw new IllegalArgumentException("Author name is required");
        }
        if (author.getEmail() == null || author.getEmail().isBlank()) {
            throw new IllegalArgumentException("Author email is required");
        }
        if (author.getAge() == null || author.getAge() < 18) {
            throw new IllegalArgumentException("Author age must be at least 18");
        }
    }
}
