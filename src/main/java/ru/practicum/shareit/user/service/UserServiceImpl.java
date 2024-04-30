package ru.practicum.shareit.user.service;

import lombok.*;
import org.springframework.stereotype.*;
import ru.practicum.shareit.exception.*;
import ru.practicum.shareit.user.dao.*;
import ru.practicum.shareit.user.dto.*;
import ru.practicum.shareit.user.model.*;

import java.util.*;
import java.util.stream.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    @Override
    public List<UserDto> getAll() {
        return userDao.getAll().stream()
                .map(UserMapper::toUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto get(long id) {
        return UserMapper.toUserDto(userDao.get(id)
                .orElseThrow(() -> new NotFoundException("Пользователь id=" + id + " не найден")));
    }

    @Override
    public UserDto create(UserDto userDto) {
        User user = UserMapper.toUser(userDto);

        User created = userDao.create(user);

        return UserMapper.toUserDto(created);
    }

    @Override
    public UserDto update(UserDto userDto) {
        long id = userDto.getId();
        userDao.get(id)
                .orElseThrow(() -> new NotFoundException("Пользователь id=" + id + " не найден"));

        User user = UserMapper.toUser(userDto);
        user.setId(id);

        User patchedUser = userDao.update(user);

        return UserMapper.toUserDto(patchedUser);
    }

    @Override
    public void delete(long id) {
        userDao.delete(id);
    }
}