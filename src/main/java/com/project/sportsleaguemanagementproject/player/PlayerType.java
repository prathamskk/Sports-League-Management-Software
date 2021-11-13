package com.project.sportsleaguemanagementproject.player;

public enum PlayerType {
    ALL_ROUNDER("all_rounder", 0),
    BATSMAN("batsman", 1),
    BOWLER("bowler", 2);

    private final String type;
    private final int id;

    PlayerType(String type, int id) {
        this.type = type;
        this.id = id;
    }
// TODO: 11/13/2021 change the comparisons to int everywhere
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
