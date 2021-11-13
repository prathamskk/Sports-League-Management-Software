package com.project.sportsleaguemanagementproject.model;

import javafx.scene.control.Button;

import java.util.Date;

public class ModelUnverifiedPlayers {
    String username;
    int aadharNo;
    String name;
    String gender;
    Date dob;
    float weight;
    float height;
    String playerType;
    Button button;


    public ModelUnverifiedPlayers(String username, int aadharNo, String name, String gender, Date dob, float weight, float height, String playerType, Button button) {
        this.username = username;
        this.aadharNo = aadharNo;
        this.name = name;
        this.gender = gender;
        this.dob = dob;
        this.weight = weight;
        this.height = height;
        this.playerType = playerType;
        this.button = button;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAadharNo() {
        return aadharNo;
    }

    public void setAadharNo(int aadharNo) {
        this.aadharNo = aadharNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public String getPlayerType() {
        return playerType;
    }

    public void setPlayerType(String playerType) {
        this.playerType = playerType;
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }
}
