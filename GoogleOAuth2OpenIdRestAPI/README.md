# OAuth2.0 and OpenID Demo
Delegate to Google's frameworks for Authentication handling

# Tooling
- Spring Boot
	- Web
- Google OAuth2.0
- [Google OpenID Connect](https://developers.google.com/identity/protocols/OpenIDConnect)

# Road Map
- Set up Controller to handle authentication endpoints
- Set up OAuth2.0
	- Create new Google [Project](https://console.developers.google.com/)
	- [Credentials](https://console.developers.google.com/apis/credentials)
	- Redirect URI
	- Set up consent [screen](https://console.developers.google.com/apis/credentials/consent)
- [Set up Controller Flow](/#Set up Controller Flow)

# Set up Controller Flow
- Set up CSRF Protection
	- Spring Session (or stick with simple in-memory for basic demo)
	- store `state` attribute as a 30 char string generated randomly
- Make GET request to Google's auth end points (`https://accounts.google.com/o/oauth2/v2/auth`)
	- client_id
	- [response_type](https://developers.google.com/identity/protocols/OpenIDConnect#response-type) = code
	- [scope](https://developers.google.com/identity/protocols/OpenIDConnect#scope-param) = `openid email`
	- redirect_uri = `<authorized-uri>/login`
	- state = `include previously generated state token`
	- nounce = `regenerated each time`
	- [login_hint](https://developers.google.com/identity/protocols/OpenIDConnect#login-hint)
	- optional: access_type=offline to retrieve refresh_token in authorization request
- Complete [List](https://developers.google.com/identity/protocols/OpenIDConnect#authenticationuriparameters) of Parameters
- Google's Example

	''''
	https://accounts.google.com/o/oauth2/v2/auth?
	 client_id=424911365001.apps.googleusercontent.com&
	 response_type=code&
	 scope=openid%20email&
	 redirect_uri=https://oauth2-login-demo.example.com/code&
	 state=security_token%3D138r5719ru3e1%26url%3Dhttps://oauth2-login-demo.example.com/myHome&
	 login_hint=jsmith@example.com&
	 openid.realm=example.com&
	 nonce=0394852-3190485-2490358&
	 hd=example.com
	''''
	
- Google's auth server makes a GET to your `redirect_uri` with some query parameters
	''''
	https://oa2cb.example.com/code?state=security_token%3D138r5719ru3e1%26url%3Dhttps://oa2cb.example.com/myHome&code=4/P7q7W91a-oMsCeLvIaQm6bTrgtp7
	''''
- Verify state matches the one set in session
- Make POST request to Google's auth server token end point (`https://www.googleapis.com/oauth2/v4/token`)
	- code = `what's in auth server param`
	- client_id
	- client_secret
	- redirect_uri = `controller endpoint that will parse response json`
	- grant_type = authorization_code
- Google's Example

	''''
	POST /oauth2/v4/token HTTP/1.1
	Host: www.googleapis.com
	Content-Type: application/x-www-form-urlencoded
	
	code=4/P7q7W91a-oMsCeLvIaQm6bTrgtp7&
	client_id=8819981768.apps.googleusercontent.com&
	client_secret={client_secret}&
	redirect_uri=https://oauth2-login-demo.example.com/code&
	grant_type=authorization_code
	''''
- Google sends back JSON
- parse response json in redirect_uri endpoint
	- access_token
	- id_token = `JSON Web Token. HTTPS and client secret means we're confident enough to not validate this token`
	- expires_in
	- token_type
	- refresh_token = `do not rely on this being always available`
- obtain user info from above
- sample ID Token

	'''json
	{"iss":"accounts.google.com",
	 "at_hash":"HK6E_P6Dh8Y93mRNtsDB1Q",
	 "email_verified":"true",
	 "sub":"10769150350006150715113082367",
	 "azp":"1234987819200.apps.googleusercontent.com",
	 "email":"jsmith@example.com",
	 "aud":"1234987819200.apps.googleusercontent.com",
	 "iat":1353601026,
	 "exp":1353604926,
	 "nonce": "0394852-3190485-2490358",
	 "hd":"example.com" }
	''''
	
- optional: validate id token using `at_hash` to compare with same access token used to make request

# Persistence
- use `sub` as a unique identifier for a Google user