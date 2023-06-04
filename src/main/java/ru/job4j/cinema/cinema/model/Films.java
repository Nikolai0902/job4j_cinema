package ru.job4j.cinema.cinema.model;

import java.util.Map;
import java.util.Objects;

public class Films {

    public static final Map<String, String> COLUMN_MAPPING = Map.of(
            "id", "id",
            "name", "name",
            "description", "description",
            "year", "year",
            "genre_id", "genreId",
            "minimal_age", "minimalAge",
            "duration_in_minutes", "durationInMinutes",
            "file_id", "fileId"
    );

    private int id;
    private String name;
    private String description;
    private int year;
    private int genreId;
    private int minimalAge;
    private int durationInMinutes;
    private int fileId;

    public Films() {
    }

    public class Builder {

        private Builder() {
        }

        public Builder id(int id) {
            Films.this.id = id;
            return this;
        }

        public Builder name(String name) {
            Films.this.name = name;
            return this;
        }

        public Builder description(String description) {
            Films.this.description = description;
            return this;
        }

        public Builder year(int year) {
            Films.this.year = year;
            return this;
        }

        public Builder genreId(int genreId) {
            Films.this.genreId = genreId;
            return this;
        }

        public Builder minimalAge(int minimalAge) {
            Films.this.minimalAge = minimalAge;
            return this;
        }

        public Builder durationInMinutes(int durationInMinutes) {
            Films.this.durationInMinutes = durationInMinutes;
            return this;
        }

        public Builder fileId(int fileId) {
            Films.this.fileId = fileId;
            return this;
        }

        public Films build() {
            return Films.this;
        }
    }

    public static Builder builder() {
        return new Films().new Builder();
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    public int getMinimalAge() {
        return minimalAge;
    }

    public void setMinimalAge(int minimalAge) {
        this.minimalAge = minimalAge;
    }

    public int getDurationInMinutes() {
        return durationInMinutes;
    }

    public void setDurationInMinutes(int durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Films films = (Films) o;
        return id == films.id && year == films.year
                && genreId == films.genreId && durationInMinutes == films.durationInMinutes
                && fileId == films.fileId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, year, genreId, durationInMinutes, fileId);
    }
}
