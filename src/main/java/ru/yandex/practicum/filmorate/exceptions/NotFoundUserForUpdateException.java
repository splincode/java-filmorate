package ru.yandex.practicum.filmorate.exceptions;

public class NotFoundUserForUpdateException extends RuntimeException {
    public NotFoundUserForUpdateException() {
        super("Не удалось обновить. Убедитесь, что такой пользователь с таким ID существует");
    }
}
