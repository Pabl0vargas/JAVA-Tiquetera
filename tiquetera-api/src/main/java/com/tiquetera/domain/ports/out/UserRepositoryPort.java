package com.tiquetera.domain.ports.out;

import com.tiquetera.domain.model.User;
import java.util.Optional;

public interface UserRepositoryPort {
    User save(User user);
    Optional<User> findByUsername(String username);
}