package com.hasikowski.demo.controller;

import com.hasikowski.demo.Dto.GameEntityDto;
import com.hasikowski.demo.model.GameEntity;
import com.hasikowski.demo.Dto.GameRecommendDto;
import com.hasikowski.demo.Dto.RecommendDto;
import com.hasikowski.demo.service.GameService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }



    @PostMapping("/games")
    public ResponseEntity<GameEntity> addGame(@RequestBody GameEntityDto game){
        return this.gameService.addGame(game);
    }

    @DeleteMapping("/games/{id}")
    public ResponseEntity<GameEntity> deleteGame(@PathVariable Long id, @RequestHeader("Authorization") String token){
        return this.gameService.deleteGame(id, token);
    }

    @GetMapping("/games/{id}")
    public ResponseEntity<List<GameEntity>> getById(@PathVariable Long id){
        return this.gameService.findById(id);
    }

    @PutMapping("/games/{id}")
    public ResponseEntity<GameEntity> editGame(@PathVariable Long id, @RequestBody GameEntityDto game, @RequestHeader("Authorization") String token){
        return this.gameService.editGame(token,id,game);
    }



    @PostMapping("/games/recommend")
    public ResponseEntity<List<GameRecommendDto>> recommendGames(@RequestBody RecommendDto recommendDto){
        return this.gameService.recommendGames(recommendDto);
    }

    @GetMapping("/games")
    public ResponseEntity<List<GameEntity>> getAllGames(@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "name") String sortBy){
        List<GameEntity> list = gameService.findAllSortedPaged(pageNo,pageSize,sortBy);

        return new ResponseEntity<List<GameEntity>>(list, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/games/name")
    public ResponseEntity<List<GameEntity>> getByName(@RequestParam String name){
        return this.gameService.findByName(name);
    }

    @GetMapping("/count")
    public Long countAllGames(){
        return gameService.countGames();
    }


    @PostMapping("/favorite/{id}")
    public ResponseEntity<GameEntity> addFavoriteGames(@PathVariable Long id, @RequestHeader(value = "Authorization") String token){
        return gameService.addFavoriteGames(id, token);
    }


    @GetMapping("/favorite")
    public ResponseEntity<List<GameEntity>> getFavoriteGames(@RequestHeader(value = "Authorization") String token){
        return gameService.getFavoriteGames(token);
    }

    @DeleteMapping("/favorite/{id}")
    public ResponseEntity<GameEntity> deleteFavoriteGame(@PathVariable Long id, @RequestHeader(value = "Authorization") String token){
        return gameService.deleteFavoriteGames(id, token);
    }
}
