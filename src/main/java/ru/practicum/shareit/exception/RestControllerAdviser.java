package ru.practicum.shareit.exception;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class RestControllerAdviser {
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorDto handleNotFoundException(final NotFoundException exception) {
        final ErrorDto errorDto = new ErrorDto(exception.getClass().getName(), exception.getMessage());
        return errorDto;
    }

    @ExceptionHandler(AlreadyExistsException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public ErrorDto handleAlreadyExistsException(final NotFoundException exception) {
        final ErrorDto errorDto = new ErrorDto(exception.getClass().getName(), exception.getMessage());
        return errorDto;
    }

    @ExceptionHandler(ValidateException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorDto handleValidateException(final ValidateException exception) {
        final ErrorDto errorDto = new ErrorDto(exception.getClass().getName(), exception.getMessage());
        return errorDto;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDto handleException(final Exception exception) {
        final ErrorDto errorDto = new ErrorDto(exception.getClass().getName(), exception.getMessage());
        return errorDto;
    }
}