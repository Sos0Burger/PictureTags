package org.sosoburger.component;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.Scroller;
import lombok.Getter;
import lombok.Setter;
import org.sosoburger.dto.TagDTO;

import java.util.List;

@Getter
@Setter
public class PictureRow {
    Image image;
    Scroller scroller;

    public PictureRow(String imageUrl, List<TagDTO> tags){
        image = new Image();
        image.setSrc(imageUrl);

        scroller = new Scroller();
        Div div = new Div();

        for (TagDTO tag: tags
             ) {
            div.add(new Div(new Label(tag.getTag().getRu()+":"+tag.getConfidence().intValue()+"%")));
        }

        scroller.setContent(div);
    }
}
