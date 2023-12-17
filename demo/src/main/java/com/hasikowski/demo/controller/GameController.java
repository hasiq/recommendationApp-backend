package com.hasikowski.demo.controller;

import com.hasikowski.demo.model.GameEntity;
import com.hasikowski.demo.service.GameService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/games")
    public List<GameEntity> getAllGames(){
        return this.gameService.findAll();
    }

    @PostMapping("/game")
    public GameEntity addGame(@RequestBody GameEntity game){
        return this.gameService.addGame(game);
    }

    @DeleteMapping("/games/{id}")
    public ResponseEntity<GameEntity> deleteGame(@PathVariable Long id){
        return this.gameService.deleteGame(id);
    }

    @GetMapping("/games/{id}")
    public ResponseEntity<GameEntity> getById(@PathVariable Long id){
        return this.gameService.findById(id);
    }

    @PutMapping("/games/{id}")
    public ResponseEntity<GameEntity> editGame(@PathVariable Long id, @RequestBody GameEntity game){
        return this.gameService.editGame(id,game);
    }
}
