package com.epam.esm.model.DTO;

import com.epam.esm.model.DTO.certificate_with_tag.CertificateWithTagDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;


@Setter
@Getter
@Builder
public class PageDTO extends RepresentationModel<PageDTO> {
    private List<CertificateWithTagDTO> list;
}
