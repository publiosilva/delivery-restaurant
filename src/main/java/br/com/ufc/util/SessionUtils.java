package br.com.ufc.util;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SessionUtils {

	public static String getCurrentUsername() {
		Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		return ((UserDetails) auth).getUsername();
	}

}
