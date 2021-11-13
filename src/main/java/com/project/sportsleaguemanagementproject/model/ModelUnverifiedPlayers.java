package com.project.sportsleaguemanagementproject.model;

import java.util.Date;

public class ModelUnverifiedPlayers {
    String name;
    int prize;
    Date date;

    public ModelUnverifiedPlayers(String tournament_name, int tournament_prize, Date registration_date) {
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
}
