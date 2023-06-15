package com.sosoburger.back.service.PictureService;

import com.sosoburger.back.dao.PictureDAO;
import com.sosoburger.back.dto.PictureDTO;
import com.sosoburger.back.dto.TagDTO;
import com.sosoburger.back.exception.NotFoundException;
import com.sosoburger.back.exception.UploadException;
import com.sosoburger.back.imagga.ImaggaApiImpl;
import com.sosoburger.back.repository.PicturesRepository;
import lombok.SneakyThrows;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class PictureServiceImpl implements PictureService {
    @Autowired
    private PicturesRepository picturesRepository;

    private final ImaggaApiImpl imaggaApi = new ImaggaApiImpl();

    @SneakyThrows
    @Override
    public PictureDTO save(MultipartFile picture) {
        List<TagDTO> tagDTOs = getTags(picture);
        try {
            return picturesRepository.save(
                    new PictureDAO(
                            null,
                            tagDTOs,
                            picture.getBytes(),
                            picture.getContentType(),
                            picture.getOriginalFilename()
                    )
            ).toDTO();
        } catch (IOException exception) {
            throw new UploadException("Ошибка загрузки файла");
        }
    }

    @SneakyThrows
    @Override
    public PictureDAO getPicture(Integer id) {
        if (picturesRepository.existsById(id)) {
            return picturesRepository.findById(id).get();
        }
        throw new NotFoundException("Файл не найден");
    }

    @Override
    public List<PictureDAO> findBySearch(String search) {
        if (search==null||search.isEmpty()){
            return  picturesRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        }
        return picturesRepository.findAll(Sort.by(Sort.Direction.DESC,"id")).
                stream()
                .filter(
                        item->
                                item.getTags()
                                        .stream()
                                        .anyMatch(
                                                tag -> tag.getTag()
                                                        .getRu().toLowerCase()
                                                        .contains(search.toLowerCase())
                                                ||      (tag.getConfidence().intValue()+"%")
                                                        .contains(search)
                                        )
                )
                .toList();
    }


    @SneakyThrows
    private List<TagDTO> getTags(MultipartFile picture) {
        try {
            var response = imaggaApi.upload(
                    MultipartBody.Part.createFormData(
                            "image",
                            picture.getName(),
                            RequestBody.create(
                                    MediaType.parse("image/*"), picture.getBytes()
                            )
                    )
            ).execute();
            if (response.isSuccessful()) {
                return response.body().getResult().getTags();
            } else {
                JSONObject error = new JSONObject(response.errorBody().string()).getJSONObject("status");
                throw new UploadException(error.getString("text"));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new UploadException("Ошибка загрузки");
        }
    }
}
