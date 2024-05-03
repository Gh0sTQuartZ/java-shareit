package ru.practicum.shareit.user.dto;

import lombok.*;
import ru.practicum.shareit.validation.*;

import javax.validation.constraints.*;

@Data
@Builder
public class UserDto {
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    @Email
    @Email(groups = Update.class)
    private String email;
}