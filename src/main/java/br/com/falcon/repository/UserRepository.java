package br.com.falcon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.falcon.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String name);
}
