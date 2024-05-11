package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.dto.*;

import java.util.*;

public interface BookingService {
    BookingDtoOut create(long userId, BookingDtoIn bookingDtoOut);

    BookingDtoOut approve(long userId, long bookingId, boolean approve);

    BookingDtoOut get(long userId, long bookingId);

    List<BookingDtoOut> getAll(long userId, String state);

    List<BookingDtoOut> getAllByOwner(long userId, String state);
}
