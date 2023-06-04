package ru.job4j.cinema.cinema.repository;


import ru.job4j.cinema.cinema.model.File;

import java.util.Collection;
import java.util.Optional;

public interface FileRepository {

    Optional<File> findById(int id);

    public Collection<File> findAll();
}
