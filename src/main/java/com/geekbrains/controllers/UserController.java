package com.geekbrains.controllers;

import com.geekbrains.controllers.dto.UserDto;
import com.geekbrains.controllers.dto.UserType;
import com.geekbrains.entities.User;
import com.geekbrains.services.UserService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "api/v1/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    public final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User createUser(@RequestBody @Valid UserDto userDto) {
        return userService.saveUser(userDto);
    }

    @GetMapping
    public List<User> getAllUsers(@RequestParam(value = "type", required = false) UserType type) {
        return userService.getAllUsersWithType(type);
    }

    @GetMapping(value = "/{id}")
    public Optional<User> getUser(@PathVariable(value = "id") Long id) {
        return userService.getUserById(id);
    }
}