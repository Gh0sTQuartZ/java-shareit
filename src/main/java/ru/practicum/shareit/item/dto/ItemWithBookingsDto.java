package ru.practicum.shareit.item.dto;

import lombok.*;
import ru.practicum.shareit.booking.model.*;

import java.time.*;
import java.util.*;

@Data
@Builder
public class ItemWithBookingsDto {
    private Long id;
    private String name;
    private String description;
    private Boolean available;
    private BookingDto lastBooking;
    private BookingDto nextBooking;
    private List<CommentDto> comments;

    public void setLastBooking(Booking booking) {
        lastBooking = BookingDto.builder()
                .id(booking.getId())
                .bookerId(booking.getBooker().getId())
                .start(booking.getStart())
                .end(booking.getEnd())
                .build();
    }

    public void setNextBooking(Booking booking) {
        nextBooking = BookingDto.builder()
                .id(booking.getId())
                .bookerId(booking.getBooker().getId())
                .start(booking.getStart())
                .end(booking.getEnd())
                .build();
    }

    @Data
    @Builder
    private static class BookingDto {
        private long id;
        private long bookerId;
        private LocalDateTime start;
        private LocalDateTime end;
    }
}
