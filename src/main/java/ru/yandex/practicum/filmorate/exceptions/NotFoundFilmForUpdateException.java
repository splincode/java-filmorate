package ru.yandex.practicum.filmorate.exceptions;

public class NotFoundFilmForUpdateException extends RuntimeException {
    public NotFoundFilmForUpdateException() {
        super("Не удалось обновить. Убедитесь, что такой фильм с таким ID существует");
    }
}
