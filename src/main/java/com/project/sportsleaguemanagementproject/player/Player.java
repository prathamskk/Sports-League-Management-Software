package com.project.sportsleaguemanagementproject.player;

public class Player {
    public String name;
    public int gender;
    public String dateOfBirth;
    public float height;
    public float weight;
    // playerType
    //playerStyle

    public Player(String name, int gender, String dateOfBirth, float height, float weight) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.height = height;
        this.weight = weight;
        this.gender = gender;
    }

//    private int getAge() {
//        int age = -1;
//
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//        Date date;
//        try {
//            date = formatter.parse(dateOfBirth);
//            Instant instant = date.toInstant();
//            ZonedDateTime zone = instant.atZone(ZoneId.systemDefault());
//            LocalDate givenDate = zone.toLocalDate();
//            Period period = Period.between(givenDate, LocalDate.now());
//
//            age = period.getYears();
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        return age;
//    }
}
