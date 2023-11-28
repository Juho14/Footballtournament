package com.example.Footballteam.domain;

import java.util.List;

public interface TeamService {

	List<AppUser> getPlayers(Team team);

	List<AppUser> getManagers(Team team);

	public Team getTeamForUser(AppUser user);

	List<AppUser> getAttendees(Team currentTeam);
	
	List<Team> getAllTeams();

	// Rest functions
	Team createTeam(Team team);

	Team getTeamById(Long teamId);

	Team updateTeam(Long teamId, Team updatedTeam);

	boolean deleteTeam(Long teamId);

	Team getTeamByName(String name);



}
