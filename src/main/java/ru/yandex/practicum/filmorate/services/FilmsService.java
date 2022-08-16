package ru.yandex.practicum.filmorate.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.FilmExistException;
import ru.yandex.practicum.filmorate.exceptions.NotFoundFilmException;
import ru.yandex.practicum.filmorate.models.Film;
import ru.yandex.practicum.filmorate.stores.FilmsStore;

import java.util.List;

@Component
public class FilmsService {
    @Autowired
    private FilmsStore store;

    public Film create(Film film) {
        if (store.hasFilm(film)) {
            throw new FilmExistException();
        }

        return store.set(Film.builder()
                .name(film.getName())
                .description(film.getDescription())
                .releaseDate(film.getReleaseDate())
                .duration(film.getDuration())
                .build());
    }

    public Film update(Film film) {
        if (!store.has(film)) {
            throw new NotFoundFilmException();
        }

        return store.set(Film.builder()
                .id(film.getId())
                .name(film.getName())
                .description(film.getDescription())
                .releaseDate(film.getReleaseDate())
                .duration(film.getDuration())
                .build());
    }

    public Film delete(Film film) {
        store.delete(film);

        return film;
    }

    public List<Film> getAll() {
        return store.values();
    }
}
