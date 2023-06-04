package ru.job4j.cinema.cinema.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.cinema.cinema.service.FileService;

/**
 * Контроллер. Файл
 * @author Buslaev
 */
@RestController
@RequestMapping("/files")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    /**
     * Взврвщает файл изображения в представление.
     * @param id файла в БД.
     * @return файл изображения.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getPhoto(@PathVariable int id) {
        var contentOptional = fileService.getFileById(id);
        if (contentOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(contentOptional.get().getPhoto());
    }
}
