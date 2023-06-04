package ru.job4j.cinema.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.cinema.model.Genres;
import ru.job4j.cinema.cinema.repository.GenresRepository;

import java.util.Optional;

/**
 * Класс сервис - жанров
 * @author Buslaev
 */
@Service
public class SimpleGenresService implements GenresService {

    public final GenresRepository sql2oGenresRepository;

    public SimpleGenresService(GenresRepository sql2oGenresRepository) {
        this.sql2oGenresRepository = sql2oGenresRepository;
    }

    /**
     * поиск жанра по id.
     * @param id id
     * @return оьект жанра.
     */
    @Override
    public Optional<Genres> findById(int id) {
        return sql2oGenresRepository.findById(id);
    }
}
