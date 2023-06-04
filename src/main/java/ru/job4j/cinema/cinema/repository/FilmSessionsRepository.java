package ru.job4j.cinema.cinema.repository;

import ru.job4j.cinema.cinema.model.FilmSessions;
import java.util.Collection;
import java.util.Optional;

/**
 * Интерфейс репозитория - сессии фильма.
 * @author Buslaev
 */
public interface FilmSessionsRepository {

    Optional<FilmSessions> findById(int id);

    Collection<FilmSessions> findAll();

    boolean deleteById(int id);

    Optional<FilmSessions> addFilmSessions(FilmSessions filmSessions);
}
