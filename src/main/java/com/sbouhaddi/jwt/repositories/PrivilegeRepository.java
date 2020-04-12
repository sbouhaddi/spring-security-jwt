package com.sbouhaddi.jwt.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sbouhaddi.jwt.models.Privilege;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

	Privilege findByName(String name);

}
