package com.sosoburger.back.service.PictureService;

import com.sosoburger.back.dao.PictureDAO;
import com.sosoburger.back.dto.PictureDTO;
import com.sosoburger.back.imagga.ImaggaTagDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PictureService {
    PictureDTO save(MultipartFile picture) throws IOException;

    PictureDAO getPicture(Integer id);

    List<PictureDAO> findBySearch(String search);

}
