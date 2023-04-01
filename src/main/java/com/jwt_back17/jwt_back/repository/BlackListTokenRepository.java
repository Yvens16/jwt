package com.jwt_back17.jwt_back.repository;

import org.springframework.stereotype.Repository;
import com.jwt_back17.jwt_back.entity.BlackListTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface BlackListTokenRepository extends JpaRepository<BlackListTokenEntity, Long>{
  Boolean existsByToken(String token);
}
