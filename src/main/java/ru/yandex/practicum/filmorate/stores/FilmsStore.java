package ru.yandex.practicum.filmorate.stores;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.models.Film;

import java.util.Map;

@Component
public class FilmsStore extends Store<Film> {
    public boolean has(Film film) {
        return data.containsKey(film.getId());
    }

    public Film set(Film film) {
        Film result = film;

        if (film.getId() == 0) {
            long id = incrementAndGetUniqueId();
            result = Film.builder().id(id).description(film.getDescription()).duration(film.getDuration()).name(film.getName()).releaseDate(film.getReleaseDate()).build();
            data.put(id, result);
        } else {
            data.put(film.getId(), film);
        }

        return result;
    }

    public void delete(Film film) {
        data.remove(film.getId(), film);
    }

    public boolean hasFilm(Film film) {
        boolean found = false;

        for (Map.Entry<Long, Film> entry : data.entrySet()) {
            if (entry.getValue().getName().equals(film.getName())) {
                found = true;
                break;
            }
        }

        return found;
    }
}
