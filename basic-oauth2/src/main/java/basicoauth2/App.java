package basicoauth2;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.filter.CompositeFilter;

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
/* @EnableOAuth2Sso */
/**
 * EnableOAuth2Sso wraps around EnableOAuth2Client and lower level beans. It is
 * composed of 1)OAuth Client (EnableOAuth2Client) and 2) an authentication
 * piece which bridges 1) to rest of Spring Security EnableOAuth2Client
 * 
 * @author gluo7
 *
 */
@EnableOAuth2Client
@RestController
public class App extends WebSecurityConfigurerAdapter {

	/**
	 * Provided by EnableOAuth2Client
	 */
	@Autowired
	OAuth2ClientContext oauth2ClientContext;

	@RequestMapping("/user")
	public Principal user(Principal principal) {
		System.out.println(principal.getName());
		return principal;
	}

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		// secure all end points
		http.antMatcher("/**").authorizeRequests() // enable access restriction
				// all other requests must be authenticated
				// allow unauthenticated access to
				// only these endpoints
				// (whitelist)
				.antMatchers("/", "/login**", "/webjars/**", "/error**").permitAll().anyRequest()
				.authenticated().and().exceptionHandling()
				.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/"))
				// logout support and redirection
				.and().logout().logoutSuccessUrl("/").permitAll()
				.clearAuthentication(true)
				// CSRF support
				.and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
				// manually configure servlet authentication filter before basic filter
				.and().addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class);
		;
		// @formatter:on
	}

	/**
	 * Create a servlet filter as a container for multiple sub filters. One sub
	 * filter for each different OAuth2.0 provider.
	 * 
	 * @return
	 */
	private Filter ssoFilter() {
		/**
		 * A generic composite servlet Filter that just delegates its behaviorto a chain
		 * (list) of user-supplied filters, achieving the functionality of a
		 * FilterChain, but conveniently using only Filter instances.
		 */
		CompositeFilter filter = new CompositeFilter();
		List<Filter> filters = new ArrayList<>();
		filters.add(facebookFilter());
		filters.add(githubFilter());
		// TODO: add google filter
		filter.setFilters(filters);
		return filter;
	}

	private OAuth2ClientAuthenticationProcessingFilter facebookFilter() {
		// An OAuth2 client filter that can be used to acquire an OAuth2 access token
		// from an authorization server,
		// and load anauthentication object into the SecurityContext
		// note this creates an authentication end point at /login/facebook instead of
		// the usual /login, so need to change front end as well
		OAuth2ClientAuthenticationProcessingFilter facebookFilter = new OAuth2ClientAuthenticationProcessingFilter("/login/facebook");
		// Rest template that is able to make OAuth2-authenticated REST requests with
		// the credentials of the provided resource.
		// need this template for http requests to facebook (with client app
		// credentials) to obtain access token
		// Note that both the OAuth2ClientAuthenticationProcessingFilter and UserInfoTokenServices requires
		// facebookTemplate because they both need to make authenticated HTTP calls to retrieve something.
		OAuth2RestTemplate facebookTemplate = new OAuth2RestTemplate(facebook(), oauth2ClientContext);
		facebookFilter.setRestTemplate(facebookTemplate);
		// need this service for http requests to resource server???
		UserInfoTokenServices tokenServices = new UserInfoTokenServices(facebookResource().getUserInfoUri(),
				facebook().getClientId());
		tokenServices.setRestTemplate(facebookTemplate);
		facebookFilter.setTokenServices(tokenServices);
		return facebookFilter;
	}
	
	private OAuth2ClientAuthenticationProcessingFilter githubFilter() {
		// An OAuth2 client filter that can be used to acquire an OAuth2 access token
		// from an authorization server,
		// and load anauthentication object into the SecurityContext
		// note this creates an authentication end point at /login/facebook instead of
		// the usual /login, so need to change front end as well
		OAuth2ClientAuthenticationProcessingFilter githubFilter = new OAuth2ClientAuthenticationProcessingFilter("/login/github");
		// Rest template that is able to make OAuth2-authenticated REST requests with
		// the credentials of the provided resource.
		// need this template for http requests to facebook (with client app
		// credentials) to obtain access token
		OAuth2RestTemplate githubTemplate = new OAuth2RestTemplate(github(), oauth2ClientContext);
		githubFilter.setRestTemplate(githubTemplate);
		// need this service for http requests to resource server???
		UserInfoTokenServices tokenServices = new UserInfoTokenServices(githubResource().getUserInfoUri(),
				github().getClientId());
		tokenServices.setRestTemplate(githubTemplate);
		githubFilter.setTokenServices(tokenServices);
		return githubFilter;
	}
	
	private OAuth2ClientAuthenticationProcessingFilter googleFilter() {
		// An OAuth2 client filter that can be used to acquire an OAuth2 access token
		// from an authorization server,
		// and load anauthentication object into the SecurityContext
		// note this creates an authentication end point at /login/facebook instead of
		// the usual /login, so need to change front end as well
		OAuth2ClientAuthenticationProcessingFilter googleFilter = new OAuth2ClientAuthenticationProcessingFilter("/login/google");
		// Rest template that is able to make OAuth2-authenticated REST requests with
		// the credentials of the provided resource.
		// need this template for http requests to facebook (with client app
		// credentials) to obtain access token
		OAuth2RestTemplate googleTemplate = new OAuth2RestTemplate(google(), oauth2ClientContext);
		googleFilter.setRestTemplate(googleTemplate);
		// need this service for http requests to resource server???
		UserInfoTokenServices tokenServices = new UserInfoTokenServices(googleResource().getUserInfoUri(),
				google().getClientId());
		tokenServices.setRestTemplate(googleTemplate);
		googleFilter.setTokenServices(tokenServices);
		return googleFilter;
	}

	/**
	 * client registration with Facebook properties injected from environment
	 * 
	 * @return
	 */
	@Bean
	@ConfigurationProperties("github.client")
	public AuthorizationCodeResourceDetails github() {
		return new AuthorizationCodeResourceDetails();
	}

	/**
	 * Configuration properties for OAuth2 Resources. Overriding
	 * (prefix="security.oauth2.resource") to provide different types of resources
	 * of each oauth provider
	 * 
	 * @return
	 */
	@Bean
	@ConfigurationProperties("github.resource")
	public ResourceServerProperties githubResource() {
		return new ResourceServerProperties();
	}
	
	/**
	 * client registration with Facebook properties injected from environment
	 * 
	 * @return
	 */
	@Bean
	@ConfigurationProperties("facebook.client")
	public AuthorizationCodeResourceDetails facebook() {
		return new AuthorizationCodeResourceDetails();
	}

	/**
	 * Configuration properties for OAuth2 Resources. Overriding
	 * (prefix="security.oauth2.resource") to provide different types of resources
	 * of each oauth provider
	 * 
	 * @return
	 */
	@Bean
	@ConfigurationProperties("facebook.resource")
	public ResourceServerProperties facebookResource() {
		return new ResourceServerProperties();
	}
	

	@Bean
	@ConfigurationProperties("google.client")
	public AuthorizationCodeResourceDetails google() {
		return new AuthorizationCodeResourceDetails();
	}

	@Bean
	@ConfigurationProperties("google.resource")
	public ResourceServerProperties googleResource() {
		return new ResourceServerProperties();
	}

	/**
	 * Handling redirects from this app to Facebook Registered provided filter into
	 * application context
	 * 
	 * @param filter
	 *            this is pulled from the Application Context after adding
	 *            {@link EnableOAuth2Client}
	 * @return
	 */
	@Bean
	public FilterRegistrationBean<OAuth2ClientContextFilter> oauth2ClientFilterRegistration(
			OAuth2ClientContextFilter filter) {
		FilterRegistrationBean<OAuth2ClientContextFilter> registration = new FilterRegistrationBean<OAuth2ClientContextFilter>();
		registration.setFilter(filter);
		// set a low number so it has higher precedence than the main Spring Security
		// filter
		// lets us handle redirects from exceptions as well
		registration.setOrder(-100);
		return registration;
	}
}

