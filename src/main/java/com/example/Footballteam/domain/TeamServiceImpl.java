package com.example.Footballteam.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
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
			System.out.println(members);
			if (members != null && !members.isEmpty()) {
				for (AppUser member : members) {
					member.setTeam(null);
				}
			}

			handleMatches(team.getHomeMatches(), team);
			handleMatches(team.getAwayMatches(), team);

			// Delete the team
			teamRepo.delete(team);

			return true;
		} else {
			return false;
		}
	}

	private void handleMatches(List<Footballmatch> matches, Team team) {
	    if (matches != null && !matches.isEmpty()) {
	        for (Footballmatch match : matches) {
	            List<AppUser> attendees = match.getAttendees();
	            if (attendees != null && !attendees.isEmpty()) {
	                Iterator<AppUser> iterator = attendees.iterator();
	                while (iterator.hasNext()) {
	                    AppUser attendee = iterator.next();
	                    if (team.equals(attendee.getTeam()) || attendee.getTeam() == null) {
	                        iterator.remove();
	                    }
	                }
	            }

	            match.setHomeTeam(null);
	            match.setAwayTeam(null);
	        }
	    }

}
}