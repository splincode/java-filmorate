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
import ru.yandex.practicum.filmorate.models.Film;
import ru.yandex.practicum.filmorate.services.FilmsService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmsController {
    @Autowired
    private FilmsService filmsService;

    @GetMapping
    public List<Film> getUsers() {
        return filmsService.getAll();
    }

    @PostMapping
    public Film createUser(@Valid @RequestBody Film film) {
        return filmsService.create(film);
    }

    @PutMapping
    public Film updateUser(@Valid @RequestBody Film film) {
        return filmsService.update(film);
    }

    @DeleteMapping
    public Film deleteUser(@RequestBody Film film) {
        return filmsService.delete(film);
    }
}
