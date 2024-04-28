package ru.practicum.shareit.item.model;

import lombok.*;
import ru.practicum.shareit.request.*;
import ru.practicum.shareit.user.model.*;

@Data
@Builder
public class Item {
    private Long id;
    private String name;
    private String description;
    private Boolean available;
    private User owner;
    private ItemRequest request;
}
