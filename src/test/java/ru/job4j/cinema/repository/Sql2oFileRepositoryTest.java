package ru.job4j.cinema.repository;

import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.cinema.configuration.DatasourceConfiguration;
import ru.job4j.cinema.cinema.model.File;
import ru.job4j.cinema.cinema.repository.Sql2oFileRepository;
import ru.job4j.cinema.cinema.repository.Sql2oFilmSessionsRepository;

import java.util.Collection;
import java.util.List;
import java.util.Properties;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Sql2oFileRepositoryTest {

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

        sql2oFileRepository = new Sql2oFileRepository(sql2o);
    }

    @Test
    public void thenGetFilmsFBI() {
        File file = new File(1, "tirist", "files/1.JPG");
        var fileOptional = sql2oFileRepository.findById(file.getId());
        AssertionsForClassTypes.assertThat(fileOptional.get()).usingRecursiveComparison().isEqualTo(file);
    }

    @Test
    public void thenGetFilesFindAll() {
        File file1 = new File(1, "tirist", "files/1.JPG");
        File file2 = new File(2, "avatar", "files/2.JPG");
        File file3 = new File(3, "operation", "files/3.JPG");
        Collection<File> collection = List.of(file1, file2, file3);
        var fileCollection = sql2oFileRepository.findAll();
        assertThat(fileCollection).usingRecursiveComparison().isEqualTo(collection);
    }
}
