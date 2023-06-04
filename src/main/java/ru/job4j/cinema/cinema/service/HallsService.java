package ru.job4j.cinema.cinema.service;

import ru.job4j.cinema.cinema.model.Halls;

import java.util.Optional;

public interface HallsService {
    Optional<Halls> findById(int id);
}
