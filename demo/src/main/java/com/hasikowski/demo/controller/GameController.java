package com.hasikowski.demo.controller;

import com.hasikowski.demo.model.GameEntity;
import com.hasikowski.demo.model.GameRecommendDto;
import com.hasikowski.demo.model.RecommendDto;
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
    public ResponseEntity<List<GameEntity>> getAllGames(){
        return  this.gameService.findAll();
    }

    @PostMapping("/game")
    public ResponseEntity<GameEntity> addGame(@RequestBody GameEntity game){
        return this.gameService.addGame(game);
    }

    @DeleteMapping("/games/{id}")
    public ResponseEntity<GameEntity> deleteGame(@PathVariable Long id){
        return this.gameService.deleteGame(id);
    }

    @GetMapping("/games/{id}")
    public ResponseEntity<List<GameEntity>> getById(@PathVariable Long id){
        return this.gameService.findById(id);
    }

    @PutMapping("/games/{id}")
    public ResponseEntity<GameEntity> editGame(@PathVariable Long id, @RequestBody GameEntity game){
        return this.gameService.editGame(id,game);
    }

    @PostMapping("/games/multiple")
    public ResponseEntity<List<GameEntity>> addMultipleGames(@RequestBody List<GameEntity> gameEntities){
        return this.gameService.addGames(gameEntities);
    }

    @PostMapping("/games/recommend")
    public ResponseEntity<List<GameRecommendDto>> recommendGames(@RequestBody RecommendDto recommendDto){
        return this.gameService.recomendGames(recommendDto);
    }
}
