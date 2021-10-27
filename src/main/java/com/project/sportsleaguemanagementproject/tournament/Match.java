package com.project.sportsleaguemanagementproject.tournament;

import com.project.sportsleaguemanagementproject.team.Team;

public class Match {
    String host;
    Team team1;
    Team team2;
    int overs;
    String location;
    String dateOfMatch;
    String startTime;
    int durationMinutes;

    public Match(String host, Team team1, Team team2, int overs, String location, String dateOfMatch, String startTime, int durationMinutes) {
        this.host = host;
        this.team1 = team1;
        this.team2 = team2;
        this.overs = overs;
        this.location = location;
        this.dateOfMatch = dateOfMatch;
        this.startTime = startTime;
        this.durationMinutes = durationMinutes;
    }
}
