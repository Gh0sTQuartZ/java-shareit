package ru.practicum.shareit.booking.dto;

import lombok.*;
import ru.practicum.shareit.booking.enums.*;
import ru.practicum.shareit.item.model.*;
import ru.practicum.shareit.user.model.*;

import java.time.*;

@Data
@Builder
public class BookingDto {
    private Long id;
    private Long bookerId;
    private LocalDateTime start;
    private LocalDateTime end;
    private Item item;
    private User booker;
    private Status status;
}
