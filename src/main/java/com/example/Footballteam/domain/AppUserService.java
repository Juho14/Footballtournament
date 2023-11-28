package com.example.Footballteam.domain;

public interface AppUserService {

    AppUser getUserById(Long userId);

	AppUser createUser(AppUser user);

    AppUser updateUser(Long userId, AppUser updatedUser);

    boolean deleteUser(Long userId);

	AppUser changePassword(Long userId, AppUser updatedUser);
}
