package ru.job4j.cinema.cinema.service;

import ru.job4j.cinema.cinema.dto.FilmSessionsDto;
import java.util.Collection;
import java.util.Optional;

/**
 * Интерфейс сервис - сессия
 * @author Buslaev
 */
public interface FilmSessionsService {

    Optional<FilmSessionsDto> findById(int id);

    Collection<FilmSessionsDto> findAll();
}
