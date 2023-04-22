package com.epam.esm.model.DTO;

import com.epam.esm.model.DTO.certificate_with_tag.CertificateWithTagDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;


@Setter
@Getter
@AllArgsConstructor
public class PageDTO<T> extends RepresentationModel<PageDTO<T>> {
    private List<T> list;
}
