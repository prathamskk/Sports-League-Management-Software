package com.project.sportsleaguemanagementproject.model;

import javafx.scene.control.Button;

import java.util.Date;

public class ModelTournamentList {
    int id;
    String name;
    int prize;
    Date date;

    Button button;

    public ModelTournamentList(int tournament_id, String tournament_name, int  tournament_prize , Date tournament_date, Button button){
        this.id = tournament_id;
        this.name = tournament_name;
        this.prize = tournament_prize;
        this.date = tournament_date;
        this.button = button;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }
}
