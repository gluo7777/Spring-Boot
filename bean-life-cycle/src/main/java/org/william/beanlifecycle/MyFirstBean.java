package org.william.beanlifecycle;

public class MyFirstBean {

	private String name;

	public MyFirstBean(String name) {
		this.name = name;
	}

	public final String getName() {
		return name;
	}

	public final void setName(String name) {
		this.name = name;
	}

	public void sayMyName() {
		System.out.println(String.format("Hey this is %s.", this.name));
	}
}
