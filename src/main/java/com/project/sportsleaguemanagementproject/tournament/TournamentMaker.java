package com.project.sportsleaguemanagementproject.tournament;

import java.util.ArrayList;

@FunctionalInterface
public interface TournamentMaker {
    ArrayList<Match> CreateMatch();
}
