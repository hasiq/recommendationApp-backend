package com.hasikowski.demo.controller;

import com.hasikowski.demo.model.GenreEntity;
import com.hasikowski.demo.service.GenreService;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GenreController {

    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/genres")
    public ResponseEntity<List<GenreEntity>> findAll(){
        return this.genreService.findAll();
    }


    @GetMapping("/genre")
    public ResponseEntity<GenreEntity> findByName(@Param(value = "name") String name){
        return this.genreService.findByName(name);
    }
}
