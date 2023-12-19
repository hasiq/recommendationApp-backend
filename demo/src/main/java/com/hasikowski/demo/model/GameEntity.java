package com.hasikowski.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "games")
public class GameEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;


   private String name;

   private String description;

   private  String author;

   private List<String> genre;


   private String releaseDate;

    public GameEntity(String name, String description, String author, List<String> genre, String releaseDate) {
        this.name = name;
        this.description = description;
        this.author = author;
        this.genre = genre;
        this.releaseDate = releaseDate;
    }
}
