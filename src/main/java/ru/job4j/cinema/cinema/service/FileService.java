package ru.job4j.cinema.cinema.service;

import ru.job4j.cinema.cinema.dto.FileDto;

import java.util.Collection;
import java.util.Optional;

public interface FileService {
    Optional<FileDto> getFileById(int id);

    Collection<FileDto> findAll();
}
