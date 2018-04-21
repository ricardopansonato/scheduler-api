package br.com.falcon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.falcon.domain.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
	
	List<Schedule> findByClient_IdOrderByStartDesc(Long id);
	
	List<Schedule> findByProfessional_IdOrderByStartDesc(Long id);
}
