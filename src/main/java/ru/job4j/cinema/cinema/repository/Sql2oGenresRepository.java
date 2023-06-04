package ru.job4j.cinema.cinema.repository;

import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;
import ru.job4j.cinema.cinema.model.Genres;

import java.util.Optional;

/**
 * Реализация репозитория - жанра.
 * @author Buslaev
 */
@Repository
public class Sql2oGenresRepository implements GenresRepository {

    private final Sql2o sql2o;

    public Sql2oGenresRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    /**
     * Поиск жанра в БД по id.
     * @param id id
     * @return жанр обернутая в Optional.
     */
    @Override
    public Optional<Genres> findById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM genres WHERE id = :id");
            var genres = query.addParameter("id", id).executeAndFetchFirst(Genres.class);
            return Optional.ofNullable(genres);
        }
    }
}
