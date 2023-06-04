package ru.job4j.cinema.cinema.repository;

import ru.job4j.cinema.cinema.model.Genres;

import java.util.Optional;

public interface GenresRepository {
    Optional<Genres> findById(int id);
}
