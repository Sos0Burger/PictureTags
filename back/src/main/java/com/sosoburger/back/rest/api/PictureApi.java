package com.sosoburger.back.rest.api;

import com.sosoburger.back.dto.PictureDTO;
import com.sosoburger.back.dto.TagDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping("/picture")
public interface PictureApi {

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(description = "Загрузка файла на сервер для анализа")
    ResponseEntity<PictureDTO> upload(@RequestPart MultipartFile picture);

    @GetMapping
    @Operation(description = "Возращает все загруженные картики с тегами")
    ResponseEntity<List<PictureDTO>> getAll(@RequestParam String search);

    @GetMapping(value = "/data/{id}")
    ResponseEntity<byte[]> getPicture(@PathVariable Integer id);

    @PutMapping(value = "{id}")
    ResponseEntity<PictureDTO> updateTags(@PathVariable("id") Integer id, @RequestBody List<TagDTO> tagDTOs);

}
