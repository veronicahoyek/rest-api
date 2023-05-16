package edu.ua.oop1veronicahoyek.app.repositories;

import edu.ua.oop1veronicahoyek.app.models.entities.Author;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataMongoTest
public class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository underTest;
    @Test
    public void findAuthorByNameAndYearOfBirthTest() {
        Author author = getAuthor();
        underTest.save(author);
        Author result = underTest.findByNameAndYearOfBirth(author.getName(),author.getYearOfBirth());
        if (result.getBooklist().isEmpty())
            result.setBooklist(null);
        assertEquals(author, result);
    }

    private Author getAuthor() {

        Author author = new Author();
        author.setId("0");
        author.setYearOfBirth(2000);
        author.setName("hello");
        author.setBooklist(null);
        return author;
    }
}