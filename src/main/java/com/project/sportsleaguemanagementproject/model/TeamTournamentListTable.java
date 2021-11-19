package com.project.sportsleaguemanagementproject.model;

import javafx.scene.control.Button;

import java.sql.Date;

public class TeamTournamentListTable {
    String name;
    int prize;
    Date date;
    String venue;
    String maxTeams;
    Button viewButton;
    Button joinButton;

    public TeamTournamentListTable(String name, int prize, Date date, String venue, String maxTeams, Button viewButton, Button joinButton) {
        this.name = name;
        this.prize = prize;
        this.date = date;
        this.venue = venue;
        this.maxTeams = maxTeams;
        this.viewButton = viewButton;
        this.joinButton = joinButton;
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

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getMaxTeams() {
        return maxTeams;
    }

    public void setMaxTeams(String maxTeams) {
        this.maxTeams = maxTeams;
    }

    public Button getViewButton() {
        return viewButton;
    }

    public void setViewButton(Button viewButton) {
        this.viewButton = viewButton;
    }

    public Button getJoinButton() {
        return joinButton;
    }

    public void setJoinButton(Button joinButton) {
        this.joinButton = joinButton;
    }
}
