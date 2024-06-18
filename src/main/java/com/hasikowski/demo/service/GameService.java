package com.hasikowski.demo.service;

import com.hasikowski.demo.Dto.GameEntityDto;
import com.hasikowski.demo.Dto.GameRecommendDto;
import com.hasikowski.demo.Dto.RecommendDto;
import com.hasikowski.demo.Dto.UserDto;
import com.hasikowski.demo.config.CustomComparator;
import com.hasikowski.demo.config.UserAuthProvider;
import com.hasikowski.demo.model.*;
import com.hasikowski.demo.repository.GameRepository;
import com.hasikowski.demo.repository.GenreRepository;
import com.hasikowski.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.*;

@Service
public class GameService  {

    private final GameRepository gameRepository;
    private final GenreRepository genreRepository;

    private final UserRepository userRepository;

    private final UserAuthProvider userAuthProvider;

    @Autowired
    public GameService(GameRepository gameRepository, GenreRepository genreRepository, UserRepository userRepository, UserAuthProvider userAuthProvider) {
        this.gameRepository = gameRepository;
        this.genreRepository = genreRepository;
        this.userRepository = userRepository;
        this.userAuthProvider = userAuthProvider;
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

    public ResponseEntity<GameEntity> addGame(GameEntityDto game){
        List<GameEntity> games = gameRepository.findByNameContainingIgnoreCase(game.getName());
        if(!games.isEmpty())
        {
            return  new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        if(game != null) {
            GameEntity gameEntity = new GameEntity(game.getName(), game.getDescription(), game.getAuthor(), getGenreFromDto(game), game.getReleaseDate(), game.getSteamLink());
            return new ResponseEntity<>(this.gameRepository.save(gameEntity), HttpStatusCode.valueOf(201));
        }
        else {
            return  new ResponseEntity<>(HttpStatusCode.valueOf(403));
        }
    }

    public ResponseEntity<GameEntity> deleteGame(Long id, @RequestHeader("Authorization") String token){
        UserEntity user = validateTokenAndReturnUser(token);
        if(!user.getRole().equals("admin"))
        {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
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

    public ResponseEntity<GameEntity> editGame(@RequestHeader("Authorization") String token, Long id, GameEntityDto game){
        UserEntity user = validateTokenAndReturnUser(token);
        if(!user.getRole().equals("admin"))
        {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        if(gameRepository.existsById(id)){
            GameEntity game1 = gameRepository.findById(id).get();
            game1.setName(game.getName());

            game1.setGenre(getGenreFromDto(game));
            game1.setAuthor(game.getAuthor());
            game1.setDescription(game.getDescription());
            game1.setSteamLink(game.getSteamLink());
            return new ResponseEntity<>(gameRepository.save(game1),HttpStatusCode.valueOf(202));
        }
        else{
            return new ResponseEntity<>(HttpStatusCode.valueOf(406));
        }
    }

    public ResponseEntity<List<GameEntity>> addGames(List<GameEntity> games){
        return new ResponseEntity<>(this.gameRepository.saveAll(games), HttpStatusCode.valueOf(201));
    }

    public ResponseEntity<List<GameRecommendDto>> recommendGames(RecommendDto recommend){
        List<GameEntity> list = gameRepository.findAll();
        List<GameRecommendDto> list1 = new ArrayList<>();
        if(recommend.getGenres().isEmpty())
        {
            return new ResponseEntity<>(null ,HttpStatusCode.valueOf(204));
        }
        for (GameEntity g : list){
           double number = 1 - compare(g.getGenre(),recommend.getGenres());
               list1.add(new GameRecommendDto(g.getId(), g.getName(), number, g.getReview()));
        }
        CustomComparator sort = new CustomComparator();
        list1.sort(sort);
//        Collections.reverse(list1);
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
            sum += (v2.get(i) * v1.get(i));
        }
        double val1 = norm(v1);
        double val2 = norm(v2);

        return sum / (val1 * val2);
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

    public ResponseEntity<List<GameEntity>> findByName(String name){
        if(name.equals(" ") || name.equals("") || gameRepository.findByNameContainingIgnoreCase(name).isEmpty())
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(gameRepository.findByNameContainingIgnoreCase(name), HttpStatus.OK);
    }

    public List<GenreEntity> getGenreFromDto(GameEntityDto game){
        List<GenreEntity> genres = new LinkedList<>();
        for (String g : game.getGenre()){
            genres.add(genreRepository.getGenreEntityByName(g));
        }
        return genres;
    }

    public Long countGames(){
        return gameRepository.count();
    }

    public UserEntity validateTokenAndReturnUser(String token){
        String substring = token.substring(7, token.length());
        Authentication authentication = userAuthProvider.validateToken(substring);

        UserDto user1 = (UserDto) authentication.getPrincipal();
        System.out.println(user1);

        UserEntity user = userRepository.findUserByFirstName(user1.getFirstName()).get();
        System.out.println(user);
        return user;
    }

    public Double norm(List<Double> vector){
        double sum = 0;
        for (Double d : vector){
            sum += d * d;
        }
        return Math.sqrt(sum);
    }

    public ResponseEntity<GameEntity> addFavoriteGames(Long id, String token){
        if(gameRepository.existsById(id)) {
            GameEntity byId = gameRepository.findById(id).get();
            UserEntity userEntity = validateTokenAndReturnUser(token);
            for (UserEntity u : byId.getUsers()){
                if(u.getFirstName().equals(userEntity.getFirstName()))
                {
                    return new ResponseEntity<>(HttpStatus.CONFLICT);
                }
            }
                byId.getUsers().add(userEntity);
                gameRepository.save(byId);

            return new ResponseEntity<>(HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<List<GameEntity>> getFavoriteGames(String token){
        UserEntity userEntity = validateTokenAndReturnUser(token);
        return new ResponseEntity<>(userEntity.getGames(),HttpStatus.OK);
    }

    public ResponseEntity<GameEntity> deleteFavoriteGames(Long id, String token){
        if(!gameRepository.existsById(id)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
       GameEntity game = gameRepository.findById(id).get();
           UserEntity userEntity = validateTokenAndReturnUser(token);
           for (int i = 0; i < game.getUsers().size(); i++) {
               if (game.getUsers().get(i).getFirstName().equals(userEntity.getFirstName())) {
                   game.getUsers().remove(i);
               }
           }
           gameRepository.save(game);
           return new ResponseEntity<>(HttpStatus.OK);
       }
}
