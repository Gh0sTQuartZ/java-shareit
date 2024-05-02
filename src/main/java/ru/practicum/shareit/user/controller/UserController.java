package ru.practicum.shareit.user.controller;

import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.validation.annotation.*;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.*;
import ru.practicum.shareit.user.service.*;
import ru.practicum.shareit.validation.*;

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
        List<UserDto> all = service.getAll();
        log.info("Размер полученного списка=" + all.size());
        log.debug("Полученный список: " + all);
        return all;
    }

    @GetMapping("/{userId}")
    public UserDto get(@PathVariable final long userId) {
        log.info("Получение пользователя id=" + userId);
        UserDto user = service.get(userId);
        log.info("Полученный пользователь: " + user);
        return user;
    }

    @PostMapping
    public UserDto create(@Validated({BasicInfo.class, AdvanceInfo.class})
                              @RequestBody final UserDto userDto) {
        log.info("Создание пользователя");
        UserDto user = service.create(userDto);
        log.info("id созданного пользователя= " + user.getId());
        log.debug("Созданный пользователь: " + user);
        return user;
    }

    @PatchMapping("/{userId}")
    public UserDto update(@PathVariable final long userId,
                          @Validated(BasicInfo.class) @RequestBody final UserDto userDto) {
        log.info("Изменение пользователя id=" + userId);
        userDto.setId(userId);
        UserDto update = service.update(userDto);
        log.info("Пользователь id=" + userId + " изменён");
        log.debug("Изменённый пользователь: " + update);
        return update;
    }

    @DeleteMapping("/{userId}")
    public void delete(@PathVariable final long userId) {
        log.info("Удаление пользователя id=" + userId);
        service.delete(userId);
        log.info("Пользователь удалён");
    }
}