package ru.job4j.cinema.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.cinema.configuration.DatasourceConfiguration;
import ru.job4j.cinema.cinema.repository.Sql2oFileRepository;
import ru.job4j.cinema.cinema.repository.Sql2oFilmSessionsRepository;
import ru.job4j.cinema.cinema.model.Films;
import ru.job4j.cinema.cinema.repository.Sql2oFilmsRepository;
import ru.job4j.cinema.cinema.repository.Sql2oGenresRepository;
import java.util.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Sql2oFilmsRepositoryTest {

    private static Sql2oFilmsRepository sql2oFilmsRepository;
    private static Sql2oGenresRepository sql2oGenresRepository;
    private static Sql2oFileRepository sql2oFileRepository;

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

        sql2oFilmsRepository = new Sql2oFilmsRepository(sql2o);
    }

    @Test
    public void thenGetFilmsFBI() {
        Films films = Films.builder().id(1)
                .name("tirist")
                .description("an action movie about a spy")
                .year(2012)
                .genreId(1)
                .minimalAge(21)
                .durationInMinutes(180)
                .fileId(1).build();
        var filmsOptional = sql2oFilmsRepository.findById(films.getId());
        assertThat(filmsOptional.get()).usingRecursiveComparison().isEqualTo(films);
    }

    @Test
    public void thenGetFilmsFindAll() {
        Films films1 = Films.builder().id(1)
                .name("tirist")
                .description("an action movie about a spy")
                .year(2012)
                .genreId(1)
                .minimalAge(21)
                .durationInMinutes(180)
                .fileId(1).build();
        Films films2 = Films.builder().id(2)
                .name("avatar")
                .description("sci-fi movie")
                .year(2009)
                .genreId(2)
                .minimalAge(18)
                .durationInMinutes(120)
                .fileId(2).build();
        Films films3 = Films.builder().id(3)
                .name("operation")
                .description("comedy of the USSR")
                .year(1970)
                .genreId(3)
                .minimalAge(16)
                .durationInMinutes(120)
                .fileId(3).build();
        Collection<Films> collection = List.of(films1, films2, films3);
        var filmsCollection = sql2oFilmsRepository.findAll();
        assertThat(filmsCollection).usingRecursiveComparison().isEqualTo(collection);
    }
}
