package ru.yandex.practicum.filmorate.services;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.NotFoundUserException;
import ru.yandex.practicum.filmorate.exceptions.NotFoundUserForUpdateException;
import ru.yandex.practicum.filmorate.exceptions.UserExistException;
import ru.yandex.practicum.filmorate.models.User;
import ru.yandex.practicum.filmorate.storages.FriendshipStorage;
import ru.yandex.practicum.filmorate.storages.UsersStorage;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsersService {
    private final UsersStorage usersStorage;
    private final FriendshipStorage friendshipStorage;

    public User getAndValidity(Long id) {
        User user = usersStorage.get(id);

        if (user == null) {
            throw new NotFoundUserException(id);
        }

        return user;
    }

    public User create(User user) {
        if (hasUsername(user)) {
            throw new UserExistException();
        }

        return usersStorage.update(
                User
                        .builder()
                        .birthday(user.getBirthday())
                        .name(StringUtils.isBlank(user.getName()) ? user.getLogin() : user.getName())
                        .email(user.getEmail())
                        .login(user.getLogin())
                        .build()
        );
    }

    public void setFriends(Long userId, Long friendId) {
        friendshipStorage.setFriendship(
                getAndValidity(userId).getId(),
                getAndValidity(friendId).getId()
        );
    }

    public void deleteFriend(Long userId, Long friendId) {
        friendshipStorage.deleteFriendship(
                getAndValidity(userId).getId(),
                getAndValidity(friendId).getId()
        );
    }

    public List<User> getFriends(Long userId) {
        Set<Long> friendsIds = friendshipStorage.getAll(
                getAndValidity(userId).getId()
        );

        return usersStorage
                .getAll()
                .stream()
                .filter((user) -> friendsIds.contains(user.getId()))
                .collect(Collectors.toList());
    }

    public List<User> getFriendsIntersection(Long userId, Long otherUserId) {
        Set<Long> friendsIds = friendshipStorage.getAll(userId);
        Set<Long> otherFriendsIds = friendshipStorage.getAll(otherUserId);

        Set<Long> intersection = new HashSet<>(friendsIds);
        intersection.retainAll(otherFriendsIds);

        return intersection.stream().map(usersStorage::get).collect(Collectors.toList());
    }

    public User update(User user) {
        if (!usersStorage.hasKey(user.getId())) {
            throw new NotFoundUserForUpdateException();
        }

        return usersStorage.update(
                User
                        .builder()
                        .id(user.getId())
                        .birthday(user.getBirthday())
                        .name(StringUtils.isBlank(user.getName()) ? user.getLogin() : user.getName())
                        .email(user.getEmail())
                        .login(user.getLogin())
                        .build()
        );
    }

    public User delete(User user) {
        usersStorage.delete(user.getId());

        return user;
    }

    public List<User> getAll() {
        return usersStorage.getAll();
    }

    public boolean hasUsername(User user) {
        boolean found = false;

        for (User current : usersStorage.getAll()) {
            if (current.getLogin().equals(user.getLogin())) {
                found = true;
                break;
            }
        }

        return found;
    }

}
