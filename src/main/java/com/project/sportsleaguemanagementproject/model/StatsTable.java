package com.project.sportsleaguemanagementproject.model;

import javafx.scene.control.Button;

public class StatsTable {
    int    stat_id;
    int    aadhar_no;
    String name;
    int    stat_balls;
    int    stat_runs;
    int    stat_overs;
    int    stat_6s;
    int    stat_4s;
    int    stat_wide_balls;
    int    stat_wickets;
    int    stat_maidens;
    Button button;

    public StatsTable(int stat_id, int aadhar_no, String name, int stat_balls, int stat_runs, int stat_overs, int stat_6s, int stat_4s, int stat_wide_balls, int stat_wickets, int stat_maidens, Button button) {
        this.stat_id = stat_id;
        this.aadhar_no = aadhar_no;
        this.name = name;
        this.stat_balls = stat_balls;
        this.stat_runs = stat_runs;
        this.stat_overs = stat_overs;
        this.stat_6s = stat_6s;
        this.stat_4s = stat_4s;
        this.stat_wide_balls = stat_wide_balls;
        this.stat_wickets = stat_wickets;
        this.stat_maidens = stat_maidens;
        this.button = button;
    }

    public int getStat_id() {
        return stat_id;
    }

    public void setStat_id(int stat_id) {
        this.stat_id = stat_id;
    }

    public int getAadhar_no() {
        return aadhar_no;
    }

    public void setAadhar_no(int aadhar_no) {
        this.aadhar_no = aadhar_no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStat_balls() {
        return stat_balls;
    }

    public void setStat_balls(int stat_balls) {
        this.stat_balls = stat_balls;
    }

    public int getStat_runs() {
        return stat_runs;
    }

    public void setStat_runs(int stat_runs) {
        this.stat_runs = stat_runs;
    }

    public int getStat_overs() {
        return stat_overs;
    }

    public void setStat_overs(int stat_overs) {
        this.stat_overs = stat_overs;
    }

    public int getStat_6s() {
        return stat_6s;
    }

    public void setStat_6s(int stat_6s) {
        this.stat_6s = stat_6s;
    }

    public int getStat_4s() {
        return stat_4s;
    }

    public void setStat_4s(int stat_4s) {
        this.stat_4s = stat_4s;
    }

    public int getStat_wide_balls() {
        return stat_wide_balls;
    }

    public void setStat_wide_balls(int stat_wide_balls) {
        this.stat_wide_balls = stat_wide_balls;
    }

    public int getStat_wickets() {
        return stat_wickets;
    }

    public void setStat_wickets(int stat_wickets) {
        this.stat_wickets = stat_wickets;
    }

    public int getStat_maidens() {
        return stat_maidens;
    }

    public void setStat_maidens(int stat_maidens) {
        this.stat_maidens = stat_maidens;
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }
}
