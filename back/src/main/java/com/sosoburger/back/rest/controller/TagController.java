package com.sosoburger.back.rest.controller;

import com.sosoburger.back.dto.TagDTO;
import com.sosoburger.back.rest.api.TagApi;
import com.sosoburger.back.service.TagService.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TagController implements TagApi {

    @Autowired
    private final TagService tagService;

    public TagController(TagService tagService){
        this.tagService = tagService;
    }
    @Override
    public ResponseEntity<TagDTO> updateTag(TagDTO tag) {
        return new ResponseEntity<>(tagService.updateTag(tag).toDTO(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> deleteTag(Integer pictureId, Integer tagId) {
        tagService.delete(pictureId, tagId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
