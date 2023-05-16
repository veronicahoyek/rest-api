package edu.ua.oop1veronicahoyek.app.repositories;

import edu.ua.oop1veronicahoyek.app.models.entities.Publisher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataMongoTest
public class PublisherRepositoryTest {
    @Autowired
    private PublisherRepository underTest;

    @Test
    public void findPublisherByNameAndFoundationYearTest() {
        Publisher publisher = getPublisher();
        underTest.save(publisher);
        Publisher result = underTest.findByNameAndFoundationYear(publisher.getName(),publisher.getFoundationYear());
        if (result.getBookList().isEmpty())
            result.setBookList(null);
        assertEquals(publisher, result);
    }

    private Publisher getPublisher() {

        Publisher publisher = new Publisher();
        publisher.setId("0");
        publisher.setName("hello");
        publisher.setAcronym("lol");
        publisher.setFoundationYear(2000);
        publisher.setBookList(null);
        return publisher;
    }
}