package com.example.Footballteam.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.Footballteam.domain.Team;
import com.example.Footballteam.domain.TeamService;

@RestController
@RequestMapping("/api/teams")
public class TeamRestController {
	
	@Autowired
	TeamService teamService;

	// Read a team by ID
	@GetMapping("/{teamId}")
	public ResponseEntity<Team> getTeam(@PathVariable Long teamId) {
		Team team = teamService.getTeamById(teamId);

		if (team != null) {
			return ResponseEntity.ok(team);

		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Team> createTeam(@RequestBody Team team) {
	    Team createdTeam = teamService.createTeam(team);
	    return ResponseEntity.status(HttpStatus.CREATED).body(createdTeam);
	}



	// Update an existing team
	@PutMapping("/{teamId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Team> updateTeam(@PathVariable Long teamId, @RequestBody Team updatedTeam) {
		Team updated = teamService.updateTeam(teamId, updatedTeam);
		if (updated != null) {
			return ResponseEntity.ok(updated);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// Delete a team by ID
	 @DeleteMapping("/{teamId}")
	 @PreAuthorize("hasRole('ADMIN')")
	    public ResponseEntity<Void> deleteTeam(@PathVariable Long teamId) {
	        boolean deleted = teamService.deleteTeam(teamId);
	        if (deleted) {
	            return ResponseEntity.noContent().build();
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }
}
