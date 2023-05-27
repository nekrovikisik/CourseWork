package com.example.deliveryproject.service;

import com.example.deliveryproject.dto.PostingDto;
import com.example.deliveryproject.dto.UserDto;
import com.example.deliveryproject.entity.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);

    User findByEmail(String email);
    User findById(Long id);

    List<UserDto> findAllUsers();

    UserDto findDtoById(Long id);

    User getUserById(Long id);

    User updateUser(User user);

    boolean deleteUserById(Long id);

}
