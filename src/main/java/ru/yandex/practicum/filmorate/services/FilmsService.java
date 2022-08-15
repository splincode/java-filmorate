package ru.yandex.practicum.filmorate.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.models.Film;
import ru.yandex.practicum.filmorate.stores.FilmsStore;

import java.util.List;

@Component
public class FilmsService {
    @Autowired
    private FilmsStore store;

    @Autowired
    private FilmsValidatorService validator;

    public Film create(Film film) {
        validator.validate(film);

        if (store.find(film) != null) {
            throw new RuntimeException("Такой фильм уже существует");
        }

        Film newFilm = Film.builder()
                .id(store.incrementAndGetUniqueId())
                .name(film.getName())
                .description(film.getDescription())
                .releaseDate(film.getReleaseDate())
                .duration(film.getDuration())
                .build();

        store.set(newFilm);

        return newFilm;
    }

    public Film update(Film film) {
        if (!store.has(film)) {
            throw new RuntimeException("Не удалось обновить. Убедитесь, что такой фильм с таким ID существует");
        }

        validator.validate(film);

        Film updatedFilm = Film.builder()
                .id(film.getId())
                .name(film.getName())
                .description(film.getDescription())
                .releaseDate(film.getReleaseDate())
                .duration(film.getDuration())
                .build();

        store.set(updatedFilm);

        return updatedFilm;
    }

    public Film delete(Film film) {
        store.delete(film);

        return film;
    }

    public List<Film> getAll() {
        return store.values();
    }
}
