package br.com.falcon.web.response;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.falcon.domain.Schedule;

public class SchedulesResponse {
	private List<ScheduleResponse> response = new ArrayList<>();
	
	public SchedulesResponse() {
		
	}
	
	public SchedulesResponse(List<Schedule> schedules) {
		if (schedules != null && !schedules.isEmpty()) {
			this.response = schedules.stream().map(s -> new ScheduleResponse(s)).collect(Collectors.toList());
		}
	}

	public List<ScheduleResponse> getResponse() {
		return response;
	}
}
