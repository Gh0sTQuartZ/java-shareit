package ru.practicum.shareit.item.service;

import lombok.*;
import org.springframework.stereotype.*;
import ru.practicum.shareit.booking.dao.*;
import ru.practicum.shareit.booking.dto.*;
import ru.practicum.shareit.booking.model.*;
import ru.practicum.shareit.exception.*;
import ru.practicum.shareit.item.dao.*;
import ru.practicum.shareit.item.dto.*;
import ru.practicum.shareit.item.model.*;
import ru.practicum.shareit.user.dao.*;
import ru.practicum.shareit.user.model.*;

import java.time.*;
import java.util.*;
import java.util.stream.*;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final CommentRepository commentRepository;

    @Override
    public List<ItemDtoWithBookings> getAll(long userId) {
        return itemRepository.findByOwner_IdOrderById(userId).stream()
                .map(ItemMapper::itemDtoWithBookings)
                .peek(this::addBookings)
                .peek(this::addComments)
                .collect(Collectors.toList());
    }

    @Override
    public ItemDtoWithBookings get(long id, long userId) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Предмет id=" + id + " не найден"));
        Long ownerId = item.getOwner().getId();
        ItemDtoWithBookings itemDto = ItemMapper.itemDtoWithBookings(item);
        if (ownerId == userId) {
            addBookings(itemDto);
        }
        addComments(itemDto);
        return itemDto;
    }

    @Override
    public ItemDto create(long userId, ItemDto itemDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь id=" + userId + " не найден"));
        Item item = ItemMapper.toItem(itemDto);
        item.setOwner(user);
        return ItemMapper.toItemDto(itemRepository.save(item));
    }

    @Override
    public ItemDto update(long userId, ItemDto itemDto) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь id=" + userId + " не найден"));
        Item item = itemRepository.findById(itemDto.getId())
                .orElseThrow(() -> new NotFoundException("Предмет id=" + itemDto.getId() + " не найден"));
        if (item.getOwner().getId() != userId) {
            throw new NotFoundException("Пользователь id=" + userId + " не является владельцем предмета id=" +
                    itemDto.getId());
        }

        if (itemDto.getName() != null) {
            item.setName(itemDto.getName());
        }
        if (itemDto.getDescription() != null) {
            item.setDescription(itemDto.getDescription());
        }
        if (itemDto.getAvailable() != null) {
            item.setAvailable(itemDto.getAvailable());
        }

        return ItemMapper.toItemDto(itemRepository.save(item));
    }

    @Override
    public List<ItemDto> search(String keyword) {
        if (keyword.isBlank()) {
            return Collections.emptyList();
        }
        return itemRepository.findByKeyword(keyword).stream()
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }

    private void addBookings(ItemDtoWithBookings itemDto) {
        List<Booking> bookings = bookingRepository.findByItem_IdAndStatusNotOrderByStart(itemDto.getId(), Status.REJECTED);
        LocalDateTime now = LocalDateTime.now();

        List<Booking> lastBookings = bookings.stream()
                .filter(b -> b.getStart().isBefore(now))
                .collect(Collectors.toList());
        if (!lastBookings.isEmpty()) {
            itemDto.setLastBooking(BookingMapper.toBookingDtoForItem(lastBookings.get(lastBookings.size() - 1)));
        }

        Optional<Booking> nextBooking = bookings.stream()
                .filter(b -> b.getStart().isAfter(now))
                .findFirst();
        if (nextBooking.isPresent()) {
            itemDto.setNextBooking(BookingMapper.toBookingDtoForItem(nextBooking.get()));
        }
    }

    private void addComments(ItemDtoWithBookings itemDto) {
        List<Comment> comments = commentRepository.findByItem_Id(itemDto.getId());
        List<CommentDtoOut> collect = comments.stream()
                .map(CommentMapper::toCommentDto)
                .collect(Collectors.toList());
        itemDto.setComments(collect);
    }
}