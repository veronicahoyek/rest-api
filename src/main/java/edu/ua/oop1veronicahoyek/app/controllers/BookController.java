package edu.ua.oop1veronicahoyek.app.controllers;

import edu.ua.oop1veronicahoyek.app.exceptions.AlreadyExistsException;
import edu.ua.oop1veronicahoyek.app.exceptions.ColumnNotFoundException;
import edu.ua.oop1veronicahoyek.app.exceptions.EntityValidationException;
import edu.ua.oop1veronicahoyek.app.exceptions.ResourceNotFoundException;
import edu.ua.oop1veronicahoyek.app.models.DTOs.book.BookInsertDTO;
import edu.ua.oop1veronicahoyek.app.models.DTOs.book.BookPrintDTO;
import edu.ua.oop1veronicahoyek.app.models.DTOs.book.BookUpdateDTO;
import edu.ua.oop1veronicahoyek.app.models.entities.Publisher;
import edu.ua.oop1veronicahoyek.app.services.BookService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
@Tag(name = "Book", description = "The Book RestAPI")
public class BookController {
    private final BookService bookService;

    @ExceptionHandler({ResourceNotFoundException.class, ColumnNotFoundException.class})
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

    @Operation(summary = "Get specific Book by Id.", description = "Get specific Book by Id.")
    @GetMapping("{bookId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public BookPrintDTO getBookById(
            @Parameter(description = "The book's Id") @PathVariable String bookId
    ) {
        return bookService.getById(bookId);
    }

    @Operation(summary = "List all available Books.", description = "List all available Books.")
    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<BookPrintDTO> getAllBooks() {
        return bookService.getAll();
    }

    @GetMapping("{pageNb}/{pageSize}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<BookPrintDTO> searchBookByTitle(
            @Parameter(description = "The page Number") @PathVariable @Validated @Min(value = 0, message = "Page number cannot be negative") int pageNb,
            @Parameter(description = "The page size") @PathVariable @Validated @Min(value = 1, message = "Page size cannot be less than 1") int pageSize,
            @Parameter(description = "Title search query") @RequestParam(required = false) String title,
            @Parameter(description = "Sort column name") @RequestParam(defaultValue = "title") String sort,
            @Parameter(description = "Sort Order, 0=DESC & 1=ASC") @RequestParam(defaultValue = "1") int order
    ) {
        return bookService.getAllBookByTitleSearchPaginated(pageNb, pageSize, title, sort, order);
    }

    @Operation(summary = "Add a new Book to the Store.", description = "Add a new Book to the Store.")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public BookPrintDTO insertBook(
            @Parameter(description = "The information of the Book to be added") @RequestBody BookInsertDTO book
    ) {
        return bookService.insert(book);
    }

    @Operation(summary = "Add new Books to the Store.", description = "Add new Books to the Store.")
    @PostMapping("multiple")
    @PreAuthorize("hasRole('ADMIN')")
    @Hidden
    public List<BookPrintDTO> insertBooks(
            @Parameter(description = "The information of the Books to be added") @RequestBody List<BookInsertDTO> bookList
    ) {
        return bookService.insertAll(bookList);
    }

    @Operation(summary = "Update a Book.", description = "Update a Book.")
    @PutMapping("{bookId}")
    @PreAuthorize("hasRole('ADMIN')")
    public BookPrintDTO updateBook(
            @Parameter(description = "The book's Id") @PathVariable String bookId,
            @Parameter(description = "The updated information of the Book to be updated") @RequestBody BookUpdateDTO book
    ) {
        return bookService.update(bookId, book);
    }

    @Operation(summary = "Delete a Book by id.", description = "Delete a Book by id.")
    @DeleteMapping("{bookId}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteBook(@Parameter(description = "The book's Id") @PathVariable String bookId) {
        bookService.deleteById(bookId);
    }

    @Operation(summary = "Get Publisher by Book id.", description = "Get Publisher by Book id.")
    @GetMapping("{bookId}/publisher")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public Publisher getPublisherByBookId(@Parameter(description = "The book's Id") @PathVariable String bookId) {
        return bookService.getPublisherByBookId(bookId);
    }

    @Operation(summary = "Buy a Book.", description = "Lower Book quantity by one.")
    @PatchMapping("{bookId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public String buyBookById(@Parameter(description = "The book's Id") @PathVariable String bookId) {
        return bookService.buyBookById(bookId);
    }

    @Operation(summary = "Add Book to stock.", description = "Increase Book Quantity.")
    @PatchMapping("{bookId}/{quantity}")
    @PreAuthorize("hasRole('ADMIN')")
    public void addBookById(
            @Parameter(description = "The book's Id") @PathVariable String bookId,
            @Parameter(description = "Quantity to add") @PathVariable Integer quantity
    ) {
        bookService.addBookById(bookId, quantity);
    }

    @Operation(summary = "Filter by Year", description = "Filter by finding between two Years")
    @GetMapping("year/{startYear}/{endYear}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public List<BookPrintDTO> filterBetweenYear(
            @Parameter(description = "Start Year") @PathVariable int startYear,
            @Parameter(description = "End Year") @PathVariable int endYear
    ) {
        return bookService.filterByYear(startYear, endYear);
    }
}