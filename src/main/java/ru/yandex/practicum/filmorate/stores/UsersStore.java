package ru.yandex.practicum.filmorate.stores;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.models.User;

import java.util.Map;

@Component
public class UsersStore extends Store<User> {
    public boolean has(User user) {
        return data.containsKey(user.getId());
    }

    public User set(User user) {
        User result = user;

        if (user.getId() == 0) {
            long id = incrementAndGetUniqueId();
            result = User.builder().id(id).login(user.getLogin()).name(user.getName()).email(user.getEmail()).birthday(user.getBirthday()).build();
            data.put(id, result);
        } else {
            data.put(user.getId(), user);
        }

        return result;
    }

    public void delete(User user) {
        data.remove(user.getId(), user);
    }

    public boolean hasUsername(User user) {
        boolean found = false;

        for (Map.Entry<Long, User> entry : data.entrySet()) {
            if (entry.getValue().getLogin().equals(user.getLogin())) {
                found = true;
                break;
            }
        }

        return found;
    }
}
