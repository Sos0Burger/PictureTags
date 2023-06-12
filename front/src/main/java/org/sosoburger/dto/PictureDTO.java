package org.sosoburger.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PictureDTO {

    private String pictureURL;
    private List<TagDTO> tags;

}
