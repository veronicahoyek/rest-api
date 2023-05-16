package edu.ua.oop1veronicahoyek.app.services;

import edu.ua.oop1veronicahoyek.app.exceptions.AlreadyExistsException;
import edu.ua.oop1veronicahoyek.app.exceptions.ResourceNotFoundException;
import edu.ua.oop1veronicahoyek.app.helper.EntityUtils;
import edu.ua.oop1veronicahoyek.app.models.DTOs.author.AuthorInsertDTO;
import edu.ua.oop1veronicahoyek.app.models.DTOs.author.AuthorUpdateDTO;
import edu.ua.oop1veronicahoyek.app.models.entities.Author;
import edu.ua.oop1veronicahoyek.app.models.entities.Book;
import edu.ua.oop1veronicahoyek.app.repositories.AuthorRepository;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService extends EntityValidationService<Author> implements CrudService <Author, AuthorInsertDTO, AuthorUpdateDTO> {
    private final AuthorRepository authorRepo;

    public AuthorService(Validator validator, AuthorRepository authorRepo) {
        super(validator);
        this.authorRepo = authorRepo;
    }

    public void checkIfExists(Author author) {
        if (authorRepo.findByNameAndYearOfBirth(author.getName(), author.getYearOfBirth()) != null)
            throw new AlreadyExistsException("This author already exists.");
    }

    public void checkIfAuthorsExists(List<Author> authorList) {
        StringBuilder sBuilder = new StringBuilder();

        for (int i = 0; i < authorList.size(); i++) {
            Author author = authorList.get(i);

            try {
                checkIfExists(author);
            } catch (AlreadyExistsException e) {
                sBuilder.append("The author inserted at index ").append(i).append(" already exists.\n");
            }

        }

        if (!sBuilder.isEmpty()) {
            throw new AlreadyExistsException(sBuilder.toString());
        }
    }

    @Override
    public List<Author> getAll() {
        return authorRepo.findAll();
    }

    @Override
    public Author getById(String id) {
        return authorRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("author", "id", id));
    }

    @Override
    public Author insert(AuthorInsertDTO insert) {
        Author author = EntityUtils.dtoToEntity(insert, Author.class);
        validateEntity(author);
        checkIfExists(author);
        return authorRepo.insert(author);
    }

    @Override
    public List<Author> insertAll(List<AuthorInsertDTO> insertList) {
        List<Author> authorList = insertList.stream().map(insert -> EntityUtils.dtoToEntity(insert, Author.class)).toList();
        validateEntities(authorList);
        checkIfAuthorsExists(authorList);
        return authorRepo.insert(authorList);
    }

    @Override
    public Author update(String id, AuthorUpdateDTO update) {
        Author author = getById(id);

        if(update.getName() != null)
            author.setName(update.getName());

        if(update.getYearOfBirth() != null)
            author.setYearOfBirth(update.getYearOfBirth());

        validateEntity(author);
        return authorRepo.save(author);
    }

    @Override
    public void deleteById(String id) {

        authorRepo.deleteById(id);

    }

    public List<Book> getBookListByAuthorId(String id) {
        return getById(id).getBooklist();
    }

    public Author getAuthorByName(String name) {
        return authorRepo.findByName(name).orElseThrow(() -> new ResourceNotFoundException("author", "name", name));
    }
}
