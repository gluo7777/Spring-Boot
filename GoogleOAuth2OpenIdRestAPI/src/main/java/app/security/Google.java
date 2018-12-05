package app.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "google")
public class Google {
	private String clientId;
	private String clientSecret;
	private String responseType;
	private String scope;
	private String userAuthorizationUri;
	private String accessTokenUri;
	private String userInfoUri;
	private String redirectUri;

	public final String getClientId() {
		return clientId;
	}

	public final void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public final String getClientSecret() {
		return clientSecret;
	}

	public final void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public final String getResponseType() {
		return responseType;
	}

	public final void setResponseType(String responseType) {
		this.responseType = responseType;
	}

	public final String getScope() {
		return scope;
	}

	public final void setScope(String scope) {
		this.scope = scope;
	}

	public final String getUserAuthorizationUri() {
		return userAuthorizationUri;
	}

	public final void setUserAuthorizationUri(String userAuthorizationUri) {
		this.userAuthorizationUri = userAuthorizationUri;
	}

	public final String getAccessTokenUri() {
		return accessTokenUri;
	}

	public final void setAccessTokenUri(String accessTokenUri) {
		this.accessTokenUri = accessTokenUri;
	}

	public final String getUserInfoUri() {
		return userInfoUri;
	}

	public final void setUserInfoUri(String userInfoUri) {
		this.userInfoUri = userInfoUri;
	}

	public final String getRedirectUri() {
		return redirectUri;
	}

	public final void setRedirectUri(String redirectUri) {
		this.redirectUri = redirectUri;
	}

	@Override
	public String toString() {
		return "Google [clientId=" + clientId + ", clientSecret=" + clientSecret + ", responseType=" + responseType
				+ ", scope=" + scope + ", userAuthorizationUri=" + userAuthorizationUri + ", accessTokenUri="
				+ accessTokenUri + ", userInfoUri=" + userInfoUri + ", redirectUri=" + redirectUri + "]";
	}

}
