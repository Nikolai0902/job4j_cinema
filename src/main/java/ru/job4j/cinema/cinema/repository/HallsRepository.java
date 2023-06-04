package ru.job4j.cinema.cinema.repository;

import ru.job4j.cinema.cinema.model.Halls;

import java.util.Optional;

public interface HallsRepository {
    Optional<Halls> findById(int id);
}
