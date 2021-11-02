package com.project.sportsleaguemanagementproject.player;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Date;

public class Player {
    public String name;
    public int gender;
    public String dateOfBirth;
    public float height;
    public float weight;
    // playerType
    //playerStyle









    public Player(String name,int gender, String dateOfBirth, float height, float weight) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.height = height;
        this.weight = weight;
        this.gender = gender;

//        this.age = calculateAge();
    }

    private int getAge() {
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
