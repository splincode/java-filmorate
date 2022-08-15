package ru.yandex.practicum.filmorate.stores;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UsersStore {
    static long autoIncrementId;

    private final Map<Long, User> mapByIds = new HashMap();
    private final Map<String, User> mapByLogins = new HashMap();

    public long incrementAndGetUniqueId() {
        autoIncrementId = autoIncrementId + 1;
        return autoIncrementId;
    }

    public boolean has(User user) {
        return mapByIds.containsKey(user.getId());
    }

    public void set(User user) {
        mapByIds.put(user.getId(), user);
        mapByLogins.put(user.getLogin(), user);
    }

    public void delete(User user) {
        mapByIds.remove(user.getId(), user);
        mapByLogins.remove(user.getLogin(), user);
    }

    public User find(User user) {
        return mapByLogins.get(user.getLogin());
    }

    public List<User> values() {
        return new ArrayList<>(mapByIds.values());
    }
}
