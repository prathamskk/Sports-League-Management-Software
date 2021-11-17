package com.project.sportsleaguemanagementproject.model;

import javafx.scene.control.Button;

public class PlayerTeamInvitesTable {
    String teamName;
    Button acceptButton;
    Button declineButton;

    public PlayerTeamInvitesTable(String teamName, Button acceptButton, Button declineButton) {
        this.teamName = teamName;
        this.acceptButton = acceptButton;
        this.declineButton = declineButton;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Button getAcceptButton() {
        return acceptButton;
    }

    public void setAcceptButton(Button acceptButton) {
        this.acceptButton = acceptButton;
    }

    public Button getDeclineButton() {
        return declineButton;
    }

    public void setDeclineButton(Button declineButton) {
        this.declineButton = declineButton;
    }
}
