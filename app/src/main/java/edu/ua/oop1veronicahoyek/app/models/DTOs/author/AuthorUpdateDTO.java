package edu.ua.oop1veronicahoyek.app.models.DTOs.author;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthorUpdateDTO {
    private String name;
    private Integer yearOfBirth;
}
