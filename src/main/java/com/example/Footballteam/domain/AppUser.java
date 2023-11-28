package com.example.Footballteam.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    private String name;
    private String phone;
    private String email;
    private int birthyear;
    private String kitnumber;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "role", nullable = false)
    private String role;

    @ManyToMany(mappedBy = "attendees", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Footballmatch> attendedMatches;

    public AppUser() {
    }

    public AppUser(String name, String phone, String email, int birthyear, String kitnumber,
            String username, String passwordHash, String role) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.birthyear = birthyear;
        this.kitnumber = kitnumber;
        this.username = username;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKitNumber() {
        return kitnumber;
    }

    public void setKitNumber(String kitNumber) {
        this.kitnumber = kitNumber;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getBirthyear() {
        return birthyear;
    }

    public void setBirthyear(int birthyear) {
        this.birthyear = birthyear;
    }

    public String getKitnumber() {
        return kitnumber;
    }

    public void setKitnumber(String kitnumber) {
        this.kitnumber = kitnumber;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    @Override
    public String toString() {
        return "AppUser [id=" + id + ", name=" + name + ", username=" + username + ", passwordHash=" + passwordHash
                + ", role=" + role + "]";
    }

}
