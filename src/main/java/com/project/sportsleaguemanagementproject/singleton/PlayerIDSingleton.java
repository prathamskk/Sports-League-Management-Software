package com.project.sportsleaguemanagementproject.singleton;

public class PlayerIDSingleton {
    private static PlayerIDSingleton single_instance = null;
    public int id;
    private PlayerIDSingleton()
    {
    }
    public static PlayerIDSingleton getInstance()
    {
        if (single_instance == null)
            single_instance = new PlayerIDSingleton();

        return single_instance;
    }
}
