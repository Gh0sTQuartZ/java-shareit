package ru.practicum.shareit.booking.controller;

import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.*;
import ru.practicum.shareit.booking.enums.*;
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
    public BookingDto create(@RequestHeader("X-Sharer-User-Id") long userId,
                             @Valid @RequestBody BookingCreateDto bookingDto) {
        log.info("добавление бронирования пользователем id=" + userId + " на предмет id=" + bookingDto.getItemId());
        BookingDto created = service.create(userId, bookingDto);
        log.info("id созданного бронирвания = " + created.getId());
        return created;
    }

    @PatchMapping("/{bookingId}")
    public BookingDto approve(@RequestHeader("X-Sharer-User-Id") long userId,
                              @PathVariable long bookingId,
                              @RequestParam boolean approved) {
        log.info("Пользователь id=" + userId + " устанавливает статус бронированию id=" + bookingId);
        BookingDto approve = service.approve(userId, bookingId, approved);
        log.info("Статус бронирования=" + approve.getStatus());
        return approve;
    }

    @GetMapping("/{bookingId}")
    public BookingDto get(@RequestHeader("X-Sharer-User-Id") long userId,
                          @PathVariable long bookingId) {
        log.info("Получение бронирования id=" + bookingId);
        BookingDto bookingDto = service.get(userId, bookingId);
        log.info("Полученное бронирование=" + bookingDto);
        return bookingDto;
    }

    @GetMapping
    public List<BookingDto> getAll(@RequestHeader("X-Sharer-User-Id") long userId,
                                   @RequestParam(defaultValue = "ALL") String state) {
        log.info("Пользователь id=" + userId + " получает все свои бронирования по параметру=" + state);
        List<BookingDto> all = service.getAll(userId, State.stringToEnum(state));
        log.info("Размер полученного списка=" + all.size());
        return all;
    }

    @GetMapping("/owner")
    public List<BookingDto> getAllByOwner(@RequestHeader("X-Sharer-User-Id") long userId,
                                          @RequestParam(defaultValue = "ALL") String state) {
        log.info("Пользователь id=" + userId + " получает все бронирования своих вещей по параметру=" + state);
        List<BookingDto> allByOwner = service.getAllByOwner(userId, State.stringToEnum(state));
        log.info("Размер полученного списка=" + allByOwner.size());
        return allByOwner;
    }
}
