package ru.yandex.practicum.filmorate.exceptions;

public class UserExistException extends RuntimeException {
    public UserExistException() {
        super("Такой пользователь уже существует");
    }
}
