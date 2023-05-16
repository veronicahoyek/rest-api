package edu.ua.oop1veronicahoyek.app.services;

import edu.ua.oop1veronicahoyek.app.exceptions.AlreadyExistsException;
import edu.ua.oop1veronicahoyek.app.exceptions.ColumnNotFoundException;
import edu.ua.oop1veronicahoyek.app.exceptions.ResourceNotFoundException;
import edu.ua.oop1veronicahoyek.app.helper.EntityUtils;
import edu.ua.oop1veronicahoyek.app.models.DTOs.book.BookInsertDTO;
import edu.ua.oop1veronicahoyek.app.models.DTOs.book.BookPrintDTO;
import edu.ua.oop1veronicahoyek.app.models.DTOs.book.BookUpdateDTO;
import edu.ua.oop1veronicahoyek.app.models.entities.Book;
import edu.ua.oop1veronicahoyek.app.models.entities.Publisher;
import edu.ua.oop1veronicahoyek.app.repositories.BookRepository;
import jakarta.validation.Validator;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService extends EntityValidationService<Book> implements CrudService <BookPrintDTO, BookInsertDTO, BookUpdateDTO> {
    private final BookRepository bookRepo;
    private final PublisherService pubService;
    private final AuthorService authorService;

    public BookService(Validator validator, BookRepository bookRepo, PublisherService pubService, AuthorService authorService) {
        super(validator);
        this.bookRepo = bookRepo;
        this.pubService = pubService;
        this.authorService = authorService;
    }

    public void checkIfExists(Book book) {
        if (bookRepo.findByTitleAndYear(book.getTitle(),book.getYear()) != null)
            throw new AlreadyExistsException("This book already exists.");
    }

    public void checkIfBooksExists(List<Book> booksList) {
        StringBuilder sBuilder = new StringBuilder();

        for (int i = 0; i < booksList.size(); i++) {
            Book book = booksList.get(i);

            try {
                checkIfExists(book);
            } catch (AlreadyExistsException e) {
                sBuilder.append("The book inserted at index ").append(i).append(" already exists.\n");
            }

        }

        if (!sBuilder.isEmpty()) {
            throw new AlreadyExistsException(sBuilder.toString());
        }
    }

    @Override
    public List<BookPrintDTO> getAll() {
//        return bookRepo.findAll().stream().map(book -> new BookPrintDTO(book)).toList();
        return bookRepo.findAll().stream().map(BookPrintDTO::new).toList();
    }

    public Book getBookById(String id) {
        return bookRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("book", "id", id));
    }

    @Override
    public BookPrintDTO getById(String id) {
        return new BookPrintDTO(getBookById(id));
    }

    @Override
    public BookPrintDTO insert(BookInsertDTO insert) {
        Book book = EntityUtils.dtoToEntity(insert, Book.class);
        book.setPublisher(pubService.getById(insert.getPublisherId()));
        book.setAuthor(authorService.getById(insert.getAuthorId()));
        validateEntity(book);
        checkIfExists(book);
        return new BookPrintDTO(bookRepo.insert(book));
    }

    @Override
    public List<BookPrintDTO> insertAll(List<BookInsertDTO> insertList) {
        List<Book> bookList = insertList.stream().map(insert -> {
            Book book = EntityUtils.dtoToEntity(insert, Book.class);
            book.setPublisher(pubService.getById(insert.getPublisherId()));
            book.setAuthor(authorService.getById(insert.getAuthorId()));
            return book;
        }).toList();

        validateEntities(bookList);
        checkIfBooksExists(bookList);
        return bookRepo.insert(bookList).stream().map(BookPrintDTO::new).toList();
    }

    @Override
    public BookPrintDTO update(String id, BookUpdateDTO update) {
        Book book = getBookById(id);

        if(update.getTitle() != null) {
            book.setTitle(update.getTitle());
        }

        if(update.getQuantity() != null)
            book.setQuantity(update.getQuantity());

        if(update.getAuthorId() != null)
            book.setAuthor(authorService.getById(update.getAuthorId()));

        if(update.getYear() != null)
            book.setYear(update.getYear());

        if(update.getIsbn() != null)
            book.setIsbn(update.getIsbn());

        if(update.getPublisherId() != null) {
            book.setPublisher(pubService.getById(update.getPublisherId()));
        }

        if(update.getGenre() != null)
            book.setGenre(update.getGenre());

        if(update.getPageCount() != null)
            book.setPageCount(update.getPageCount());

        validateEntity(book);
        return new BookPrintDTO(bookRepo.save(book));
    }

    @Override
    public void deleteById(String id) {
        bookRepo.deleteById(id);
    }

    public Publisher getPublisherByBookId(String id) {
        return getBookById(id).getPublisher();
    }

    public String buyBookById(String id) {
        Book book = getBookById(id);
        if (book.getQuantity() > 0) {
            book.setQuantity(book.getQuantity() - 1);
            bookRepo.save(book);
            return "Book bought successfully.";
        }

        return "Book is out of stock.";
    }

    public void addBookById(String id, int quantity) {
        Book book = getBookById(id);
        book.setQuantity(book.getQuantity() + quantity);
        bookRepo.save(book);
    }

    public List<BookPrintDTO> getAllBookByTitleSearchPaginated(int pageNb, int pageSize, String title, String sort, int order) {
        if (!Book.getListColumns().contains(sort)) {
            throw new ColumnNotFoundException(sort, "book");
        }

        Sort.Direction direction;
        if (order == 0) {
            direction = Sort.Direction.DESC;
        } else {
            direction = Sort.Direction.ASC;
        }

        Pageable pageable = PageRequest.of(pageNb, pageSize, Sort.by(direction, sort));

        List<Book> bookList;
        if (title == null || title.isEmpty()) {
            bookList = bookRepo.findAll(pageable).getContent();
        } else {
            bookList = bookRepo.findAllByTitleContainingIgnoreCase(title, pageable).getContent();
        }

        return bookList.stream().map(BookPrintDTO::new).toList();
    }

    public List<BookPrintDTO> filterByYear(int startYear, int endYear) {
        return bookRepo.findByYearBetween(Math.min(startYear, endYear), Math.max(startYear, endYear)).stream().map(BookPrintDTO::new).toList();
    }
}
