package app.security;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Holds response from Google after authentication
 * 
 * @author gluo7
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenResponse {
	private String access_token;
	private String id_token;
	private String expires_in;
	private String token_type;
	private String refresh_token;

	public final String getAccess_token() {
		return access_token;
	}

	public final void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public final String getId_token() {
		return id_token;
	}

	public final void setId_token(String id_token) {
		this.id_token = id_token;
	}

	public final String getExpires_in() {
		return expires_in;
	}

	public final void setExpires_in(String expires_in) {
		this.expires_in = expires_in;
	}

	public final String getToken_type() {
		return token_type;
	}

	public final void setToken_type(String token_type) {
		this.token_type = token_type;
	}

	public final String getRefresh_token() {
		return refresh_token;
	}

	public final void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}

}
