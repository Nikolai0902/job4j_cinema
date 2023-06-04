package ru.job4j.cinema.cinema.service;

import ru.job4j.cinema.cinema.dto.FilmsDto;

import java.util.Collection;
import java.util.Optional;

public interface FilmsService {

    Optional<FilmsDto> findById(int id);

    Collection<FilmsDto> findAll();
}
