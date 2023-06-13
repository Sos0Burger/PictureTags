package org.sosoburger.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.dom.DomEvent;
import com.vaadin.flow.dom.DomEventListener;
import com.vaadin.flow.internal.MessageDigestUtil;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import org.json.JSONObject;
import org.sosoburger.api.PictureApiImpl;
import org.sosoburger.dto.PictureDTO;
import org.sosoburger.dto.TagDTO;
import retrofit2.Response;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicReference;

@Route("")
public class UploadPicture extends VerticalLayout {

    private static final PictureApiImpl pictureApi = new PictureApiImpl();
    public UploadPicture() {
        this.setAlignItems(Alignment.CENTER);

        AtomicReference<MultipartBody.Part> multipartFile = new AtomicReference<>();

        MemoryBuffer buffer = new MemoryBuffer();
        Upload imageUpload = new Upload(buffer);
        Button uploadToServerButton = new Button("Загрузить");
        Div imageContainer = new Div();
        Div textContainer = new Div();
        Scroller scroller = new Scroller();

        imageUpload.setDropLabel(new Label("Перетащите изображение сюда"));
        imageUpload.setUploadButton(new Button("Выбрать файл"));
        imageUpload.setAcceptedFileTypes("image/*");
        imageUpload.addSucceededListener(event->{
            imageContainer.add(
                    imageComponent(
                    event.getMIMEType(),
                    event.getFileName(),
                    buffer.getInputStream()
            ));
            try {
               multipartFile.set(MultipartBody.Part.createFormData(
                       "picture",
                       event.getFileName(),
                       RequestBody.create(
                               MediaType.parse("image/*"), buffer.getInputStream().readAllBytes()
                       )
               ));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            uploadToServerButton.setEnabled(true);
        });
        imageUpload.getElement().addEventListener("file-remove", new DomEventListener() {
            @Override
            public void handleEvent(DomEvent arg0) {
                uploadToServerButton.setEnabled(false);
                imageContainer.removeAll();
                scroller.setContent(null);
                textContainer.removeAll();
            }
        });


        uploadToServerButton.addClickListener(buttonClickEvent ->
            uploadOnClick(multipartFile, scroller,textContainer)
        );


        uploadToServerButton.setEnabled(false);
        uploadToServerButton.setDisableOnClick(true);

        scroller.setScrollDirection(Scroller.ScrollDirection.VERTICAL);
        scroller.getStyle()
                .set("border-bottom", "1px solid var(--lumo-contrast-20pct)")
                .set("padding", "var(--lumo-space-m)");
        scroller.setHeight("128px");

        add(imageUpload, imageContainer, uploadToServerButton, textContainer, scroller);
    }

    private void uploadOnClick(AtomicReference<MultipartBody.Part> multipartFile, Scroller scroller, Div textContainer ){
        Response<PictureDTO> response;
        try {
            response = pictureApi.upload(multipartFile.get()).execute();
            if(response.isSuccessful()){
                Div div = new Div();
                for (TagDTO tag:response.body().getTags()
                ) {
                    div.add(new Div(new Label(tag.getTag().getRu()+":"+tag.getConfidence().intValue()+"%\n")));
                }
                scroller.setContent(div);
            }
            else if (response.errorBody()!=null){
                try {
                    textContainer.add(new Label(new JSONObject(response.errorBody().string()).getString("message")));
                } catch (IOException e) {
                    textContainer.add(new Label("Произошла ошибка"));
                }
            }
            else{
                textContainer.add(new Label("Ошибка подключения"));
            }
        } catch (IOException e) {
            textContainer.add(new Label("Произошла ошибка"));
        }
    }
    private Component imageComponent(String mimeType, String fileName, InputStream stream) {
        if (mimeType.startsWith("image")) {
            Image image = new Image();
            try {
                byte[] bytes = stream.readAllBytes();
                image
                        .getElement()
                        .setAttribute("src", new StreamResource(fileName, () -> new ByteArrayInputStream(bytes)));
                try (ImageInputStream in = ImageIO.createImageInputStream(new ByteArrayInputStream(bytes))) {
                    final Iterator<ImageReader> readers = ImageIO.getImageReaders(in);
                    if (readers.hasNext()) {
                        ImageReader reader = readers.next();
                        try {
                            reader.setInput(in);
                            image.setMaxWidth("100%");
                        } finally {
                            reader.dispose();
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return image;
        }
        Div content = new Div();
        String text = String.format(
                "Mime type: '%s'\nSHA-256 hash: '%s'",
                mimeType,
                Arrays.toString(MessageDigestUtil.sha256(stream.toString()))
        );
        content.setText(text);
        return content;
    }
}
