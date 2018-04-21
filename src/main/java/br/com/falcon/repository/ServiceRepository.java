package br.com.falcon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.falcon.domain.Service;

public interface ServiceRepository extends JpaRepository<Service, Long> {
	
	List<Service> findByIndustryIdOrderByName(final Long id);
}
