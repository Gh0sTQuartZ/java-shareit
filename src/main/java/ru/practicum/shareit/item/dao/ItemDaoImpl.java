package ru.practicum.shareit.item.dao;

import org.springframework.stereotype.*;
import ru.practicum.shareit.item.model.*;

import java.util.*;
import java.util.stream.*;

@Component
public class ItemDaoImpl implements ItemDao {
    private final Map<Long, Item> itemMap = new HashMap<>();
    private final Map<Long, List<Item>> userItemsMap = new HashMap<>();
    private long idCounter = 1;

    @Override
    public List<Item> getAll(long userId) {
        return userItemsMap.get(userId);
    }

    @Override
    public Optional<Item> get(long id) {
        return Optional.ofNullable(itemMap.get(id));
    }

    @Override
    public Item create(Item item) {
        item.setId(idCounter++);
        itemMap.put(item.getId(), item);
        long ownerId = item.getOwner().getId();
        if (!userItemsMap.containsKey(ownerId)) {
            userItemsMap.put(ownerId, new ArrayList<>());
        }
        userItemsMap.get(ownerId).add(item);
        return item;
    }

    @Override
    public Item update(Item item) {
        itemMap.put(item.getId(), item);
        return item;
    }

    @Override
    public List<Item> search(String keyword) {
        String text = keyword.toLowerCase();
        return itemMap.values().stream()
                .filter(i -> (i.getName().toLowerCase().contains(text) ||
                        i.getDescription().toLowerCase().contains(text) &&
                                i.getAvailable()))
                .collect(Collectors.toList());
    }
}