package ru.yandex.practicum.filmorate.exceptions;

public class NotFoundUserException extends RuntimeException {
    public NotFoundUserException() {
        super("Не удалось обновить. Убедитесь, что такой пользователь с таким ID существует");
    }
}
