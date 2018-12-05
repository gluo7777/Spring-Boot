package app.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import app.Session;

@Controller
public class SiteController {
	
	@Autowired
	Session session;
	
	@GetMapping("/")
	public String home(HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
		String sessionId = Session.getSessionIdFromRequest(servletRequest);
		if(sessionId == null || !sessionId.equals(session.getSessionId())) {
			this.session.clear();
			this.session.setSessionId(Session.generate());
		}
		Cookie cookie = new Cookie(Session.SESSION_ID, session.getSessionId());
		servletResponse.addCookie(cookie);
		return "index.html";
	}
}
