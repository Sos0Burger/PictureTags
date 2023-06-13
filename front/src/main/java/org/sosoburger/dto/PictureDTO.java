package org.sosoburger.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.sosoburger.component.PictureRow;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PictureDTO {

    private String pictureURL;
    private List<TagDTO> tags;

    public PictureRow toPictureRow(){
        return new PictureRow(pictureURL, tags);
    }
}
