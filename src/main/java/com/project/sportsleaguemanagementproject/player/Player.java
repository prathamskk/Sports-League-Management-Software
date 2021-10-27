package com.project.sportsleaguemanagementproject.player;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Date;

public class Player {
    //    matches, runs, highest score, average strike rate
//    batsman, baller, all rounder, wicket keeper
//    batting style, bowling style
    public String nationality;
    public String name;
    public float height;
    public float weight;
    public int gender;
    public String dateOfBirth;
    public int age;

    public Player(String name, String dateOfBirth, int gender, String nationality, float height, float weight) {
        this.nationality = nationality;
        this.name = name;
        this.height = height;
        this.weight = weight;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.age = calculateAge();
    }

    private int calculateAge() {
        int age = -1;

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try {
            date = formatter.parse(dateOfBirth);
            Instant instant = date.toInstant();
            ZonedDateTime zone = instant.atZone(ZoneId.systemDefault());
            LocalDate givenDate = zone.toLocalDate();
            Period period = Period.between(givenDate, LocalDate.now());

            age = period.getYears();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return age;
    }
}
