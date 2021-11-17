package com.project.sportsleaguemanagementproject.tournament;

import com.project.sportsleaguemanagementproject.team.Team;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class Tournament {
    String name;
    String host;
    TournamentTypes type;
    String prize;
    Date finalRegistrationDate;
    boolean registrationDone = false;
    Date startDate;
    Date endDate;
    ArrayList<Team> teams;
    int maxNoOfTeams;
    Brackets brackets;

    public Tournament(String name, String host, TournamentTypes type, String prize, Date finalRegistrationDate, Date startDate, int maxNoOfTeams) {
        this.name = name;
        this.host = host;
        this.type = type;
        this.prize = prize;
        this.finalRegistrationDate = finalRegistrationDate;
        this.startDate = startDate;
        this.maxNoOfTeams = maxNoOfTeams;
    }

    public void setEndDate(Date endDate){
        this.endDate = endDate;
    }

    public boolean addTeam(Team team){
        if(teams.size() < maxNoOfTeams) {
            teams.add(team);
            return true;
        }
        else return false;
    }
    public boolean addTeams(ArrayList<Team> teams){
        boolean addedAll = true;
        for (Team t: teams) {
            if(!addTeam(t)){
                addedAll = false;
                break;
            }
        }
        return  addedAll;
    }

    public void createBrackets(){
        brackets = new Brackets(teams);
    }
    public void updateBrackets(ArrayList<Team> winningTeams){
        brackets.updateBrackets(winningTeams);
    }

    public Brackets getBrackets() {
        return brackets;
    }
}
