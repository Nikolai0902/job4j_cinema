package ru.job4j.cinema.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.cinema.dto.FilmsDto;
import ru.job4j.cinema.cinema.model.Films;
import ru.job4j.cinema.cinema.repository.FilmsRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
public class SimpleFilmsService implements FilmsService {

    private final FilmsRepository sql2oFilmsRepository;
    private final SimpleGenresService simpleGenresService;

    public SimpleFilmsService(FilmsRepository sql2oFilmsRepository,
                              SimpleGenresService simpleGenresService) {
        this.sql2oFilmsRepository = sql2oFilmsRepository;
        this.simpleGenresService = simpleGenresService;
    }

    @Override
    public Optional<FilmsDto> findById(int id) {
        var optionalFilms = sql2oFilmsRepository.findById(id);
        if (optionalFilms.isEmpty()) {
            return Optional.empty();
        }
        return createFilmsDto(optionalFilms.get());
    }

    @Override
    public Collection<FilmsDto> findAll() {
        Collection<Films> collection = sql2oFilmsRepository.findAll();
        Collection<FilmsDto> dtoCollection = new ArrayList<>();
        for (Films col: collection) {
            Optional<FilmsDto> filmsDtoOpt = createFilmsDto(col);
            if (filmsDtoOpt.isEmpty()) {
                return dtoCollection;
            }
            dtoCollection.add(filmsDtoOpt.get());
        }
        return dtoCollection;
    }

    private Optional<FilmsDto> createFilmsDto(Films films) {
        var optionalGenres = simpleGenresService.findById(films.getGenreId());
        if (optionalGenres.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(FilmsDto.builder()
                .id(films.getId())
                .name(films.getName())
                .description(films.getDescription())
                .year(films.getYear())
                .genre(optionalGenres.get().getName())
                .minimalAge(films.getMinimalAge())
                .durationInMinutes(films.getDurationInMinutes())
                .file(films.getFileId())
                .build()
        );
    }
}
