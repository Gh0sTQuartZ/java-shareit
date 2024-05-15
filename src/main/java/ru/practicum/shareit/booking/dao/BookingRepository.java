package ru.practicum.shareit.booking.dao;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import ru.practicum.shareit.booking.enums.*;
import ru.practicum.shareit.booking.model.*;

import java.time.*;
import java.util.*;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByBooker_Id(long bookerId, Sort sort);

    @Query("select b from Booking as b " +
            "left join b.booker as u " +
            "where u.id = ?1 and " +
            "b.start <= ?2 and " +
            "b.end > ?2")
    List<Booking> findByBooker_IdCurrent(long bookerId, LocalDateTime now, Sort sort);


    List<Booking> findByBooker_IdAndEndIsBefore(long bookerId, LocalDateTime end, Sort sort);

    List<Booking> findByBooker_IdAndStartIsAfter(long bookerId, LocalDateTime start, Sort sort);

    List<Booking> findByBooker_IdAndStatusIs(long bookerId, Status status, Sort sort);

    List<Booking> findByItem_Owner_Id(long bookerId, Sort sort);

    @Query("select b from Booking as b " +
            "left join b.item as i " +
            "left join i.owner as u " +
            "where u.id = ?1 and " +
            "b.start <= ?2 and " +
            "b.end > ?2")
    List<Booking> findByItem_Owner_IdCurrent(long bookerId, LocalDateTime now, Sort sort);

    List<Booking> findByItem_Owner_IdAndEndIsBefore(long bookerId, LocalDateTime end, Sort sort);

    List<Booking> findByItem_Owner_IdAndStartIsAfter(long bookerId, LocalDateTime start, Sort sort);

    List<Booking> findByItem_Owner_IdAndStatusIs(long bookerId, Status status, Sort sort);

    @Query("select b from Booking as b " +
            "left join b.booker as u " +
            "left join b.item as i " +
            "where u.id = ?1 and " +
            "i.id = ?2 and " +
            "b.start <= ?3")
    List<Booking> findCurrentOrPastBookings(long userId, long itemId, LocalDateTime start);

    List<Booking> findByItem_IdAndStatusIs(Long itemId, Status status, Sort sort);
}
