package app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import app.security.Google;

@SpringBootApplication
@EnableConfigurationProperties(value= {Google.class})
public class App{
	
	private static final Logger log = LoggerFactory.getLogger(App.class);

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
		log.info("Hello World!");
	}
	
	@Bean(name="securityTemplate")
	public RestTemplate securityTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate;
	}
	
	@Bean
	public ApplicationRunner runner(Google google) {
		return args -> {
			log.info("Google credentials: {}", google);
		};
	}
}
