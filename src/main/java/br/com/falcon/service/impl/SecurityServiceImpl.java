package br.com.falcon.service.impl;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.com.falcon.service.SecurityService;

@Service
public class SecurityServiceImpl implements SecurityService {

	@Override
	public Boolean hasProtectedAccess() {
		final Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		final SimpleGrantedAuthority professional = new SimpleGrantedAuthority("PROFESSIONAL");
		final SimpleGrantedAuthority client = new SimpleGrantedAuthority("CLIENT");
		return authorities.contains(professional) || authorities.contains(client);
	}
}
