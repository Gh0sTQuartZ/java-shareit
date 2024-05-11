package ru.practicum.shareit.item.model;

import lombok.*;
import ru.practicum.shareit.user.model.*;

import javax.persistence.*;
import java.time.*;

@Data
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String text;
    @ManyToOne
    private Item item;
    @ManyToOne
    private User author;
    private LocalDateTime created;
}
