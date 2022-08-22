package ru.yandex.practicum.filmorate.mocks;

import lombok.Value;

@Value
public class UserMock {
    long id;
    String email;
    String login;
    String name;
    String birthday;
}
