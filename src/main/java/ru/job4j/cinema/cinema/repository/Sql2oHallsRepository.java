package ru.job4j.cinema.cinema.repository;

import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;
import ru.job4j.cinema.cinema.model.Halls;

import java.util.Optional;

/**
 * Реализация репозитория - зал.
 * @author Buslaev
 */
@Repository
public class Sql2oHallsRepository implements HallsRepository {

    private final Sql2o sql2o;

    public Sql2oHallsRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    /**
     * Поиск зала в БД по id.
     * @param id id
     * @return зал обернутый в Optional.
     */
    @Override
    public Optional<Halls> findById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM halls WHERE id = :id");
            var halls = query.setColumnMappings(Halls.COLUMN_MAPPING)
                    .addParameter("id", id).executeAndFetchFirst(Halls.class);
            return Optional.ofNullable(halls);
        }
    }
}
