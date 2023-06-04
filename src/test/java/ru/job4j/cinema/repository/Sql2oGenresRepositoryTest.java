package ru.job4j.cinema.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.cinema.configuration.DatasourceConfiguration;
import ru.job4j.cinema.cinema.repository.Sql2oFilmSessionsRepository;
import ru.job4j.cinema.cinema.model.Genres;
import ru.job4j.cinema.cinema.repository.Sql2oGenresRepository;
import java.util.Properties;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Sql2oGenresRepositoryTest {

    private static Sql2oGenresRepository sql2oGenresRepository;

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

        sql2oGenresRepository = new Sql2oGenresRepository(sql2o);
    }

    @Test
    public void  getTicketFBI() {
        Genres genres = new Genres(2, "fantasy");
        var genresOptional = sql2oGenresRepository
                .findById(2);
        assertThat(genresOptional.get()).usingRecursiveComparison().isEqualTo(genres);
    }
}
