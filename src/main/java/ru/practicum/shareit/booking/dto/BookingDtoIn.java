package ru.practicum.shareit.booking.dto;

import lombok.*;

import javax.validation.constraints.*;
import java.time.*;

@Data
public class BookingDtoIn {
    @NotNull
    private Long itemId;
    @NotNull
    @FutureOrPresent
    private LocalDateTime start;
    @NotNull
    @FutureOrPresent
    private LocalDateTime end;
}
