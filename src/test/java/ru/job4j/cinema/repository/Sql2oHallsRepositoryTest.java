package ru.job4j.cinema.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.cinema.configuration.DatasourceConfiguration;
import ru.job4j.cinema.cinema.repository.Sql2oFilmSessionsRepository;
import ru.job4j.cinema.cinema.repository.Sql2oHallsRepository;
import ru.job4j.cinema.cinema.model.Halls;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import java.util.Properties;

public class Sql2oHallsRepositoryTest {

    private static Sql2oHallsRepository sql2oHallsRepository;

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

        sql2oHallsRepository = new Sql2oHallsRepository(sql2o);
    }

    @Test
    public void  getTicketFBI() {
        Halls halls = new Halls(1, "Hall 1", 10, 50, "Hall 3d");
        var hallsOptional = sql2oHallsRepository
                .findById(1);
        assertThat(hallsOptional.get()).usingRecursiveComparison().isEqualTo(halls);
    }
}
