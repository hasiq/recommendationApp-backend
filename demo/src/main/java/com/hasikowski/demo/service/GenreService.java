package com.hasikowski.demo.service;

import com.hasikowski.demo.model.GenreEntity;
import com.hasikowski.demo.repository.GenreRepository;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GenreService {

    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public ResponseEntity<List<GenreEntity>> findAll(){
        return new ResponseEntity<>(genreRepository.findAll().stream().sorted(Comparator.comparing(GenreEntity::getName)).toList(), HttpStatusCode.valueOf(200));
    }

    public ResponseEntity<GenreEntity> findByName(String name){
        String lowerCase = name.toLowerCase();
        return new ResponseEntity<>(genreRepository.getGenreEntityByName(lowerCase), HttpStatusCode.valueOf(200));
    }
}
