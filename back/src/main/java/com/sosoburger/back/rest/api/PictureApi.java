package com.sosoburger.back.rest.api;

import com.sosoburger.back.dto.PictureDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/picture")
public interface PictureApi {

    ResponseEntity<PictureDTO> upload(@RequestPart MultipartFile picture);
}
