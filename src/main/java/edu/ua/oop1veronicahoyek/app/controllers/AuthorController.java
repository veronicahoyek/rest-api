package edu.ua.oop1veronicahoyek.app.controllers;

import edu.ua.oop1veronicahoyek.app.exceptions.AlreadyExistsException;
import edu.ua.oop1veronicahoyek.app.exceptions.EntityValidationException;
import edu.ua.oop1veronicahoyek.app.exceptions.ResourceNotFoundException;
import edu.ua.oop1veronicahoyek.app.models.DTOs.author.AuthorInsertDTO;
import edu.ua.oop1veronicahoyek.app.models.DTOs.author.AuthorUpdateDTO;
import edu.ua.oop1veronicahoyek.app.models.entities.Author;
import edu.ua.oop1veronicahoyek.app.models.entities.Book;
import edu.ua.oop1veronicahoyek.app.services.AuthorService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("authors")
@RequiredArgsConstructor
@Tag(name = "Author", description = "The Author RestAPI")
public class AuthorController {
    private final AuthorService authorService;

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<String> handleResourceNotFoundException(RuntimeException runtimeException) {
        return new ResponseEntity<>(runtimeException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({EntityValidationException.class})
    public ResponseEntity<String> handleValidationException(RuntimeException runtimeException) {
        return new ResponseEntity<>(runtimeException.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler({AlreadyExistsException.class})
    public ResponseEntity<String> handleExistsException(RuntimeException runtimeException) {
        return new ResponseEntity<>(runtimeException.getMessage(), HttpStatus.CONFLICT);
    }

    @Operation(summary = "List all available Authors.", description = "List all available Authors.")
    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<Author> getAllAuthors() {
        return authorService.getAll();
    }

    @Operation(summary = "Get specific Author by Id.", description = "Get specific Author by Id.")
    @GetMapping("{authorId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public Author getAuthorById(@Parameter(description = "The author's Id") @PathVariable String authorId) {
        return authorService.getById(authorId);
    }

    @Operation(summary = "Add a new Author to the Store.", description = "Add a new Author to the Store.")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Author insertAuthor(@Parameter(description = "The information of the Author to be added") @RequestBody AuthorInsertDTO author) {
        return authorService.insert(author);
    }

    @PostMapping("multiple")
    @PreAuthorize("hasRole('ADMIN')")
    @Hidden
    public List<Author> insertAuthors(@RequestBody List<AuthorInsertDTO> authorList) {
        return authorService.insertAll(authorList);
    }

    @Operation(summary = "Update an Author.", description = "Update a Author.")
    @PutMapping("{authorId}")
    @PreAuthorize("hasRole('ADMIN')")
    public Author updateAuthor(
            @Parameter(description = "The author's Id") @PathVariable String authorId,
            @Parameter(description = "The updated information of the Author to be updated") @RequestBody AuthorUpdateDTO author
    ) {
        return authorService.update(authorId, author);
    }

    @Operation(summary = "Delete an Author by id.", description = "Delete a Author by id.")
    @DeleteMapping("{authorId}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteAuthor(@Parameter(description = "The author's Id") @PathVariable String authorId) {
        authorService.deleteById(authorId);
    }

    @Operation(summary = "Get all Books by Author id.", description = "Get all Books by Author id.")
    @GetMapping("{authorId}/books")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<Book> getBookListByAuthorId(@Parameter(description = "The author's Id") @PathVariable String authorId) {
        return authorService.getBookListByAuthorId(authorId);
    }

    @GetMapping("name/{authorName}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Hidden
    public Author getAuthorByName(@PathVariable String authorName) {
        return authorService.getAuthorByName(authorName);
    }
}
