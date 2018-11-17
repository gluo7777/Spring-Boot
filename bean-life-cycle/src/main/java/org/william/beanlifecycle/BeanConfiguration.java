package org.william.beanlifecycle;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {
	@Bean("greeting")
	public String greeting() {
		return "Hello World!";
	}
}
