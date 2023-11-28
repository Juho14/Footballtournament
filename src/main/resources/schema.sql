USE footballteam;

-- Disable foreign key checks
SET foreign_key_checks = 0;

-- Drop tables in reverse order of dependency
DROP TABLE IF EXISTS match_attendees;
DROP TABLE IF EXISTS app_user;
DROP TABLE IF EXISTS footballmatch;
DROP TABLE IF EXISTS team;

-- Enable foreign key checks
SET foreign_key_checks = 1;

-- Create Team Table
CREATE TABLE team (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- Create FootballMatch Table
CREATE TABLE footballmatch (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    match_date DATE,
    result VARCHAR(255),
    home_team_id BIGINT,
    away_team_id BIGINT,
    FOREIGN KEY (home_team_id) REFERENCES team(id),
    FOREIGN KEY (away_team_id) REFERENCES team(id)
);

-- Create AppUser Table
CREATE TABLE app_user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    phone VARCHAR(15),
    email VARCHAR(255) NOT NULL,
    birthyear INT,
    kitnumber VARCHAR(20),
    username VARCHAR(255) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    role VARCHAR(20),
    team_id BIGINT,
    FOREIGN KEY (team_id) REFERENCES team(id)
);

-- Create Match_Attendees Table (Many-to-Many Relationship)
CREATE TABLE match_attendees (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    match_id BIGINT,
    attendee_id BIGINT,
    FOREIGN KEY (match_id) REFERENCES footballmatch(id),
    FOREIGN KEY (attendee_id) REFERENCES app_user(id)
);
