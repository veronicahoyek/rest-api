package edu.ua.oop1veronicahoyek.app.helper;

import edu.ua.oop1veronicahoyek.app.models.DTOs.author.AuthorInsertDTO;
import edu.ua.oop1veronicahoyek.app.models.DTOs.book.BookInsertDTO;
import edu.ua.oop1veronicahoyek.app.models.DTOs.publisher.PublisherInsertDTO;
import edu.ua.oop1veronicahoyek.app.models.entities.Author;
import edu.ua.oop1veronicahoyek.app.models.entities.Book;
import edu.ua.oop1veronicahoyek.app.models.entities.Publisher;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class EntityUtilsTest {

    private EntityUtils underTest;
    @Test
    public void DtoToEntityTest() {
        // Create a DTO object
        BookInsertDTO myDto1 = new BookInsertDTO("le",0,"456",2000,"1234567890","123","leul",12);
        AuthorInsertDTO myDto2 = new AuthorInsertDTO("auth",2001);
        PublisherInsertDTO myDto3 = new PublisherInsertDTO("penguin","pg",2000);

        // Convert the DTO object to an entity object
        Book book = underTest.dtoToEntity(myDto1,Book.class);
        Author author = underTest.dtoToEntity(myDto2,Author.class);
        Publisher publisher = underTest.dtoToEntity(myDto3,Publisher.class);

        // Verify that the entity object has the same values as the DTO object
        assertEquals("le", book.getTitle());
        assertEquals("auth", author.getName());
        assertEquals("penguin", publisher.getName());
    }

}