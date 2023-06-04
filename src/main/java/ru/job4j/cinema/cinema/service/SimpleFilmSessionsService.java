package ru.job4j.cinema.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.cinema.repository.FilmSessionsRepository;
import ru.job4j.cinema.cinema.dto.FilmSessionsDto;
import ru.job4j.cinema.cinema.model.FilmSessions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
public class SimpleFilmSessionsService implements FilmSessionsService {

    private final FilmSessionsRepository sql2oFilmSessionsRepository;
    private final FilmsService filmsService;

    public SimpleFilmSessionsService(FilmSessionsRepository sql2oFilmSessionsRepository,
                                     FilmsService filmsService) {
        this.sql2oFilmSessionsRepository = sql2oFilmSessionsRepository;
        this.filmsService = filmsService;
    }

    @Override
    public Optional<FilmSessionsDto> findById(int id) {
        var optionalFSDto = sql2oFilmSessionsRepository.findById(id);
        if (optionalFSDto.isEmpty()) {
            return Optional.empty();
        }
        return createFilmSessionDto(optionalFSDto.get());
    }

    @Override
    public Collection<FilmSessionsDto> findAll() {
        Collection<FilmSessions> collection = sql2oFilmSessionsRepository.findAll();
        Collection<FilmSessionsDto> dtoCollection = new ArrayList<>();
        for (FilmSessions col : collection) {
            Optional<FilmSessionsDto> filmSessionDto = createFilmSessionDto(col);
            if (filmSessionDto.isEmpty()) {
                return dtoCollection;
            }
            dtoCollection.add(filmSessionDto.get());
        }
        return dtoCollection;
    }

    private Optional<FilmSessionsDto> createFilmSessionDto(FilmSessions sessions) {
        var optionalFilms = filmsService.findById(sessions.getFilmId());
        if (optionalFilms.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(new FilmSessionsDto(
                sessions.getId(),
                sessions.getFilmId(),
                optionalFilms.get().getName(),
                sessions.getHallsId(),
                sessions.getStartTime(),
                sessions.getEndTime(),
                sessions.getPrice()
        ));
    }
}
