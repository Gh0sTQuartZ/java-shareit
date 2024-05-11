package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.*;

import java.util.*;

public interface ItemService {
    List<ItemDtoWithBookings> getAll(long userId);

    ItemDtoWithBookings get(long id, long userId);

    ItemDto create(long userId, ItemDto itemDto);

    ItemDto update(long userId, ItemDto itemDto);

    List<ItemDto> search(String keyword);
}