package ru.practicum.shareit.exception;

import lombok.extern.slf4j.*;
import org.springframework.http.*;
import org.springframework.web.bind.*;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
@Slf4j
public class RestControllerAdviser {
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorDto handleNotFoundException(final NotFoundException exception) {
        final ErrorDto errorDto = new ErrorDto(exception.getClass().getName(), exception.getMessage());
        log.warn("Выброшено исключение: " + exception.getClass() + " сообщение: " + exception.getMessage());
        return errorDto;
    }

    @ExceptionHandler(AlreadyExistsException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public ErrorDto handleAlreadyExistsException(final NotFoundException exception) {
        final ErrorDto errorDto = new ErrorDto(exception.getClass().getName(), exception.getMessage());
        log.warn("Выброшено исключение: " + exception.getClass() + " сообщение: " + exception.getMessage());
        return errorDto;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorDto handleMethodArgumentNotValidException(final MethodArgumentNotValidException exception) {
        final ErrorDto errorDto = new ErrorDto(exception.getClass().getName(), exception.getMessage());
        log.warn("Выброшено исключение: " + exception.getClass() + " сообщение: " + exception.getMessage());
        return errorDto;
    }

    @ExceptionHandler(AvailableException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorDto handleAvailableException(final AvailableException exception) {
        final ErrorDto errorDto = new ErrorDto(exception.getClass().getName(), exception.getMessage());
        log.warn("Выброшено исключение: " + exception.getClass() + " сообщение: " + exception.getMessage());
        return errorDto;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDto handleException(final Exception exception) {
        final ErrorDto errorDto = new ErrorDto(exception.getClass().getName(), exception.getMessage());
        log.error(exception.getMessage(), exception);
        return errorDto;
    }
}