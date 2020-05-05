/*
 * Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package examples.java.simple;

import examples.java.simple.model.Blog;
import examples.java.simple.repository.BlogRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.time.LocalDateTime;

@SpringBootApplication
public class SampleSimpleApplication{
	public static void main(String[] args) throws Exception {
		SpringApplication.run(SampleSimpleApplication.class, args);
	}

	@Bean
	@Profile("default")
	public ApplicationRunner loadTestData(BlogRepository blogRepository){
		return args -> {
			blogRepository.save(new Blog(
					"How to build a webapp",
					"Step 1: Google\nStep 2: Copy/paste\nStep 3: Put it on your resume\n\tThanks for reading!",
					LocalDateTime.now(),
					Blog.addTags("Web", "Application", "Spring")
			));
		};
	}
}
