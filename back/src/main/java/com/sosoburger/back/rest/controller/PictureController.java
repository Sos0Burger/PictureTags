package com.sosoburger.back.rest.controller;

import com.sosoburger.back.dao.PictureDAO;
import com.sosoburger.back.dto.PictureDTO;
import com.sosoburger.back.rest.api.PictureApi;
import com.sosoburger.back.service.PictureService.PictureService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class PictureController implements PictureApi {
    @Autowired
    private final PictureService pictureService;

    public PictureController(PictureService pictureService){
        this.pictureService = pictureService;
    }
    @SneakyThrows
    @Override
    public ResponseEntity<PictureDTO> upload(MultipartFile picture) {
        return new ResponseEntity<>(pictureService.save(picture), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<byte[]> getPicture(Integer id) {
        PictureDAO pictureDAO = pictureService.getPicture(id);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", pictureDAO.getType());
        return new ResponseEntity<>(pictureDAO.getData(), responseHeaders, HttpStatus.OK);
    }
}
