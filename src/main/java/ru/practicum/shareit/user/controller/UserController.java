package ru.practicum.shareit.user.controller;

import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.*;
import ru.practicum.shareit.user.service.*;

import java.util.*;

@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService service;

    @GetMapping
    public List<UserDto> getAll() {
        log.info("Получение списка всех пользователей");
        return service.getAll();
    }

    @GetMapping("/{userId}")
    public UserDto get(@PathVariable final long userId) {
        log.info("Получение пользователя id=" + userId);
        return service.get(userId);
    }

    @PostMapping
    public UserDto create(@RequestBody final UserDto userDto) {
        log.info("Создание пользователя");
        return service.create(userDto);
    }

    @PatchMapping("/{userId}")
    public UserDto patch(@PathVariable final long userId,
                         @RequestBody final UserDto userDto) {
        log.info("Изменение пользователя id=" + userId);
        return service.patch(userId, userDto);
    }

    @DeleteMapping("/{userId}")
    public UserDto delete(@PathVariable final long userId) {
        log.info("Удаление пользователя id=" + userId);
        return service.delete(userId);
    }
}