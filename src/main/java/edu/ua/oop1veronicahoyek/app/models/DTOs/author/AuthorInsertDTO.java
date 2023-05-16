package edu.ua.oop1veronicahoyek.app.models.DTOs.author;

import lombok.Data;
import lombok.NonNull;

@Data
public class AuthorInsertDTO {
    @NonNull private String name;
    @NonNull private Integer yearOfBirth;
}
