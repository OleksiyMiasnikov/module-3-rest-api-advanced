package com.epam.esm.controller;

import com.epam.esm.controller.advice.ModuleExceptionHandler;
import com.epam.esm.model.DTO.certificate.CertificateDTO;
import com.epam.esm.model.DTO.certificate.CreateCertificateRequest;
import com.epam.esm.model.entity.Certificate;
import com.epam.esm.service.CertificateService;
import com.epam.esm.service.mapper.CertificateMapper;
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

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class CertificateControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Mock
    private CertificateMapper mapper;

    @Mock
    CertificateService service;

    @InjectMocks
    CertificateController subject;
    private final Certificate certificate1;
    private final Certificate certificate2;
    private final Certificate certificate3;

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
        verify(service).findAll();
    }

    @Test
    void createTest() throws Exception {
        String bodyContent = """
                {
                    "name": "new certificate",
                    "description": "description of new certificate",
                    "price": 153.45,
                    "duration": 8
                }""";
        Certificate expectedCertificate = Certificate.builder()
                .id(5)
                .name("new certificate")
                .description("description of new certificate")
                .price(153.45)
                .duration(8)
                .build();
        CertificateDTO expectedDTO = CertificateDTO.builder()
                .id(5)
                .name("new certificate")
                .description("description of new certificate")
                .price(153.45)
                .duration(8)
                .build();

        when(service.create(any(CreateCertificateRequest.class))).thenReturn(expectedCertificate);
        when(mapper.toDTO(any(Certificate.class))).thenReturn(expectedDTO);

        this.mockMvc.perform(post("/certificates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bodyContent))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(expectedCertificate.getId()))
                .andExpect(jsonPath("$.name").value(expectedCertificate.getName()))
                .andExpect(jsonPath("$.description").value(expectedCertificate.getDescription()))
                .andExpect(jsonPath("$.price").value(expectedCertificate.getPrice()))
                .andExpect(jsonPath("$.duration").value(expectedCertificate.getDuration()));

        verify(service).create(any(CreateCertificateRequest.class));
    }

    @Test
    void findByIdTest() throws Exception {

        when(service.findById(1)).thenReturn(certificate1);

        this.mockMvc.perform(get("/certificates/{id}", 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(certificate1.getId()))
                .andExpect(jsonPath("$.name").value(certificate1.getName()))
                .andExpect(jsonPath("$.description").value(certificate1.getDescription()))
                .andExpect(jsonPath("$.price").value(certificate1.getPrice()))
                .andExpect(jsonPath("$.duration").value(certificate1.getDuration()));

        verify(service).findById(1);
    }

    @Test
    void updateTest() throws Exception {
        String bodyContent = """
                {
                    "name": "new name of certificate",
                    "price": 222.45
                }""";
        Certificate expectedCertificate = Certificate.builder()
                .id(1)
                .name("new name of certificate")
                .description("description of certificate 1")
                .price(222.45)
                .duration(5)
                .build();

        when(service.update(any(Integer.class),any(Certificate.class))).thenReturn(expectedCertificate);

        this.mockMvc.perform(patch("/certificates/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bodyContent))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(expectedCertificate.getId()))
                .andExpect(jsonPath("$.name").value(expectedCertificate.getName()))
                .andExpect(jsonPath("$.description").value(expectedCertificate.getDescription()))
                .andExpect(jsonPath("$.price").value(expectedCertificate.getPrice()))
                .andExpect(jsonPath("$.duration").value(expectedCertificate.getDuration()));

        verify(service).update(any(Integer.class), any(Certificate.class));
    }

    @Test
    void deleteTest() throws Exception {
        when(service.delete(any(Integer.class))).thenReturn(Boolean.TRUE);

        this.mockMvc.perform(delete("/certificates/{id}",1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        verify(service).delete(1);
    }
}
