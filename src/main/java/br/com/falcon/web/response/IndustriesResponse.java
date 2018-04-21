package br.com.falcon.web.response;

import java.util.List;
import java.util.stream.Collectors;

import br.com.falcon.domain.Industry;

public class IndustriesResponse {
	private List<IndustryResponse> industries;
	
	public IndustriesResponse() {
		
	}
	
	public IndustriesResponse(List<Industry> industries) {
		this.industries = industries.stream().map(i -> new IndustryResponse(i.getId(), i.getName())).collect(Collectors.toList());
	}

	public List<IndustryResponse> getIndustries() {
		return industries;
	}
}
