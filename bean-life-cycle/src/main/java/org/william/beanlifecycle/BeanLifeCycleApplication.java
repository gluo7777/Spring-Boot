package org.william.beanlifecycle;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BeanLifeCycleApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(BeanLifeCycleApplication.class);
		app.setSources(Collections.singleton("classpath:/beans/mybeans.xml"));
		app.run(args);
	}
}
