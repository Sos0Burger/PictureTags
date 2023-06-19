package com.sosoburger.back.rest.controller;

import com.sosoburger.back.BackApplication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

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
        mvc.perform(post("/picture").contentType(MediaType.MULTIPART_FORM_DATA).content());
    }

    @Test
    void getAll() {
    }
}