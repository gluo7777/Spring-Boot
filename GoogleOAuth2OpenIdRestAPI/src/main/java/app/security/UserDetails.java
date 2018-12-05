package app.security;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class UserDetails {
	private String iss;
	private String at_hash;
	private String email_verified;
	private String sub;
	private String azp;
	private String email;
	private String aud;
	private String iat;
	private String exp;
	private String nonce;
	private String hd;

	public final String getIss() {
		return iss;
	}

	public final void setIss(String iss) {
		this.iss = iss;
	}

	public final String getAt_hash() {
		return at_hash;
	}

	public final void setAt_hash(String at_hash) {
		this.at_hash = at_hash;
	}

	public final String getEmail_verified() {
		return email_verified;
	}

	public final void setEmail_verified(String email_verified) {
		this.email_verified = email_verified;
	}

	public final String getSub() {
		return sub;
	}

	public final void setSub(String sub) {
		this.sub = sub;
	}

	public final String getAzp() {
		return azp;
	}

	public final void setAzp(String azp) {
		this.azp = azp;
	}

	public final String getEmail() {
		return email;
	}

	public final void setEmail(String email) {
		this.email = email;
	}

	public final String getAud() {
		return aud;
	}

	public final void setAud(String aud) {
		this.aud = aud;
	}

	public final String getIat() {
		return iat;
	}

	public final void setIat(String iat) {
		this.iat = iat;
	}

	public final String getExp() {
		return exp;
	}

	public final void setExp(String exp) {
		this.exp = exp;
	}

	public final String getNonce() {
		return nonce;
	}

	public final void setNonce(String nonce) {
		this.nonce = nonce;
	}

	public final String getHd() {
		return hd;
	}

	public final void setHd(String hd) {
		this.hd = hd;
	}
}
