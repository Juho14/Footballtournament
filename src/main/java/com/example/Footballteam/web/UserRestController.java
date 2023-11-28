package com.example.Footballteam.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.Footballteam.domain.AppUser;
import com.example.Footballteam.domain.AppUserRepository;
import com.example.Footballteam.domain.AppUserService;
import com.example.Footballteam.domain.AuthService;
import com.example.Footballteam.domain.TeamRepository;
import com.example.Footballteam.domain.TeamService;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

	@Autowired
	AppUserService userService;

	@Autowired
	TeamService teamService;
	
	@Autowired
	AuthService authService;

	@Autowired
	TeamRepository teamRepo;

	@Autowired
	AppUserRepository userRepo;

	// Get all users
	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Iterable<AppUser>> getUsers() {
		Iterable<AppUser> users = userRepo.findAll();
		return ResponseEntity.ok(users);
	}

	// Get user by ID
	@GetMapping("/{userId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<AppUser> getUserById(@PathVariable Long userId) {
		AppUser user = userService.getUserById(userId);
		if (user != null) {
			return ResponseEntity.ok(user);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// Create a new user
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<AppUser> createUser(@RequestBody AppUser user) {
		AppUser createdUser = userService.createUser(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
	}

	// Update an existing user
	@PutMapping("/{userId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<AppUser> updateUser(@PathVariable Long userId, @RequestBody AppUser updatedUser) {
		AppUser user = userService.updateUser(userId, updatedUser);
		if (user != null) {
			return ResponseEntity.ok(user);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	// Change password
	@PutMapping("/password")
	public ResponseEntity<AppUser> updatePassword(@RequestBody AppUser updatedUser) {
		AppUser currentUser = authService.getCurrentUser();
		AppUser user = userService.changePassword(currentUser.getId(), updatedUser);
		if (user != null) {
			return ResponseEntity.ok(user);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// Delete a user
	@DeleteMapping("/{userId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
		boolean deleted = userService.deleteUser(userId);
		if (deleted) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
