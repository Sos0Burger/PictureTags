package com.sosoburger.back.rest.api;

import com.sosoburger.back.dto.TagDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/tag")
public interface TagApi {
    @PutMapping
    @Operation(description = "Обновление тега")
    ResponseEntity<TagDTO> updateTag(@RequestBody TagDTO tag);
}
