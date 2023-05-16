package edu.ua.oop1veronicahoyek.app.services;

import edu.ua.oop1veronicahoyek.app.exceptions.AlreadyExistsException;
import edu.ua.oop1veronicahoyek.app.exceptions.EntityValidationException;
import edu.ua.oop1veronicahoyek.app.exceptions.ResourceNotFoundException;
import edu.ua.oop1veronicahoyek.app.helper.EntityUtils;
import edu.ua.oop1veronicahoyek.app.models.DTOs.author.AuthorInsertDTO;
import edu.ua.oop1veronicahoyek.app.models.entities.Author;
import edu.ua.oop1veronicahoyek.app.repositories.AuthorRepository;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepo;

    @InjectMocks
    private AuthorService authorService;


    BookService bookService;

    private Validator validator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        authorService = new AuthorService(validator, authorRepo);
    }

    @Test
    public void testGetAll() {
        when(authorRepo.findAll()).thenReturn(List.of(new Author("1", "Author 1", 2000, null)));
        List<Author> authors = authorService.getAll();
        assertNotNull(authors);
        assertEquals(1, authors.size());
        assertEquals("Author 1", authors.get(0).getName());
        assertEquals(2000, authors.get(0).getYearOfBirth());
    }

    @Test
    public void testGetById() {
        String authorId = "1";
        when(authorRepo.findById(authorId)).thenReturn(java.util.Optional.of(new Author(authorId, "Author 1", 2000, null)));
        Author author = authorService.getById(authorId);
        assertNotNull(author);
        assertEquals(authorId, author.getId());
        assertEquals("Author 1", author.getName());
        assertEquals(2000, author.getYearOfBirth());
    }

    @Test
    public void testGetByIdThrowsResourceNotFoundException() {
        String authorId = "1";
        when(authorRepo.findById(authorId)).thenReturn(java.util.Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> authorService.getById(authorId));
    }

    @Test
    public void testInsert() {
        AuthorInsertDTO authorInsertDTO = new AuthorInsertDTO("Author1", 2000);
        Author expected = EntityUtils.dtoToEntity(authorInsertDTO, Author.class);

        when(authorRepo.insert(any(Author.class))).thenReturn(expected);

        Author insertedAuthor = authorService.insert(authorInsertDTO);

        assertNotNull(insertedAuthor);
        assertEquals(expected.getName(), insertedAuthor.getName());
        assertEquals(expected.getYearOfBirth(), insertedAuthor.getYearOfBirth());
    }


    @Test
    public void testInsertThrowsEntityValidationException() {
        AuthorInsertDTO authorInsertDTO = new AuthorInsertDTO("", 0);
        assertThrows(EntityValidationException.class, () -> authorService.insert(authorInsertDTO));

    }

    @Test
    public void testInsertThrowsAlreadyExistsException() {
        AuthorInsertDTO authorInsertDTO = new AuthorInsertDTO("Author 1", 2000);
        Author author = new Author("1", "Author 1", 2000,null);
        when(authorRepo.findByNameAndYearOfBirth(author.getName(), author.getYearOfBirth())).thenReturn(author);

        AlreadyExistsException exception = assertThrows(AlreadyExistsException.class, () -> authorService.insert(authorInsertDTO));
        assertEquals("This author already exists.", exception.getMessage());
    }

    @Test
    public void testInsertAll() {
        List<AuthorInsertDTO> authorInsertDTOS = new ArrayList<>();
        AuthorInsertDTO a1 = new AuthorInsertDTO("Author 1", 2000);
        AuthorInsertDTO a2 = new AuthorInsertDTO("Author 2", 1990);
        AuthorInsertDTO a3 = new AuthorInsertDTO("Author 3", 1980);
        authorInsertDTOS.add(a1);
        authorInsertDTOS.add(a2);
        authorInsertDTOS.add(a3);

        List<Author> expected = new ArrayList<>();
        expected.add(EntityUtils.dtoToEntity(a1,Author.class));
        expected.add(EntityUtils.dtoToEntity(a2,Author.class));
        expected.add(EntityUtils.dtoToEntity(a3,Author.class));

        when(authorRepo.insert(anyList())).thenReturn(expected);

        List<Author> actualAuthors = authorService.insertAll(authorInsertDTOS);

        assertEquals(expected.size(), actualAuthors.size());

        for (int i = 0; i < expected.size(); i++) {
            Author author = expected.get(i);
            Author actual = actualAuthors.get(i);

            assertEquals(author.getName(), actual.getName());
            assertEquals(author.getYearOfBirth(), actual.getYearOfBirth());
        }

        verify(authorRepo, times(1)).insert(anyList());
    }

}

