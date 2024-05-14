package ru.practicum.shareit.booking.dto;

import lombok.*;
import org.springframework.validation.annotation.*;

import javax.validation.constraints.*;
import java.time.*;

@Data
@Validated
public class BookingCreateDto {
    @NotNull
    private Long itemId;
    @FutureOrPresent
    private LocalDateTime start;
    @Future
    private LocalDateTime end;

    @AssertTrue(message = "Ошибка валидации времени")
    public boolean isTimeValid() {
        if (start != null && end != null) {
            return !(start.equals(end) || end.isBefore(start));
        } else {
            return false;
        }
    }
}
