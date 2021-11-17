package com.project.sportsleaguemanagementproject.singleton;

public class TournamentTableButtonClickSingleton {
    private static TournamentTableButtonClickSingleton single_instance = null;
    public int id;
    private TournamentTableButtonClickSingleton()
    {
    }
    public static TournamentTableButtonClickSingleton getInstance()
    {
        if (single_instance == null)
            single_instance = new TournamentTableButtonClickSingleton();

        return single_instance;
    }
}
