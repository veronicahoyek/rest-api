package edu.ua.oop1veronicahoyek.app.services;


import java.util.List;

public interface CrudService <T, insertT, updateT> {
    List<T> getAll();
    T getById(String id);
    T insert(insertT insert);
    List<T> insertAll(List<insertT> insertList);
    T update(String id, updateT update);
    void deleteById(String id);
}
