package com.epam.esm.service;

import com.epam.esm.model.entity.Tag;
import com.epam.esm.repository.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TagServiceTest {
    @Mock
    private TagRepository repo;
    private TagService subject;
    Tag tag;
    int id = 1;
    String name = "tag1";
    @BeforeEach
    void setUp(){
        subject = new TagService(repo);
        tag = Tag.builder()
                .id(id)
                .name(name)
                .build();
    }

    @Test
    void create() {
        when(repo.create(name)).thenReturn(id);
        when(repo.findById(id)).thenReturn(Optional.of(tag));
        Tag result = subject.create(name);
        assertThat(result).isEqualTo(tag);
    }

    @Test
    void findAll() {
        Tag tag2 = Tag.builder()
                .id(++id)
                .name("tag2")
                .build();
        when(repo.findAll()).thenReturn(List.of(tag2, tag, tag2));
        List<Tag> result = subject.findByName("");
        assertThat(result.size()).isEqualTo(3);
        assertThat(result).isEqualTo(List.of(tag2, tag, tag2));
    }

    @Test
    void findById() {
        when(repo.findById(id)).thenReturn(Optional.of(tag));
        Tag result = subject.findById(id);
        assertThat(result).isEqualTo(tag);
    }

    @Test
    void findByName() {
        when(repo.findByName(name)).thenReturn(List.of(tag));
        List<Tag> result = subject.findByName(name);
        assertThat(result.size()).isEqualTo(1);
        assertThat(result).isEqualTo(List.of(tag));
    }

    @Test
    void delete() {
        when(repo.delete(id)).thenReturn(true);
        assertThat(subject.delete(id)).isTrue();
    }
}