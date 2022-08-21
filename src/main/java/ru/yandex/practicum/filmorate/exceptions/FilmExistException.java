package ru.yandex.practicum.filmorate.exceptions;

public class FilmExistException extends RuntimeException {
    public FilmExistException() {
        super("Такой фильм уже существует");
    }
}
