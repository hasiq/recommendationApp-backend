package com.hasikowski.demo.service;

import com.hasikowski.demo.model.*;
import com.hasikowski.demo.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public ResponseEntity<List<GameEntity>> findById(Long id){
        if(!this.gameRepository.existsById(id)){
            return new ResponseEntity<>(HttpStatusCode.valueOf(400));
        }
        else {
            GameEntity game = this.gameRepository.findById(id).get();
            List<GameEntity> list = new ArrayList<>();
            list.add(game);
            return new ResponseEntity<>(list ,HttpStatusCode.valueOf(200));
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

    public ResponseEntity<List<GameRecommendDto>> recomendGames(RecommendDto recommend){
        List<GameEntity> list = gameRepository.findAll();
        List<GameRecommendDto> list1 = new ArrayList<>();
        for (GameEntity g : list){
           double number = compare(g.getGenre(),recommend.getGenres());
               list1.add(new GameRecommendDto(g.getId(), g.getName(), number));
        }
        if(list1.isEmpty())
        {
            return new ResponseEntity<>(HttpStatusCode.valueOf(204));
        }
        CustomComparator sort = new CustomComparator();
        list1.sort(sort);
        int size = Math.min(list1.size(), recommend.getLimit());
        list1 = list1.subList(0, size);
        return new ResponseEntity<>(list1,HttpStatusCode.valueOf(200));
    }

    public double compare(List<GenreEntity> genres, List<String> compare){
        List<Double> v1 = new ArrayList<>();
        List<Double> v2 = new ArrayList<>();
        Set<String> union = new HashSet<>();
        List<String> genre = new ArrayList<>();
        Double sum = 0D;
        for(GenreEntity g : genres){
            union.add(g.getName());
            genre.add(g.getName());
        }


        for (int i =0; i < compare.size(); i++){
            union.add(compare.get(i));
        }

        for(String u : union)
        {
                if(genre.contains(u)){
                    v1.add(1D);
                }
                else {
                    v1.add(0D);
                }
                if(compare.contains(u)){
                    v2.add(1D);
                }
                else {
                    v2.add(0D);
                }
        }
        for (int i = 0; i < v1.size(); i++){
            sum += Math.pow(v2.get(i) - v1.get(i), 2);
        }
        return Math.sqrt(sum);
    }

    public List<GameEntity> findAllSortedPaged(Integer pageNo, Integer pageSize, String sortBy){
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<GameEntity> pagedResult = gameRepository.findAll(paging);

        if(pagedResult.hasContent()){
            return  pagedResult.getContent();
        }
        else {
            return  new ArrayList<GameEntity>();
        }
    }

    public List<GameEntity> findByName(String name){
        return gameRepository.findGameEntitiesByNameContaining(name);
    }
}
