package ru.practicum.shareit.item.service;

import lombok.*;
import org.springframework.stereotype.*;
import ru.practicum.shareit.exception.*;
import ru.practicum.shareit.item.dao.*;
import ru.practicum.shareit.item.dto.*;
import ru.practicum.shareit.item.model.*;
import ru.practicum.shareit.user.dao.*;
import ru.practicum.shareit.user.model.*;

import java.util.*;
import java.util.stream.*;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemDao itemDao;
    private final UserDao userDao;

    @Override
    public List<ItemDto> getAll(long userId) {
        return itemDao.getAll(userId).stream()
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public ItemDto get(long id) {
        return ItemMapper.toItemDto(itemDao.get(id)
                .orElseThrow(() -> new NotFoundException("Предмет id=" + id + " не найден")));
    }

    @Override
    public ItemDto create(long userId, ItemDto itemDto) {
        User user = userDao.get(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь id=" + userId + " не найден"));
        Item item = ItemMapper.toItem(itemDto);
        validate(item);
        item.setOwner(user);
        return ItemMapper.toItemDto(itemDao.create(item));
    }

    @Override
    public ItemDto patch(long userId, long id, ItemDto itemDto) {
        userDao.get(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь id=" + userId + " не найден"));
        Item existingItem = itemDao.get(id)
                .orElseThrow(() -> new NotFoundException("Предмет id=" + id + " не найден"));
        if (existingItem.getOwner().getId() != userId) {
            throw new NotFoundException("Пользователь id=" + userId + " не является владельцем предмета id=" + id);
        }

        Item item = ItemMapper.toItem(itemDto);
        item.setId(id);
        return ItemMapper.toItemDto(itemDao.patch(item));
    }

    @Override
    public List<ItemDto> search(String keyword) {
        return itemDao.search(keyword).stream()
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }

    private void validate(Item item) {
        if (item.getName() == null || item.getName().isBlank()) {
            throw new ValidateException("Название предмета не может быть пустым");
        }
        if (item.getDescription() == null || item.getDescription().isBlank()) {
            throw new ValidateException("Описание предмета не может быть пустым");
        }
        if (item.getAvailable() == null) {
            throw new ValidateException("Статус доступности предмета не может быть пустым");
        }
    }
}