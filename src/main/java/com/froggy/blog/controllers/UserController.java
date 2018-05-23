package com.froggy.blog.controllers;

import com.froggy.blog.dto.CreateUserDTO;
import org.springframework.http.HttpStatus;

public interface UserController {

    HttpStatus addNewUser(CreateUserDTO user);

    String findUserByName(String username);
}
