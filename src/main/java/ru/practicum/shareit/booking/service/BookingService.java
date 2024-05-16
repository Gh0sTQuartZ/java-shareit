package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.dto.*;
import ru.practicum.shareit.booking.enums.*;

import java.util.*;

public interface BookingService {
    BookingDto create(long userId, BookingCreateDto bookingDtoOut);

    BookingDto approve(long userId, long bookingId, boolean approve);

    BookingDto get(long userId, long bookingId);

    List<BookingDto> getAll(long userId, State state);

    List<BookingDto> getAllByOwner(long userId, State state);
}
