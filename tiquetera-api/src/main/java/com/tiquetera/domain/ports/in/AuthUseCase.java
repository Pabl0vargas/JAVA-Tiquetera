package com.tiquetera.domain.ports.in;

import com.tiquetera.domain.model.User;

public interface AuthUseCase {
    User register(User user);
    String login(String username, String password);
}