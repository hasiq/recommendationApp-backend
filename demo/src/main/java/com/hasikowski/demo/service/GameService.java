package com.hasikowski.demo.service;

import com.hasikowski.demo.model.GameEntity;
import com.hasikowski.demo.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GameService  {

    private final GameRepository gameRepository;

    @Autowired
    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }


    public List<GameEntity> findAll(){
        return this.gameRepository.findAll();
    }

    public GameEntity addGame(GameEntity game){
        return this.gameRepository.save(game);
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
}
