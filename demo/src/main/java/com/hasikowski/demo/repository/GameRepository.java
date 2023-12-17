package com.hasikowski.demo.repository;

import com.hasikowski.demo.model.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<GameEntity,Long> {

    @Override
    List<GameEntity> findAll();

    @Override
    <S extends GameEntity> S save(S entity);

    @Override
    void deleteById(Long id);

    @Override
    Optional<GameEntity> findById(Long id);
}
