package com.example.Footballteam.domain;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface FootballmatchRepository extends CrudRepository<Footballmatch, Long> {

    // Query by hometeam and awayteam names
    List<Footballmatch> findByHomeTeamNameAndAwayTeamName(String hometeamName, String awayteamName);
    List<Footballmatch> findByHomeTeamNameOrAwayTeamName(String hometeamName, String awayteamName);
    
    // Query by hometeam and awayteam separately
    List<Footballmatch> findByHomeTeamName(String name);
    List<Footballmatch> findByAwayTeamName(String name);

    // Query by entire team object (if needed)
    List<Footballmatch> findByHomeTeam(Team homeTeam);
    List<Footballmatch> findByAwayTeam(Team awayTeam);
    
    List<Footballmatch> findByHomeTeamOrAwayTeam(Team homeTeam, Team awayTeam);
    Footballmatch getMatchById(Long matchId);
    
    List<Footballmatch> findAll();
}
