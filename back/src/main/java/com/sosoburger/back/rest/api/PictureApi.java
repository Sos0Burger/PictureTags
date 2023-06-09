package com.sosoburger.back.rest.api;

import com.sosoburger.back.dto.PictureDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/picture")
public interface PictureApi {

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(description = "Загрузка файла на сервер для анализа")
    ResponseEntity<PictureDTO> upload(@RequestPart MultipartFile picture);

    @GetMapping(value = "/{id}")
    ResponseEntity<byte[]> getPicture(@PathVariable Integer id);

}
