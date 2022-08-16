package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.models.User;
import ru.yandex.practicum.filmorate.services.UsersService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UsersService usersService;

    @GetMapping
    public List<User> getUsers() {
        return usersService.getAll();
    }

    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        return usersService.create(user);
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        return usersService.update(user);
    }

    @DeleteMapping
    public User deleteUser(@RequestBody User user) {
        return usersService.delete(user);
    }
}
