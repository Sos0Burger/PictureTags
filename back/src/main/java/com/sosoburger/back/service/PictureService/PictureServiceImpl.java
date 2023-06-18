package com.sosoburger.back.service.PictureService;

import com.sosoburger.back.dao.PictureDAO;
import com.sosoburger.back.dto.PictureDTO;
import com.sosoburger.back.exception.NotFoundException;
import com.sosoburger.back.exception.UploadException;
import com.sosoburger.back.imagga.ImaggaApiImpl;
import com.sosoburger.back.imagga.ImaggaTagDTO;
import com.sosoburger.back.repository.PicturesRepository;
import com.sosoburger.back.repository.TagsRepository;
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
import java.util.ArrayList;
import java.util.List;

@Service
public class PictureServiceImpl implements PictureService {
    @Autowired
    private PicturesRepository picturesRepository;
    @Autowired
    private TagsRepository tagsRepository;

    private final ImaggaApiImpl imaggaApi = new ImaggaApiImpl();

    @SneakyThrows
    @Override
    public PictureDTO save(MultipartFile picture) {
        List<ImaggaTagDTO> imaggaTagDTOS = getTags(picture);

        List<Integer> tags = new ArrayList<>();
        for (var item:imaggaTagDTOS
             ) {
            tags.add(tagsRepository.save(item.toTagDAO()).getTag_id());
        }

        try {
            return picturesRepository.save(
                    new PictureDAO(
                            null,
                            tags,
                            picture.getBytes(),
                            picture.getContentType(),
                            picture.getOriginalFilename()
                    )
            ).toDTO(tagsRepository.findAllById(tags));
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
    public List<PictureDTO> findBySearch(String search) {
        List<PictureDAO> pictureDAOs;
        if (search==null||search.isEmpty()){
           pictureDAOs = picturesRepository.findAll(Sort.by(Sort.Direction.DESC, "pictureid"));
        }
        else {
            pictureDAOs = picturesRepository.findAll(Sort.by(Sort.Direction.DESC, "pictureid")).
                    stream()
                    .filter(
                            item ->
                                    item.getTags()
                                            .stream()
                                            .anyMatch(
                                                    tag -> tagsRepository.findById(tag).get().getTag_name().toLowerCase()
                                                            .contains(search.toLowerCase())
                                                            || (tagsRepository.findById(tag).get().getConfidence().intValue() + "%")
                                                            .contains(search)
                                            )
                    )
                    .toList();
        }
        List<PictureDTO> pictureDTOs = new ArrayList<>();
        for (var item: pictureDAOs
             ) {
            pictureDTOs.add(item.toDTO(tagsRepository.findAllById(item.getTags())));
        }
        return pictureDTOs;
    }
    @SneakyThrows
    private List<ImaggaTagDTO> getTags(MultipartFile picture) {
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
