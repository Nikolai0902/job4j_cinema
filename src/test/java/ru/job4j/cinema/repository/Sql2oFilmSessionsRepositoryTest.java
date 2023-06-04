package ru.job4j.cinema.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.cinema.configuration.DatasourceConfiguration;
import ru.job4j.cinema.cinema.repository.Sql2oFilmSessionsRepository;
import ru.job4j.cinema.cinema.model.FilmSessions;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Properties;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Sql2oFilmSessionsRepositoryTest {

    private static Sql2oFilmSessionsRepository sql2oFilmSessionsRepository;

    @BeforeAll
    public static void initRepositories() throws Exception {
        var properties = new Properties();
        try (var inputStream = Sql2oFilmSessionsRepository.class.getClassLoader().getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        var sql2o = configuration.databaseClient(datasource);

        sql2oFilmSessionsRepository = new Sql2oFilmSessionsRepository(sql2o);
    }

    @AfterEach
    public void clearFilmSessions() {
        var filmSessions = sql2oFilmSessionsRepository.findAll();
        for (var filmSession : filmSessions) {
            sql2oFilmSessionsRepository.deleteById(filmSession.getId());
        }
    }

    @Test
    public void whenSaveFilmSessionsThenGetFilmSessions() {
        FilmSessions filmSessions = new FilmSessions(0, 1, 2,
                LocalDateTime.now(), LocalDateTime.now().plusHours(2), 400);
        var addFilmSessions = sql2oFilmSessionsRepository.addFilmSessions(filmSessions);
        assertThat(addFilmSessions.get()).usingRecursiveComparison().isEqualTo(filmSessions);
    }

    @Test
    public void thenGetFilmSessionsFBI() {
        FilmSessions filmSessions = new FilmSessions(0, 1, 2,
                LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES),
                LocalDateTime.now().plusHours(2).truncatedTo(ChronoUnit.MINUTES), 400);
        sql2oFilmSessionsRepository.addFilmSessions(filmSessions);
        var filmSessionsOptional = sql2oFilmSessionsRepository.findById(filmSessions.getId());
        assertThat(filmSessionsOptional.get()).usingRecursiveComparison().isEqualTo(filmSessions);
    }
}
