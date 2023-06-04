package ru.job4j.cinema.cinema.service;

import ru.job4j.cinema.cinema.model.User;

import java.util.Optional;

public interface UserService {
    Optional<User> save(User user);

    Optional<User> findByEmailAndPassword(String email, String password);
}
