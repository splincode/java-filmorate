package ru.yandex.practicum.filmorate.stores;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.models.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class FilmsStore {
    static long autoIncrementId;

    private final Map<Long, Film> filmsByIds = new HashMap();
    private final Map<String, Film> filmsByNames = new HashMap();

    public long incrementAndGetUniqueId() {
        autoIncrementId = autoIncrementId + 1;
        return autoIncrementId;
    }

    public boolean has(Film film) {
        return filmsByIds.containsKey(film.getId());
    }

    public void set(Film film) {
        filmsByIds.put(film.getId(), film);
        filmsByNames.put(film.getName(), film);
    }

    public void delete(Film film) {
        filmsByIds.remove(film.getId(), film);
        filmsByNames.remove(film.getName(), film);
    }

    public Film find(Film film) {
        return filmsByNames.get(film.getName());
    }

    public List<Film> values() {
        return new ArrayList<>(filmsByIds.values());
    }
}
