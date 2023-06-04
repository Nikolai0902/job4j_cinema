package ru.job4j.cinema.cinema.model;

import java.util.Objects;

/**
 * Модель данных жанра.
 * @author Buslaev
 */
public class Genres {
    private int id;
    private String name;

    public Genres() {
    }

    public Genres(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Genres genres = (Genres) o;
        return id == genres.id && Objects.equals(name, genres.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
