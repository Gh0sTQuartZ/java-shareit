package ru.practicum.shareit.user.dto;

import lombok.*;

import javax.validation.constraints.*;

@Data
@Builder
public class UserDto {
    private Long id;
    @NotNull
    @NotBlank
    private String name;
    @NotNull
    @NotBlank
    @Email
    private String email;
}