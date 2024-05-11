package ru.practicum.shareit.exception;

import lombok.*;

@Getter
public class ErrorDto {
    private final String exceptionClass;
    private final String error;

    public ErrorDto(final String exceptionClass, final String error) {
        this.exceptionClass = exceptionClass;
        this.error = error;
    }
}
