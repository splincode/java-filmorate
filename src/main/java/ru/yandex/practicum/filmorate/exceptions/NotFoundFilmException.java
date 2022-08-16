package ru.yandex.practicum.filmorate.exceptions;

public class NotFoundFilmException extends RuntimeException {
    public NotFoundFilmException() {
        super("Не удалось обновить. Убедитесь, что такой фильм с таким ID существует");
    }
}
