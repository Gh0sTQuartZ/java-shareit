package ru.practicum.shareit.booking.dto;

import ru.practicum.shareit.booking.model.*;

public class BookingMapper {
    private BookingMapper() {
    }

    public static BookingDto toBookingDto(Booking booking) {
        return BookingDto.builder()
                .id(booking.getId())
                .start(booking.getStart())
                .end(booking.getEnd())
                .item(booking.getItem())
                .booker(booking.getBooker())
                .status(booking.getStatus())
                .build();
    }

    public static Booking toBooking(BookingCreateDto bookingDto) {
        Booking booking = new Booking();
        booking.setStart(bookingDto.getStart());
        booking.setEnd(bookingDto.getEnd());
        return booking;
    }

    public static BookingForItem toBookingForItem(Booking booking) {
        return BookingForItem.builder()
                .id(booking.getId())
                .bookerId(booking.getBooker().getId())
                .build();
    }
}
