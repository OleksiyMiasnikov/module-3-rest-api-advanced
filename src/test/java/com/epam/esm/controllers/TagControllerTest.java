package com.epam.esm.controllers;

import com.epam.esm.exceptions.ModuleExceptionHandler;
import com.epam.esm.models.Tag;
import com.epam.esm.services.TagService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.matchers.JUnitMatchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    @AfterEach
    void tearDown() {
    }

    @Test
    void findAllTest() throws Exception {
        String expected = "[{\"id\":1,\"name\":\"first tag\"},{\"id\":2,\"name\":\"second tag\"}]";

        when(service.findAll()).thenReturn(listOfTwoTags);

        this.mockMvc.perform(get("/tags"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(expected)));
    }

    @Test
    void create() {
    }

    @Test
    void findByIdTest() throws Exception {
        String expected = "[{\"id\":1,\"name\":\"first tag\"}]";

        when(service.findById(any())).thenReturn(tag1);

        this.mockMvc.perform(get("/tags/{id}", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(expected)));
    }

    @Test
    void findByName() {
    }

    @Test
    void delete() {
    }
}