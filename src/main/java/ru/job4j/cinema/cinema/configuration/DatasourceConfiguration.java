package ru.job4j.cinema.cinema.configuration;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.sql2o.Sql2o;
import org.sql2o.converters.Converter;
import org.sql2o.converters.ConverterException;
import org.sql2o.quirks.NoQuirks;
import org.sql2o.quirks.Quirks;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Класс конфигурирования
 * @author Buslaev
 */
@Configuration
public class DatasourceConfiguration {

    /**
     * Создаем обьект пула соединения DataSource.
     * В качестве его реализации выступает класс BasicDataSource.
     * @param url - jdbc.url
     * @param username - jdbc.username
     * @param password - jdbc.password
     * @return - пул соединения с БД..
     */
    @Bean
    public DataSource connectionPool(@Value("${jdbc.url}") String url,
                                     @Value("${jdbc.username}") String username,
                                     @Value("${jdbc.password}") String password) {
        return new BasicDataSource() {
            {
                setUrl(url);
                setUsername(username);
                setPassword(password);
            }
        };
    }

    /**
     * создает экземпляр Sql2o.
     * Sql2o это клиент для работы с БД, который позволяет писать меньше boiler-plate кода.
     * В целом это небольшая ORM. ORM это Object Relation Mapping.
     * @param dataSource - пул соединения с БД.
     * @return - экземпляр Sql2o.
     */
    @Bean
    public Sql2o databaseClient(DataSource dataSource) {
        return new Sql2o(dataSource, createConverters());
    }

    /**
     * Cоздает конвертер, который делает преобразование из Timestamp в LocalDateTime и наоборот.
     * Конвертер будет делать преобразования вместо того, чтобы каждый раз дублировать логику.
     * Этот конвертер будет использоваться Sql2o.
     * @return
     */
    private Quirks createConverters() {
        return new NoQuirks() {
            {
                converters.put(LocalDateTime.class, new Converter<LocalDateTime>() {

                    @Override
                    public LocalDateTime convert(Object value) throws ConverterException {
                        if (value == null) {
                            return null;
                        }
                        if (!(value instanceof Timestamp)) {
                            throw new ConverterException("Invalid value to convert");
                        }
                        return ((Timestamp) value).toLocalDateTime();
                    }

                    @Override
                    public Object toDatabaseParam(LocalDateTime value) {
                        return value == null ? null : Timestamp.valueOf(value);
                    }

                });
            }
        };
    }

}

