package ru.job4j.cinema.cinema.repository;

import ru.job4j.cinema.cinema.model.User;

import java.util.Collection;
import java.util.Optional;

/**
 * Интерфейс репозитория - пользователя.
 * @author Buslaev
 */
public interface UserRepository {

    Optional<User> save(User user);

    Optional<User> findByEmailAndPassword(String email, String password);

    Collection<User> findAll();

    boolean deleteById(int id);
}
