package ru.job4j.cinema.cinema.repository;

import ru.job4j.cinema.cinema.model.Films;

import java.util.Collection;
import java.util.Optional;

/**
 * Интерфейс репозитория - фильма.
 * @author Buslaev
 */
public interface FilmsRepository {

    Optional<Films> findById(int id);

    public Collection<Films> findAll();
}

