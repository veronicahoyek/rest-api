package edu.ua.oop1veronicahoyek.app.models.entities;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

@Document("authors")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Author {
    @Id private String id;

    @Size(min = 1, message = "Name too short")
    @Size(max = 100, message = "Name too long")
    private String name;

    @Min(1700)
    private Integer yearOfBirth;

    @JsonManagedReference
    @ReadOnlyProperty
    @DocumentReference(lookup = "{'author':?#{#self._id}}")
    List<Book> booklist;
}
