package com.example.Footballteam.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.Footballteam.domain.AppUser;
import com.example.Footballteam.domain.AuthService;
import com.example.Footballteam.domain.Footballmatch;
import com.example.Footballteam.domain.FootballmatchService;
import com.example.Footballteam.domain.Team;
import com.example.Footballteam.domain.TeamService;

@Controller
public class FootballteamController {

	@Autowired
	private TeamService teamService;

	@Autowired
	private FootballmatchService matchService;

	@Autowired
	private AuthService authService;

	@GetMapping("/login")
	public String login() {
		
	//	To delete users if there are problems with logging in
		/*
		Iterable<AppUser> deleteUsers = userRepo.findAll();
		for (AppUser user : deleteUsers) {
			userRepo.delete(user);
		}
		*/
		return authService.login();
	}

	@GetMapping
	public String index(Model model) {
		AppUser user = authService.getCurrentUser();
		if (user != null) {
		} else {
			System.out.println("User is null");
		}
		model.addAttribute("user", user);
		return "index";
	}

	@GetMapping("/memberlist")
	public String showMembers(Model model) {
		AppUser currentUser = authService.getCurrentUser();

		Team currentTeam = teamService.getTeamForUser(currentUser);

		if (currentTeam != null) {
			List<AppUser> players = teamService.getPlayers(currentTeam);
			List<AppUser> managers = teamService.getManagers(currentTeam);
			model.addAttribute("players", players);
			model.addAttribute("managers", managers);
			model.addAttribute("currentTeam", currentTeam);
			return "memberlist";
		} else {
			return "index";

		}

	}

	@GetMapping("/schedule")
	public String showSchedule(Model model) {
		AppUser currentUser = authService.getCurrentUser();
		if (currentUser == null) {
			System.out.println("Current user is null");
			return "index";
		}

		Team currentTeam = teamService.getTeamForUser(currentUser);
		if (currentTeam != null) {
			List<Footballmatch> matches = matchService.getMatchesForTeam(currentTeam.getName());
			model.addAttribute("currentUser", currentUser);
			model.addAttribute("teamSchedule", matches);
			return "matchschedule";
		} else {
			return "index";
		}
	}

}