package ru.practicum.shareit.item.dto;

import lombok.*;
import ru.practicum.shareit.booking.dto.*;

import java.util.*;

@Data
@Builder
public class ItemWithBookingsDto {
    private Long id;
    private String name;
    private String description;
    private Boolean available;
    private BookingForItem lastBooking;
    private BookingForItem nextBooking;
    private List<CommentDto> comments;
}
