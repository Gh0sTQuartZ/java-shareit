package ru.practicum.shareit.user.dao;

import org.springframework.data.jpa.repository.*;
import ru.practicum.shareit.user.model.*;

public interface UserRepository extends JpaRepository<User, Long> {

}
