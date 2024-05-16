package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.*;

public interface CommentService {
    CommentDto create(long userId, long itemId, CommentCreateDto commentDto);
}
