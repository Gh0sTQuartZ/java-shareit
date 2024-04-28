package ru.practicum.shareit.item.dao;

import ru.practicum.shareit.item.model.*;

import java.util.*;

public interface ItemDao {
    List<Item> getAll(long userId);

    Optional<Item> get(long id);

    Item create(Item item);

    Item patch(Item item);

    List<Item> search(String keyword);
}
