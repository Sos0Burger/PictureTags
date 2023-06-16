package com.sosoburger.back.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PictureDTO {
    private Integer id;
    private String pictureURL;
    private String name;
    private List<TagDTO> tags;

}
