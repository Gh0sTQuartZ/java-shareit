package ru.practicum.shareit.item.controller;

import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.*;
import ru.practicum.shareit.item.service.*;

import javax.validation.*;
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
        List<ItemDto> all = service.getAll(userId);
        log.info("Размер полученного списка= " + all.size());
        log.debug("Полученный список: " + all);
        return all;
    }

    @GetMapping("/{itemId}")
    public ItemDto get(@PathVariable long itemId) {
        log.info("Получение предмета id=" + itemId);
        ItemDto item = service.get(itemId);
        log.info("Полученный предмет: " + item);
        return item;
    }

    @PostMapping
    public ItemDto create(@RequestHeader("X-Sharer-User-Id") long userId,
                          @Valid @RequestBody ItemDto itemDto) {
        log.info("Добавление предмета пользователем id=" + userId);
        ItemDto item = service.create(userId, itemDto);
        log.info("id созданного предмета = " + item.getId());
        log.debug("Созданный предмет: " + item);
        return item;
    }

    @PatchMapping("/{itemId}")
    public ItemDto update(@RequestHeader("X-Sharer-User-Id") long userId,
                          @PathVariable long itemId,
                          @RequestBody ItemDto itemDto) {
        log.info("Изменение предмета id=" + itemId);
        itemDto.setId(itemId);
        ItemDto update = service.update(userId, itemDto);
        log.info("Предмет id=" + itemId + " изменён");
        log.debug("Изменённый предмет: " + update);
        return update;
    }

    @GetMapping("/search")
    public List<ItemDto> search(@RequestParam String text) {
        log.info("Поиск предмета по ключевому слову=" + text);
        List<ItemDto> search = service.search(text);
        log.info("Количество найденных предметов=" + search.size());
        log.debug("Найденные предметы: " + search);
        return search;
    }
}