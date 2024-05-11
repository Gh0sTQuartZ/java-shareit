package ru.practicum.shareit.booking.service;

import lombok.*;
import org.springframework.stereotype.*;
import ru.practicum.shareit.booking.dao.*;
import ru.practicum.shareit.booking.dto.*;
import ru.practicum.shareit.booking.model.*;
import ru.practicum.shareit.exception.*;
import ru.practicum.shareit.item.dao.*;
import ru.practicum.shareit.item.model.*;
import ru.practicum.shareit.user.dao.*;
import ru.practicum.shareit.user.model.*;

import java.time.*;
import java.util.*;
import java.util.stream.*;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Override
    public BookingDtoOut create(long userId, BookingDtoIn bookingDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь id=" + userId + " не найден"));
        Item item = itemRepository.findById(bookingDto.getItemId())
                .orElseThrow(() -> new NotFoundException("Предмет id=" + bookingDto.getItemId() + " не найден"));
        if (!item.getAvailable()) {
            throw new AvailableException("Вещь не доступна для бронирования");
        }
        if (item.getOwner().getId() == userId) {
            throw new NotFoundException("Нельзя забронировать собственную вещь");
        }
        if (bookingDto.getStart().isAfter(bookingDto.getEnd())) {
            throw new DateException("Дата начала не может быть позже даты конца");
        }
        if (bookingDto.getEnd().isBefore(bookingDto.getStart())) {
            throw new DateException("Дата конца не может быть раньше даты начала");
        }
        if (bookingDto.getStart().isEqual(bookingDto.getEnd())) {
            throw new DateException("Даты начала и конца не могут совпадать");
        }

        Booking booking = BookingMapper.toBooking(bookingDto);
        booking.setItem(item);
        booking.setBooker(user);
        booking.setStatus(Status.WAITING);

        return BookingMapper.toBookingDto(bookingRepository.save(booking));
    }

    @Override
    public BookingDtoOut approve(long userId, long bookingId, boolean approved) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь id=" + userId + " не найден"));
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException("Бронирование id=" + bookingId + " не найдено"));
        if (booking.getItem().getOwner().getId() != userId) {
            throw new NotFoundException("Только владелец вещи может установить статус бронирования");
        }
        if (booking.getStatus().equals(Status.APPROVED) && approved ||
                booking.getStatus().equals(Status.REJECTED) && !approved) {
            throw new AvailableException("Нельзя изменить статус на тот же");
        }

        if (approved) {
            booking.setStatus(Status.APPROVED);
        } else {
            booking.setStatus(Status.REJECTED);
        }

        return BookingMapper.toBookingDto(bookingRepository.save(booking));
    }

    @Override
    public BookingDtoOut get(long userId, long bookingId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь id=" + userId + " не найден"));
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException("Бронирование id=" + bookingId + " не найдено"));
        if (booking.getBooker().getId() != userId && booking.getItem().getOwner().getId() != userId) {
            throw new NotFoundException("Вы не являетесь владельцем бронирования или вещи");
        }

        return BookingMapper.toBookingDto(booking);
    }

    @Override
    public List<BookingDtoOut> getAll(long userId, String state) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь id=" + userId + " не найден"));
        List<Booking> all;
        LocalDateTime now = LocalDateTime.now();

        switch (state) {
            case "ALL":
                all = bookingRepository.findByBooker_IdOrderByStartDesc(userId);
                break;
            case "CURRENT":
                all = bookingRepository.findByBooker_IdAndStartIsBeforeAndEndIsAfterOrderByStartDesc(userId, now, now);
                break;
            case "PAST":
                all = bookingRepository.findByBooker_IdAndEndIsBeforeOrderByStartDesc(userId, now);
                break;
            case "FUTURE":
                all = bookingRepository.findByBooker_IdAndStartIsAfterOrderByStartDesc(userId, now);
                break;
            case "WAITING":
                all = bookingRepository.findByBooker_IdAndStatusIsOrderByStartDesc(userId, Status.WAITING);
                break;
            case "REJECTED":
                all = bookingRepository.findByBooker_IdAndStatusIsOrderByStartDesc(userId, Status.REJECTED);
                break;
            default:
                throw new NotSupportedException("Unknown state: " + state);
        }
        return all.stream()
                .map(BookingMapper::toBookingDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingDtoOut> getAllByOwner(long userId, String state) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь id=" + userId + " не найден"));
        List<Booking> all;
        LocalDateTime now = LocalDateTime.now();

        switch (state) {
            case "ALL":
                all = bookingRepository.findByItem_Owner_IdOrderByStartDesc(userId);
                break;
            case "CURRENT":
                all = bookingRepository.findByItem_Owner_IdAndStartIsBeforeAndEndIsAfterOrderByStartDesc(userId, now, now);
                break;
            case "PAST":
                all = bookingRepository.findByItem_Owner_IdAndEndIsBeforeOrderByStartDesc(userId, now);
                break;
            case "FUTURE":
                all = bookingRepository.findByItem_Owner_IdAndStartIsAfterOrderByStartDesc(userId, now);
                break;
            case "WAITING":
                all = bookingRepository.findByItem_Owner_IdAndStatusIsOrderByStartDesc(userId, Status.WAITING);
                break;
            case "REJECTED":
                all = bookingRepository.findByItem_Owner_IdAndStatusIsOrderByStartDesc(userId, Status.REJECTED);
                break;
            default:
                throw new NotSupportedException("Unknown state: " + state);
        }
        return all.stream()
                .map(BookingMapper::toBookingDto)
                .collect(Collectors.toList());
    }
}
