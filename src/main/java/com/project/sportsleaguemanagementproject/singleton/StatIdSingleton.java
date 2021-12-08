package com.project.sportsleaguemanagementproject.singleton;

public class StatIdSingleton {
    private static StatIdSingleton single_instance = null;
    public int id;
    private StatIdSingleton()
    {
    }
    public static StatIdSingleton getInstance()
    {
        if (single_instance == null)
            single_instance = new StatIdSingleton();

        return single_instance;
    }
}
