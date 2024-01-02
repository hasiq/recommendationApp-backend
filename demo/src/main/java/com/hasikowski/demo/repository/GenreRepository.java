package com.hasikowski.demo.repository;

import com.hasikowski.demo.model.GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenreRepository extends JpaRepository<GenreEntity,Long> {

    @Override
    List<GenreEntity> findAll();
}
