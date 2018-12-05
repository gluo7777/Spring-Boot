package app.security;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDetails2 {
	private String family_name;
	private String name;
	private String picture;
	private String email;
	private String link;
	private String given_name;
	private String id;
	private String verified_email;

	public final String getFamily_name() {
		return family_name;
	}

	public final void setFamily_name(String family_name) {
		this.family_name = family_name;
	}

	public final String getName() {
		return name;
	}

	public final void setName(String name) {
		this.name = name;
	}

	public final String getPicture() {
		return picture;
	}

	public final void setPicture(String picture) {
		this.picture = picture;
	}

	public final String getEmail() {
		return email;
	}

	public final void setEmail(String email) {
		this.email = email;
	}

	public final String getLink() {
		return link;
	}

	public final void setLink(String link) {
		this.link = link;
	}

	public final String getGiven_name() {
		return given_name;
	}

	public final void setGiven_name(String given_name) {
		this.given_name = given_name;
	}

	public final String getId() {
		return id;
	}

	public final void setId(String id) {
		this.id = id;
	}

	public final String getVerified_email() {
		return verified_email;
	}

	public final void setVerified_email(String verified_email) {
		this.verified_email = verified_email;
	}

}
