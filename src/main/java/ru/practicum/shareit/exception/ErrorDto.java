package ru.practicum.shareit.exception;

import lombok.*;

@Getter
public class ErrorDto {
    private final String exceptionClass;
    private final String message;

    public ErrorDto(final String exceptionClass, final String message) {
        this.exceptionClass = exceptionClass;
        this.message = message;
    }
}
