package ru.yandex.practicum.filmorate.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.FilmExistException;
import ru.yandex.practicum.filmorate.exceptions.NotFoundFilmForUpdateException;
import ru.yandex.practicum.filmorate.exceptions.NotFoundUserException;
import ru.yandex.practicum.filmorate.models.Film;
import ru.yandex.practicum.filmorate.storages.FilmsLikesStorage;
import ru.yandex.practicum.filmorate.storages.FilmsStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FilmsService {
    private final UsersService usersService;
    private final FilmsStorage filmsStorage;
    private final FilmsLikesStorage likesStorage;

    public Film getAndValidity(Long filmId) {
        Film film = filmsStorage.get(filmId);

        if (film == null) {
            throw new NotFoundUserException(filmId);
        }

        return film;
    }

    public Film create(Film film) {
        if (filmsStorage.hasKey(film.getId())) {
            throw new FilmExistException();
        }

        Film created = filmsStorage.update(Film.builder()
                .name(film.getName())
                .description(film.getDescription())
                .releaseDate(film.getReleaseDate())
                .duration(film.getDuration())
                .build());

        likesStorage.createEmptyLikes(created.getId());

        return created;
    }

    public Film update(Film film) {
        if (!filmsStorage.hasKey(film.getId())) {
            throw new NotFoundFilmForUpdateException();
        }

        return filmsStorage.update(Film.builder()
                .id(film.getId())
                .name(film.getName())
                .description(film.getDescription())
                .releaseDate(film.getReleaseDate())
                .duration(film.getDuration())
                .build());
    }

    public Film delete(Film film) {
        filmsStorage.delete(film.getId());

        return film;
    }

    public List<Film> getAll() {
        return filmsStorage.getAll();
    }

    public void setLike(Long filmId, Long userId) {
        this.likesStorage.setLike(filmId, usersService.getAndValidity(userId).getId());
    }

    public void deleteLike(Long filmId, Long userId) {
        this.likesStorage.deleteLike(filmId, usersService.getAndValidity(userId).getId());
    }

    public int getLikes(Long filmId) {
        return likesStorage.getLikes(filmId).size();
    }

    public List<Film> getPopularBy(int count) {
        List<Film> films = new ArrayList<>(filmsStorage.getAll());

        films.sort((o1, o2) -> {
            int likes1 = likesStorage.getLikes(o1.getId()).size();
            int likes2 = likesStorage.getLikes(o2.getId()).size();
            return Integer.compare(likes2, likes1);
        });

        return films.stream()
                .limit(count)
                .collect(Collectors.toList());
    }

}
