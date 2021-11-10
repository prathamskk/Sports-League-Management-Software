package com.project.sportsleaguemanagementproject.player;

public enum PlayerGender {
    MALE("male"),
    FEMALE("female"),
    OTHER("other");

    private final String gender;

    PlayerGender(String gender) {
        this.gender = gender;
    }

    public String getGender(){
        return gender;
    }

    public static String[] toArray(){
        int length = PlayerGender.values().length;
        PlayerGender[] playerGenderValues = PlayerGender.values();
        String[] ret = new String[length];
        for(int i = 0;  i < length; i++){
            ret[i] = playerGenderValues[i].getGender();
        }
        return ret;
    }
}