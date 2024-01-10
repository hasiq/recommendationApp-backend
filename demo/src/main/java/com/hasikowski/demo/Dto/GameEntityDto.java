package com.hasikowski.demo.Dto;

import com.hasikowski.demo.model.GenreEntity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GameEntityDto {

    private Long id;


    private String name;

    private String description;

    private  String author;

    private List<String> genre;


    private String releaseDate;

    private String steamLink;
}
