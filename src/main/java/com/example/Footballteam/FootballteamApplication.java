package com.example.Footballteam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FootballteamApplication {

//	private static final Logger log = LoggerFactory.getLogger(FootballteamApplication.class);

	// This is an application that helps people organize tournaments.

	public static void main(String[] args) {
		SpringApplication.run(FootballteamApplication.class, args);
	}

	/* 
	@Bean
	public CommandLineRunner dataLoader(TeamRepository teamRepo, FootballmatchRepository matchRepo, AppUserRepository userRepo,
			FootballmatchService matchService) {
		return (args) -> {
			log.info("Setting the data for teams and users.");

			Team team1 = new Team("Viikingit");
			Team team2 = new Team("FC Tikka");
			Team team3 = new Team("EsPa");

			teamRepo.saveAll(Arrays.asList(team1, team2, team3));

			LocalDate matchDate1 = LocalDate.of(2019, 9, 20); // Set the date for match1
			Footballmatch match1 = new Footballmatch(team1, team2, matchDate1, "4-2");

			LocalDate matchDate2 = LocalDate.of(2023, 11, 1); // Set the date for match2
			Footballmatch match2 = new Footballmatch(team3, team1, matchDate2, "1-1");

			LocalDate matchDate3 = LocalDate.of(2023, 11, 15); // Set the date for match3
			Footballmatch match3 = new Footballmatch(team2, team3, matchDate3, "TBD");

			// Save matches to the repository
			matchRepo.saveAll(Arrays.asList(match1, match2, match3));

			// Add players and managers
			 * 
			 * Test data for logging in: 
			 * 
			 * Role_Player
			 * Username: Paaland Password Paaland
			 * Username: Player Password Player
			 * 
			 * Role_Manager
			 * Username: Baldie Password Baldie
			 * Username: Manager Password Manager
			 * 
			 * Username: admin Password admin
			 * 
			AppUser player1 = new AppUser("Nordic Meatshield", "0441982654", "paaland@citeh.com", 2000, "9", "Paaland",
					"$2y$10$.PlUVg7dZf09OlgnHpQCj.EfZm95qIW.4MW9B4uo7NNEFnHH390Ui", "ROLE_PLAYER");
			AppUser player2 = new AppUser("John Cena", "0441234567", "John.Cena@WWE.com", 1978, "8", "JohnCena",
					"$2y$10$43wWCer5W.ridVvqqISXhetq31uxvfWL4676hpnZTXKMirzZQzJUK", "ROLE_PLAYER");
			AppUser player3 = new AppUser("player", "0508671842", "user@user.com", 2000, "14", "player",
					"$2y$10$A5hFKNht6mubrWKYeQwV6uqw3SfFSuZv296uHD71TYijGTj1fQWPi", "ROLE_PLAYER");
			AppUser player4 = new AppUser("Javi Garcia", "4554920396", "javig@gmail.com", 1987, "16", "Javig",
					"$2y$10$DRlxet1qjhVDJWgq/JZp.OAAuDBLCz710h0CItw6ec4TRhCBB1HxO", "ROLE_PLAYER");
			AppUser manager1 = new AppUser("Baldie Guardiola", "3333333333", "baldie@mcfc.com", 1968, "Manager",
					"Baldie", "$2y$10$EmXxHIYPhviww3bAugbGo.4bkCwixS4VGnz/LcLLuOvpWiUCfPah2", "ROLE_MANAGER");
			AppUser manager2 = new AppUser("Pen Pag", "3030303030", "bozo@glazers.com", 1975, "Manager", "Bozo",
					"$2y$10$8e8pMeTZ9UHRkfKsOl8cFuC/p6DBSZwnf/UzhFXENwd1o0S/NyzTq", "ROLE_MANAGER");
			AppUser manager3 = new AppUser("Manager", "5559185736", "robbo@brighton.com", 1984, "Manager", "manager",
					"$2y$10$G0euPvVITWrtvyo6YySgb.SbUOiTEZcqqBH/z9GDZPFcOuGEJ9dSm", "ROLE_MANAGER");

			AppUser admin = new AppUser("Admin", "+3581234567", "admin@tournamentapp.com", 2001, "ADMIN", "admin",
					"$2y$10$TRKRqzovS.nCIPhNE6N5VOXttdYGBJCJWfyIKDHQr/zxb4aBci5O.", "ROLE_ADMIN");
			userRepo.saveAll(Arrays.asList(admin, player1, player2, player3, player4, manager1, manager2, manager3));

			// Add players and managers to their respective teams
			player1.setTeam(team1);
			player2.setTeam(team2);
			player3.setTeam(team3);
			player4.setTeam(team1);
			manager1.setTeam(team1);
			manager2.setTeam(team2);
			manager3.setTeam(team3);

			team1.getMembers().addAll(Arrays.asList(player1, manager1, player4));
			team2.getMembers().addAll(Arrays.asList(player2, manager2));
			team3.getMembers().addAll(Arrays.asList(player3, manager3));

			// Save the teams with associated matches
			teamRepo.saveAll(Arrays.asList(team1, team2, team3));

			// Add attendees to matches. Leaving 1 out to test the attend feature.
			matchService.addAttendee(match1, player4); // Javi Garcia, Viikingit
			matchService.addAttendee(match1, player2); // John Cena, FC Tikka
			matchService.addAttendee(match1, manager1);// Baldie Guardiola, Viikingit
			matchService.addAttendee(match1, manager2);// Pen Pag, FC Tikka

			matchService.addAttendee(match2, player3);
			matchService.addAttendee(match2, manager1);
			matchService.addAttendee(match2, manager3);
			matchService.addAttendee(match2, player4);
			matchService.addAttendee(match2, player1);

			log.info("Saving all data.");
			for (Team team : teamRepo.findAll()) {
				log.info(team.toString());
			}
		};
	}
	*/
}