package app;

import java.io.Serializable;

import org.springframework.data.redis.core.RedisHash;

@RedisHash("RedisObj")
public class RedisObj implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String firstName;
	private String lastName;
	private long age;

	public RedisObj(String id, String firstName, String lastName, long age) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
	}

	public RedisObj() {
		super();
		// TODO Auto-generated constructor stub
	}

	public final String getId() {
		return id;
	}

	public final void setId(String id) {
		this.id = id;
	}

	public final String getFirstName() {
		return firstName;
	}
	public final void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public final String getLastName() {
		return lastName;
	}
	public final void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public final long getAge() {
		return age;
	}
	public final void setAge(long age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "RedisObj [id=" + id + ", firstName=" + firstName + ", lastName="
				+ lastName + ", age=" + age + "]";
	}

}
