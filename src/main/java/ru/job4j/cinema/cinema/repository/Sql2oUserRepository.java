package ru.job4j.cinema.cinema.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;
import ru.job4j.cinema.cinema.model.User;

import java.util.Collection;
import java.util.Optional;

/**
 * Реализация репозитория - пользователь.
 * @author Buslaev
 */
@Repository
public class Sql2oUserRepository implements UserRepository {

    private final Sql2o sql2o;

    private static final Logger LOG = LoggerFactory.getLogger(Sql2oUserRepository.class.getName());

    public Sql2oUserRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    /**
     * Добавление пользователя.
     * @param user пользователь.
     * @return пользователь обернутый в Optional.
     */
    @Override
    public Optional<User> save(User user) {
        Optional<User> rsl = Optional.empty();
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("INSERT INTO users (full_name, email, password) "
                            + "VALUES (:fullName, :email, :password)", true)
                    .addParameter("fullName", user.getFullName())
                    .addParameter("email", user.getEmail())
                    .addParameter("password", user.getPassword());
            int generatedId = query.executeUpdate().getKey(Integer.class);
            user.setId(generatedId);
            rsl = Optional.of(user);
        } catch (Exception e) {
            LOG.error("Exception connection", e);
        }
        return rsl;
    }

    /**
     * Поиск пользователя.
     * @param email email.
     * @param password password.
     * @return пользователь обернутый в Optional.
     */
    @Override
    public Optional<User> findByEmailAndPassword(String email, String password) {
        Optional<User> rsl = Optional.empty();
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM users WHERE email = :email AND password = :password");
            query.addParameter("email", email);
            query.addParameter("password", password);
            var user = query.setColumnMappings(User.COLUMN_MAPPING).executeAndFetchFirst(User.class);
            rsl = Optional.ofNullable(user);
        } catch (Exception e) {
            LOG.error("Exception connection", e);
        }
        return rsl;
    }

    /**
     * Все пользователи.
     * @return пользователи.
     */
    @Override
    public Collection<User> findAll() {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM users");
            return query.setColumnMappings(User.COLUMN_MAPPING).executeAndFetch(User.class);
        }
    }

    /**
     * Удаление пользователя.
     * @param id id.
     */
    @Override
    public boolean deleteById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("DELETE FROM users WHERE id = :id");
            query.addParameter("id", id);
            var result = query.executeUpdate().getResult();
            return result > 0;
        }
    }
}
