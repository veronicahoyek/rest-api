package edu.ua.oop1veronicahoyek.app.repositories;

import edu.ua.oop1veronicahoyek.app.models.entities.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataMongoTest
public class BookRepositoryTest {

@Autowired
    private BookRepository underTest;

    @Test
    public void FindBookByTitleAndYearTest() {
        Book book = getBook();
        underTest.save(book);
        Book result = underTest.findByTitleAndYear(book.getTitle(),book.getYear());
        assertEquals(book, result);
    }

    @Test
    public void testFindAllByTitleContainingIgnoreCase() {
        // Create a list of books
        List<Book> books = new ArrayList<>();
        Book book1 = new Book();
        book1.setId("0");
        book1.setTitle("hello");
        book1.setQuantity(0);
        book1.setYear(2000);
        book1.setIsbn("1231231231");
        book1.setAuthor(null);
        book1.setPublisher(null);
        book1.setGenre("lol");
        book1.setPageCount(10);

        Book book2 = new Book();
        book2.setId("1");
        book2.setTitle("hello 1");
        book2.setQuantity(1);
        book2.setYear(2001);
        book2.setIsbn("1231231211");
        book2.setAuthor(null);
        book2.setPublisher(null);
        book2.setGenre("lol1");
        book2.setPageCount(11);
        books.add(book1);
        books.add(book2);
        underTest.saveAll(books);

        // Test the method with a search term
        Pageable pageable = PageRequest.of(0, 2); // get first 2 books
        Page<Book> result = underTest.findAllByTitleContainingIgnoreCase("hello", pageable);

        // Verify the results
        assertEquals(2, result.getNumberOfElements()); // only 2 books should match the search term
        assertEquals(book1.getTitle(), result.getContent().get(0).getTitle());
        assertEquals(book2.getTitle(), result.getContent().get(1).getTitle());
    }
    private Book getBook() {

        Book book = new Book();
        book.setId("0");
        book.setTitle("hello");
        book.setQuantity(0);
        book.setYear(2000);
        book.setIsbn("1231231231");
        book.setAuthor(null);
        book.setPublisher(null);
        book.setGenre("lol");
        book.setPageCount(10);
        return book;
    }
}
