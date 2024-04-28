package ru.practicum.shareit.item.controller;

import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.*;
import ru.practicum.shareit.item.service.*;

import java.util.*;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
@Slf4j
public class ItemController {
    private final ItemService service;

    @GetMapping
    public List<ItemDto> getAll(@RequestHeader("X-Sharer-User-Id") long userId) {
        log.info("Получение списка всех пердметов пользователя id=" + userId);
        return service.getAll(userId);
    }

    @GetMapping("/{itemId}")
    public ItemDto get(@PathVariable long itemId) {
        log.info("Получение предмета id=" + itemId);
        return service.get(itemId);
    }

    @PostMapping
    public ItemDto create(@RequestHeader("X-Sharer-User-Id") long userId,
                          @RequestBody ItemDto itemDto) {
        log.info("Добавление предмета пользователем id=" + userId);
        return service.create(userId, itemDto);
    }

    @PatchMapping("/{itemId}")
    public ItemDto patch(@RequestHeader("X-Sharer-User-Id") long userId,
                         @PathVariable long itemId,
                         @RequestBody ItemDto itemDto) {
        log.info("Изменение предмета id=" + itemId);
        return service.patch(userId, itemId, itemDto);
    }

    @GetMapping("/search")
    public List<ItemDto> search(@RequestParam String text) {
        log.info("Поиск предмета по ключевому слову=" + text);
        return service.search(text);
    }
}