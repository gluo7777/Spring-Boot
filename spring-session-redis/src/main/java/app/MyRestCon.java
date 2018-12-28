package app;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MyRestCon {
	@GetMapping("/info")
	public String getInfo(HttpSession session) {
		String key = "firstGetInfoAccessTime";
		LocalDateTime firstGetInfoAccessTime = Optional.ofNullable(session.getAttribute(key)).map(LocalDateTime.class::cast).orElse(LocalDateTime.now());
		session.setAttribute(key, firstGetInfoAccessTime);
		return String.format("You last accessed this method on '%s'!",firstGetInfoAccessTime.toString());
	}
}
