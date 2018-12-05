package app.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import app.Session;
import app.security.ClientResponse;
import app.security.Google;
import app.security.TokenResponse;
import app.security.UserDetails;
import app.security.UserDetails2;

@RestController
@RequestMapping
public class AuthController {
	@Autowired
	Google google;
	@Autowired
	@Qualifier("securityTemplate")
	RestTemplate securityTemplate;
	@Autowired
	Session session;

	Logger log = LoggerFactory.getLogger(AuthController.class);

	@GetMapping("/login")
	public ResponseEntity<?> getAuthorization(HttpServletRequest servletRequest) {
		String sessionId = Session.getSessionIdFromRequest(servletRequest);
		if (sessionId != null && sessionId.equals(session.getSessionId())) {
			session.setCsrfToken(Session.generate());
			HttpHeaders parameters = buildAuthorizationHeaders(session.getCsrfToken());
			String requestUri = UriComponentsBuilder.fromHttpUrl(google.getUserAuthorizationUri())
					.queryParams(parameters).build().toUri().toString();
			log.info("Redirect URL={}", requestUri);
			return ResponseEntity.status(HttpStatus.FOUND).header(HttpHeaders.LOCATION, requestUri).build();
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

	@GetMapping("/access")
	public ResponseEntity<?> getAccessToken(@RequestParam Map<String, String> parameters, HttpServletResponse servletResponse) throws URISyntaxException {
		log.info("Received parameters={}", parameters);
		String csrfToken = Optional.ofNullable(parameters.get("state")).orElse("");
		if (csrfToken.equals(session.getCsrfToken())) {
			// make post to get access token
			String authorizationToken = Optional.ofNullable(parameters.get("code"))
					.orElseThrow(() -> new RuntimeException());
			RequestEntity<?> request = RequestEntity.post(new URI(google.getAccessTokenUri()))
					.header("content-type", MediaType.APPLICATION_FORM_URLENCODED_VALUE)
					.body(this.buildAccessTokenRequestString(authorizationToken));
			ResponseEntity<TokenResponse> response = this.securityTemplate.exchange(request, TokenResponse.class);
			if (response.getStatusCode().is2xxSuccessful()) {
				// parse json
				TokenResponse info = response.getBody();
				session.setAccessToken(String.format("%s %s", info.getToken_type(), info.getAccess_token()));
				// create authenticated session token and add to response cookies
				session.setSessionId(Session.generate());
				session.setAuthenticated(true);
				servletResponse.addCookie(new Cookie(Session.SESSION_ID, session.getSessionId()));
				// make call to retrieve user details
				request = RequestEntity.get(new URI(google.getUserInfoUri())).header("Authorization", session.getAccessToken()).build();
				ResponseEntity<UserDetails2> response2 = this.securityTemplate.exchange(request, UserDetails2.class);
				session.setDetails(response2.getBody());
				// return authenticated response
				ClientResponse clientResponse = new ClientResponse();
				clientResponse.setName(session.getDetails().getName());
				return ResponseEntity.status(HttpStatus.OK).body(clientResponse);
			}else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

	private HttpHeaders buildAuthorizationHeaders(String state) {
		HttpHeaders parameters = new HttpHeaders();
		parameters.set("client_id", google.getClientId());
		parameters.set("response_type", google.getResponseType());
		parameters.set("scope", google.getScope());
		parameters.set("redirect_uri", google.getRedirectUri());
		parameters.set("state", state);
		parameters.set("nonce", Session.generate());
		return parameters;
	}

	private String buildAccessTokenRequestString(String oneTimeAuthorizationCode) {
		return new StringBuilder().append("code=").append(oneTimeAuthorizationCode).append("&").append("client_id=")
				.append(google.getClientId()).append("&").append("client_secret=").append(google.getClientSecret())
				.append("&").append("redirect_uri=").append(google.getRedirectUri()).append("&").append("grant_type=")
				.append("authorization_code").toString();
	}
}
