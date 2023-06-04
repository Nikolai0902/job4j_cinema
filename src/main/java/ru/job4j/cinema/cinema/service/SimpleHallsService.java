package ru.job4j.cinema.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.cinema.repository.HallsRepository;
import ru.job4j.cinema.cinema.model.Halls;

import java.util.Optional;

@Service
public class SimpleHallsService implements HallsService {

    public final HallsRepository sql2oHallsRepository;

    public SimpleHallsService(HallsRepository sql2oHallsRepository) {
        this.sql2oHallsRepository = sql2oHallsRepository;
    }

    @Override
    public Optional<Halls> findById(int id) {
        return sql2oHallsRepository.findById(id);
    }
}
