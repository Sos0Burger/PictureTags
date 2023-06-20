package com.sosoburger.back.rest.controller;

import com.sosoburger.back.BackApplication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.File;
import java.nio.file.Files;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = BackApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:applicationtest.properties")
public class PictureControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    void upload() {

        File file = new File("src/test/resources/testimage.jpg");
        try {
            byte[] bytes = Files.readAllBytes(file.toPath());

            MockMultipartFile multipartFile = new MockMultipartFile("picture", "testimage.jpg", MediaType.MULTIPART_FORM_DATA_VALUE, bytes);

            final MvcResult result = mvc.perform(
                            multipart("/picture").file(multipartFile))
                    .andExpect(status().isCreated())
                    .andReturn();

            final String json = result
                    .getResponse()
                    .getContentAsString()
                    .replaceAll("(?s),\"tags\":\\[.*]","");

            System.out.println(json);

            final String expected = "{id:1, pictureURL:'http://localhost/picture/data/1', name:'testimage.jpg'}";

            JSONAssert.assertEquals(expected, json, true);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getAll() {
        try {
            //todo как-то по другому проверять
            mvc.perform(get("/picture").param("search","")).andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}