package com.example.Footballteam.domain;

public interface AuthService {
    AppUser getCurrentUser();
    String login();
}
