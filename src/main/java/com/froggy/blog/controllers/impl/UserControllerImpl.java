package com.froggy.blog.controllers.impl;

import com.froggy.blog.controllers.UserController;
import com.froggy.blog.dto.CreateUserDTO;
import com.froggy.blog.models.User;
import com.froggy.blog.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jws.soap.SOAPBinding;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/users/")
public class UserControllerImpl implements UserController {

    @Autowired
    UserRepo userRepo;

    @Override
    @RequestMapping(value = "", method = POST)
    public HttpStatus addNewUser(@RequestBody CreateUserDTO user) {
        try {
            userRepo.save(new User(user.getUsername(), user.getPassword(), user.getEmail()));
            return HttpStatus.CREATED;
        } catch (Exception e) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    @Override
    @RequestMapping(value = "{username}", method = GET)
    public String findUserByName(@PathVariable("username") String username) {
        return userRepo.findByUsername(username).orElse(User.getDefaultUser()).toString();
    }


}
