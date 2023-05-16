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

@Document("publishers")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Publisher {
    @Id private String id;

    @Size(min = 1, message = "Name too short")
    @Size(max = 100, message = "Name too long")
    private String name;

    @Size(min = 1, message = "Acronym too short")
    @Size(max = 10, message = "Acronym too long")
    private String acronym;

    @Min(value = 1700, message = "Accepted publishers are founded after the year 1700 AC")
    private Integer foundationYear;

    @JsonManagedReference
    @ReadOnlyProperty
    @DocumentReference(lookup = "{'publisher':?#{#self._id}}")
    List<Book> bookList;
}
