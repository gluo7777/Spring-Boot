package basicoauth2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;

/**
 * Based off of: https://spring.io/guides/tutorials/spring-boot-oauth2/
 * For educational purposes only.
 * @author gluo7
 * localhost:8080 pre-registered client with Facebook, GitHub, etc. so will work for this port.
 * $ curl https://start.spring.io/starter.tgz -d style=web -d name=simple | tar -xzvf -
 */
@SpringBootApplication
/**
 * Redirects to Facebook (or other OAuth provider).
 * Can place on WebSecurityConfigurerAdapter to customzie which paths to filter, otherwise secures all paths.
 * Manage Facebook Settings here: https://developers.facebook.com
 * Single Sign On: Means that at as long you stay logged in, you only need to sign in once even if close and open in a different tab.
 * To Reset Login status, delete the JSESSIONID cookie value.
 */
@EnableOAuth2Sso
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

}
