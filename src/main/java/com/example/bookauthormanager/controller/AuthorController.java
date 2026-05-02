package com.example.bookauthormanager.controller;

import com.example.bookauthormanager.entity.Author;
import com.example.bookauthormanager.service.AuthorService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/add")
    public String showAddAuthorForm(Model model) {
        model.addAttribute("author", new Author());
        return "authors/addAuthor";
    }

    @PostMapping("/add")
    public String addAuthor(@ModelAttribute("author") Author author, Model model) {
        try {
            authorService.createAuthor(author);
            return "redirect:/authors/list";
        } catch (IllegalArgumentException | DataIntegrityViolationException ex) {
            model.addAttribute("author", author);
            model.addAttribute("errorMessage", ex.getMessage());
            return "authors/addAuthor";
        }
    }

    @GetMapping("/list")
    public String listAuthors(Model model) {
        model.addAttribute("authors", authorService.getAllAuthors());
        return "authors/listAuthors";
    }

    @GetMapping("/edit/{id}")
    public String showEditAuthorForm(@PathVariable Long id, Model model) {
        model.addAttribute("author", authorService.getAuthorById(id));
        return "authors/editAuthor";
    }

    @PostMapping("/update/{id}")
    public String updateAuthor(@PathVariable Long id, @ModelAttribute("author") Author author, Model model) {
        try {
            authorService.updateAuthor(id, author);
            return "redirect:/authors/list";
        } catch (IllegalArgumentException | DataIntegrityViolationException | EntityNotFoundException ex) {
            author.setId(id);
            model.addAttribute("author", author);
            model.addAttribute("errorMessage", ex.getMessage());
            return "authors/editAuthor";
        }
    }
}
