package br.com.falcon.factory;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import br.com.falcon.domain.User;
import br.com.falcon.model.security.AuthUser;

public class UserFactory {

	public static AuthUser create(User user) {
		Collection<? extends GrantedAuthority> authorities;
		try {
			authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(user.getRole());
		} catch (Exception e) {
			authorities = null;
		}
		return new AuthUser(user.getId(), user.getUsername(), user.getPassword(), user.getTenant(), authorities);
	}
}
