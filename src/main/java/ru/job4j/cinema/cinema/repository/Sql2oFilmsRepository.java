package ru.job4j.cinema.cinema.repository;

import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;
import ru.job4j.cinema.cinema.model.Films;

import java.util.Collection;
import java.util.Optional;

/**
 * Реализация репозитория - фильмов.
 * @author Buslaev
 */
@Repository
public class Sql2oFilmsRepository implements FilmsRepository {

    private final Sql2o sql2o;

    public Sql2oFilmsRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    /**
     * Поиск фильма в БД по id.
     * @param id id
     * @return фильм обернутая в Optional.
     */
    @Override
    public Optional<Films> findById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM films WHERE id = :id");
            var films = query.setColumnMappings(Films.COLUMN_MAPPING)
                    .addParameter("id", id).executeAndFetchFirst(Films.class);
            return Optional.ofNullable(films);
        }
    }

    /**
     * Вывод коллекции фильмов из БД.
     * @return коллекция фильмов.
     */
    @Override
    public Collection<Films> findAll() {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM films");
            return query.setColumnMappings(Films.COLUMN_MAPPING).executeAndFetch(Films.class);
        }
    }
}
