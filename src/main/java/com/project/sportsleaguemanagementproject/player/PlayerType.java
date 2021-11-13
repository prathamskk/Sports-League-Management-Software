package com.project.sportsleaguemanagementproject.player;

public enum PlayerType {
    ALL_ROUNDER("all_rounder"),
    BATSMAN("batsman"),
    BOWLER("bowler");

    private final String type;

    PlayerType(String type) {
        this.type = type;
    }

    public String getType(){
        return type;
    }

    public static String[] toArray(){
        int length = PlayerType.values().length;
        PlayerType[] playerTypeValues = PlayerType.values();
        String[] ret = new String[length];
        for(int i = 0;  i < length; i++){
            ret[i] = playerTypeValues[i].getType();
        }
        return ret;
    }
}
