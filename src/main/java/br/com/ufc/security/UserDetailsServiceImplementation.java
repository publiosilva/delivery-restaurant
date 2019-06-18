package br.com.ufc.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import br.com.ufc.repository.UserRepository;

@Repository
public class UserDetailsServiceImplementation implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		br.com.ufc.model.User user = userRepository.findByEmail(username);

		if (user == null) {
			throw new UsernameNotFoundException("User not found");
		}

		return new User(user.getUsername(), user.getPassword(), true, true, true, true, user.getAuthorities());
	}

}
