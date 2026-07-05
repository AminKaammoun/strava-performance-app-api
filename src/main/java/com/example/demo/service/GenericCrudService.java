package com.example.demo.service;

import java.util.List;

public interface GenericCrudService<T, ID> {
    T save(T entity);
    T update(ID id, T entity);
    List<T> findAll();
    T findById(ID id);
    void delete(ID id);
}
