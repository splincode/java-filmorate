package ru.yandex.practicum.filmorate.mocks;

import lombok.Value;

@Value
public class FilmMock {
    long id = 0;
    String name;
    String description;
    String releaseDate;
    int duration;
}
