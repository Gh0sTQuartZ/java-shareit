package ru.practicum.shareit.booking.dto;

import lombok.*;
import ru.practicum.shareit.booking.model.*;
import ru.practicum.shareit.item.model.*;
import ru.practicum.shareit.user.model.*;

import java.time.*;

@Data
@Builder
public class BookingDtoOut {
    private Long id;
    private LocalDateTime start;
    private LocalDateTime end;
    private Item item;
    private User booker;
    private Status status;
}
