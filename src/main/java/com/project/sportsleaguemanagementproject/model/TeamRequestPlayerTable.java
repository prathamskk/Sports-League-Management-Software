package com.project.sportsleaguemanagementproject.model;

public class TeamRequestPlayerTable {
    int aadharNo;
    String name;
   public TeamRequestPlayerTable(int aadharNo, String name){
       this.aadharNo = aadharNo;
       this.name = name;
   }

    public int getAadharNo() {
        return aadharNo;
    }

    public void setAadharNo(int aadharNo) {
        this.aadharNo = aadharNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
