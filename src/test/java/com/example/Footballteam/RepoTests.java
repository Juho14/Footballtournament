package com.example.Footballteam;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import com.example.Footballteam.domain.AppUser;
import com.example.Footballteam.domain.AppUserRepository;
import com.example.Footballteam.domain.AppUserService;
import com.example.Footballteam.domain.Footballmatch;
import com.example.Footballteam.domain.FootballmatchRepository;
import com.example.Footballteam.domain.FootballmatchService;
import com.example.Footballteam.domain.Team;
import com.example.Footballteam.domain.TeamRepository;

import jakarta.transaction.Transactional;

@SpringBootTest(classes = FootballteamApplication.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RepoTests {

	@Autowired
	TeamRepository teamRepo;

	@Autowired
	FootballmatchRepository matchRepo;

	@Autowired
	FootballmatchService matchService;

	@Autowired
	AppUserRepository userRepo;

	@Autowired
	AppUserService userService;

	@Test
	@Transactional
	public void createNewTeamsAndMatch() {
		Team team1 = new Team("Wilds");
		Team team2 = new Team("Cheetas");
		LocalDate date = LocalDate.of(2023, 12, 24);
		teamRepo.save(team1);
		teamRepo.save(team2);
		Footballmatch match = new Footballmatch(team1, team2, date, "2-1");
		matchRepo.save(match);
		assertThat(team1.getId()).isNotNull();
		assertThat(team2.getId()).isNotNull();
		assertThat(match.getId()).isNotNull();
	}

	@Test
	@Transactional
	@Commit
	public void deleteTeam() {
	    Team team = new Team("Wilds");
	    System.out.println(team + " test team");
	    
	    assertThat(team.getId()).isNull();

	    teamRepo.save(team);
	    
	    assertThat(team.getId()).isNotNull();

	    teamRepo.delete(team);

	    assertThat(teamRepo.findById(team.getId())).isEmpty();
	}

	@Test
	@Transactional
	public void deleteMatches() {
	    List<Footballmatch> matches = matchService.getAllMatches();
	    assertThat(matches).isNotNull();

	    for (Footballmatch match : matches) {
	        assertThat(match).isNotNull();
	        matchService.deleteMatch(match.getId());
	    }

	    List<Footballmatch> remainingMatches = matchService.getAllMatches();
	    assertThat(remainingMatches).isEmpty();
	}


	@Test
	@Transactional
	public void createNewUser() {
		AppUser player = new AppUser("Nordic Meatshield", "0441982654", "paaland@citeh.com", 2000, "9", "Paaland",
				"$2y$10$.PlUVg7dZf09OlgnHpQCj.EfZm95qIW.4MW9B4uo7NNEFnHH390Ui", "ROLE_PLAYER");
		assertThat(player.getId()).isNull();
		userService.createUser(player);
		assertThat(player.getId()).isNotNull();
	}

	@Test
	@Transactional
	public void DeleteUser() {
		AppUser player = new AppUser("Nordic Meatshield", "0441982654", "paaland@citeh.com", 2000, "9", "Paaland",
				"$2y$10$.PlUVg7dZf09OlgnHpQCj.EfZm95qIW.4MW9B4uo7NNEFnHH390Ui", "ROLE_PLAYER");
		userService.createUser(player);
		assertThat(player.getId()).isNotNull();
		System.out.println(player + " Player deletion test");
		userService.deleteUser(player.getId());
		assertThat(userService.getUserById(player.getId())).isNull();

	}

}
