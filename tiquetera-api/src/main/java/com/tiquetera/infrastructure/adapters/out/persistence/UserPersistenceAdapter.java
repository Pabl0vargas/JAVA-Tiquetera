package com.tiquetera.infrastructure.adapters.out.persistence;

import com.tiquetera.domain.model.User;
import com.tiquetera.domain.ports.out.UserRepositoryPort;
import com.tiquetera.infrastructure.adapters.out.persistence.mapper.UserPersistenceMapper;
import com.tiquetera.infrastructure.adapters.out.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter implements UserRepositoryPort {

    private final UserRepository userRepository;
    private final UserPersistenceMapper userMapper;

    @Override
    public User save(User user) {
        return userMapper.toDomain(userRepository.save(userMapper.toEntity(user)));
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username).map(userMapper::toDomain);
    }
}