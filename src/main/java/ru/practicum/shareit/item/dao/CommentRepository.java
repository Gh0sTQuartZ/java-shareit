package ru.practicum.shareit.item.dao;

import org.springframework.data.jpa.repository.*;
import ru.practicum.shareit.item.model.*;

import java.util.*;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByItem_Id(long itemId);
}
