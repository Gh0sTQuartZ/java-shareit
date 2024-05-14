package ru.practicum.shareit.booking.dto;

import lombok.*;

@Data
@Builder
public class BookingForItem {
    private long id;
    private long bookerId;
}
