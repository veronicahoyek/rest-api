package edu.ua.oop1veronicahoyek;

import edu.ua.oop1veronicahoyek.app.helper.CharUtilsTest;
import edu.ua.oop1veronicahoyek.app.helper.EntityUtilsTest;
import edu.ua.oop1veronicahoyek.app.helper.ISBN.ISBN10SpecificationTest;
import edu.ua.oop1veronicahoyek.app.helper.ISBN.ISBN13SpecificationTest;
import edu.ua.oop1veronicahoyek.app.isbnValidator.ISBNValidatorTest;
import edu.ua.oop1veronicahoyek.app.models.entities.Author;
import edu.ua.oop1veronicahoyek.app.repositories.AuthorRepositoryTest;
import edu.ua.oop1veronicahoyek.app.repositories.BookRepositoryTest;
import edu.ua.oop1veronicahoyek.app.repositories.PublisherRepositoryTest;
import edu.ua.oop1veronicahoyek.app.services.AuthorServiceTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Oop1VeronicaHoyekApplicationTests {

    private ISBN10SpecificationTest a1;
    private ISBN13SpecificationTest a2;
    private CharUtilsTest a3;
    private EntityUtilsTest a4;
    private ISBNValidatorTest a5;
    private AuthorRepositoryTest a6;
    private BookRepositoryTest a7;
    private PublisherRepositoryTest a8;
    private AuthorServiceTest a9;

    @Test
    void contextLoads() {
    }

}
