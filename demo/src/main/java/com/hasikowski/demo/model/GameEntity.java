package com.hasikowski.demo.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "games")
public class GameEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;


   private String name;

   private String description;

   private  String author;

    @ManyToMany
    @JoinTable(
            name = "game_genre_relation",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    @JsonManagedReference
   private List<GenreEntity> genre;


   private String releaseDate;

   private String steamLink;

   private String review;

   @ManyToMany
   @JoinTable(
           name = "game_user_relation",
           joinColumns = @JoinColumn(name = "game_id"),
           inverseJoinColumns = @JoinColumn(name = "user_id")
   )
   @JsonManagedReference
   private List<UserEntity> users;


    public GameEntity(String name, String description, String author, List<GenreEntity> genre, String releaseDate, String steamLink) {
        this.name = name;
        this.description = description;
        this.author = author;
        this.genre = genre;
        this.releaseDate = releaseDate;
        this.steamLink = steamLink;
    }

    public GameEntity(String name, String description, String author, List<GenreEntity> genre, String releaseDate, String steamLink, String review) {
        this.name = name;
        this.description = description;
        this.author = author;
        this.genre = genre;
        this.releaseDate = releaseDate;
        this.steamLink = steamLink;
        this.review = review;
    }
}
