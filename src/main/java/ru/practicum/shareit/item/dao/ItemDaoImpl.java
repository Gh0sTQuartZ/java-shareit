package ru.practicum.shareit.item.dao;

import org.springframework.stereotype.*;
import ru.practicum.shareit.item.model.*;

import java.util.*;
import java.util.stream.*;

@Component
public class ItemDaoImpl implements ItemDao {
    private final Map<Long, Item> data = new HashMap<>();
    private long idCounter = 1;

    @Override
    public List<Item> getAll(long userId) {
        return data.values().stream()
                .filter(i -> i.getOwner().getId() == userId)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Item> get(long id) {
        return Optional.ofNullable(data.get(id));
    }

    @Override
    public Item create(Item item) {
        item.setId(idCounter++);
        data.put(item.getId(), item);
        return item;
    }

    @Override
    public Item patch(Item item) {
        Item existingItem = data.get(item.getId());

        if (item.getName() != null) {
            existingItem.setName(item.getName());
        }
        if (item.getDescription() != null) {
            existingItem.setDescription(item.getDescription());
        }
        if (item.getAvailable() != null) {
            existingItem.setAvailable(item.getAvailable());
        }

        return existingItem;
    }

    @Override
    public List<Item> search(String keyword) {
        if (keyword.isBlank()) {
            return Collections.emptyList();
        }

        String text = keyword.toLowerCase();
        return data.values().stream()
                .filter(i -> (i.getName().toLowerCase().contains(text) ||
                        i.getDescription().toLowerCase().contains(text) &&
                                i.getAvailable()))
                .collect(Collectors.toList());
    }
}