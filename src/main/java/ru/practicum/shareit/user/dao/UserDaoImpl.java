package ru.practicum.shareit.user.dao;

import org.springframework.stereotype.*;
import ru.practicum.shareit.user.model.*;

import java.util.*;
import java.util.stream.*;

@Component
public class UserDaoImpl implements UserDao {
    private Map<Long, User> data = new HashMap<>();
    private long idCounter = 1;

    @Override
    public List<User> getAll() {
        return data.values().stream().collect(Collectors.toList());
    }

    @Override
    public Optional<User> get(long id) {
        return Optional.ofNullable(data.get(id));
    }

    @Override
    public Optional<User> create(User user) {
        if (!isEmailExists(user)) {
            user.setId(idCounter++);
            data.put(user.getId(), user);
            return Optional.of(user);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> patch(User user) {
        User existingUser = data.get(user.getId());

        if (user.getName() != null) {
            existingUser.setName(user.getName());
        }
        if (user.getEmail() != null) {
            if (!isEmailExists(user)) {
                existingUser.setEmail(user.getEmail());
            } else {
                return Optional.empty();
            }
        }
        return Optional.of(existingUser);
    }

    @Override
    public Optional<User> delete(Long id) {
        return Optional.ofNullable(data.remove(id));
    }

    private boolean isEmailExists(User user) {
        return data.values().stream()
                .anyMatch(u -> u.getEmail().equals(user.getEmail()) && u.getId() != user.getId());
    }
}