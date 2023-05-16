package edu.ua.oop1veronicahoyek.app.models.DTOs.publisher;

import lombok.Data;
import lombok.NonNull;

@Data
public class PublisherInsertDTO {
    @NonNull private String name;
    @NonNull private String acronym;
    @NonNull private Integer foundationYear;
}
