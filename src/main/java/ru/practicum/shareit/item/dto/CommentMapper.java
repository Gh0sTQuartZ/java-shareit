package ru.practicum.shareit.item.dto;

import ru.practicum.shareit.item.model.*;

public class CommentMapper {
    private CommentMapper() {

    }

    public static Comment toComment(CommentCreateDto commentDto) {
        Comment comment = new Comment();
        comment.setText(commentDto.getText());
        return comment;
    }

    public static CommentDto toCommentDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .text(comment.getText())
                .authorName(comment.getAuthor().getName())
                .created(comment.getCreated())
                .build();
    }
}
