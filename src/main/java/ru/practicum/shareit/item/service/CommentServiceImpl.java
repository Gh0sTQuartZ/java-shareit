package ru.practicum.shareit.item.service;

import lombok.*;
import org.springframework.stereotype.*;
import ru.practicum.shareit.booking.dao.*;
import ru.practicum.shareit.booking.model.*;
import ru.practicum.shareit.exception.*;
import ru.practicum.shareit.item.dao.*;
import ru.practicum.shareit.item.dto.*;
import ru.practicum.shareit.item.model.*;
import ru.practicum.shareit.user.dao.*;
import ru.practicum.shareit.user.model.*;

import java.time.*;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final BookingRepository bookingRepository;

    @Override
    public CommentDtoOut create(long userId, long itemId, CommentDtoIn commentDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь id=" + userId + " не найден"));
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Предмет id=" + itemId + " не найден"));
        LocalDateTime now = LocalDateTime.now();
        List<Booking> bookings = bookingRepository.findByBooker_IdAndItem_IdAndStartIsBefore(userId, itemId, now);
        if (bookings.isEmpty()) {
            throw new AvailableException("Пользователь id=" + userId + "не пользовался вещью id=" + item);
        }

        Comment comment = CommentMapper.toComment(commentDto);
        comment.setItem(item);
        comment.setAuthor(user);
        comment.setCreated(now);
        return CommentMapper.toCommentDto(commentRepository.save(comment));
    }
}
