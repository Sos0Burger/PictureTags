package org.sosoburger.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
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

    TextField searchField = new TextField();

    Button searchButton = new Button("Найти");

    private final ComponentRenderer<Component, PictureDTO> pictureRenderer = new ComponentRenderer<>(
            picture -> {
                HorizontalLayout pictureLayout = new HorizontalLayout();
                pictureLayout.setMargin(true);
                pictureLayout.setAlignItems(Alignment.CENTER);
                pictureLayout.setDefaultVerticalComponentAlignment(Alignment.START);

                Image image = new Image(picture.getPictureURL(), "https://i.imgur.com/UWzk1sl.png");

                Details details = new Details(picture.getName(), image);
                details.getSummary().getStyle().set("font-size", "30px");
                details.getSummary().getStyle().set("color", "blue");
                pictureLayout.add(details);

                Grid<TagDTO> tagGrid = new Grid<>();
                tagGrid.setAllRowsVisible(true);


                tagGrid.setItems(picture.getTags());
                tagGrid.addComponentColumn(tagDTO -> new Label(tagDTO.getTag().getRu()))
                        .setHeader("Тег")
                        .setComparator(tagDTO -> tagDTO.getTag().getRu()).setAutoWidth(true);
                tagGrid.addComponentColumn(tagDTO -> new Label(tagDTO.getConfidence().intValue() + "%"))
                        .setHeader("Точность").setComparator(TagDTO::getConfidence).setAutoWidth(true);

                pictureLayout.add(tagGrid);
                return pictureLayout;
            });

    public UploadedPictures() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);

        searchField.setWidth("50%");
        searchField.setPlaceholder("Поиск");
        searchField.setPrefixComponent(new Icon(VaadinIcon.SEARCH));

        searchButton.addClickListener(event->setUploadedPictures(searchField.getValue()));

        HorizontalLayout searchLayout = new HorizontalLayout(searchField, searchButton);

        pictureList.setRenderer(pictureRenderer);



        add(searchLayout, pictureList, text);
        setUploadedPictures("");
    }

    private void setUploadedPictures(String search) {
        text.removeAll();
        try {
            var response = pictureApi.findBySearch(search).execute();
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
