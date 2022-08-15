package ru.yandex.practicum.filmorate.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.models.User;
import ru.yandex.practicum.filmorate.stores.UsersStore;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@Component
public class UsersService {
    @Autowired
    private UsersStore store;

    @Autowired
    private UsersValidatorService validator;

    public User create(User user) {
        validator.validate(user);

        if (store.find(user) != null) {
            throw new RuntimeException("Такой пользователь уже существует");
        }

        User newUser = User.builder()
                .id(store.incrementAndGetUniqueId())
                .birthday(user.getBirthday())
                .name(StringUtils.isBlank(user.getName()) ? user.getLogin() : user.getName())
                .email(user.getEmail())
                .login(user.getLogin())
                .build();

        store.set(newUser);

        return newUser;
    }

    public User update(User user) {
        if (!store.has(user)) {
            throw new RuntimeException("Не удалось обновить. Убедитесь, что такой пользователь с таким ID существует");
        }

        validator.validate(user);

        User updatedUser = User.builder()
                .id(user.getId())
                .birthday(user.getBirthday())
                .name(StringUtils.isBlank(user.getName()) ? user.getLogin() : user.getName())
                .email(user.getEmail())
                .login(user.getLogin())
                .build();

        store.set(updatedUser);

        return updatedUser;
    }

    public User delete(User user) {
        store.delete(user);

        return user;
    }

    public List<User> getAll() {
        return store.values();
    }
}
