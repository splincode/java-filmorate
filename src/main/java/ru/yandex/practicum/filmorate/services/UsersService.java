package ru.yandex.practicum.filmorate.services;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotFoundUserException;
import ru.yandex.practicum.filmorate.exceptions.UserExistException;
import ru.yandex.practicum.filmorate.models.User;
import ru.yandex.practicum.filmorate.stores.UsersStore;

import java.util.List;

@Component
public class UsersService {
    @Autowired
    private UsersStore store;

    public User create(User user) {
        if (store.hasUsername(user)) {
            throw new UserExistException();
        }

        return store.set(User.builder().birthday(user.getBirthday()).name(StringUtils.isBlank(user.getName()) ? user.getLogin() : user.getName()).email(user.getEmail()).login(user.getLogin()).build());
    }

    public User update(User user) {
        if (!store.has(user)) {
            throw new NotFoundUserException();
        }

        return store.set(User.builder().id(user.getId()).birthday(user.getBirthday()).name(StringUtils.isBlank(user.getName()) ? user.getLogin() : user.getName()).email(user.getEmail()).login(user.getLogin()).build());
    }

    public User delete(User user) {
        store.delete(user);

        return user;
    }

    public List<User> getAll() {
        return store.values();
    }
}
