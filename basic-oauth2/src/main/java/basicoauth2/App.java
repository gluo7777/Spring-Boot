package basicoauth2;

import java.security.Principal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Based off of: https://spring.io/guides/tutorials/spring-boot-oauth2/ For
 * educational purposes only.
 * 
 * @author gluo7 localhost:8080 pre-registered client with Facebook, GitHub,
 *         etc. so will work for this port. $ curl
 *         https://start.spring.io/starter.tgz -d style=web -d name=simple | tar
 *         -xzvf -
 */
@SpringBootApplication
/**
 * Redirects to Facebook (or other OAuth provider). Can place on
 * WebSecurityConfigurerAdapter to customzie which paths to filter, otherwise
 * secures all paths. Manage Facebook Settings here:
 * https://developers.facebook.com Single Sign On: Means that at as long you
 * stay logged in, you only need to sign in once even if close and open in a
 * different tab. To Reset Login status, delete the JSESSIONID cookie value.
 */
@EnableOAuth2Sso
@RestController
public class App extends WebSecurityConfigurerAdapter {

	@RequestMapping("/user")
	public Principal user(Principal principal) {
		return principal;
	}

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// secure all end points
		http.antMatcher("/**").authorizeRequests() // enable access restriction
			.antMatchers("/","/login**","/webjars/**","/error**","/logout**") // allow unauthenticated access to only these endpoints (whitelist)
			.permitAll()
			.anyRequest() // all other requests must be authenticated
			.authenticated()
			// logout support and redirection
			.and().logout().logoutSuccessUrl("/").permitAll()
			// CSRF support
			.and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
			;
	}
}
