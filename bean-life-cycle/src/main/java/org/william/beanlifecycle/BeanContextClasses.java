package org.william.beanlifecycle;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class BeanContextClasses implements ApplicationRunner{

	// configuration framework and basic functionality
	@Autowired
	BeanFactory factory;
	
	// BeanFactory + more enterprise-specific functionality
	@Autowired
	ApplicationContext context;
	
	// xml configured bean
	@Autowired
	MyFirstBean myFirstBean; 
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		String msg = factory.getBean("greeting", String.class);
		System.out.println(msg);
		msg = context.getBean("greeting", String.class);
		System.out.println(msg);
		System.out.println(context.isSingleton("greeting"));
		Resource resource = context.getResource("classpath:/props/william.properties");
		if(resource.exists()) {
			System.out.println(resource.getFile());
		}
		myFirstBean.sayMyName();
	}

}
