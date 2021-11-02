package com.project.sportsleaguemanagementproject.match;

import com.project.sportsleaguemanagementproject.team.Team;

import java.time.LocalDate;

public class Match {
    private String host;
    private Team team1;
    private Team team2;
    private int overs;
    private String location;
    private LocalDate dateOfMatch;
    private String startTime;
    private int durationMinutes;

    public Match(String host, Team team1, Team team2, int overs, String location, LocalDate dateOfMatch, String startTime, int durationMinutes) {
        this.host = host;
        this.team1 = team1;
        this.team2 = team2;
        this.overs = overs;
        this.location = location;
        this.dateOfMatch = dateOfMatch;
        this.startTime = startTime;
        this.durationMinutes = durationMinutes;
    }

    public Match(Team team1, Team team2) {
        this.team1 = team1;
        this.team2 = team2;
    }

    public Match(Team team) {
        this.team1 = team;
        this.team2 = team;
    }

    @Override
    public String toString() {
        return "Match{" +
                "host='" + host + '\'' +
                ", team1=" + team1 +
                ", team2=" + team2 +
                ", overs=" + overs +
                ", location='" + location + '\'' +
                ", dateOfMatch=" + dateOfMatch +
                ", startTime='" + startTime + '\'' +
                ", durationMinutes=" + durationMinutes +
                '}';
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Team getTeam1() {
        return team1;
    }

    public void setTeam1(Team team1) {
        this.team1 = team1;
    }

    public Team getTeam2() {
        return team2;
    }

    public void setTeam2(Team team2) {
        this.team2 = team2;
    }

    public int getOvers() {
        return overs;
    }

    public void setOvers(int overs) {
        this.overs = overs;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getDateOfMatch() {
        return dateOfMatch;
    }

    public void setDateOfMatch(LocalDate dateOfMatch) {
        this.dateOfMatch = dateOfMatch;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }
}
