package com.example.Footballteam.domain;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
public class Team {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, updatable = false)
	private Long id;
	private String name;

	@OneToMany(targetEntity = AppUser.class, mappedBy = "team", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<AppUser> members = new ArrayList<>();

	@OneToMany(mappedBy = "homeTeam")
	@JsonIgnore
	private List<Footballmatch> homeMatches = new ArrayList<>();

	@OneToMany(mappedBy = "awayTeam")
	@JsonIgnore
	private List<Footballmatch> awayMatches = new ArrayList<>();

	
	public Team() {

	}

	public Team(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public List<AppUser> getMembers() {
		return members;
	}

	public void setMembers(List<AppUser> members) {
		this.members = members;
	}
	
	@Override
	public String toString() {
		return "Team [id=" + id + ", name=" + name + "]";
	}

	public List<Footballmatch> getHomeMatches() {
		return homeMatches;
	}

	public void setHomeMatches(List<Footballmatch> homeMatches) {
		this.homeMatches = homeMatches;
	}

	public List<Footballmatch> getAwayMatches() {
		return awayMatches;
	}

	public void setAwayMatches(List<Footballmatch> awayMatches) {
		this.awayMatches = awayMatches;
	}

}
