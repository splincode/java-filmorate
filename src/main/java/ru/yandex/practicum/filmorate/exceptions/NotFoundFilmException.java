package ru.yandex.practicum.filmorate.exceptions;

public class NotFoundFilmException extends RuntimeException {
    public NotFoundFilmException(Long id) {
        super("Такой фильм c ID(" + id + ") не найден");
    }
}
