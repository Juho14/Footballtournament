package com.example.Footballteam.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.Footballteam.domain.AppUser;
import com.example.Footballteam.domain.AppUserRepository;
import com.example.Footballteam.domain.AuthService;
import com.example.Footballteam.domain.Footballmatch;
import com.example.Footballteam.domain.FootballmatchService;
import com.example.Footballteam.domain.Team;
import com.example.Footballteam.domain.TeamService;

@Controller
public class AttendeeController {
	
	@Autowired
	AuthService authService;
	
	@Autowired
	TeamService teamService;
	
	@Autowired
	FootballmatchService matchService;
	
	@Autowired
	AppUserRepository userRepo;
	
	@GetMapping("/viewattendees/{matchId}")
	public String showAttendees(@PathVariable Long matchId, Model model) {
		AppUser currentUser = authService.getCurrentUser();
		if (currentUser == null) {
			System.out.println("Current user is null");
		}

		Team currentTeam = teamService.getTeamForUser(currentUser);

		if (currentTeam != null) {
			Footballmatch match = matchService.getMatchById(matchId);
			if (match != null) {
				List<AppUser> attendees = matchService.getAttendeesForMatch(currentTeam, matchId, currentUser);
				model.addAttribute("attendees", attendees);
				model.addAttribute("currentTeam", currentTeam);
				model.addAttribute("match", match);

				return "viewattendees";
			} else {
				System.out.println("No match found for the provided matchId.");
				return "redirect:/schedule";
			}
		} else {
			System.out.println("No team found for the current user.");
			return "redirect:/schedule";
		}
	}

	@PostMapping("/attendmatch/{matchId}")
	public String attendMatch(@PathVariable Long matchId, Model model) {
		AppUser currentUser = authService.getCurrentUser();
		Team currentTeam = currentUser.getTeam();
		Footballmatch match = matchService.getMatchById(matchId);
		List<AppUser> attendees = matchService.getAttendeesForMatch(currentTeam, matchId, currentUser);

		if (currentUser != null && match != null) {
			attendees.add(currentUser);
			matchService.addAttendee(match, currentUser);
			return "redirect:/schedule";
		} else {
			return "redirect:/schedule";
		}
	}

	@PostMapping("/abandonmatch/{matchId}")
	public String abandonMatch(@PathVariable Long matchId, Model model) {
		AppUser currentUser = authService.getCurrentUser();
		Team currentTeam = currentUser.getTeam();
		Footballmatch match = matchService.getMatchById(matchId);
		List<AppUser> attendees = matchService.getAttendeesForMatch(currentTeam, matchId, currentUser);

		if (currentUser != null && match != null) {
			if (attendees.contains(currentUser)) {
				attendees.remove(currentUser);
				matchService.removeAttendee(match, currentUser);
			}
			return "redirect:/schedule";
		} else {
			return "redirect:/schedule";
		}
	}

	@PostMapping("/removeattendee/{matchId}/{attendeeId}")
	@PreAuthorize("hasRole('ADMIN')")
	public String removeAttendee(@PathVariable Long matchId, @PathVariable Long attendeeId) {
		AppUser currentUser = authService.getCurrentUser();
		Footballmatch match = matchService.getMatchById(matchId);
		AppUser attendeeToRemove = userRepo.findById(attendeeId).orElse(null);

		if (currentUser != null && match != null && attendeeToRemove != null) {
					matchService.removeAttendee(match, attendeeToRemove);
			return "redirect:/matches/" + matchId;
		} else {
			return "redirect:/schedule";
		}
	}

}
