package com.vskubev.business.authservice.service;

/**
 * @author skubev
 */
public interface CrudService<T> {

    T create(T entity);

    void deleteById(long id);

    T getById(long id);

    T update(long id, T entity);
}
