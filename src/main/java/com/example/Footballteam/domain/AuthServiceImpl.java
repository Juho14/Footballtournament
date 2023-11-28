package com.example.Footballteam.domain;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
    private AppUserRepository userRepo;

	@Override
	public AppUser getCurrentUser() {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    if (authentication != null) {
	        Object principal = authentication.getPrincipal();
	        if (principal instanceof UserDetails) {
	            UserDetails userDetails = (UserDetails) principal;
	            return userRepo.findByUsername(userDetails.getUsername());
	        }
	    }
	    return null;
	}

    @Override
    public String login() {
        return "login";
    }
}
