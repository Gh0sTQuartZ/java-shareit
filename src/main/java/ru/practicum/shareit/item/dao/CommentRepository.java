package ru.practicum.shareit.item.dao;

import org.springframework.data.jpa.repository.*;
import ru.practicum.shareit.item.model.*;

import java.util.*;


public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByItem_id(long itemId);

    List<Comment> findByItem_Owner_id(long userId);
}
