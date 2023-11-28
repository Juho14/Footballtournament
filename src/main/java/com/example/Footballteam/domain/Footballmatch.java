package com.example.Footballteam.domain;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Footballmatch {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, updatable = false)
	private Long id;

	private LocalDate matchDate;
	private String result;

	@ManyToOne
	@JoinColumn(name = "homeTeam_id")
	private Team homeTeam;

	@ManyToOne
	@JoinColumn(name = "awayTeam_id")
	private Team awayTeam;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "MATCH_ATTENDEES", joinColumns = @JoinColumn(name = "match_id"), inverseJoinColumns = @JoinColumn(name = "attendee_id"))
	@JsonIgnore
	private List<AppUser> attendees;

	public Footballmatch() {
	}

	public Footballmatch(Team homeTeam, Team awayTeam, LocalDate matchDate, String result) {
		this.homeTeam = homeTeam;
		this.awayTeam = awayTeam;
		this.matchDate = matchDate;
		this.result = result;
	}

	public Long getId() {
		return id;
	}

	public LocalDate getMatchDate() {
		return matchDate;
	}

	public void setMatchDate(LocalDate matchDate) {
		this.matchDate = matchDate;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Team getHomeTeam() {
		return homeTeam;
	}

	public void setHomeTeam(Team homeTeam) {
		this.homeTeam = homeTeam;
		if (homeTeam != null) {
			homeTeam.getHomeMatches().add(this);
		}
	}

	public Team getAwayTeam() {
		return awayTeam;
	}

	public void setAwayTeam(Team awayTeam) {
		this.awayTeam = awayTeam;
		if (awayTeam != null) {
			awayTeam.getAwayMatches().add(this);
		}
	}

	public void addAttendee(AppUser player) {
		if (attendees == null) {
			attendees = new ArrayList<>();
		}
		attendees.add(player);
	}

	public List<AppUser> getAttendees() {
		return attendees;
	}

	public void setAttendees(List<AppUser> attendees) {
		this.attendees = attendees;
	}

	@Override
	public String toString() {
		return "Match [id=" + id + ", matchDate=" + matchDate + ", result=" + result + ", homeTeam=" + homeTeam
				+ ", awayTeam=" + awayTeam + "]";
	}

}
