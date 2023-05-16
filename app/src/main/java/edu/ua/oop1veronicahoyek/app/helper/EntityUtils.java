package edu.ua.oop1veronicahoyek.app.helper;

import org.springframework.beans.BeanUtils;

import java.lang.reflect.InvocationTargetException;

public class EntityUtils {
    // This function uses the BeanUtils to copy similarly named field from one instance of a Class to another
    static public <T, U> T dtoToEntity(U dto, Class<T> entityClass) {
        try {
            T obj = entityClass.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(dto, obj);
            return obj;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}