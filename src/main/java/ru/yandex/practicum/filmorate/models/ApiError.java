package ru.yandex.practicum.filmorate.models;

import lombok.Value;

@Value
public class ApiError<T> {
    int statusCode;
    T messages;
}
