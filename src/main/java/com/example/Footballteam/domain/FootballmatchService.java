package com.example.Footballteam.domain;

import java.util.List;

public interface FootballmatchService {
	
	Footballmatch createMatch(Footballmatch match);
	
	List<Footballmatch> getMatchesForTeam(String teamName);

	Footballmatch getMatchById(Long matchId);

	void addAttendee(Footballmatch match, AppUser player);

	List<AppUser> getAttendeesForMatch(Team currentTeam, Long matchId, AppUser user);
	
	//Rest functions

	Footballmatch updateMatch(Long matchId, Footballmatch updatedMatch);

	boolean deleteMatch(Long matchId);

	List<Footballmatch> getAllMatches();

	void abandonMatch(Footballmatch match, AppUser currentUser);

	void removeAttendee(Footballmatch match, AppUser attendee);
	

}
