package br.com.falcon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.falcon.domain.Industry;

public interface IndustryRepository extends JpaRepository<Industry, Integer> {
	
	List<Industry> findByOrderById();

}
