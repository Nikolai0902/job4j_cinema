package ru.job4j.cinema.cinema.repository;

import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;
import ru.job4j.cinema.cinema.model.FilmSessions;

import java.util.Collection;
import java.util.Optional;

/**
 * Реализация репозитория - сессии фильмов.
 * @author Buslaev
 */
@Repository
public class Sql2oFilmSessionsRepository implements FilmSessionsRepository {

    private final Sql2o sql2o;

    public Sql2oFilmSessionsRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    /**
     * Поиск сессии в БД по id.
     * @param id id
     * @return сессия обернутая в Optional.
     */
    @Override
    public Optional<FilmSessions> findById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM film_sessions WHERE id = :id");
            var filmSessions = query.setColumnMappings(FilmSessions.COLUMN_MAPPING)
                    .addParameter("id", id).executeAndFetchFirst(FilmSessions.class);
            return Optional.ofNullable(filmSessions);
        }
    }

    /**
     * Вывод коллекции сессии из БД.
     * @return коллекция сессии.
     */
    @Override
    public Collection<FilmSessions> findAll() {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM film_sessions");
            return query.setColumnMappings(FilmSessions.COLUMN_MAPPING).executeAndFetch(FilmSessions.class);
        }
    }

    /**
     * Удаление сессии в БД по id.
     * @param id id
     */
    @Override
    public boolean deleteById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("DELETE FROM film_sessions WHERE id = :id");
            query.addParameter("id", id);
            var result = query.executeUpdate().getResult();
            return result > 0;
        }
    }

    /**
     * Добавление новой сессии.
     * @param filmSessions filmSessions
     * @return добавленная сессия обернутая в Optional.
     */
    @Override
    public Optional<FilmSessions> addFilmSessions(FilmSessions filmSessions) {
        Optional<FilmSessions> filmSessionsOptional = Optional.empty();
        try (var connection = sql2o.open()) {
            var sql = """
                    INSERT INTO film_sessions(film_id, halls_id, start_time, end_time, price)
                    VALUES (:filmId, :hallsId, :startTime, :endTime, :price)
                    """;
            var query = connection.createQuery(sql, true)
                    .addParameter("filmId", filmSessions.getFilmId())
                    .addParameter("hallsId", filmSessions.getHallsId())
                    .addParameter("startTime", filmSessions.getStartTime())
                    .addParameter("endTime", filmSessions.getEndTime())
                    .addParameter("price", filmSessions.getPrice());
            int generatedId = query.executeUpdate().getKey(Integer.class);
            filmSessions.setId(generatedId);
            filmSessionsOptional = Optional.of(filmSessions);
        }
        return filmSessionsOptional;
    }
}
