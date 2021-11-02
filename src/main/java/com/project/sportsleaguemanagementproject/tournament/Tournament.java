package com.project.sportsleaguemanagementproject.tournament;

import com.project.sportsleaguemanagementproject.match.Match;
import com.project.sportsleaguemanagementproject.team.Team;

import java.util.ArrayList;

public class Tournament {
    String name;
    String host;
    TournamentTypes type;
    ArrayList<Team> teams;
    ArrayList<Match> matches;

    public Tournament(String name, String host, TournamentTypes type, ArrayList<Team> teams, ArrayList<Match> matches) {
        this.name = name;
        this.host = host;
        this.type = type;
        this.teams = teams;
        this.matches = matches;
    }

//    public void main(){
//        createBrackets();
//
//        switch (type) {
//            case TEST_MATCH -> {
//            }
//            case ONE_DAY_INTERNATIONAL -> {
//            }
//            case T_20 -> {
//            }
//            case T_10 -> {
//            }
//            case INDIVIDUAL_MATCH -> {
//                matches.get(0).setDateOfMatch("yyyy-mm-dd");
//            }
//            default -> throw new IllegalStateException("Unexpected value: " + type);
//        }
//
//
//    }

//    public void createBrackets(){
//        while (matches.isEmpty()) {
//            matches.add(new Match(teams.get(0), teams.get(1)));
//            teams.remove(0);
//            teams.remove(1);
//        }
//    }
}
