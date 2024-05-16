package ru.practicum.shareit.booking.enums;

public enum State {
    ALL,
    CURRENT,
    PAST,
    FUTURE,
    WAITING,
    REJECTED;

    public static State stringToEnum(String state) {
        for (State s : State.values()) {
            if (state.equals(s.name())) {
                return s;
            }
        }
        throw new IllegalArgumentException("Unknown state: " + state);
    }
}
