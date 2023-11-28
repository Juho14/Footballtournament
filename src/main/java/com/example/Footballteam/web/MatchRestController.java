package com.example.Footballteam.web;

import com.example.Footballteam.domain.Footballmatch;
import com.example.Footballteam.domain.FootballmatchRepository;
import com.example.Footballteam.domain.FootballmatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/matches")
public class MatchRestController {

	@Autowired
	FootballmatchService matchService;
	
	@Autowired
	FootballmatchRepository matchRepo;

	// Get all matches
	@GetMapping
	public ResponseEntity<Iterable<Footballmatch>> get() {
		Iterable<Footballmatch> users = matchRepo.findAll();
		return ResponseEntity.ok(users);
	}

	// Get a match by ID
	@GetMapping("/{matchId}")
	public ResponseEntity<Footballmatch> getMatchById(@PathVariable Long matchId) {
		Footballmatch match = matchService.getMatchById(matchId);

		if (match != null) {
			return ResponseEntity.ok(match);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// Create a new match
	@PostMapping
	@PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
	public ResponseEntity<Footballmatch> createMatch(@RequestBody Footballmatch match) {
		Footballmatch createdMatch = matchService.createMatch(match);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdMatch);

	}

	// Update a match
	@PutMapping("/{matchId}")
	@PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
	public ResponseEntity<Footballmatch> updateMatch(@PathVariable Long matchId, @RequestBody Footballmatch updatedMatch) {
		Footballmatch result = matchService.updateMatch(matchId, updatedMatch);
		if (result != null) {
			return ResponseEntity.ok(result);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// Delete a match
	@DeleteMapping("/{matchId}")
	@PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
	public ResponseEntity<Void> deleteMatch(@PathVariable Long matchId) {
		boolean deleted = matchService.deleteMatch(matchId);
		return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
	}
}
