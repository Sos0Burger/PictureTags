package com.sosoburger.back.service.PictureService;

import com.sosoburger.back.dto.PictureDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface PictureService {
    PictureDTO save(MultipartFile picture) throws IOException;
}
