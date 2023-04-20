package com.epam.esm.controller;

import com.epam.esm.controller.advice.ModuleExceptionHandler;
import com.epam.esm.model.DTO.SortingEntity;
import com.epam.esm.model.DTO.certificate_with_tag.CertificateWithTagDTO;
import com.epam.esm.model.DTO.certificate_with_tag.CertificateWithTagRequest;
import com.epam.esm.model.entity.CertificateWithTag;
import com.epam.esm.service.CertificateWithTagService;
import com.epam.esm.service.mapper.CertificateWithTagMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CertificateWithTagControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    CertificateWithTagService service;
    @Mock
    CertificateWithTagMapper mapper;

    @InjectMocks
    CertificateWithTagController subject;

    private final CertificateWithTag certificate1;
    private final CertificateWithTag certificate2;
    private final CertificateWithTag certificate3;

    {
        certificate1 = CertificateWithTag.builder()
                .tag("tag_1")
                .name("certificate 1")
                .description("description of certificate 1")
                .price(15.5)
                .duration(5)
                .build();
        certificate2 = CertificateWithTag.builder()
                .tag("tag_1")
                .name("certificate 2")
                .description("description of certificate 2")
                .price(21.0)
                .duration(10)
                .build();
        certificate3 = CertificateWithTag.builder()
                .tag("tag_2")
                .name("certificate 3")
                .description("description of certificate 1")
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
    void create() throws Exception {
        String bodyContent = """
                {
                    "tag": "new tag",
                    "name": "new certificate",
                    "description": "description of new certificate",
                    "price": 153.45,
                    "duration": 8
                }""";
        CertificateWithTagDTO expectedDTO = CertificateWithTagDTO.builder()
                .tag("new tag")
                .name("new certificate")
                .description("description of new certificate")
                .price(153.45)
                .duration(8)
                .build();
        CertificateWithTag expectedCertificate = CertificateWithTag.builder()
                .tag("new tag")
                .name("new certificate")
                .description("description of new certificate")
                .price(153.45)
                .duration(8)
                .build();

        when(service.create(any(CertificateWithTagRequest.class))).thenReturn(expectedCertificate);
        when(mapper.toDTO(any(CertificateWithTag.class))).thenReturn(expectedDTO);

        this.mockMvc.perform(post("/certificates_with_tags")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bodyContent))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tag").value(expectedCertificate.getTag()))
                .andExpect(jsonPath("$.name").value(expectedCertificate.getName()))
                .andExpect(jsonPath("$.description").value(expectedCertificate.getDescription()))
                .andExpect(jsonPath("$.price").value(expectedCertificate.getPrice()))
                .andExpect(jsonPath("$.duration").value(expectedCertificate.getDuration()));

        verify(service).create(any(CertificateWithTagRequest.class));
    }

    @Test
    @Disabled
    void findAll() throws Exception {
        SortingEntity sortingEntity = SortingEntity.builder()
                .sort_by("name")
                .direction("ASC")
                .build();
        List<CertificateWithTag> list = new LinkedList<>(List.of(certificate1, certificate2, certificate3));
        String expected1 = "\"tag\":\"tag_1\"," +
                "\"name\":\"certificate 1\"," +
                "\"description\":\"description of certificate 1\"," +
                "\"price\":15.5," +
                "\"duration\":5,";
        String expected2 = "\"tag\":\"tag_1\"," +
                "\"name\":\"certificate 2\"," +
                "\"description\":\"description of certificate 2\"," +
                "\"price\":21.0," +
                "\"duration\":10,";
        String expected3 = "\"tag\":\"tag_2\"," +
                "\"name\":\"certificate 3\"," +
                "\"description\":\"description of certificate 1\"," +
                "\"price\":150.0," +
                "\"duration\":14,";

        when(service.findAll(sortingEntity)).thenReturn(list);

        this.mockMvc.perform(get("/certificates_with_tags")
                        .param("field", sortingEntity.getSort_by())
                        .param("direction", sortingEntity.getDirection()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(expected1)))
                .andExpect(content().string(containsString(expected2)))
                .andExpect(content().string(containsString(expected3)));

        verify(service).findAll(sortingEntity);
    }

    @Test
    @Disabled
    void findByTagName() throws Exception {
        SortingEntity sortingEntity = SortingEntity.builder()
                .sort_by("name")
                .direction("ASC")
                .build();
        List<CertificateWithTag> list = new LinkedList<>(List.of(certificate1, certificate2));
        String expected1 = "\"tag\":\"tag_1\"," +
                "\"name\":\"certificate 1\"," +
                "\"description\":\"description of certificate 1\"," +
                "\"price\":15.5," +
                "\"duration\":5,";
        String expected2 = "\"tag\":\"tag_1\"," +
                "\"name\":\"certificate 2\"," +
                "\"description\":\"description of certificate 2\"," +
                "\"price\":21.0," +
                "\"duration\":10,";

        when(service.findByTagName("tag_1", sortingEntity)).thenReturn(list);

        this.mockMvc.perform(get("/certificates_with_tags/tag/{name}", "tag_1")
                        .param("field", sortingEntity.getSort_by())
                        .param("direction", sortingEntity.getDirection()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(expected1)))
                .andExpect(content().string(containsString(expected2)));

        verify(service).findByTagName("tag_1", sortingEntity);
    }

    @Test
    void findByPartOfNameOrDescription() throws Exception {
        List<CertificateWithTag> list = new LinkedList<>(List.of(certificate1, certificate3));
        String expected1 = "\"tag\":\"tag_1\"," +
                "\"name\":\"certificate 1\"," +
                "\"description\":\"description of certificate 1\"," +
                "\"price\":15.5," +
                "\"duration\":5,";
        String expected2 = "\"tag\":\"tag_2\"," +
                "\"name\":\"certificate 3\"," +
                "\"description\":\"description of certificate 1\"," +
                "\"price\":150.0," +
                "\"duration\":14,";
        CertificateWithTagDTO certificateDto1 = CertificateWithTagDTO.builder()
                .tag("tag_1")
                .name("certificate 1")
                .description("description of certificate 1")
                .price(15.5)
                .duration(5)
                .build();
        CertificateWithTagDTO certificateDto3 = CertificateWithTagDTO.builder()
                .tag("tag_2")
                .name("certificate 3")
                .description("description of certificate 1")
                .price(150d)
                .duration(14)
                .build();

        when(service.findByPartOfNameOrDescription("certificate 1")).thenReturn(list);
        when(mapper.toDTO(certificate1)).thenReturn(certificateDto1);
        when(mapper.toDTO(certificate3)).thenReturn(certificateDto3);

        this.mockMvc.perform(get("/certificates_with_tags//search/{pattern}",
                        "certificate 1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(expected1)))
                .andExpect(content().string(containsString(expected2)));

        verify(service).findByPartOfNameOrDescription("certificate 1");
    }
}