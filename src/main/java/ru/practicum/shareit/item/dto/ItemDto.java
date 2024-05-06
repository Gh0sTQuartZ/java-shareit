package ru.practicum.shareit.item.dto;

import lombok.*;

import javax.validation.constraints.*;

@Data
@Builder
public class ItemDto {
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotNull
    private Boolean available;
}
