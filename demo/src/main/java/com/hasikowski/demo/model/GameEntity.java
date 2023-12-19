package com.hasikowski.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "games")
public class GameEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;


   private String name;

   private String description;

   private  String author;

   private List<String> genre;

   @Column(name = "Created")
   private String yearOfProduction;
}
