package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.dto.*;

import java.util.*;

public interface UserService {
    List<UserDto> getAll();

    UserDto get(long id);

    UserDto create(UserDto userDto);

    UserDto update(UserDto userDto);

    void delete(long id);
}