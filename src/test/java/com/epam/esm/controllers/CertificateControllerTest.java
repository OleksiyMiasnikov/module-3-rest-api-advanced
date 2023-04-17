package com.epam.esm.controllers;

import com.epam.esm.controllers.advice.ModuleExceptionHandler;
import com.epam.esm.models.Certificate;
import com.epam.esm.services.CertificateService;
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

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CertificateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    CertificateService service;

    @InjectMocks
    CertificateController subject;
    private Certificate certificate1;
    private Certificate certificate2;
    private Certificate certificate3;

    {
        certificate1 = Certificate.builder()
                .id(1)
                .name("certificate 1")
                .description("description of certificate 1")
                .price(15.5)
                .duration(5)
                .build();
        certificate2 = Certificate.builder()
                .id(2)
                .name("certificate 2")
                .description("description of certificate 2")
                .price(21.0)
                .duration(10)
                .build();
        certificate3 = Certificate.builder()
                .id(3)
                .name("certificate 3")
                .description("description of certificate 3")
                .price(150d)
                .duration(14)
                .build();
    }
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(subject)
                .setControllerAdvice(new ModuleExceptionHandler())
                .build();
    }

    @Test
    void findAllTest() throws Exception {
        List<Certificate> list = new LinkedList<>(List.of(certificate1, certificate2, certificate3));
        String expected1 = "{\"id\":1," +
                "\"name\":\"certificate 1\"," +
                "\"description\":\"description of certificate 1\"," +
                "\"price\":15.5," +
                "\"duration\":5,";
        String expected2 = "{\"id\":2," +
                "\"name\":\"certificate 2\"," +
                "\"description\":\"description of certificate 2\"," +
                "\"price\":21.0," +
                "\"duration\":10,";
        String expected3 = "{\"id\":3," +
                "\"name\":\"certificate 3\"," +
                "\"description\":\"description of certificate 3\"," +
                "\"price\":150.0," +
                "\"duration\":14,";
        when(service.findAll()).thenReturn(list);

        this.mockMvc.perform(get("/certificates"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(expected1)))
                .andExpect(content().string(containsString(expected2)))
                .andExpect(content().string(containsString(expected3)));
    }

    @Test
    void createTest() throws Exception {
        String expected = "{\"id\":5," +
                "\"name\":\"new certificate\"," +
                "\"description\":\"description of new certificate\"," +
                "\"price\":153.45," +
                "\"duration\":8";
        String bodyContent = "{\n" +
                "    \"name\": \"new certificate\",\n" +
                "    \"description\": \"description of new certificate\",\n" +
                "    \"price\": 153.45,\n" +
                "    \"duration\": 8\n" +
                "}";
        Certificate expectedCertificate = Certificate.builder()
                .id(5)
                .name("new certificate")
                .description("description of new certificate")
                .price(153.45)
                .duration(8)
                .build();

        when(service.create(any(Certificate.class))).thenReturn(expectedCertificate);

        this.mockMvc.perform(post("/certificates")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(bodyContent))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(expected)));
    }

    @Test
    void findByIdTest() throws Exception {
        String expected = "{\"id\":1," +
                "\"name\":\"certificate 1\"," +
                "\"description\":\"description of certificate 1\"," +
                "\"price\":15.5," +
                "\"duration\":5,";

        when(service.findById(1)).thenReturn(certificate1);

        this.mockMvc.perform(get("/certificates/{id}", 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(expected)));
    }

    @Test
    void updateTest() throws Exception {
        String expected = "{\"id\":1," +
                "\"name\":\"new name of certificate\"," +
                "\"description\":\"description of certificate 1\"," +
                "\"price\":222.45," +
                "\"duration\":5";
        String bodyContent = "{\n" +
                "    \"name\": \"new name of certificate\",\n" +
                "    \"price\": 222.45\n" +
                "}";
        Certificate expectedCertificate = Certificate.builder()
                .id(1)
                .name("new name of certificate")
                .description("description of certificate 1")
                .price(222.45)
                .duration(5)
                .build();

        when(service.update(any(Integer.class),any(Certificate.class))).thenReturn(expectedCertificate);

        this.mockMvc.perform(patch("/certificates/{id}", 1)
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(bodyContent))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(expected)));
    }

    @Test
    void deleteTest() throws Exception {
        when(service.delete(any(Integer.class))).thenReturn(Boolean.TRUE);

        this.mockMvc.perform(delete("/certificates/{id}",1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }
}
