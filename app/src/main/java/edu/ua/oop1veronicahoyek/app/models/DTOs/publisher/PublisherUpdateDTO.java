package edu.ua.oop1veronicahoyek.app.models.DTOs.publisher;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PublisherUpdateDTO {
    private String name;
    private String acronym;
    private Integer foundationYear;
}
