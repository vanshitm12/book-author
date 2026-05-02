package com.example.bookauthormanager.repository;

import com.example.bookauthormanager.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
