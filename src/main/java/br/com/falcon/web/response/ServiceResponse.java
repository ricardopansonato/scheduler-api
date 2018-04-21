package br.com.falcon.web.response;

public class ServiceResponse {
	private Long id;
	private String name;
	
	public ServiceResponse() {
		
	}
	
	public ServiceResponse(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}
