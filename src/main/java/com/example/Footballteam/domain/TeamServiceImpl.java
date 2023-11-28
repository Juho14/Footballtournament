package com.example.Footballteam.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TeamServiceImpl implements TeamService {

	@Autowired
	private FootballmatchRepository matchRepository;

	@Autowired
	private TeamRepository teamRepo;

	@Override
	public List<AppUser> getPlayers(Team team) {
		List<AppUser> players = new ArrayList<>();
		for (AppUser user : team.getMembers()) {
			if ("ROLE_PLAYER".equals(user.getRole()) && team.equals(user.getTeam())) {
				players.add(user);
			}
		}
		return players;
	}

	@Override
	public List<AppUser> getManagers(Team team) {
		List<AppUser> managers = new ArrayList<>();
		for (AppUser user : team.getMembers()) {
			if ("ROLE_MANAGER".equals(user.getRole()) && team.equals(user.getTeam())) {
				managers.add(user);
			}
		}
		return managers;
	}

	@Override
	public Team getTeamForUser(AppUser user) {
		return user.getTeam();
	}

	@Override
	public List<AppUser> getAttendees(Team currentTeam) {
		List<Footballmatch> matches = matchRepository.findByHomeTeamOrAwayTeam(currentTeam, currentTeam);
		List<AppUser> attendeesList = new ArrayList<>();

		for (Footballmatch match : matches) {
			List<AppUser> matchAttendees = match.getAttendees();
			attendeesList.addAll(matchAttendees);
		}

		return attendeesList;
	}

	@Override
	public List<Team> getAllTeams() {
		return teamRepo.findAll();
	}

	@Override
	public Team getTeamById(Long teamId) {
		Optional<Team> optionalTeam = teamRepo.findById(teamId);
		return optionalTeam.orElse(null); // Returns null if not found, handle accordingly in your controller
	}

	@Override
	public Team getTeamByName(String name) {
		return teamRepo.findByName(name);
	}

	@Override
	public Team createTeam(Team team) {
		return teamRepo.save(team);
	}

	@Override
	public Team updateTeam(Long teamId, Team updatedTeam) {
		Optional<Team> optionalTeam = teamRepo.findById(teamId);

		if (optionalTeam.isPresent()) {
			Team existingTeam = optionalTeam.get();
			existingTeam.setName(updatedTeam.getName());
			return teamRepo.save(existingTeam);
		} else {
			return null;
		}
	}

	@Override
	public boolean deleteTeam(Long teamId) {
	    Optional<Team> optionalTeam = teamRepo.findById(teamId);
	    if (optionalTeam.isPresent()) {
	        Team team = optionalTeam.get();

	        // Set the team of members to null
	        List<AppUser> members = team.getMembers();
	        if (members != null && !members.isEmpty()) {
	            for (AppUser member : members) {
	                member.setTeam(null);
	            }
	        }

	        // Set homeTeam and awayTeam to null in matches where the team is involved
	        List<Footballmatch> homeMatches = team.getHomeMatches();
	        if (homeMatches != null && !homeMatches.isEmpty()) {
	            for (Footballmatch match : homeMatches) {
	                match.setHomeTeam(null);
	            }
	        }

	        List<Footballmatch> awayMatches = team.getAwayMatches();
	        if (awayMatches != null && !awayMatches.isEmpty()) {
	            for (Footballmatch match : awayMatches) {
	                match.setAwayTeam(null);
	            }
	        }

	        // Delete the team
	        teamRepo.delete(team);

	        return true;
	    } else {
	        return false;
	    }
	}


}