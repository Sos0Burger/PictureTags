package org.sosoburger.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.dom.DomEvent;
import com.vaadin.flow.dom.DomEventListener;
import com.vaadin.flow.internal.MessageDigestUtil;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Iterator;

@Route("")
public class UploadImage extends VerticalLayout {

    public UploadImage() {
        this.setAlignItems(Alignment.CENTER);

        MemoryBuffer buffer = new MemoryBuffer();
        Upload imageUpload = new Upload(buffer);
        Button uploadToServerButton = new Button("Загрузить");
        Div imageContainer = new Div();


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
           uploadToServerButton.setEnabled(true);
        });
        imageUpload.getElement().addEventListener("file-remove", new DomEventListener() {
            @Override
            public void handleEvent(DomEvent arg0) {
                uploadToServerButton.setEnabled(false);
                imageContainer.removeAll();
            }
        });


        uploadToServerButton.addClickListener(buttonClickEvent -> {
            //todo
        });
        uploadToServerButton.setEnabled(false);
        uploadToServerButton.setDisableOnClick(true);

        add(imageUpload, imageContainer, uploadToServerButton);
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
