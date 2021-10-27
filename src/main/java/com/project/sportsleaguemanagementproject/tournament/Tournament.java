//package com.project.sportsleaguemanagementproject.tournament;
//
//import com.project.sportsleaguemanagementproject.team.Team;
//
//import java.util.ArrayList;
//import java.util.Random;
//
//
//public class Tournament{
//    String name;
//    String host;
//    MatchTypes type;
//    Team[] teams;
//    ArrayList<Match> matches;
//
//    public Tournament(String name, String host, MatchTypes type, Team[] teams) {
//        this.name = name;
//        this.host = host;
//        this.type = type;
//        this.teams = teams;
//        shuffleTeams();
//    }
//
//    public void main(){
//        TournamentMaker maker;
//        switch (type) {
//            case ONE_DAY_INTERNATIONAL -> maker = () -> null;
//            case T_20 -> {
//            }
//            case TEST_MATCH -> maker = () -> {
//                ArrayList<Match> matches = new ArrayList<>();
//                matches.add(new Match(
//                        host,
//                        teams[0],
//                        teams[1],
//                        -1,
//                        "",
//                        "",
//                        "",
//                        -1
//                    )
//                );
//                return matches;
//            };
//            default -> throw new IllegalStateException("Unexpected value: " + type);
//        }
//    }
//
//    private void createBrackets(int overs){
//        for(int i = 0; i < teams.length; i += 2){
//            matches.add(new Match(
//                    "",
//                    teams[i],
//                    teams[i+1],
//                    overs,
//                    "",
//                    "",
//                    "",
//                    -1
//                )
//            );
//        }
//    }
//
//    private  void shuffleTeams(){
//        int index;
//        Team temp;
//        Random random = new Random();
//        for (int i = teams.length - 1; i > 0; i--)
//        {
//            index = random.nextInt(i + 1);
//            temp = teams[index];
//            teams[index] = teams[i];
//            teams[i] = temp;
//        }
//    }
//}
