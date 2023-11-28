package com.example.Footballteam.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FootballmatchServiceImpl implements FootballmatchService {

	@Autowired
	TeamRepository teamRepo;
	
	@Autowired
	TeamService teamService;

	@Autowired
	FootballmatchRepository matchRepo;


	@Override
	public List<Footballmatch> getMatchesForTeam(String teamName) {
		return matchRepo.findByHomeTeamNameOrAwayTeamName(teamName, teamName);
	}

	@Override
	public Footballmatch getMatchById(Long matchId) {
		Optional<Footballmatch> optionalMatch = matchRepo.findById(matchId);
		return optionalMatch.orElse(null);
	}

	@Override
	public List<AppUser> getAttendeesForMatch(Team currentTeam, Long matchId, AppUser user) {

		Footballmatch currentMatch = getMatchById(matchId);

		List<AppUser> matchAttendees = currentMatch.getAttendees();
		List<AppUser> attendeesList = new ArrayList<>();

		if (matchAttendees != null && !matchAttendees.isEmpty()) {
			attendeesList.addAll(matchAttendees);
		}

		List<AppUser> teamAttendees = attendeesList.stream().filter(attendee -> attendee.getTeam().equals(currentTeam))
				.collect(Collectors.toList());

		return teamAttendees;
	}

	@Override
	public void addAttendee(Footballmatch match, AppUser player) {
		Optional<Footballmatch> optionalMatch = matchRepo.findById(match.getId());

		optionalMatch.ifPresent(existingMatch -> {
			List<AppUser> attendees = existingMatch.getAttendees();
			if (attendees == null) {
				attendees = new ArrayList<>();
			}

			if (!attendees.contains(player)) {
				attendees.add(player);
				matchRepo.save(existingMatch);
			}
		});
	}

	@Override
	public void abandonMatch(Footballmatch match, AppUser attendee) {
		if (match != null && attendee != null) {
			List<AppUser> attendees = match.getAttendees();
			attendees.remove(attendee);
			matchRepo.save(match);
		}
	}

	@Override
	public void removeAttendee(Footballmatch match, AppUser attendee) {
		if (match != null && attendee != null) {
			List<AppUser> attendees = match.getAttendees();
			attendees.remove(attendee);
			matchRepo.save(match);
		}
	}
	
	@Override
	public Footballmatch createMatch(Footballmatch match) {
		match.setResult("TBD");
		return matchRepo.save(match);
	}

	@Override
	public Footballmatch updateMatch(Long matchId, Footballmatch updatedMatch) {
		Optional<Footballmatch> optionalMatch = matchRepo.findById(matchId);

		if (optionalMatch.isPresent()) {
			Footballmatch existingMatch = optionalMatch.get();
			existingMatch.setHomeTeam(updatedMatch.getHomeTeam());
			existingMatch.setAwayTeam(updatedMatch.getAwayTeam());
			existingMatch.setMatchDate(updatedMatch.getMatchDate());
			existingMatch.setResult(updatedMatch.getResult());
			return matchRepo.save(existingMatch);
		} else {
			throw new NoSuchElementException("Match not found with id: " + matchId);
		}
	}

	@Override
	public boolean deleteMatch(Long matchId) {
	    Optional<Footballmatch> optionalMatch = matchRepo.findById(matchId);

	    if (optionalMatch.isPresent()) {
	        Footballmatch match = optionalMatch.get();
	        matchRepo.delete(match);
	        return true;
	    } else {
	        return false;
	    }
	}

	@Override
	public List<Footballmatch> getAllMatches() {
		return matchRepo.findAll();
	}

}
