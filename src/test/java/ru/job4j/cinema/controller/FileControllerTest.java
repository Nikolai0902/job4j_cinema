package ru.job4j.cinema.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import ru.job4j.cinema.cinema.controller.FileController;
import ru.job4j.cinema.cinema.dto.FileDto;
import ru.job4j.cinema.cinema.service.FileService;
import org.mockito.ArgumentCaptor;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FileControllerTest {

    private FileService fileService;
    private FileController fileController;
    private MultipartFile testFile;

    @BeforeEach
    public void initServices() {
        fileService = mock(FileService.class);
        fileController = new FileController(fileService);
        testFile = new MockMultipartFile("testFile.img", new byte[] {1, 2, 3});
    }

    @Test
    public void whenRequestFileIDThenGetPhotoFile() throws Exception  {
        var fileDto = new FileDto(testFile.getOriginalFilename(), testFile.getBytes());
        var idArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        when(fileService.getFileById(idArgumentCaptor.capture())).thenReturn(Optional.of(fileDto));

        var idFile = fileDto.getId();
        var view = fileController.getPhoto(idFile);

        assertThat(view).isEqualTo(ResponseEntity.ok(fileDto.getPhoto()));
    }
}
