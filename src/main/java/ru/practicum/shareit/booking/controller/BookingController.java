package ru.practicum.shareit.booking.controller;

import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.*;
import ru.practicum.shareit.booking.service.*;

import javax.validation.*;
import java.util.*;

@RestController
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
@Slf4j
public class BookingController {
    private final BookingService service;

    @PostMapping
    public BookingDtoOut create(@RequestHeader("X-Sharer-User-Id") long userId,
                                @Valid @RequestBody BookingDtoIn bookingDto) {
        return service.create(userId, bookingDto);
    }

    @PatchMapping("/{bookingId}")
    public BookingDtoOut approve(@RequestHeader("X-Sharer-User-Id") long userId,
                                 @PathVariable long bookingId,
                                 @RequestParam boolean approved) {
        return service.approve(userId, bookingId, approved);
    }

    @GetMapping("/{bookingId}")
    public BookingDtoOut get(@RequestHeader("X-Sharer-User-Id") long userId,
                             @PathVariable long bookingId) {
        return service.get(userId, bookingId);
    }

    @GetMapping
    public List<BookingDtoOut> getAll(@RequestHeader("X-Sharer-User-Id") long userId,
                                      @RequestParam(defaultValue = "ALL") String state) {
        return service.getAll(userId, state);
    }

    @GetMapping("/owner")
    public List<BookingDtoOut> getAllByOwner(@RequestHeader("X-Sharer-User-Id") long userId,
                                             @RequestParam(defaultValue = "ALL") String state) {
        return service.getAllByOwner(userId, state);
    }
}
