package ru.job4j.cinema.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.cinema.dto.FileDto;
import ru.job4j.cinema.cinema.model.File;
import ru.job4j.cinema.cinema.repository.FileRepository;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
public class SimpleFileService implements FileService {

    private final FileRepository sql2oFileRepository;

    public SimpleFileService(FileRepository sql2oFileRepository) {
        this.sql2oFileRepository = sql2oFileRepository;
    }

    @Override
    public Optional<FileDto> getFileById(int id) {
        var fileOptional = sql2oFileRepository.findById(id);
        if (fileOptional.isEmpty()) {
            return Optional.empty();
        }
        var content = readFileAsBytes(fileOptional.get().getPath());
        return Optional.of(new FileDto(fileOptional.get().getName(), content));
    }

    private byte[] readFileAsBytes(String path) {
        try {
            return Files.readAllBytes(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Collection<FileDto> findAll() {
        Collection<File> collection = sql2oFileRepository.findAll();
        Collection<FileDto> dtoCollection = new ArrayList<>();
        for (File col: collection) {
            dtoCollection.add(new FileDto(col.getName(), readFileAsBytes(col.getPath())));
        }
        return dtoCollection;
    }
}
