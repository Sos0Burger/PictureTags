package org.sosoburger.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.virtuallist.VirtualList;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;
import org.json.JSONObject;
import org.sosoburger.api.PictureApiImpl;
import org.sosoburger.dto.PictureDTO;
import org.sosoburger.dto.TagDTO;

import java.io.IOException;

@Route("uploaded")
public class UploadedPictures extends VerticalLayout {

    PictureApiImpl pictureApi = new PictureApiImpl();

    VirtualList<PictureDTO> pictureList = new VirtualList<>();

    Div text = new Div();

    private final ComponentRenderer<Component, PictureDTO> pictureRenderer = new ComponentRenderer<>(
            picture -> {
                VerticalLayout pictureLayout = new VerticalLayout();
                pictureLayout.setMargin(true);
                pictureLayout.setAlignItems(Alignment.CENTER);

                Image image = new Image(picture.getPictureURL(), "https://i.imgur.com/UWzk1sl.png");
                pictureLayout.add(image);

                Grid<TagDTO> tagGrid = new Grid<>();
                tagGrid.setMinWidth("256px");
                tagGrid.setAllRowsVisible(true);

                tagGrid.setItems(picture.getTags());
                tagGrid.addComponentColumn(tagDTO -> new Label(tagDTO.getTag().getRu()))
                        .setHeader("Тег")
                        .setComparator(tagDTO -> tagDTO.getTag().getRu()).setAutoWidth(true);
                tagGrid.addComponentColumn(tagDTO -> new Label(tagDTO.getConfidence().intValue() + "%"))
                        .setHeader("Точность").setComparator(TagDTO::getConfidence).setAutoWidth(true);

                Details details = new Details("Теги", tagGrid);
                details.getSummary().getStyle().set("font-size", "30px");
                details.getSummary().getStyle().set("color", "blue");

                pictureLayout.add(details);
                return pictureLayout;
            });

    public UploadedPictures() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);

        pictureList.setRenderer(pictureRenderer);
        add(pictureList, text);
        setUploadedPictures();
    }

    private void setUploadedPictures() {
        text.removeAll();
        try {
            var response = pictureApi.getAll().execute();
            if (response.isSuccessful()) {
                pictureList.setItems(response.body());
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
