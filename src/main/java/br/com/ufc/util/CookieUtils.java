package br.com.ufc.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtils {

	public static void setValue(HttpServletResponse response, String cookieName, String cookieValue) {
//		Sanitize cookie name
		cookieName = sanitizeCookieName(cookieName);

		Cookie cookie = new Cookie(cookieName, cookieValue);
		cookie.setPath("/");

		response.addCookie(cookie);
	}

	public static String getValue(HttpServletRequest request, String cookieName) {
//		Sanitize cookie name
		cookieName = sanitizeCookieName(cookieName);

		Cookie[] cookies = request.getCookies();

		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(cookieName)) {
				return cookie.getValue();
			}
		}

		return null;
	}

	private static String sanitizeCookieName(String cookieName) {
		return cookieName.replace("@", "");
	}

}
