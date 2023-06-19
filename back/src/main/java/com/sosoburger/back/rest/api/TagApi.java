package com.sosoburger.back.rest.api;

import com.sosoburger.back.dto.TagDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/tag")
public interface TagApi {
    @PutMapping
    @Operation(description = "Обновление тега")
    ResponseEntity<TagDTO> updateTag(@RequestBody TagDTO tag);

    @DeleteMapping
    @Operation(description = "Удаление тега")
    ResponseEntity<?> deleteTag(@RequestParam("pictureid") Integer pictureId, @RequestParam("tagid") Integer tagId);
}
