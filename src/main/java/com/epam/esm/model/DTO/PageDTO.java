package com.epam.esm.model.DTO;

import lombok.AllArgsConstructor;
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
