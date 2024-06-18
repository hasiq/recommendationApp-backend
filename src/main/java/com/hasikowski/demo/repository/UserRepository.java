package com.hasikowski.demo.repository;

import com.hasikowski.demo.model.GameEntity;
import com.hasikowski.demo.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findUserByLogin(String login);

    Optional<UserEntity> findUserByFirstName(String firstName);
}
