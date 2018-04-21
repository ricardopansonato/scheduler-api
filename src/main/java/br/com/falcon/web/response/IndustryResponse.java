package br.com.falcon.web.response;

public class IndustryResponse {
	
	private Long id;
	private String name;
	
	public IndustryResponse() {
		
	}
	
	public IndustryResponse(Long id, String name) {
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
