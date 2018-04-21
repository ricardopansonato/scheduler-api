package br.com.falcon.web.response;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import br.com.falcon.domain.User;

public class ServiceResponse {
	private Long id;
	private String name;
	private List<UserResponse> professionals;
	
	public ServiceResponse() {
		
	}
	
	public ServiceResponse(Long id, String name, Set<User> professionals) {
		this.id = id;
		this.name = name;
		this.professionals = professionals.stream().map(s -> new UserResponse(s.getId(), s.getUsername())).collect(Collectors.toList());
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public List<UserResponse> getProfessionals() {
		return professionals;
	}
}
