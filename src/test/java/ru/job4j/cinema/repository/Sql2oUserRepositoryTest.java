package ru.job4j.cinema.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.cinema.configuration.DatasourceConfiguration;
import ru.job4j.cinema.cinema.model.User;
import ru.job4j.cinema.cinema.repository.Sql2oFilmSessionsRepository;
import ru.job4j.cinema.cinema.repository.Sql2oUserRepository;
import java.util.Properties;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Sql2oUserRepositoryTest {

    private static Sql2oUserRepository sql2oUserRepository;

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

        sql2oUserRepository = new Sql2oUserRepository(sql2o);
    }

    @AfterEach
    public void clearUser() {
        var users = sql2oUserRepository.findAll();
        for (var user : users) {
            sql2oUserRepository.deleteById(user.getId());
        }
    }

    @Test
    public void whenSaveUserThenGetUser() {
        User user = new User(1, "Nik", "2323@mail", "5151");
        var userOptional = sql2oUserRepository.save(user);
        assertThat(userOptional.get()).usingRecursiveComparison().isEqualTo(user);
    }

    @Test
    public void whenSaveUserThenGetUserEmailAndPassword() {
        User user = new User(1, "Nik", "2323@mail", "5151");
        sql2oUserRepository.save(user);
        var userOptional = sql2oUserRepository
                .findByEmailAndPassword(user.getEmail(), user.getPassword());
        assertThat(userOptional.get()).usingRecursiveComparison().isEqualTo(user);
    }
}
