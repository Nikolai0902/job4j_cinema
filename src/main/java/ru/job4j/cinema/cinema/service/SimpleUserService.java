package ru.job4j.cinema.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.cinema.model.User;
import ru.job4j.cinema.cinema.repository.UserRepository;

import java.util.Optional;

@Service
public class SimpleUserService implements UserService {

    public final UserRepository sql2oUserRepository;

    public SimpleUserService(UserRepository sql2UserRepository) {
        this.sql2oUserRepository = sql2UserRepository;
    }

    @Override
    public Optional<User> save(User user) {
        return sql2oUserRepository.save(user);
    }

    @Override
    public Optional<User> findByEmailAndPassword(String email, String password) {
        return sql2oUserRepository.findByEmailAndPassword(email, password);
    }
}
