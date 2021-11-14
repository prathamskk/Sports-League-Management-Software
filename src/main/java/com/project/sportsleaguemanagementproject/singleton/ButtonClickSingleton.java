package com.project.sportsleaguemanagementproject.singleton;

public class ButtonClickSingleton {
    private static ButtonClickSingleton single_instance = null;
    public String id;
    private ButtonClickSingleton()
    {
        id = "DefaultUsername";
    }
    public static ButtonClickSingleton getInstance()
    {
        if (single_instance == null)
            single_instance = new ButtonClickSingleton();

        return single_instance;
    }
}
