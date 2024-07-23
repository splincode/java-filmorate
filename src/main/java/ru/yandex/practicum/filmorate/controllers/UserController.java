package ru.yandex.practicum.filmorate.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UsersService usersService;

    @GetMapping
    public List<User> getUsers() {
        return usersService.getAll();
    }

    @GetMapping("{id}")
    public User getUser(@PathVariable("id") Long userId) {
        return usersService.getAndValidity(userId);
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

    @PutMapping("{id}/friends/{friendId}")
    public void setFriends(@PathVariable("id") Long userId, @PathVariable("friendId") Long friendId) {
        this.usersService.setFriends(userId, friendId);
    }

    @DeleteMapping("{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable("id") Long userId, @PathVariable("friendId") Long friendId) {
        this.usersService.deleteFriend(userId, friendId);
    }

    @GetMapping("{id}/friends")
    public List<User> getFriends(@PathVariable("id") Long userId) {
        return this.usersService.getFriends(userId);
    }

    @GetMapping("{id}/friends/common/{otherId}")
    public List<User> getCommonFriends(@PathVariable("id") Long userId, @PathVariable("otherId") Long friendId) {
        return this.usersService.getFriendsIntersection(userId, friendId);
    }

}
