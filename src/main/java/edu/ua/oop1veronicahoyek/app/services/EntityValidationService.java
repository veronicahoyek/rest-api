package edu.ua.oop1veronicahoyek.app.services;

import edu.ua.oop1veronicahoyek.app.exceptions.EntityValidationException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
public abstract class EntityValidationService<T> {
    private final Validator validator;

    void validateEntity(T entity) {
        Set<ConstraintViolation<T>> violations = validator.validate(entity);

        if (!violations.isEmpty()) {
            StringBuilder sBuilder = new StringBuilder();
            for (ConstraintViolation<T> bookConstraintViolation: violations) {
                sBuilder.append(bookConstraintViolation.getMessage()).append("\n");
            }
            throw new EntityValidationException(entity.getClass().getSimpleName() + " information error occurred:\n" + sBuilder);
        }
    }

    void validateEntities(List<T> entityList) {
        StringBuilder sBuilder = new StringBuilder();

        for (int i=0; i<entityList.size(); i++) {
            T entity = entityList.get(i);
            Set<ConstraintViolation<T>> violations = validator.validate(entity);

            if (!violations.isEmpty()) {
                sBuilder.append(entity.getClass().getSimpleName()).append(" information error occurred for on index (").append(i).append("):\n");
                for (ConstraintViolation<T> bookConstraintViolation: violations) {
                    sBuilder.append(bookConstraintViolation.getMessage()).append("\n");
                }
                sBuilder.append("\n");
            }
        }

        if (!sBuilder.isEmpty()) {
            throw new EntityValidationException(sBuilder.toString());
        }
    }
}
