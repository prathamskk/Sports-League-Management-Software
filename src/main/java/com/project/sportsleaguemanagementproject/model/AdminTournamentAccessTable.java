package com.project.sportsleaguemanagementproject.model;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;

public class AdminTournamentAccessTable {
    String username;
    String status;
    Button button;

    public AdminTournamentAccessTable(String username, String status, Button button) {
        this.username = username;
        this.status = status;
        this.button = button;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }
}
