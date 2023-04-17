package com.epam.esm.controller;

import com.epam.esm.controller.advice.ModuleExceptionHandler;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.service.TagService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TagControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Mock
    TagService service;
    @InjectMocks
    TagController subject;

    private final Tag tag1;
    private final Tag tag2;

    private final List<Tag> listOfTwoTags;

    {
        tag1 = Tag.builder()
                .id(1)
                .name("first tag")
                .build();
        tag2 = Tag.builder()
                .id(2)
                .name("second tag")
                .build();
        listOfTwoTags = new LinkedList<>(List.of(tag1, tag2));
    }

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(subject)
                .setControllerAdvice(new ModuleExceptionHandler())
                .build();
    }

    @Test
    void findAllTest() throws Exception {
        String expected = "[{\"id\":1,\"name\":\"first tag\"},{\"id\":2,\"name\":\"second tag\"}]";

        when(service.findByName("")).thenReturn(listOfTwoTags);

        this.mockMvc.perform(get("/tags"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(expected));

        verify(service).findByName("");
    }

    @Test
    void createTest() throws Exception {
        String bodyContent = "{\n \"name\": \"new tag\"\n}";
        Tag expectedTag = Tag.builder()
                .id(5)
                .name("new tag")
                .build();

        when(service.create(any(String.class))).thenReturn(expectedTag);

        this.mockMvc.perform(post("/tags")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bodyContent))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(expectedTag.getId()))
                .andExpect(jsonPath("$.name").value(expectedTag.getName()));

        verify(service).create("new tag");
    }

    @Test
    void findByIdTest() throws Exception {
        when(service.findById(any(Integer.class))).thenReturn(tag1);

        this.mockMvc.perform(get("/tags/{id}",1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(tag1.getId()))
                .andExpect(jsonPath("$.name").value(tag1.getName()));

        verify(service).findById(1);
    }

    @Test
    void findByNameTest() throws Exception{
        String expected = "[{\"id\":2,\"name\":\"second tag\"}]";
        when(service.findByName(any(String.class))).thenReturn(new LinkedList<>(List.of(tag2)));

        this.mockMvc.perform(get("/tags").param("name","first tag"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(expected));

        verify(service).findByName("first tag");
    }

    @Test
    void deleteTest() throws Exception {
        when(service.delete(any(Integer.class))).thenReturn(Boolean.TRUE);

        this.mockMvc.perform(delete("/tags/{id}",1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        verify(service).delete(1);
    }
}