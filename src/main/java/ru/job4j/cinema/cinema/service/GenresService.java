package ru.job4j.cinema.cinema.service;

import ru.job4j.cinema.cinema.model.Genres;

import java.util.Optional;

public interface GenresService {
    Optional<Genres> findById(int id);
}
