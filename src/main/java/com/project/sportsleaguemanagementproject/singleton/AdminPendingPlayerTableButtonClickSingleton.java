package com.project.sportsleaguemanagementproject.singleton;

public class AdminPendingPlayerTableButtonClickSingleton {
    private static AdminPendingPlayerTableButtonClickSingleton single_instance = null;
    public String id;
    private AdminPendingPlayerTableButtonClickSingleton()
    {
        id = "DefaultUsername";
    }
    public static AdminPendingPlayerTableButtonClickSingleton getInstance()
    {
        if (single_instance == null)
            single_instance = new AdminPendingPlayerTableButtonClickSingleton();

        return single_instance;
    }
}
