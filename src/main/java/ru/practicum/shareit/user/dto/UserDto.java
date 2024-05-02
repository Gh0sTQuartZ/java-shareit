package ru.practicum.shareit.user.dto;

import lombok.*;
import ru.practicum.shareit.validation.*;

import javax.validation.constraints.*;

@Data
@Builder
public class UserDto {
    private Long id;
    @NotBlank(groups = AdvanceInfo.class)
    private String name;
    @NotBlank(groups = AdvanceInfo.class)
    @Email(groups = BasicInfo.class)
    private String email;
}