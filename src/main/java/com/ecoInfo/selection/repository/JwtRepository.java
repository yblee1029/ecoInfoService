package com.ecoInfo.selection.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecoInfo.selection.domain.User;

@Repository
public interface JwtRepository extends JpaRepository<User, Long> {

	User findByUserid(String userId);
}
