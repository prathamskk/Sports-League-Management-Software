package com.project.sportsleaguemanagementproject;

public class LoginSingleton {
    private static LoginSingleton single_instance = null;
    public String username;
    private LoginSingleton()
    {
        username = "DefaultUsername";
    }
    public static LoginSingleton getInstance()
    {
        if (single_instance == null)
            single_instance = new LoginSingleton();

        return single_instance;
    }
}
