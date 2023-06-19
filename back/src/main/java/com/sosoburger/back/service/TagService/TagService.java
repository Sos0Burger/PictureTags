package com.sosoburger.back.service.TagService;

import com.sosoburger.back.dao.TagDAO;
import com.sosoburger.back.dto.TagDTO;

public interface TagService {
    TagDAO updateTag(TagDTO tag);
}
