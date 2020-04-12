package com.sbouhaddi.jwt.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sbouhaddi.jwt.models.CustomUser;

public interface CustomUserRepository extends JpaRepository<CustomUser, Long> {

	CustomUser findByUserName(String username);
}
