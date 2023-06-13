package org.sosoburger.views;

import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.json.JSONObject;
import org.sosoburger.api.PictureApiImpl;
import org.sosoburger.dto.PictureDTO;
import org.sosoburger.dto.TagDTO;

import java.io.IOException;

@Route("uploaded")
public class UploadedPictures extends VerticalLayout {

    private final Grid<PictureDTO> grid = new Grid<>();
    PictureApiImpl pictureApi = new PictureApiImpl();
    Div text = new Div();

    public UploadedPictures() {
        setSizeFull();
        add(grid, text);
        setUploadedPictures();
    }

    private void setUploadedPictures() {
        text.removeAll();
        try {
            var response = pictureApi.getAll().execute();

            if (response.isSuccessful()) {
                grid.setItems(response.body());

                grid.addComponentColumn(picture ->
                                new Scroller(
                                new Image(
                                picture.getPictureURL(),
                                "Не удалось загрузить изображение")
                        ))
                        .setHeader("Изображение")
                        .setWidth("512px")
                        .setTextAlign(ColumnTextAlign.CENTER);

                grid.addComponentColumn(picture -> {
                    Grid<TagDTO> tagGrid = new Grid<>();
                    tagGrid.setItems(picture.getTags());

                    tagGrid.addComponentColumn(tagDTO -> new Label(tagDTO.getTag().getRu()))
                            .setHeader("Тег")
                            .setComparator(tagDTO -> tagDTO.getTag().getRu());
                    tagGrid.addComponentColumn(tagDTO -> new Label(tagDTO.getConfidence().intValue()+"%"))
                            .setHeader("Точность").setComparator(TagDTO::getConfidence);

                    return tagGrid;
                })
                        .setHeader("Теги").
                        setAutoWidth(true)
                        .setTextAlign(ColumnTextAlign.CENTER);
            } else {
                if (response.errorBody() != null) {
                    text.add(new Label(new JSONObject(response.errorBody().string()).getString("message")));
                } else {
                    text.add(new Label("Ошибка подключения"));
                }
            }
        } catch (IOException e) {
            text.add(new Label("Произошла ошибка"));
        }

    }
}
