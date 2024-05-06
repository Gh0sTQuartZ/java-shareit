package ru.practicum.shareit.user.dao;

import ru.practicum.shareit.user.model.*;

import java.util.*;

public interface UserDao {
    List<User> getAll();

    Optional<User> get(long id);

    User create(User user);

    User update(User user);

    void delete(Long id);
}