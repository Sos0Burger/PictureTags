package com.sosoburger.back.rest.controller;

import com.sosoburger.back.dto.PictureDTO;
import com.sosoburger.back.rest.api.PictureApi;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public class PictureController implements PictureApi {
    @Override
    public ResponseEntity<PictureDTO> upload(MultipartFile picture) {
        return null; //todo
    }
}
