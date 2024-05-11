package ru.practicum.shareit.booking.dao;

import org.springframework.data.jpa.repository.*;
import ru.practicum.shareit.booking.model.*;

import java.time.*;
import java.util.*;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByBooker_IdOrderByStartDesc(long bookerId);

    List<Booking> findByBooker_IdAndStartIsBeforeAndEndIsAfterOrderByStartDesc(long bookerId, LocalDateTime start,
                                                                               LocalDateTime before);

    List<Booking> findByBooker_IdAndEndIsBeforeOrderByStartDesc(long bookerId, LocalDateTime end);

    List<Booking> findByBooker_IdAndStartIsAfterOrderByStartDesc(long bookerId, LocalDateTime start);

    List<Booking> findByBooker_IdAndStatusIsOrderByStartDesc(long bookerId, Status status);

    List<Booking> findByItem_Owner_IdOrderByStartDesc(long bookerId);

    List<Booking> findByItem_Owner_IdAndStartIsBeforeAndEndIsAfterOrderByStartDesc(long bookerId, LocalDateTime start,
                                                                                   LocalDateTime before);

    List<Booking> findByItem_Owner_IdAndEndIsBeforeOrderByStartDesc(long bookerId, LocalDateTime end);

    List<Booking> findByItem_Owner_IdAndStartIsAfterOrderByStartDesc(long bookerId, LocalDateTime start);

    List<Booking> findByItem_Owner_IdAndStatusIsOrderByStartDesc(long bookerId, Status status);

    List<Booking> findByItem_IdAndStatusNotOrderByStart(long itemId, Status status);

    List<Booking> findByBooker_IdAndItem_IdAndStartIsBefore(long userId, long itemId, LocalDateTime start);
}
