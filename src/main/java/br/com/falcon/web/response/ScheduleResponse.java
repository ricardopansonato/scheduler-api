package br.com.falcon.web.response;

import java.util.Date;

import br.com.falcon.domain.Schedule;

public class ScheduleResponse {
	
	private Long id;
	private String serviceName;
	private Date startDate;
	private Date endDate;
	
	public ScheduleResponse() {
		
	}
	
	public ScheduleResponse(Schedule schedule) {
		this.id = schedule.getId();
		this.startDate = schedule.getStart();
		this.endDate = schedule.getEnd();
		this.serviceName = schedule.getServiceName();
		
	}

	public Long getId() {
		return id;
	}

	public String getServiceName() {
		return serviceName;
	}

	public Date getStartDate() {
		return startDate;
	}

	public Date getEndDate() {
		return endDate;
	}
}
