package ru.yandex.practicum.filmorate.storages;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.models.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.yandex.practicum.filmorate.builders.AutoIncrementBuilder.autoincrement;

@Component
public class FilmsStorage {
    private final Map<Long, Film> table = new HashMap();

    public Film get(Long filmId) {
        return table.get(filmId);
    }

    public boolean hasKey(Long key) {
        return table.containsKey(key);
    }

    public Film update(Film value) {
        Film result = value;

        if (value.getId() == 0) {
            long id = autoincrement(Film.class);
            result = Film
                    .builder()
                    .id(id)
                    .description(value.getDescription())
                    .duration(value.getDuration())
                    .name(value.getName())
                    .releaseDate(value.getReleaseDate())
                    .build();

            table.put(id, result);
        } else {
            table.put(value.getId(), value);
        }

        return result;
    }

    public void delete(Long key) {
        table.remove(key);
    }

    public List<Film> getAll() {
        return new ArrayList<>(table.values());
    }

    public List<Film> getFilmsBy(List<Long> ids) {
        return ids.stream().map(table::get).collect(Collectors.toList());
    }
}
