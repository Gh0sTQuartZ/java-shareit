package ru.practicum.shareit.item.dto;

import lombok.*;

import javax.validation.constraints.*;

@Data
public class CommentCreateDto {
    @NotBlank
    String text;
}
