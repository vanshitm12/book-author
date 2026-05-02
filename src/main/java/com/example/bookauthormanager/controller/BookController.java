package com.example.bookauthormanager.controller;

import com.example.bookauthormanager.entity.Author;
import com.example.bookauthormanager.entity.Book;
import com.example.bookauthormanager.service.AuthorService;
import com.example.bookauthormanager.service.BookService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;

    public BookController(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @GetMapping("/add")
    public String showAddBookForm(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("authors", authorService.getAllAuthors());
        return "books/addBook";
    }

    @PostMapping("/add")
    public String addBook(
            @ModelAttribute("book") Book book,
            @RequestParam(value = "authorId", required = false) Long authorId,
            @RequestParam(value = "newAuthorName", required = false) String newAuthorName,
            @RequestParam(value = "newAuthorEmail", required = false) String newAuthorEmail,
            @RequestParam(value = "newAuthorAge", required = false) Integer newAuthorAge,
            Model model) {
        try {
            attachNewAuthorIfNeeded(book, authorId, newAuthorName, newAuthorEmail, newAuthorAge);
            bookService.createBook(book, authorId);
            return "redirect:/books/list";
        } catch (IllegalArgumentException | DataIntegrityViolationException | EntityNotFoundException ex) {
            model.addAttribute("book", book);
            model.addAttribute("authors", authorService.getAllAuthors());
            model.addAttribute("errorMessage", ex.getMessage());
            return "books/addBook";
        }
    }

    @GetMapping("/list")
    public String listBooks(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "books/listBooks";
    }

    @GetMapping("/edit/{id}")
    public String showEditBookForm(@PathVariable Long id, Model model) {
        model.addAttribute("book", bookService.getBookById(id));
        model.addAttribute("authors", authorService.getAllAuthors());
        return "books/editBook";
    }

    @PostMapping("/update/{id}")
    public String updateBook(
            @PathVariable Long id,
            @ModelAttribute("book") Book book,
            @RequestParam(value = "authorId", required = false) Long authorId,
            @RequestParam(value = "newAuthorName", required = false) String newAuthorName,
            @RequestParam(value = "newAuthorEmail", required = false) String newAuthorEmail,
            @RequestParam(value = "newAuthorAge", required = false) Integer newAuthorAge,
            Model model) {
        try {
            attachNewAuthorIfNeeded(book, authorId, newAuthorName, newAuthorEmail, newAuthorAge);
            bookService.updateBook(id, book, authorId);
            return "redirect:/books/list";
        } catch (IllegalArgumentException | DataIntegrityViolationException | EntityNotFoundException ex) {
            book.setId(id);
            model.addAttribute("book", book);
            model.addAttribute("authors", authorService.getAllAuthors());
            model.addAttribute("errorMessage", ex.getMessage());
            return "books/editBook";
        }
    }

    private void attachNewAuthorIfNeeded(
            Book book,
            Long authorId,
            String newAuthorName,
            String newAuthorEmail,
            Integer newAuthorAge) {
        if (authorId != null) {
            return;
        }

        Author author = new Author();
        author.setName(newAuthorName);
        author.setEmail(newAuthorEmail);
        author.setAge(newAuthorAge);
        book.setAuthor(author);
    }
}
