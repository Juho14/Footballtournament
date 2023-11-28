package com.example.Footballteam.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface TeamRepository extends CrudRepository<Team, Long> {

	Team findByName(String string);
	List<Team> findAll();

}
