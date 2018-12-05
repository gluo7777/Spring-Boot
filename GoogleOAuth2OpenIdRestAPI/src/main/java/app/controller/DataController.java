package app.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import app.Session;

/**
 * All methods in this API requires session to be authenticated
 * @author gluo7
 *
 */
@RestController
public class DataController {
	
	@Autowired
	Session session;
	
	@GetMapping("/test")
	public ResponseEntity<?> testAuthentication(HttpServletRequest request) {
		String sessionId = Session.getSessionIdFromRequest(request);
		if(String.valueOf(sessionId).equals(session.getSessionId()) && session.isAuthenticated()) {
			return ResponseEntity.status(HttpStatus.OK).body("You're Authenticated!");
		}else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You're not authenticated!");
		}
	}
}
