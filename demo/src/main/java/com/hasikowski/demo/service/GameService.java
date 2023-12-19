package com.hasikowski.demo.service;

import com.hasikowski.demo.model.GameEntity;
import com.hasikowski.demo.repository.GameRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GameService  {

    private final GameRepository gameRepository;

    @Autowired
    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }


    public ResponseEntity<List<GameEntity>> findAll(){
        if(!this.gameRepository.findAll().isEmpty()) {
            return new ResponseEntity<>(this.gameRepository.findAll(), HttpStatusCode.valueOf(200));
        }
        else
        {
            return  new ResponseEntity<>(this.gameRepository.findAll(),HttpStatusCode.valueOf(204));
        }
    }

    public ResponseEntity<GameEntity> addGame(GameEntity game){
        if(game != null) {
            return new ResponseEntity<>(this.gameRepository.save(game), HttpStatusCode.valueOf(201));
        }
        else {
            return  new ResponseEntity<>(HttpStatusCode.valueOf(403));
        }
    }

    public ResponseEntity<GameEntity> deleteGame(Long id){
        if(!this.gameRepository.existsById(id)) {
            System.out.println("Nie istnieje taka gra");
            return new ResponseEntity<>(HttpStatusCode.valueOf(403));
        }
        else {
            this.gameRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatusCode.valueOf(204));
        }

    }

    public ResponseEntity<GameEntity> findById(Long id){
        if(!this.gameRepository.existsById(id)){
            return new ResponseEntity<>(HttpStatusCode.valueOf(400));
        }
        else {
            GameEntity game = this.gameRepository.findById(id).get();
            return new ResponseEntity<>(game ,HttpStatusCode.valueOf(200));
        }
    }

    public ResponseEntity<GameEntity> editGame(Long id, GameEntity game){
        if(gameRepository.existsById(id)){
            GameEntity game1 = gameRepository.findById(id).get();
            game1.setName(game.getName());
            game1.setGenre(game.getGenre());
            game1.setAuthor(game.getAuthor());
            game1.setDescription(game.getDescription());
            return new ResponseEntity<>(gameRepository.save(game1),HttpStatusCode.valueOf(202));
        }
        else{
            return new ResponseEntity<>(HttpStatusCode.valueOf(406));
        }
    }

    public ResponseEntity<List<GameEntity>> addGames(List<GameEntity> games){
        return new ResponseEntity<>(this.gameRepository.saveAll(games), HttpStatusCode.valueOf(201));
    }

    
}
