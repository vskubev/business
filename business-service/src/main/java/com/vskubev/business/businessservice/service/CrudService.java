package com.vskubev.business.businessservice.service;

/**
 * @author skubev
 */
public interface CrudService<T> {

    T create(T entity);

    void deleteById(long id);

    T getById(long id);
}
