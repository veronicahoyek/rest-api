package edu.ua.oop1veronicahoyek.app.controllers;

import edu.ua.oop1veronicahoyek.app.exceptions.AlreadyExistsException;
import edu.ua.oop1veronicahoyek.app.exceptions.EntityValidationException;
import edu.ua.oop1veronicahoyek.app.exceptions.ResourceNotFoundException;
import edu.ua.oop1veronicahoyek.app.models.DTOs.publisher.PublisherInsertDTO;
import edu.ua.oop1veronicahoyek.app.models.DTOs.publisher.PublisherUpdateDTO;
import edu.ua.oop1veronicahoyek.app.models.entities.Book;
import edu.ua.oop1veronicahoyek.app.models.entities.Publisher;
import edu.ua.oop1veronicahoyek.app.services.PublisherService;
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
@RequestMapping("/publishers")
@RequiredArgsConstructor
@Tag(name = "Publisher", description = "The Publisher RestAPI")
public class PublisherController {
    private final PublisherService publisherService;

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

    @Operation(summary = "List all available Publishers.", description = "List all available Publishers.")
    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<Publisher> getAllPublishers() {
        return publisherService.getAll();
    }

    @Operation(summary = "Get specific Publisher by Id.", description = "Get specific Publisher by Id.")
    @GetMapping("{publisherId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public Publisher getPublisherById(@Parameter(description = "The publisher's Id") @PathVariable String publisherId) {
        return publisherService.getById(publisherId);
    }

    @Operation(summary = "Add a new Publisher to the Store.", description = "Add a new Publisher to the Store.")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Publisher insertPublisher(
            @Parameter(description = "The information of the Publisher to be added") @RequestBody PublisherInsertDTO publisher
    ) {
        return publisherService.insert(publisher);
    }

    @Operation(summary = "Add new Publishers to the Store.", description = "Add new Publishers to the Store.")
    @PostMapping("multiple")
    @PreAuthorize("hasRole('ADMIN')")
    @Hidden
    public List<Publisher> insertPublishers(
            @Parameter(description = "The information of the Publishers to be added") @RequestBody List<PublisherInsertDTO> publisherList
    ) {
        return publisherService.insertAll(publisherList);
    }

    @Operation(summary = "Update a Publisher.", description = "Update a Publisher.")
    @PutMapping("{publisherId}")
    @PreAuthorize("hasRole('ADMIN')")
    public Publisher updatePublisher(
            @Parameter(description = "The publisher's Id") @PathVariable String publisherId,
            @Parameter(description = "The updated information of the Publisher to be updated") @RequestBody PublisherUpdateDTO publisher
    ) {
        return publisherService.update(publisherId, publisher);
    }

    @Operation(summary = "Delete a Publisher by id.", description = "Delete a Publisher by id.")
    @DeleteMapping("{publisherId}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deletePublisher(@Parameter(description = "The publisher's Id") @PathVariable String publisherId) {
        publisherService.deleteById(publisherId);
    }

    @Operation(summary = "Get all Books by Publisher id.", description = "Get all Books by Publisher id.")
    @GetMapping("{publisherId}/books")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<Book> getBookListByPublisherId(@Parameter(description = "The publisher's Id") @PathVariable String publisherId) {
        return publisherService.getBookListByPublisherId(publisherId);
    }

    @GetMapping("name/{publisherName}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Hidden
    public Publisher getAuthorByName(@PathVariable String publisherName) {
        return publisherService.getPublisherByName(publisherName);
    }
}
