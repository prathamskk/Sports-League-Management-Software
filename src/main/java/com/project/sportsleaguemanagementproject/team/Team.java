package com.project.sportsleaguemanagementproject.team;

import com.project.sportsleaguemanagementproject.player.Player;

import java.util.Arrays;

public class Team {
    public String teamName;
    public int teamSize;

    public Player coach;
    public Player manager;
    public Player captain;

    public Player[] players;

    public Team(String teamName, Player manager, Player coach, Player[] players) {
        this.teamName = teamName;
        this.teamSize = players.length;
        this.coach = coach;
        this.manager = manager;
        this.players = players;
    }

    public Team() {

    }

    @Override
    public String toString() {
        return "Team{" +
                "teamName='" + teamName + '\'' +
                ", teamSize=" + teamSize +
                ", coach=" + coach +
                ", manager=" + manager +
                ", captain=" + captain +
                ", players=" + Arrays.toString(players) +
                '}';
    }
}
