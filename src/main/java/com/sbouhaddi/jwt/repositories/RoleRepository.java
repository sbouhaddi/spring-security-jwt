package com.sbouhaddi.jwt.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sbouhaddi.jwt.models.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	Role findByName(String string);

}
