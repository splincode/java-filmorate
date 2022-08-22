package ru.yandex.practicum.filmorate.exceptions;

public class NotFoundUserException extends RuntimeException {
    public NotFoundUserException(Long id) {
        super("Такой пользователь c ID(" + id + ") не найден");
    }
}
