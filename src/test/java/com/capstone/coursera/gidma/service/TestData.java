package com.capstone.coursera.gidma.service;

import static com.capstone.coursera.gidma.service.utils.Utils.SDF_1;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import org.joda.time.DateTime;

import com.capstone.coursera.gidma.service.model.Follower;
import com.capstone.coursera.gidma.service.model.User;
import com.capstone.coursera.gidma.service.model.UserCheckIn;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This is a utility class to aid in the construction of Video objects with random names, urls, and durations. The class also provides a
 * facility to convert objects into JSON using Jackson, which is the format that the VideoSvc controller is going to expect data in for
 * integration testing.
 * 
 *
 */
public class TestData {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static int patientIdSuffix = 1;
    private static int followerIdSuffix = 1;
    
    private static List<String> firstAndLastNameListTeen = new ArrayList<String>();
    private static List<String> firstAndLastNameListFollowers = new ArrayList<String>();
    
    static{
        firstAndLastNameListTeen.add("Andy  Gardner");
        firstAndLastNameListTeen.add("Rolando   Hogan");
        firstAndLastNameListTeen.add("Sally Campbell");
        firstAndLastNameListTeen.add("Janie Burke");
        firstAndLastNameListTeen.add("Ellis French");
        firstAndLastNameListTeen.add("Natasha   Hubbard");
        firstAndLastNameListTeen.add("Beverly   Maxwell");
        firstAndLastNameListTeen.add("Susan Fletcher");
        firstAndLastNameListTeen.add("Theodore  Boyd");
        firstAndLastNameListTeen.add("Bobbie    Morton");
        
        firstAndLastNameListFollowers.add("Phil  Nichols");
        firstAndLastNameListFollowers.add("Teri  Kim");
        firstAndLastNameListFollowers.add("Kapil  Gupta");
        firstAndLastNameListFollowers.add("Noel  Ramsey");
        firstAndLastNameListFollowers.add("Oliver    Holland");
        firstAndLastNameListFollowers.add("Stacey    Harvey");
        firstAndLastNameListFollowers.add("Jose  Dawson");
        firstAndLastNameListFollowers.add("Pavan  Gupta");
        firstAndLastNameListFollowers.add("Dana  Simmons");
        firstAndLastNameListFollowers.add("Jon   Mccormick");
        firstAndLastNameListFollowers.add("Mitchell  Santos");
        firstAndLastNameListFollowers.add("Sheila    Lopez");
        firstAndLastNameListFollowers.add("Francis   Simon");
        firstAndLastNameListFollowers.add("Pavan  Jain");
        firstAndLastNameListFollowers.add("Nick  Owen");
        firstAndLastNameListFollowers.add("Katie Armstrong");
        firstAndLastNameListFollowers.add("Nicholas  Bryant");
        firstAndLastNameListFollowers.add("Clifford  Sharp");
        firstAndLastNameListFollowers.add("Test  Name");
    }
    
    

    public static User getRandomPatientUser() {
        User user = new User();

        // UUID.randomUUID().toString();

        //int nameRandomInt = randomInt(1, 11);
        String fullName = firstAndLastNameListTeen.get(patientIdSuffix);
        
        String id = String.valueOf(patientIdSuffix++);
        
        user.setUserId("test_Teen_" + id);
        user.setPassword("pass123");
        user.setFirstName(fullName.substring(0,fullName.indexOf(" ")).trim());
        user.setLastName(fullName.substring(fullName.indexOf(" "), fullName.length()).trim());
        user.setDateOfBirth(String.valueOf(randomInt(1, 12)) + "/" + String.valueOf(randomInt(1, 30)) + "/" + String.valueOf(randomInt(1995, 2005)) );
        user.setMedicalRecordNumber("MRN__" + id);
        user.setFollowed(true);

        return user;
    }

    public static User getRandomFollowerUser() {
        User user = new User();

        //int nameRandomInt = randomInt(12, 29);
        String fullName = firstAndLastNameListFollowers.get(followerIdSuffix);
        
        String id = String.valueOf(followerIdSuffix++);
        user.setUserId("test_Follower_" + id);
        user.setPassword("pass123");
        user.setFirstName(fullName.substring(0,fullName.indexOf(" ")).trim());
        user.setLastName(fullName.substring(fullName.indexOf(" "), fullName.length()).trim());
        user.setDateOfBirth(String.valueOf(randomInt(1, 12)) + "/" + String.valueOf(randomInt(1, 30)) + "/" + String.valueOf(randomInt(1970, 2000)) );

        return user;
    }

    public static List<UserCheckIn> getUserCheckinDataForDateRange(Date dt1, Date dt2, String userId) {
        List<UserCheckIn> userCheckInLists = new ArrayList<UserCheckIn>();

        if (dt2.after(dt1)) {

            long daysDiff = TimeUnit.DAYS.convert((dt2.getTime() - dt1.getTime()), TimeUnit.MILLISECONDS);

            System.out.println("daysDiff : " + daysDiff);

            long totalTimes = 3;

            DateTime dateTimeStart = new DateTime(dt1.getTime());

            for (int i = 0; i <= daysDiff; i++) {
                for (int j = 0; j < totalTimes; j++) {

                    UserCheckIn userCheckIn = new UserCheckIn();
                    userCheckIn.setUserId(userId);

                    Date currentDate = null;
                    if (j == 0) {
                        currentDate = dateTimeStart.withHourOfDay(8).toDate();
                        userCheckIn.setBloodSugarLvlFasting(String.valueOf(randomInt(50, 190)));//70, 150
                    } else if (j == 1) {
                        currentDate = dateTimeStart.withHourOfDay(14).toDate();
                        userCheckIn.setBloodSugarLvlMT(String.valueOf(randomInt(50, 170)));//90, 130
                    } else if (j == 2) {
                        currentDate = dateTimeStart.withHourOfDay(20).toDate();
                        userCheckIn.setBloodSugarLvlBT(String.valueOf(randomInt(50, 180)));//90, 150
                    }

                    try {
                        currentDate = SDF_1.parse(SDF_1.format(currentDate));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    userCheckIn.setCheckInDateTime(currentDate);

                    userCheckIn.setBloodSugarLvlTime(dateTimeStart.getHourOfDay() + ":" + dateTimeStart.getMinuteOfDay() + ":"
                            + dateTimeStart.getSecondOfDay());

                    userCheckIn.setEatMT("FoodNumber:" + String.valueOf(randomInt(1, 10)));
                    userCheckIn.setInsulin(((randomInt(0, 2) == 0) ? "N" : "Y"));
                    userCheckIn.setWhoWithYou("my Friend No. " + String.valueOf(randomInt(1, 10)));
                    userCheckIn.setWhereWereYou("zip: " + String.valueOf(randomInt(11111, 99999)));
                    userCheckIn.setMood(String.valueOf(randomInt(1, 6)));
                    userCheckIn.setStress(String.valueOf(randomInt(1, 6)));
                    userCheckIn.setEnergyLvl(String.valueOf(randomInt(0, 101)));
                    userCheckIn.setBloodSugarLvlTimeEvent(String.valueOf(randomInt(1, 12)));

                    userCheckInLists.add(userCheckIn);
                }

                dateTimeStart = dateTimeStart.plusDays(1);
            }

        } else {
            System.out.println("Date 2 : " + dt2 + " is before dt1 : " + dt1);
        }

        return userCheckInLists;
    }

    public static Follower getUserfollowerWithCheckInDataPrivacy(Follower follower) {

        follower.setBloodSugarLvlFasting(getRandonBoolean());
        follower.setBloodSugarLvlMT(getRandonBoolean());
        follower.setBloodSugarLvlBT(getRandonBoolean());
        follower.setBloodSugarLvlTime(getRandonBoolean());
        follower.setEatMT(getRandonBoolean());
        follower.setInsulin(getRandonBoolean());
        follower.setWhoWithYou(getRandonBoolean());
        follower.setWhereWereYou(getRandonBoolean());
        follower.setMood(getRandonBoolean());
        follower.setStress(getRandonBoolean());
        follower.setEnergyLvl(getRandonBoolean());
        follower.setBloodSugarLvlTimeEvent(getRandonBoolean());

        System.out.println("getUserfollowerWithCheckInDataPrivacy : follower : " + follower);

        return follower;
    }

    private static boolean getRandonBoolean() {
        return ((randomInt(0, 2) == 0) ? false : true);
    }

    public static int randomInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }

    /**
     * Convert an object to JSON using Jackson's ObjectMapper
     * 
     * @param o
     * @return
     * @throws Exception
     */
    public static String toJson(Object o) throws Exception {
        return objectMapper.writeValueAsString(o);
    }
}
