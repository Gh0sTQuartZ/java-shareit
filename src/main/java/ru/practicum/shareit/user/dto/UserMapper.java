package ru.practicum.shareit.user.dto;

import ru.practicum.shareit.user.model.*;

public class UserMapper {
    private UserMapper() {
    }

    public static UserDto toUserDto(final User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    public static User toUser(final UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        return user;
        /*
        return User.builder()
                .id(userDto.getId())
                .name(userDto.getName())
                .email(userDto.getEmail())
                .build();

         */
    }
}
