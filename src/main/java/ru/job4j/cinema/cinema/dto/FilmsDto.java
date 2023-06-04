package ru.job4j.cinema.cinema.dto;

import java.util.Objects;

/**
 * Модель данных фильма DTO.
 * @author Buslaev
 */
public class FilmsDto {

    private int id;
    private String name;
    private String description;
    private int year;
    private String genre;
    private int minimalAge;
    private int durationInMinutes;
    private int fileId;

    public FilmsDto() {
    }

    public class Builder {

        private Builder() {
        }

        public Builder id(int id) {
            FilmsDto.this.id = id;
            return this;
        }

        public Builder name(String name) {
            FilmsDto.this.name = name;
            return this;
        }

        public Builder description(String description) {
            FilmsDto.this.description = description;
            return this;
        }

        public Builder year(int year) {
            FilmsDto.this.year = year;
            return this;
        }

        public Builder genre(String genre) {
            FilmsDto.this.genre = genre;
            return this;
        }

        public Builder minimalAge(int minimalAge) {
            FilmsDto.this.minimalAge = minimalAge;
            return this;
        }

        public Builder durationInMinutes(int durationInMinutes) {
            FilmsDto.this.durationInMinutes = durationInMinutes;
            return this;
        }

        public Builder file(int file) {
            FilmsDto.this.fileId = file;
            return this;
        }

        public FilmsDto build() {
            return FilmsDto.this;
        }
    }

    public static Builder builder() {
        return new FilmsDto().new Builder();
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

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
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
        FilmsDto filmsDto = (FilmsDto) o;
        return id == filmsDto.id && durationInMinutes == filmsDto.durationInMinutes
                && Objects.equals(genre, filmsDto.genre) && fileId == filmsDto.fileId;
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, genre, durationInMinutes);
        result = 31 * result + fileId;
        return result;
    }
}
