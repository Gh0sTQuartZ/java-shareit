package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.dto.*;

import java.util.*;

public interface UserService {
    List<UserDto> getAll();

    UserDto get(long id);

    UserDto create(UserDto userDto);

    UserDto patch(long id, UserDto userDto);

    UserDto delete(long id);
}