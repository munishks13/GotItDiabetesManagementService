package com.capstone.coursera.gidma.service.integration.test;

import static com.capstone.coursera.gidma.service.utils.Utils.DATE_FORMAT_1;
import static com.capstone.coursera.gidma.service.utils.Utils.displayExecutionInfo;
import static com.capstone.coursera.gidma.service.utils.Utils.displayExecutionStartInfo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.junit.Test;

import retrofit.RestAdapter.LogLevel;
import retrofit.client.ApacheClient;
import retrofit.converter.GsonConverter;

import com.capstone.coursera.gidma.service.TestData;
import com.capstone.coursera.gidma.service.client.GIDMASvcApi;
import com.capstone.coursera.gidma.service.client.SecuredRestBuilder;
import com.capstone.coursera.gidma.service.model.BloodSugarLevelTimeEvents;
import com.capstone.coursera.gidma.service.model.CheckInQuestion;
import com.capstone.coursera.gidma.service.model.Follower;
import com.capstone.coursera.gidma.service.model.User;
import com.capstone.coursera.gidma.service.model.UserBasicInfo;
import com.capstone.coursera.gidma.service.model.UserCheckIn;
import com.capstone.coursera.gidma.service.utils.ControllerServicePathConstants;
import com.capstone.coursera.gidma.service.utils.CustomQueryParams;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GIDMASvcClientApiTest {

    private final String USERNAME = "admin";
    private final String PASSWORD = "pass";

    private final String USERNAME_SIGNUP = "signUpUser";
    private final String PASSWORD_SIGNUP = "signUpUserPass";

    private final String CLIENT_ID = "mobile";
    private final String READ_ONLY_CLIENT_ID = "mobileReader";

    private final String TEST_URL_SECURE = "https://localhost:8443";

    Gson gson = new GsonBuilder().setDateFormat(DATE_FORMAT_1).create();

    private SecuredRestBuilder securedRestBuilder = new SecuredRestBuilder()
            .setLoginEndpoint(TEST_URL_SECURE + ControllerServicePathConstants.TOKEN_PATH).setClientId(CLIENT_ID)
            .setClient(new ApacheClient(UnsafeHttpsClient.createUnsafeClient())).setEndpoint(TEST_URL_SECURE).setLogLevel(LogLevel.BASIC)
            .setConverter(new GsonConverter(gson));

    private GIDMASvcApi gidmaSvcSignUp = securedRestBuilder.setUsername(USERNAME_SIGNUP).setPassword(PASSWORD_SIGNUP).build()
            .create(GIDMASvcApi.class);

    private GIDMASvcApi gidmaSvcAdmin = securedRestBuilder.setUsername(USERNAME).setPassword(PASSWORD).build().create(GIDMASvcApi.class);

    private User patientUser1 = TestData.getRandomPatientUser();
    private User patientUser2 = TestData.getRandomPatientUser();
    private User patientUser3 = TestData.getRandomPatientUser();
    private User patientUser4 = TestData.getRandomPatientUser();
    private User patientUser5 = TestData.getRandomPatientUser();

    private User FollowerUser1 = TestData.getRandomFollowerUser();
    private User FollowerUser2 = TestData.getRandomFollowerUser();
    private User FollowerUser3 = TestData.getRandomFollowerUser();
    private User FollowerUser4 = TestData.getRandomFollowerUser();
    private User FollowerUser5 = TestData.getRandomFollowerUser();
    private User FollowerUser6 = TestData.getRandomFollowerUser();
    private User FollowerUser7 = TestData.getRandomFollowerUser();
    private User FollowerUser8 = TestData.getRandomFollowerUser();
    private User FollowerUser9 = TestData.getRandomFollowerUser();
    private User FollowerUser10 = TestData.getRandomFollowerUser();

    private final List<CheckInQuestion> checkInQuestionList = gidmaSvcSignUp.getCheckInQuestions();
    private final List<BloodSugarLevelTimeEvents> bslteList = gidmaSvcSignUp.getBloodSugarLevelTimeEvents();

    @Test
    public void testTheSystem() throws Exception {
        String functionName = "===>>>> testTheSystem: ";
        displayExecutionStartInfo(functionName);
        displayExecutionInfo(functionName + "\n\n \t\t  **************** STARTING ****************  \n\n");

        displayExecutionInfo(functionName + "Creating all the Users.............: ");

        /*
         * 
         * Creating all the Users.......patient.......and followers........
         */

        // Register Patient User 1
        User patientUser1FromDB = gidmaSvcSignUp.signUpUser(patientUser1);
        assertEquals(patientUser1.getUserId(), patientUser1FromDB.getUserId());

        // Register Patient User 2
        User patientUser2FromDB = gidmaSvcSignUp.signUpUser(patientUser2);
        assertEquals(patientUser2.getUserId(), patientUser2FromDB.getUserId());

        // Register Follower User 1
        User followerUser1FromDB = gidmaSvcSignUp.signUpUser(FollowerUser1);
        assertEquals(FollowerUser1.getUserId(), followerUser1FromDB.getUserId());

        // Register Follower User 2
        User followerUser2FromDB = gidmaSvcSignUp.signUpUser(FollowerUser2);
        assertEquals(FollowerUser2.getUserId(), followerUser2FromDB.getUserId());

        // Register Follower User 3
        User followerUser3FromDB = gidmaSvcSignUp.signUpUser(FollowerUser3);
        assertEquals(FollowerUser3.getUserId(), followerUser3FromDB.getUserId());

        // Add some additional User... patient and followers...
        User patientUser3FromDB = gidmaSvcSignUp.signUpUser(patientUser3);
        User patientUser4FromDB = gidmaSvcSignUp.signUpUser(patientUser4);
        User patientUser5FromDB = gidmaSvcSignUp.signUpUser(patientUser5);
        
        gidmaSvcSignUp.signUpUser(FollowerUser4);
        gidmaSvcSignUp.signUpUser(FollowerUser5);
        gidmaSvcSignUp.signUpUser(FollowerUser6);
        gidmaSvcSignUp.signUpUser(FollowerUser7);
        gidmaSvcSignUp.signUpUser(FollowerUser8);
        gidmaSvcSignUp.signUpUser(FollowerUser9);
        gidmaSvcSignUp.signUpUser(FollowerUser10);
        
        

        displayExecutionInfo(functionName + "Creating All Users Service APIs using their login and password info..............: ");

        // Now Get the Service ApIs for all the users
        GIDMASvcApi gidmaSvcPatient1 = getUserSpecificServiceApis(patientUser1FromDB);
        GIDMASvcApi gidmaSvcPatient2 = getUserSpecificServiceApis(patientUser2FromDB);
        GIDMASvcApi gidmaSvcPatient3 = getUserSpecificServiceApis(patientUser3FromDB);

        GIDMASvcApi gidmaSvcFollower1 = getUserSpecificServiceApis(followerUser1FromDB);
        GIDMASvcApi gidmaSvcFollower2 = getUserSpecificServiceApis(followerUser2FromDB);
        GIDMASvcApi gidmaSvcFollower3 = getUserSpecificServiceApis(followerUser3FromDB);

        displayExecutionInfo(functionName + "getting all user from DB using their Service APIs..........: ");

        /*
         * 
         * Now get all user from DB using their respective Service APIs.
         */
        patientUser1FromDB = gidmaSvcPatient1.getUser(patientUser1.getUserId());
        patientUser2FromDB = gidmaSvcPatient2.getUser(patientUser2.getUserId());

        followerUser1FromDB = gidmaSvcFollower1.getUser(FollowerUser1.getUserId());
        followerUser2FromDB = gidmaSvcFollower2.getUser(FollowerUser2.getUserId());
        followerUser3FromDB = gidmaSvcFollower3.getUser(FollowerUser3.getUserId());

        /*
         * 
         * perform Some Check-in by Patient Users
         */

        displayExecutionInfo(functionName + "Perform Some Check in for Patient Users............. Checking in for Patient 1: ");

        performCheckInforUserAndTest(new DateTime(2015, 10, 10, 0, 0, 0).toDate(), new DateTime(2015, 11, 17, 0, 0, 0).toDate(),
                patientUser1FromDB.getUserId(), gidmaSvcPatient1);

        displayExecutionInfo(functionName + "Perform Some Check in for Patient Users............. Checking in for Patient 2: ");

        performCheckInforUserAndTest(new DateTime(2015, 10, 23, 0, 0, 0).toDate(), new DateTime(2015, 11, 17, 0, 0, 0).toDate(),
                patientUser2FromDB.getUserId(), gidmaSvcPatient2);

        displayExecutionInfo(functionName + "Perform Some Check in for Patient Users............. Checking in for Patient 3: ");

        performCheckInforUserAndTest(new DateTime(2015, 10, 23, 0, 0, 0).toDate(), new DateTime(2015, 11, 17, 0, 0, 0).toDate(),
                patientUser3FromDB.getUserId(), gidmaSvcPatient3);
        
        /*
         * 
         * Get the List of Patients to Follow...... from Patient and Follower perspective.... it should be 1 (excluding him/herself) and 2
         * respectively....
         */
        List<User> patientListFromPatient = gidmaSvcPatient1.getPatientUsersList();
        assertTrue(patientListFromPatient.size() == 4);

        List<User> patientListFromFollower = gidmaSvcFollower1.getPatientUsersList();
        assertTrue(patientListFromFollower.size() == 5);

        /*
         * 
         * Place follow request .......For Patient -1 from Follower 1 and 2
         */

        displayExecutionInfo(functionName + "Place Follower Request............. for Patient 1 from Follower 1 and 2 : ");
        Follower follower1 = placeApprovalRequestAndTest(gidmaSvcFollower1, followerUser1FromDB.getUserId(), patientUser1FromDB.getUserId());
        Follower follower2 = placeApprovalRequestAndTest(gidmaSvcFollower2, followerUser2FromDB.getUserId(), patientUser1FromDB.getUserId());

        displayExecutionInfo(functionName + "Approve Follower Request............. for Patient 1 from Follower 1 and 2 : ");

        List<UserBasicInfo> followUserListForPatient1 = gidmaSvcPatient1.getFollowersList();
        assertTrue(followUserListForPatient1.size() == 2);

        follower1 = gidmaSvcPatient1.approveFollowRequest(TestData.getUserfollowerWithCheckInDataPrivacy(follower1));
        assertTrue(follower1.isConfirmed());

        follower2 = gidmaSvcPatient1.approveFollowRequest(TestData.getUserfollowerWithCheckInDataPrivacy(follower2));
        assertTrue(follower2.isConfirmed());

        followUserListForPatient1 = gidmaSvcPatient1.getFollowersList();
        assertTrue(followUserListForPatient1.size() == 2);// till now it have 2 followers...

        List<UserBasicInfo> followUserListForFollower1 = gidmaSvcFollower1.getFollowingList();
        assertTrue(followUserListForFollower1.size() == 1);// till now it have 1 user to follow...

        List<UserBasicInfo> followUserListForFollower2 = gidmaSvcFollower2.getFollowingList();
        assertTrue(followUserListForFollower2.size() == 1);// till now it have 1 user to follow...

        /*
         * 
         * Place follow request .......For Patient -2 from Follower 1 and 2 and 3
         */

        displayExecutionInfo(functionName + "Place Follower Request............. for Patient 2 from Follower 1 and 2 and 3: ");
        follower1 = placeApprovalRequestAndTest(gidmaSvcFollower1, followerUser1FromDB.getUserId(), patientUser2FromDB.getUserId());
        follower2 = placeApprovalRequestAndTest(gidmaSvcFollower2, followerUser2FromDB.getUserId(), patientUser2FromDB.getUserId());
        Follower follower3 = placeApprovalRequestAndTest(gidmaSvcFollower3, followerUser3FromDB.getUserId(), patientUser2FromDB.getUserId());
        Follower followerPatientUser1 = placeApprovalRequestAndTest(gidmaSvcPatient1, patientUser1FromDB.getUserId(),
                patientUser2FromDB.getUserId());

        displayExecutionInfo(functionName + "Approve Follower Request............. for Patient 2 from Follower 1 and 2 and 3 : ");

        List<UserBasicInfo> followUserListForPatient2 = gidmaSvcPatient2.getFollowersList();
        displayExecutionInfo("\n\n\n\n ==============>>>>>> Approve Request......patientUser2FromDB : " + patientUser2FromDB);
        assertTrue(followUserListForPatient2.size() == 4);

        /*
         * 
         * Approve MULTIPLE request together.......For Patient -2 from Follower 1 and 2 and 3
         */

        List<Follower> followersList = new ArrayList<Follower>();

        followersList.add(TestData.getUserfollowerWithCheckInDataPrivacy(follower1));
        followersList.add(TestData.getUserfollowerWithCheckInDataPrivacy(follower2));
        followersList.add(TestData.getUserfollowerWithCheckInDataPrivacy(follower3));
        followersList.add(TestData.getUserfollowerWithCheckInDataPrivacy(followerPatientUser1));

        List<Follower> followersListFromDb = gidmaSvcPatient2.approveOrUpdateMultipleFollowRequest(followersList, patientUser2.getUserId());
        for (Follower follower : followersListFromDb) {
            assertNotNull(follower);
            assertTrue(follower.isConfirmed());
        }

        followUserListForPatient2 = gidmaSvcPatient2.getFollowersList();
        assertTrue(followUserListForPatient2.size() == 4);// till now it have 3 followers... + 1 patientUser follower...

        followUserListForFollower1 = gidmaSvcFollower1.getFollowingList();
        assertTrue(followUserListForFollower1.size() == 2);// till now it have 2 user to follow...

        followUserListForFollower2 = gidmaSvcFollower2.getFollowingList();
        assertTrue(followUserListForFollower2.size() == 2);// till now it have 2 user to follow...

        List<UserBasicInfo> followUserListForFollower3 = gidmaSvcFollower3.getFollowingList();
        assertTrue(followUserListForFollower3.size() == 1);// till now it have 1 user to follow...

        /*
         * 
         * Now Check in info retrieved from follower and same patient is of same number....
         */
        CustomQueryParams queryParams = new CustomQueryParams();
        queryParams.setUserId(patientUser1FromDB.getUserId());

        List<UserCheckIn> userCheckInListsFromDbFromPatient1 = gidmaSvcPatient1.getUserCheckInInfo(queryParams);
        List<UserCheckIn> userCheckInListsFromDbFromFollower1 = gidmaSvcFollower1.getUserCheckInInfo(queryParams);
        assertEquals(userCheckInListsFromDbFromPatient1.size(), userCheckInListsFromDbFromFollower1.size());

        /*
         * 
         * Now Check in info with Date Range for ....
         * 
         * For Patient 1 the check-in data entered for new DateTime(2015, 10, 10, 0, 0, 0).toDate(), new DateTime(2015, 11, 8, 0, 0, 0) 3
         * times per day...
         * 
         * so From date only from 2015-10-28 should bring .... 21 * 3 = 63
         * 
         * so TO date only from starting to 2015-10-28 should bring .... 19 * 3 = 57
         * 
         * so From date only from 2015-10-15 to 2015-11-04 should bring .... 21 * 3 = 63
         */
        queryParams.setFromDate("2015-10-28");
        queryParams.setToDate(null);
        userCheckInListsFromDbFromFollower1 = gidmaSvcFollower1.getUserCheckInInfo(queryParams);
        assertTrue(userCheckInListsFromDbFromFollower1.size() == 63);

        queryParams.setFromDate(null);
        queryParams.setToDate("2015-10-28");
        userCheckInListsFromDbFromFollower1 = gidmaSvcFollower1.getUserCheckInInfo(queryParams);
        assertTrue(userCheckInListsFromDbFromFollower1.size() == 57);

        queryParams.setFromDate("2015-10-15");
        queryParams.setToDate("2015-11-04");
        userCheckInListsFromDbFromFollower1 = gidmaSvcFollower1.getUserCheckInInfo(queryParams);
        assertTrue(userCheckInListsFromDbFromFollower1.size() == 63);

        /*
         * 
         * Now Check the privacy info.... just just some 2-3 day data and compare with the privacy...setup by patientUser1FromDB get the
         * privacy map and compare the retrieved checkin data from pricay map.
         */
        queryParams.setFromDate("2015-10-19");
        queryParams.setToDate("2015-10-21");
        userCheckInListsFromDbFromFollower1 = gidmaSvcFollower1.getUserCheckInInfo(queryParams);

        followerUser1FromDB = gidmaSvcFollower1.getUser(followerUser1FromDB.getUserId());

        // displayExecutionInfo("=================>>>>>>>> : followerUser1FromDB.getFollowers() : \n\n " +
        // followerUser1FromDB.getFollowers());

        Follower followerToMatch = new Follower();
        for (Follower follower : followerUser1FromDB.getFollowers()) {
            if (follower.getUserId().equals(queryParams.getUserId())) {
                followerToMatch = follower;
                break;
            }
        }

        for (UserCheckIn userCheckIn : userCheckInListsFromDbFromFollower1) {

            testForCheckInData(followerToMatch.isBloodSugarLvlFasting(), userCheckIn.getBloodSugarLvlFasting());
            testForCheckInData(followerToMatch.isBloodSugarLvlMT(), userCheckIn.getBloodSugarLvlMT());
            testForCheckInData(followerToMatch.isBloodSugarLvlBT(), userCheckIn.getBloodSugarLvlBT());
            testForCheckInData(followerToMatch.isBloodSugarLvlTime(), userCheckIn.getBloodSugarLvlTime());
            testForCheckInData(followerToMatch.isEatMT(), userCheckIn.getEatMT());
            testForCheckInData(followerToMatch.isInsulin(), userCheckIn.getInsulin());
            testForCheckInData(followerToMatch.isWhoWithYou(), userCheckIn.getWhoWithYou());
            testForCheckInData(followerToMatch.isWhereWereYou(), userCheckIn.getWhereWereYou());
            testForCheckInData(followerToMatch.isMood(), userCheckIn.getMood());
            testForCheckInData(followerToMatch.isStress(), userCheckIn.getStress());
            testForCheckInData(followerToMatch.isEnergyLvl(), userCheckIn.getEnergyLvl());
            testForCheckInData(followerToMatch.isBloodSugarLvlTimeEvent(), userCheckIn.getBloodSugarLvlTimeEvent());
        }

        /*
         * 
         * Now follower 3 try to access Patient1 data for which he/she is not follower...
         */
        try {
            gidmaSvcFollower3.getUserCheckInInfo(queryParams);
        } catch (Exception eX) {
            assertTrue(eX instanceof IllegalAccessException);
        }

        List<UserBasicInfo> patientUserForFollowListForPatient1 = gidmaSvcPatient1.getPatientUserForFollowList();
        assertTrue(patientUserForFollowListForPatient1.size() == 3);// till now it have Patient 1 following patient 2 and exclude
                                                                    // him/herself from list...should be 3

        List<UserBasicInfo> patientUserForFollowListForFollower1 = gidmaSvcFollower1.getPatientUserForFollowList();
        assertTrue(patientUserForFollowListForFollower1.size() == 3);// till now it already following 2

        List<UserBasicInfo> patientUserForFollowListForFollower3 = gidmaSvcFollower3.getPatientUserForFollowList();
        assertTrue(patientUserForFollowListForFollower3.size() == 4);// till now it already following 1

        displayExecutionInfo(functionName + "\n\n \t\t  **************** ++++ FINISHED ++++ ****************  \n\n");
    }

    private void testForCheckInData(boolean data, String compareData) {
        if (!data) {// if data is false ..check no data for that key in checkinMap.
            assertTrue(StringUtils.isBlank(compareData));
        }
    }

    // @Test
    // @Ignore
    // public void testAllUserList() throws Exception {
    // displayExecutionStartInfo("testAllUserList");
    //
    // List<User> userList = gidmaSvcAdmin.allUserList();
    // for (User user : userList) {
    // System.out.println("\n user: " + user);
    // }
    // }

    // @Test
    // public void testTest1() throws Exception {
    // System.out.println("\n\n\n\n\n \t ===========>>>>> Executing ....testTest1 =========>>>> \n\n\n\n\n");
    // User userReceived = gidmaSvc.test1("myTestUserId");
    // System.out.println("testTest1 : userReceived : " + userReceived);
    // assertEquals("myTestUserId", userReceived.getUserId());
    // }
    //
    // @Test
    // public void testTest2() throws Exception {
    // System.out.println("\n\n\n\n\n \t ===========>>>>> Executing ....testTest2 =========>>>> \n\n\n\n\n");
    //
    // User userForSignUp = new User();
    // userForSignUp.setUserId("test2");
    // User userReceived = gidmaSvc.test2(userForSignUp);
    //
    // System.out.println("testTest2 : userForSignUp : " + userForSignUp);
    // System.out.println("testTest2 : userReceived : " + userReceived);
    // assertEquals(userForSignUp.getUserId(), userReceived.getUserId());
    // }

    private GIDMASvcApi getUserSpecificServiceApis(User user) {
        return securedRestBuilder.setUsername(user.getUserId()).setPassword(user.getPassword()).build().create(GIDMASvcApi.class);
    }

    private void performCheckInforUserAndTest(Date dt1, Date dt2, String userId, GIDMASvcApi gidmaSvcApi) throws IllegalAccessException {
        List<UserCheckIn> userCheckInListsToAdd = TestData.getUserCheckinDataForDateRange(dt1, dt2, userId);
        for (UserCheckIn userCheckIn : userCheckInListsToAdd) {
            UserCheckIn userCheckInDb = gidmaSvcApi.userCheckIn(userCheckIn);
            assertNotNull(userCheckInDb);
        }

        CustomQueryParams queryParams = new CustomQueryParams();
        queryParams.setUserId(userId);
        List<UserCheckIn> userCheckInListsFromDb = gidmaSvcApi.getUserCheckInInfo(queryParams);
        assertEquals(userCheckInListsToAdd.size(), userCheckInListsFromDb.size());
    }

    private Follower placeApprovalRequestAndTest(GIDMASvcApi gidmaSvcApi, String followerUserId, String patientUserId) {
        Follower follower = gidmaSvcApi.placeFollowRequest(followerUserId, patientUserId);
        assertNotNull(follower);
        assertTrue(follower.getId() > 0);
        assertFalse(follower.isConfirmed());// when the follow request is placed it is false till it is confirmed.

        return follower;
    }

}
