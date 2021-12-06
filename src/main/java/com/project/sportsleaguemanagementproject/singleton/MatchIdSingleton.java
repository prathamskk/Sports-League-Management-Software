package com.project.sportsleaguemanagementproject.singleton;

public class MatchIdSingleton {
    private static MatchIdSingleton single_instance = null;
    public int id;
    private MatchIdSingleton()
    {
    }
    public static MatchIdSingleton getInstance()
    {
        if (single_instance == null)
            single_instance = new MatchIdSingleton();

        return single_instance;
    }
}
