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
        validate(user);

        User created = userDao.create(user)
                .orElseThrow(() -> new AlreadyExistsException("Email=" + user.getEmail() + " уже занят"));

        return UserMapper.toUserDto(created);
    }

    @Override
    public UserDto patch(long id, UserDto userDto) {
        userDao.get(id)
                .orElseThrow(() -> new NotFoundException("Пользователь id=" + id + " не найден"));
        if (userDto.getEmail() != null && !isValidEmailAddress(userDto.getEmail())) {
            throw new ValidateException("Email не соответствует стандартам записи");
        }

        User user = UserMapper.toUser(userDto);
        user.setId(id);

        User patchedUser = userDao.patch(user)
                .orElseThrow(() -> new AlreadyExistsException("Email=" + user.getEmail() + " уже занят"));

        return UserMapper.toUserDto(patchedUser);
    }

    @Override
    public UserDto delete(long id) {
        return UserMapper.toUserDto(userDao.delete(id)
                .orElseThrow(() -> new NotFoundException("Пользователь id=" + id + " не найден")));
    }

    private void validate(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            throw new ValidateException("Имя пользователя не может быть пустым");
        }
        if (user.getEmail() == null || !isValidEmailAddress(user.getEmail())) {
            throw new ValidateException("Email не соответствует стандартам записи");
        }
    }

    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }
}