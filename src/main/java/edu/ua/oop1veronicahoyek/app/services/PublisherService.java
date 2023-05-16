package edu.ua.oop1veronicahoyek.app.services;

import edu.ua.oop1veronicahoyek.app.exceptions.AlreadyExistsException;
import edu.ua.oop1veronicahoyek.app.exceptions.ResourceNotFoundException;
import edu.ua.oop1veronicahoyek.app.helper.EntityUtils;
import edu.ua.oop1veronicahoyek.app.models.DTOs.publisher.PublisherInsertDTO;
import edu.ua.oop1veronicahoyek.app.models.DTOs.publisher.PublisherUpdateDTO;
import edu.ua.oop1veronicahoyek.app.models.entities.Book;
import edu.ua.oop1veronicahoyek.app.models.entities.Publisher;
import edu.ua.oop1veronicahoyek.app.repositories.PublisherRepository;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublisherService extends EntityValidationService<Publisher> implements CrudService <Publisher, PublisherInsertDTO, PublisherUpdateDTO>{
    private final PublisherRepository pubRepo;

    public PublisherService(Validator validator, PublisherRepository pubRepo) {
        super(validator);
        this.pubRepo = pubRepo;
    }

    public void checkIfExists(Publisher publisher) {
        if (pubRepo.findByNameAndFoundationYear(publisher.getName(), publisher.getFoundationYear()) != null)
            throw new AlreadyExistsException("This publisher already exists.");
    }

    public void checkIfPublishersExists(List<Publisher> publisherList) {
        StringBuilder sBuilder = new StringBuilder();

        for (int i = 0; i < publisherList.size(); i++) {
            Publisher publisher = publisherList.get(i);

            try {
                checkIfExists(publisher);
            } catch (AlreadyExistsException e) {
                sBuilder.append("The publisher inserted at index ").append(i).append(" already exists.\n");
            }

        }

        if (!sBuilder.isEmpty()) {
            throw new AlreadyExistsException(sBuilder.toString());
        }
    }

    @Override
    public List<Publisher> getAll() {
        return pubRepo.findAll();
    }

    @Override
    public Publisher getById(String id) {
        return pubRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("publisher", "id", id));
    }

    @Override
    public Publisher insert(PublisherInsertDTO insert) {
        Publisher publisher = EntityUtils.dtoToEntity(insert, Publisher.class);
        validateEntity(publisher);
        checkIfExists(publisher);
        return pubRepo.insert(publisher);
    }

    @Override
    public List<Publisher> insertAll(List<PublisherInsertDTO> insertList) {
        List<Publisher> pubList = insertList.stream().map(insert -> EntityUtils.dtoToEntity(insert, Publisher.class)).toList();
        validateEntities(pubList);
        checkIfPublishersExists(pubList);
        return pubRepo.insert(pubList);
    }

    @Override
    public Publisher update(String id, PublisherUpdateDTO update) {
        Publisher publisher = getById(id);

        if(update.getName() != null)
            publisher.setName(update.getName());

        if(update.getAcronym() != null)
            publisher.setAcronym(update.getAcronym());

        if(update.getFoundationYear() != null)
            publisher.setFoundationYear(update.getFoundationYear());

        validateEntity(publisher);
        return pubRepo.save(publisher);
    }

    @Override
    public void deleteById(String id) {
        pubRepo.deleteById(id);
    }

    public List<Book> getBookListByPublisherId(String id) {
        return getById(id).getBookList();
    }

    public Publisher getPublisherByName(String name) {
        return pubRepo.findByName(name).orElseThrow(() -> new ResourceNotFoundException("publisher", "name", name));
    }
}
