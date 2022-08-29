package ru.yandex.practicum.filmorate.storages;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.yandex.practicum.filmorate.builders.AutoIncrementBuilder.autoincrement;

@Component
public class UsersStorage {
    private final Map<Long, User> table = new HashMap();

    public User get(Long id) {
        return this.table.get(id);
    }

    public boolean hasKey(Long key) {
        return table.containsKey(key);
    }

    public User update(User user) {
        User result = user;

        if (user.getId() == 0) {
            long id = autoincrement(User.class);
            result = User
                    .builder()
                    .id(id)
                    .login(user.getLogin())
                    .name(user.getName())
                    .email(user.getEmail())
                    .birthday(user.getBirthday())
                    .build();

            table.put(id, result);
        } else {
            table.put(user.getId(), user);
        }

        return result;
    }

    public void delete(Long key) {
        table.remove(key);
    }

    public List<User> getAll() {
        return new ArrayList<>(table.values());
    }

}
