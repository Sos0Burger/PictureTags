package org.sosoburger.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.virtuallist.VirtualList;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;
import org.json.JSONObject;
import org.sosoburger.api.PictureApiImpl;
import org.sosoburger.dto.PictureDTO;
import org.sosoburger.dto.TagDTO;
import org.sosoburger.validator.ConfidenceValidator;

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
                Editor<TagDTO> editor = tagGrid.getEditor();

                tagGrid.setAllRowsVisible(true);

                tagGrid.setItems(picture.getTags());

                Grid.Column<TagDTO> tagColumn = tagGrid.addComponentColumn(tagDTO -> new Label(tagDTO.getTag_name()))
                        .setHeader("Тег")
                        .setComparator(TagDTO::getTag_name).setAutoWidth(true);
                Grid.Column<TagDTO> confidenceColumn = tagGrid.addComponentColumn(tagDTO -> new Label(tagDTO.getStringConfidence()))
                        .setHeader("Точность").setComparator(TagDTO::getConfidence).setAutoWidth(true);

                Grid.Column<TagDTO> editColumn = tagGrid.addComponentColumn(tagDTO -> {
                    Button editButton = new Button("Редактировать");
                    editButton.addClickListener(e -> {
                        if (editor.isOpen())
                            editor.cancel();
                        tagGrid.getEditor().editItem(tagDTO);
                    });
                    return editButton;
                }).setAutoWidth(true);

                Grid.Column<TagDTO> deleteColumn = tagGrid.addComponentColumn(tagDTO -> {
                    Button deleteButton = new Button("Удалить");
                    deleteButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY,
                            ButtonVariant.LUMO_ERROR);
                    deleteButton.addClickListener(e -> {
                        if (editor.isOpen())
                            editor.cancel();

                    });
                    return deleteButton;
                }).setAutoWidth(true);

                Binder<TagDTO> binder = new Binder<>(TagDTO.class);
                editor.setBinder(binder);
                editor.setBuffered(true);

                TextField tagField = new TextField();
                tagField.setWidthFull();
                binder.forField(tagField)
                        .asRequired("Поле не должно быть пустым")
                        .bind(TagDTO::getTag_name, TagDTO::setTag_name);
                tagColumn.setEditorComponent(tagField);

                TextField confidenceField = new TextField();
                confidenceField.setWidthFull();
                binder.forField(confidenceField)
                        .asRequired("Поле не должно быть пустым")
                        .withValidator(new ConfidenceValidator("Введите корректное значение, например 69%"))
                        .bind(TagDTO::getStringConfidence, TagDTO::setConfidence);
                confidenceColumn.setEditorComponent(confidenceField);

                Button saveButton = new Button("Сохранить", e -> {
                    try {
                        var response = pictureApi.updateTag(
                                new TagDTO(
                                        editor.getItem().getId(),
                                        Float.parseFloat(confidenceField.
                                                getValue()
                                                .replace("%", "")),
                                        tagField.getValue())
                        )
                                .execute();
                        if (response.isSuccessful()){
                            editor.save();
                        }
                        else{
                            Notification notification =
                                    Notification.show(
                                            "Не удалось сохранить",
                                            500,
                                            Notification.Position.BOTTOM_CENTER);
                            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                        }
                    } catch (IOException ex) {
                        Notification notification =
                                Notification.show(
                                        "Не удалось сохранить",
                                        500,
                                        Notification.Position.BOTTOM_CENTER);
                        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                    }


                });
                Button cancelButton = new Button(VaadinIcon.CLOSE.create(),
                        e -> editor.cancel());
                cancelButton.addThemeVariants(ButtonVariant.LUMO_ICON,
                        ButtonVariant.LUMO_ERROR);
                HorizontalLayout actions = new HorizontalLayout(saveButton,
                        cancelButton);
                actions.setPadding(false);
                editColumn.setEditorComponent(actions);

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
