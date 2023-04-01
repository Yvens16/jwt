package com.jwt_back17.jwt_back.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jwt_back17.jwt_back.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
  Boolean existsByUsername(String username);
  //Boolean existsByEmail(String email);
  UserEntity findUserByUsername(String username);
  Optional<UserEntity> findByEmail(String email);

  Optional<UserEntity> findByUsername(String username);
}
