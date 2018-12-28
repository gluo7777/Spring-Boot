package app;

import java.math.BigInteger;
import java.security.SecureRandom;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import app.security.UserDetails2;

@Component
public class Session {

	private static final Logger LOG = LoggerFactory.getLogger(Session.class);

	public static final String SESSION_ID = "APPSESSION";
	private String sessionId;
	private boolean authenticated;
	private String csrfToken;
	private String accessToken;
	private UserDetails2 details;

	public final String getSessionId() {
		return sessionId;
	}

	public final void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public static final String getSessionIdFromRequest(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		LOG.info("Recieved cookies: {}", (Object[]) cookies);
		String sessionId = null;
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(Session.SESSION_ID)) {
					sessionId = cookie.getValue();
				}
			}
		}
		return sessionId;
	}

	public final String getCsrfToken() {
		return csrfToken;
	}

	public final void setCsrfToken(String csrfToken) {
		this.csrfToken = csrfToken;
	}

	public final String getAccessToken() {
		return accessToken;
	}

	public final void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public final boolean isAuthenticated() {
		return authenticated;
	}

	public final void setAuthenticated(boolean authenticated) {
		this.authenticated = authenticated;
	}

	public final UserDetails2 getDetails() {
		return details;
	}

	public final void setDetails(UserDetails2 details) {
		this.details = details;
	}

	public final void clear() {
		this.sessionId = null;
		this.csrfToken = null;
		this.accessToken = null;
		this.authenticated = false;
		this.details = null;
	}

	public static String generate() {
		return new BigInteger(130, new SecureRandom()).toString(32);
	}

}
