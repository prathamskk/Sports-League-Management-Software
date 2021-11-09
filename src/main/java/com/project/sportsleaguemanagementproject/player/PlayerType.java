package com.project.sportsleaguemanagementproject.player;

public enum PlayerType {
    ALL_ROUNDER("all_rounder"),
    BATSMAN("batsman"),
    BOWLER("bowler"),
    VALUES("");

    private String type;

    PlayerType(String type) {
        this.type = type;
    }

    public String getType(){
        return type;
    }

    public String[] toArray(){
        int length = PlayerType.values().length - 1;
        PlayerType[] playerTypeValues = PlayerType.values();
        String[] ret = new String[length];
        for(int i = 0;  i < length; i++){
            ret[i] = playerTypeValues[i].getType();
        }
        return ret;
    }
}
