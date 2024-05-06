package ru.practicum.shareit.user.dao;

import org.springframework.stereotype.*;
import ru.practicum.shareit.exception.*;
import ru.practicum.shareit.user.model.*;

import java.util.*;
import java.util.stream.*;

@Component
public class UserDaoImpl implements UserDao {
    private final Map<Long, User> userMap = new HashMap<>();
    private final Map<String, Long> userEmailMap = new HashMap<>();
    private long idCounter = 1;

    @Override
    public List<User> getAll() {
        return userMap.values().stream().collect(Collectors.toList());
    }

    @Override
    public Optional<User> get(long id) {
        return Optional.ofNullable(userMap.get(id));
    }

    @Override
    public User create(User user) {
        if (!isEmailExists(user)) {
            user.setId(idCounter++);
            userMap.put(user.getId(), user);
            userEmailMap.put(user.getEmail(), user.getId());
        } else {
            throw new AlreadyExistsException("Email=" + user.getEmail() + " уже используется");
        }
        return user;
    }

    @Override
    public User update(User user) {
        User existingUser = userMap.get(user.getId());

        if (user.getName() != null) {
            existingUser.setName(user.getName());
        }
        if (user.getEmail() != null) {
            if (!isEmailExists(user)) {
                userEmailMap.remove(existingUser.getEmail());
                existingUser.setEmail(user.getEmail());
                userEmailMap.put(existingUser.getEmail(), existingUser.getId());
            } else {
                throw new AlreadyExistsException("Email=" + user.getEmail() + " уже используется");
            }
        }
        return existingUser;
    }

    @Override
    public void delete(Long id) {
        User user = userMap.get(id);
        userMap.remove(id);
        userEmailMap.remove(user.getEmail());
    }

    private boolean isEmailExists(User user) {
        return userEmailMap.containsKey(user.getEmail()) && !userEmailMap.get(user.getEmail()).equals(user.getId());
    }
}