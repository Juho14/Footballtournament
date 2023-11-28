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
import com.example.Footballteam.domain.AppUserService;
import com.example.Footballteam.domain.AuthService;
import com.example.Footballteam.domain.Team;
import com.example.Footballteam.domain.TeamService;

@Controller
@RequestMapping("/users")
public class UserController {

	@Autowired
	AppUserService userService;
	
	@Autowired
	AppUserRepository userRepo;
	
	@Autowired
	TeamService teamService;
	
	@Autowired
	AuthService authService;

	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
	public String showUserList(Model model) {
		Iterable<AppUser> users = userRepo.findAll();
		model.addAttribute("users", users);
		return "userlist";
	}

	@GetMapping("/add")
	@PreAuthorize("hasRole('ADMIN')")
	public String addUserForm(Model model) {
		List<Team> teams = teamService.getAllTeams();
		model.addAttribute("teams", teams);
		model.addAttribute("user", new AppUser());
		return "createuser";
	}
	
	@GetMapping("/add/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public String addAdminForm(Model model) {
		model.addAttribute("user", new AppUser());
		return "createadmin";
	}
	
	@GetMapping("/edit/{userId}")
	@PreAuthorize("hasRole('ADMIN')")
	public String editUserForm(@PathVariable Long userId, Team team, Model model) {
		List<Team> teams = teamService.getAllTeams();
		AppUser user = userService.getUserById(userId);
		model.addAttribute("user", user);
		model.addAttribute("teams", teams);
		return "edituser";
	}
	
	@GetMapping("/edit/password")
	public String editPasswordForm(Model model) {
		AppUser currentUser = authService.getCurrentUser();
		model.addAttribute("user", currentUser);
		return "editpassword";
		
	}
}
