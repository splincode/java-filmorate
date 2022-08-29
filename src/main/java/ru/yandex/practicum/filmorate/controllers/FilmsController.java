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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.models.Film;
import ru.yandex.practicum.filmorate.services.FilmsService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/films")
public class FilmsController {
    private final FilmsService filmsService;

    @GetMapping
    public List<Film> getAllFilms() {
        return filmsService.getAll();
    }

    @GetMapping("{id}")
    public Film getUser(@PathVariable("id") Long filmId) {
        return filmsService.getAndValidity(filmId);
    }

    @PostMapping
    public Film createFilm(@Valid @RequestBody Film film) {
        return filmsService.create(film);
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        return filmsService.update(film);
    }

    @DeleteMapping
    public Film deleteFilm(@RequestBody Film film) {
        return filmsService.delete(film);
    }

    @PutMapping("{id}/like/{userId}")
    public void setLike(@PathVariable("id") Long filmId, @PathVariable("userId") Long userId) {
        filmsService.setLike(filmId, userId);
    }

    @DeleteMapping("{id}/like/{userId}")
    public void deleteLike(@PathVariable("id") Long filmId, @PathVariable("userId") Long userId) {
        filmsService.deleteLike(filmId, userId);
    }

    @GetMapping("{id}/likes")
    public int getLikes(@PathVariable("id") Long filmId) {
        return filmsService.getLikes(filmId);
    }

    @GetMapping("popular")
    public List<Film> getPopularFilms(@RequestParam(value = "count", required = false) Optional<Integer> count) {
        return filmsService.getPopularBy(count.orElse(10));
    }
}
