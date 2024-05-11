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
    private final UserRepository repository;

    @Override
    public List<UserDto> getAll() {
        return repository.findAll().stream()
                .map(UserMapper::toUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto get(long id) {
        return UserMapper.toUserDto(repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь id=" + id + " не найден")));
    }

    @Override
    public UserDto create(UserDto userDto) {
        User user = UserMapper.toUser(userDto);
        User save = repository.save(user);
        return UserMapper.toUserDto(save);
    }

    @Override
    public UserDto update(UserDto userDto) {
        User user = repository.findById(userDto.getId())
                .orElseThrow(() -> new NotFoundException("Пользователь id=" + userDto.getId() + " не найден"));
        if (userDto.getName() != null) {
            user.setName(userDto.getName());
        }
        if (userDto.getEmail() != null) {
            user.setEmail(userDto.getEmail());
        }

        User save = repository.save(user);

        return UserMapper.toUserDto(save);
    }

    @Override
    public void delete(long id) {
        repository.deleteById(id);
    }
}