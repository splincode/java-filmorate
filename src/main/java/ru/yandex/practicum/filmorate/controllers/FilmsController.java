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
import ru.yandex.practicum.filmorate.core.http.models.ResponseDataWrapper;
import ru.yandex.practicum.filmorate.models.Film;
import ru.yandex.practicum.filmorate.services.FilmsService;

import java.util.List;

@RestController
public class FilmsController extends AbstractController {
    @Autowired
    private FilmsService filmsService;

    @GetMapping("/films")
    public ResponseEntity<ResponseDataWrapper<List<Film>>> getUsers() {
        return interception(() -> filmsService.getAll());
    }

    @PostMapping("/films")
    public ResponseEntity<ResponseDataWrapper<Film>> createUser(@RequestBody Film film) {
        return interception(() -> filmsService.create(film));
    }

    @PutMapping("/films")
    public ResponseEntity<ResponseDataWrapper<Film>> updateUser(@RequestBody Film film) {
        return interception(() -> filmsService.update(film), HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/films")
    public ResponseEntity<ResponseDataWrapper<Film>> deleteUser(@RequestBody Film film) {
        return interception(() -> filmsService.delete(film));
    }
}
