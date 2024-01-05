package com.hasikowski.demo.service;

import com.hasikowski.demo.model.GenreEntity;
import com.hasikowski.demo.repository.GenreRepository;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@Service
public class GenreService {

    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public ResponseEntity<List<GenreEntity>> findAll(){
        return new ResponseEntity<>(genreRepository.findAll(), HttpStatusCode.valueOf(200));
    }
}
