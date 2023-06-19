package com.sosoburger.back.service.TagService;

import com.sosoburger.back.dao.PictureDAO;
import com.sosoburger.back.dao.TagDAO;
import com.sosoburger.back.dto.TagDTO;
import com.sosoburger.back.exception.NotFoundException;
import com.sosoburger.back.repository.PicturesRepository;
import com.sosoburger.back.repository.TagsRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagServiceImpl implements TagService{

    @Autowired
    private TagsRepository tagsRepository;

    @Autowired
    private PicturesRepository picturesRepository;

    @SneakyThrows
    @Override
    public TagDAO updateTag(TagDTO tag) {
        if(tagsRepository.existsById(tag.getId())) {
            return tagsRepository.save(new TagDAO(tag.getId(), tag.getConfidence(), tag.getTag_name()));
        }
        throw new NotFoundException("ID не существует");
    }

    @SneakyThrows
    @Override
    public void delete(Integer pictureId, Integer tagId) {
        if(tagsRepository.existsById(tagId)){
            if(picturesRepository.existsById(pictureId)){
                TagDAO tagDAO = tagsRepository.findById(tagId).get();
                PictureDAO pictureDAO = picturesRepository.findById(pictureId).get();
                if(pictureDAO.getTags().contains(tagDAO.getTag_id())){
                    pictureDAO.getTags().remove(tagDAO.getTag_id());
                    tagsRepository.delete(tagDAO);
                    picturesRepository.save(pictureDAO);
                    return;
                }
                throw new NotFoundException("Тега с таким ID у картики не сущетсвует");
            }
            throw new NotFoundException("ID картинки не сущетвует");
        }
        throw new NotFoundException("ID тега не существует");
    }
}
