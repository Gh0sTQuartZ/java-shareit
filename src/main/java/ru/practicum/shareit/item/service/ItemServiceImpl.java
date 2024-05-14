package ru.practicum.shareit.item.service;

import lombok.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;
import ru.practicum.shareit.booking.dao.*;
import ru.practicum.shareit.booking.dto.*;
import ru.practicum.shareit.booking.enums.*;
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
    public List<ItemWithBookingsDto> getAll(long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь id=" + userId + " не найден"));
        List<ItemWithBookingsDto> itemDto = itemRepository.findByOwner_Id(userId,
                        Sort.by(Sort.DEFAULT_DIRECTION, "id")).stream()
                .map(ItemMapper::toItemWithBookingsDto)
                .collect(Collectors.toList());
        List<Booking> bookings = bookingRepository.findByItem_Owner_IdAndStatusIs(userId, Status.APPROVED,
                Sort.by(Sort.Direction.ASC, "start"));
        List<Comment> comments = commentRepository.findByItem_Owner_id(userId);
        itemDto.stream().forEach(i -> {
            setBookings(i,
                    bookings.stream().filter(b -> b.getItem().getId().equals(i.getId())).collect(Collectors.toList()));
            setComments(i,
                    comments.stream().filter(c -> c.getItem().getId().equals(i.getId())).collect(Collectors.toList()));
        });
        return itemDto;
    }

    @Override
    public ItemWithBookingsDto get(long id, long userId) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Предмет id=" + id + " не найден"));
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь id=" + userId + " не найден"));
        ItemWithBookingsDto itemDto = ItemMapper.toItemWithBookingsDto(item);
        if (item.getOwner().getId() == userId) {
            List<Booking> bookings = bookingRepository.findByItem_IdAndStatusIs(id, Status.APPROVED,
                    Sort.by(Sort.Direction.ASC, "start"));
            setBookings(itemDto, bookings);
        }
        List<Comment> comments = commentRepository.findByItem_id(id);
        setComments(itemDto, comments);
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


    private void setBookings(ItemWithBookingsDto item, List<Booking> bookings) {
        LocalDateTime now = LocalDateTime.now();
        List<Booking> lastBookings = bookings.stream()
                .filter(b -> b.getStart().isBefore(now))
                .collect(Collectors.toList());
        Optional<Booking> nextBooking = bookings.stream()
                .filter(b -> b.getStart().isAfter(now))
                .findFirst();
        if (!lastBookings.isEmpty()) {
            item.setLastBooking(BookingMapper.toBookingForItem(lastBookings.get(lastBookings.size() - 1)));
        }
        if (nextBooking.isPresent()) {
            item.setNextBooking(BookingMapper.toBookingForItem(nextBooking.get()));
        }
    }

    private void setComments(ItemWithBookingsDto item, List<Comment> comments) {
        item.setComments(comments.stream()
                .map(CommentMapper::toCommentDto)
                .collect(Collectors.toList()));
    }
}