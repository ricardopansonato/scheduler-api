package br.com.falcon.web.response;

import java.util.List;
import java.util.stream.Collectors;

import br.com.falcon.domain.Service;

public class ServicesResponse {
	
	private List<ServiceResponse> services;
	
	public ServicesResponse() {
		
	}
	
	public ServicesResponse(List<Service> services) {
		this.services = services.stream().map(s -> new ServiceResponse(s.getId(), s.getName())).collect(Collectors.toList());
	}

	public List<ServiceResponse> getServices() {
		return services;
	}
}
