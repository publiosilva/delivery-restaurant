package br.com.ufc.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.ufc.model.Role;
import br.com.ufc.model.User;
import br.com.ufc.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public void save(User user) {
		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));

		Role role = new Role();
		role.setId("ROLE_CLIENT");
		List<Role> roles = new ArrayList<Role>();
		roles.add(role);
		user.setRoles(roles);

		userRepository.save(user);
	}

	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

}
