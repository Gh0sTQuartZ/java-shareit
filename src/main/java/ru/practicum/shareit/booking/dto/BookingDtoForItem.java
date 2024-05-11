package ru.practicum.shareit.booking.dto;

import lombok.*;

@Data
@Builder
public class BookingDtoForItem {
    private long id;
    private long bookerId;
}
