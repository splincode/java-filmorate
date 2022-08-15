package ru.yandex.practicum.filmorate.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.core.http.controllers.AbstractController;
import ru.yandex.practicum.filmorate.models.User;
import ru.yandex.practicum.filmorate.core.http.models.ResponseDataWrapper;
import ru.yandex.practicum.filmorate.services.UsersService;

import java.util.List;

@RestController
public class UserController extends AbstractController {
    @Autowired
    private UsersService usersService;

    @GetMapping("/users")
    public ResponseEntity<ResponseDataWrapper<List<User>>> getUsers() {
        return interception(() -> usersService.getAll());
    }

    @PostMapping("/users")
    public ResponseEntity<ResponseDataWrapper<User>> createUser(@RequestBody User user) {
        return interception(() -> usersService.create(user));
    }

    @PutMapping("/users")
    public ResponseEntity<ResponseDataWrapper<User>> updateUser(@RequestBody User user) {
        return interception(() -> usersService.update(user), HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/users")
    public ResponseEntity<ResponseDataWrapper<User>> deleteUser(@RequestBody User user) {
        return interception(() -> usersService.delete(user));
    }
}
