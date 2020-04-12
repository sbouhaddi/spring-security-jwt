package com.sbouhaddi.jwt.bootstrap;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sbouhaddi.jwt.models.CustomUser;
import com.sbouhaddi.jwt.models.Privilege;
import com.sbouhaddi.jwt.models.Role;
import com.sbouhaddi.jwt.repositories.CustomUserRepository;
import com.sbouhaddi.jwt.repositories.PrivilegeRepository;
import com.sbouhaddi.jwt.repositories.RoleRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class BootstrapData implements CommandLineRunner {

	private final CustomUserRepository customUserRepository;

	private final RoleRepository roleRepository;

	private final PrivilegeRepository privilegeRepository;

	private final PasswordEncoder passwordEncoder;

	@Override
	public void run(String... args) throws Exception {

		if (customUserRepository.count() < 1) {

			Privilege readPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE");
			Privilege writePrivilege = createPrivilegeIfNotFound("WRITE_PRIVILEGE");

			List<Privilege> adminPrivileges = Arrays.asList(readPrivilege, writePrivilege);
			createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
			createRoleIfNotFound("ROLE_USER", Arrays.asList(readPrivilege));

			Role adminRole = roleRepository.findByName("ROLE_ADMIN");
			CustomUser user = new CustomUser();
			user.setUserName("root");
			user.setLastName("root");
			user.setPassword(passwordEncoder.encode("root"));
			user.setEmail("test@test.com");
			user.setRoles(Arrays.asList(adminRole));
			user.setEnabled(true);
			customUserRepository.save(user);
		}
		log.info("Saved Users " + customUserRepository.count());

	}

	@Transactional
	private Privilege createPrivilegeIfNotFound(String name) {

		Privilege privilege = privilegeRepository.findByName(name);
		if (privilege == null) {
			privilege = new Privilege();
			privilege.setName(name);
			privilegeRepository.save(privilege);
		}
		return privilege;
	}

	@Transactional
	private Role createRoleIfNotFound(String name, Collection<Privilege> privileges) {

		Role role = roleRepository.findByName(name);
		if (role == null) {
			role = new Role();
			role.setName(name);
			role.setPrivileges(privileges);
			roleRepository.save(role);
		}
		return role;
	}

}
