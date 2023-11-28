package com.example.Footballteam.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.Footballteam.domain.AppUser;
import com.example.Footballteam.domain.Footballmatch;
import com.example.Footballteam.domain.FootballmatchService;
import com.example.Footballteam.domain.Team;
import com.example.Footballteam.domain.TeamService;

@Controller
@RequestMapping("/matches")
public class MatchController {
	
	@Autowired
	FootballmatchService matchService;
	
	@Autowired
	TeamService teamService;


    @GetMapping
    public String showMatches(Model model) {
        List<Footballmatch> matches = matchService.getAllMatches();
        model.addAttribute("matches", matches);
        return "listofmatches";
    }
    
    @GetMapping("/add")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public String addMatchForm(Model model) {
    	List<Team> teams = teamService.getAllTeams();
    	model.addAttribute("teams", teams);
    	return "creatematch";
    }

    @GetMapping("/{matchId}")
    public String showMatchDetails(@PathVariable Long matchId, Model model) {
        Footballmatch match = matchService.getMatchById(matchId);
        if (match != null) {
            List<AppUser> attendees = match.getAttendees();
            model.addAttribute("match", match);
            model.addAttribute("attendees", attendees);

            return "matchdetails";
        } else {
            return "redirect:/matches";
        }
    }
    
    @GetMapping("/edit/{matchId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public String showEditMatchForm(@PathVariable(name = "matchId") Long matchId, Model model) {
        Footballmatch match = matchService.getMatchById(matchId);
        List<Team> teams = teamService.getAllTeams();
        
        model.addAttribute("teams", teams);
        model.addAttribute("match", match);

        return "editmatch";
    }
	
}
