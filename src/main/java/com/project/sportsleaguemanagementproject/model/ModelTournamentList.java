package com.project.sportsleaguemanagementproject.model;

import java.util.Date;

public class ModelTournamentList {
    String name;
    int prize;
    Date date;
    int teams;

    public ModelTournamentList(String tournament_name,int  tournament_prize ,Date tournament_date ,int tournament_teams){
        this.name = tournament_name;
        this.prize = tournament_prize;
        this.date = tournament_date;
        this.teams = tournament_teams;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrize() {
        return prize;
    }

    public void setPrize(int prize) {
        this.prize = prize;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getTeams() {
        return teams;
    }

    public void setTeams(int teams) {
        this.teams = teams;
    }
}
