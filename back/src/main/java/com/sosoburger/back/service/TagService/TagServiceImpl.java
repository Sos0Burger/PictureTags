package com.sosoburger.back.service.TagService;

import com.sosoburger.back.dao.TagDAO;
import com.sosoburger.back.dto.TagDTO;
import com.sosoburger.back.exception.NotFoundException;
import com.sosoburger.back.repository.TagsRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagServiceImpl implements TagService{

    @Autowired
    private TagsRepository tagsRepository;

    @SneakyThrows
    @Override
    public TagDAO updateTag(TagDTO tag) {
        if(tagsRepository.existsById(tag.getId())) {
            return tagsRepository.save(new TagDAO(tag.getId(), tag.getConfidence(), tag.getTag_name()));
        }
        throw new NotFoundException("ID не существует");
    }
}
