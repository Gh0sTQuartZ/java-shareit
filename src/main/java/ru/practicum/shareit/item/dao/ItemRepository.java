package ru.practicum.shareit.item.dao;

import org.springframework.data.jpa.repository.*;
import ru.practicum.shareit.item.model.*;

import java.util.*;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByOwner_IdOrderById(long ownerId);

    @Query("select i from Item as i " +
            "where (lower(i.name) like lower(concat('%', ?1,'%')) or " +
            "lower(i.description) like lower(concat('%', ?1,'%'))) and " +
            "i.available = true")
    List<Item> findByKeyword(String keyword);
}
