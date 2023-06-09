package ru.job4j.cinema.cinema.service;

import ru.job4j.cinema.cinema.model.User;

import java.util.Optional;

/**
 * Интерфейс сервис - пользователь
 * @author Buslaev
 */
public interface UserService {
    Optional<User> save(User user);

    Optional<User> findByEmailAndPassword(String email, String password);
}
