package br.com.falcon.web.request;

import java.util.Date;

public class ScheduleRequest {

	private Long professionalId;
	private Date startDate;

	public Long getProfessionalId() {
		return professionalId;
	}

	public void setProfessionalId(Long professionalId) {
		this.professionalId = professionalId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

}
