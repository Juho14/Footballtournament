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
import com.example.Footballteam.domain.AppUserRepository;
import com.example.Footballteam.domain.Team;
import com.example.Footballteam.domain.TeamService;

@Controller
@RequestMapping("/teams")
public class TeamController {

    @Autowired
    TeamService teamService;

    @Autowired
    AppUserRepository userRepo;

    @GetMapping
    public String showTeams(Model model) {
        List<Team> teams = teamService.getAllTeams();
        model.addAttribute("teams", teams);
        return "listofteams";
    }

    @GetMapping("/{teamId}")
    public String showTeamDetails(@PathVariable Long teamId, Model model) {
        Team team = teamService.getTeamById(teamId);
        if (team != null) {
            model.addAttribute("team", team);
            List<AppUser> managers = teamService.getManagers(team);
            model.addAttribute("managers", managers);
            return "teamDetails";
        } else {
            return "redirect:/teams";
        }
    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public String showCreateTeamForm(Model model) {
        model.addAttribute("team", new Team());
        return "createteam";
    }

    @GetMapping("/edit/{teamId}")
    @PreAuthorize("hasRole('ADMIN')")
    public String showEditTeamForm(@PathVariable Long teamId, Model model) {
        Team team = teamService.getTeamById(teamId);
        model.addAttribute("team", team);
        return "editteam";
    }

}
