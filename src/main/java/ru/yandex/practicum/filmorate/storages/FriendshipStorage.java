package ru.yandex.practicum.filmorate.storages;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Component
public class FriendshipStorage {
    private final Map<Long, Set<Long>> table = new HashMap();

    public void setFriendship(Long firstUserId, Long secondUserId) {
        Set<Long> user1 = getSetBy(firstUserId);
        user1.add(secondUserId);

        Set<Long> user2 = getSetBy(secondUserId);
        user2.add(firstUserId);

        table.put(firstUserId, user1);
        table.put(secondUserId, user2);
    }


    public void deleteFriendship(Long firstUserId, Long secondUserId) {
        Set<Long> user1 = getSetBy(firstUserId);
        user1.remove(secondUserId);

        Set<Long> user2 = getSetBy(secondUserId);
        user2.remove(firstUserId);

        table.put(firstUserId, user1);
        table.put(secondUserId, user2);
    }

    public Set<Long> getSetBy(Long userId) {
        return Optional.ofNullable(table.get(userId)).orElse(new HashSet<>());
    }

    public Set<Long> getAll(Long userId) {
        Set<Long> ids = table.get(userId);

        if (ids != null) {
            return ids;
        }

        return new HashSet<>();
    }
}
