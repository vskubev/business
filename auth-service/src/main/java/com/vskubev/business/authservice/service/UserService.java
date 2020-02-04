package com.vskubev.business.authservice.service;

import com.vskubev.business.authservice.map.UserDTO;

import java.util.List;

/**
 * @author skubev
 */
public interface UserService {

    UserDTO create(UserDTO entity);

    void deleteById(long id);

    UserDTO getById(long id);

    UserDTO update(long id, UserDTO entity);

    List<UserDTO> getUsers();

    UserDTO getCurrentUser();
}
