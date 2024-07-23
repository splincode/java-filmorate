package ru.yandex.practicum.filmorate.storages;

import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class FilmsLikesStorage {
    private final Map<Long, Set<Long>> table = new HashMap();

    public void setLike(Long filmId, Long userId) {
        Set<Long> likes = getLikes(filmId);
        likes.add(userId);

        table.put(filmId, likes);
    }

    public void createEmptyLikes(Long filmId) {
        table.put(filmId, new HashSet<>());
    }

    public void deleteLike(Long filmId, Long userId) {
        Set<Long> likes = getLikes(filmId);
        likes.remove(userId);

        table.put(filmId, likes);
    }

    public Set<Long> getLikes(Long filmId) {
        return Optional.ofNullable(table.get(filmId)).orElse(new HashSet<>());
    }

    public List<Long> getSortedFilmsByPopular(long limit) {
        List<Map.Entry<Long, Set<Long>>> list = new LinkedList<>(table.entrySet());

        list.sort(Comparator.comparingInt(entry -> (entry.getValue().size())));

        return list.stream().map((Map.Entry::getKey)).limit(limit).collect(Collectors.toList());
    }
}
