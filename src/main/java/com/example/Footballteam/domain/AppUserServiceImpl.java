package com.example.Footballteam.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppUserServiceImpl implements AppUserService {

	@Autowired
	AppUserRepository userRepo;
	
	@Autowired
	TeamRepository teamRepo;
	
	@Autowired
	TeamService teamService;
	
	@Autowired
	FootballmatchRepository matchRepo;
	
	@Autowired
	FootballmatchService matchService;
	
    @Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public AppUser getUserById(Long userId) {
		Optional<AppUser> optionalUser = userRepo.findById(userId);
		return optionalUser.orElse(null);
	}

	@Override
	public AppUser createUser(AppUser user) {
	    user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
	    if (user.getRole().equals("ROLE_ADMIN")) {
	    	user.setKitnumber("ADMIN");
	    }
	    return userRepo.save(user);
	}


	@Override
	public AppUser updateUser(Long userId, AppUser updatedUser) {
	    Optional<AppUser> optionalExistingUser = userRepo.findById(userId);

	    if (optionalExistingUser.isPresent()) {
	        AppUser existingUser = optionalExistingUser.get();

	        existingUser.setName(updatedUser.getName());
	        existingUser.setPhone(updatedUser.getPhone());
	        existingUser.setEmail(updatedUser.getEmail());
	        existingUser.setBirthyear(updatedUser.getBirthyear());
	        existingUser.setKitNumber(updatedUser.getKitNumber());
	        existingUser.setUsername(updatedUser.getUsername());
	        existingUser.setRole(updatedUser.getRole());
	        existingUser.setTeam(updatedUser.getTeam());
	        return userRepo.save(existingUser);
	    } else {

	        return null;
	    }
	}
	
	@Override
	public AppUser changePassword(Long userId, AppUser updatedUser) {

	    Optional<AppUser> optionalExistingUser = userRepo.findById(userId);

	    if (optionalExistingUser.isPresent()) {
	        AppUser existingUser = optionalExistingUser.get();
	        existingUser.setPasswordHash(passwordEncoder.encode(updatedUser.getPasswordHash()));
	        return userRepo.save(existingUser);
	    } else {

	        return null;
	    }
	}

	@Override
	public boolean deleteUser(Long userId) {
	    Optional<AppUser> userOptional = userRepo.findById(userId);

	    if (userOptional.isPresent()) {
	        AppUser user = userOptional.get();

	        List<Footballmatch> allMatches = matchService.getAllMatches();
	        for (Footballmatch match : allMatches) {
	            if (match.getAttendees() != null) {
	                match.getAttendees().remove(user);
	                matchRepo.save(match);
	            }
	        }

	        user.setTeam(null);
	        userRepo.delete(user);
	        return true;
	    } else {
	        return false;
	    }
	}

}
