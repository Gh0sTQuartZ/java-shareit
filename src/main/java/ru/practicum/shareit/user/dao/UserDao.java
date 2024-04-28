package ru.practicum.shareit.user.dao;

import ru.practicum.shareit.user.model.*;

import java.util.*;

public interface UserDao {
    List<User> getAll();
    Optional<User> get(long id);
    Optional<User> create(User user);
    Optional<User> patch(User user);
    Optional<User> delete(Long id);
}