package br.com.falcon.jwt;

import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;

public class JwtAuthenticationResponse implements Serializable {

    private static final long serialVersionUID = 1250166508152483573L;

    private final String token;
    private final UserDetails user;

    public JwtAuthenticationResponse(String token, UserDetails user) {
        this.token = token;
        this.user = user;
    }

	public String getToken() {
		return token;
	}

	public UserDetails getUser() {
		return user;
	}
}
