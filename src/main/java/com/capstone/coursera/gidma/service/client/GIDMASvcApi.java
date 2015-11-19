package com.capstone.coursera.gidma.service.client;

import static com.capstone.coursera.gidma.service.utils.ControllerServicePathConstants.*;

import java.util.List;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

import com.capstone.coursera.gidma.service.model.BloodSugarLevelTimeEvents;
import com.capstone.coursera.gidma.service.model.CheckInQuestion;
import com.capstone.coursera.gidma.service.model.Follower;
import com.capstone.coursera.gidma.service.model.User;
import com.capstone.coursera.gidma.service.model.UserBasicInfo;
import com.capstone.coursera.gidma.service.model.UserCheckIn;
import com.capstone.coursera.gidma.service.utils.CustomQueryParams;

/**
 * This interface defines an API for a VideoSvc. The interface is used to provide a contract for client/server interactions. The interface
 * is annotated with Retrofit annotations so that clients can automatically convert the
 * 
 *
 */
public interface GIDMASvcApi {

    @POST(USER_SVC_SIGN_UP_PATH)
    public User signUpUser(@Body User userForSignUp);

    @GET(USER_SVC_INFO_PATH)
    public User getUser(@Path("userId") String userId);

    @GET(USER_SVC_ALL_USER_LIST_PATH)
    public List<User> allUserList();

    @POST(CHECKIN_SVC_PATH)
    public UserCheckIn userCheckIn(@Body UserCheckIn userCheckInTestModel);

    @POST(CHECKIN_SVC_GET_INFO)
    public List<UserCheckIn> getUserCheckInInfo(@Body CustomQueryParams queryParams) throws IllegalAccessException;

    @POST(USER_SVC_FOLLOW_REQ)
    public Follower placeFollowRequest(@Path("followerUserId") String followerUserId, @Path("patientUserId") String patientUserId);

    @POST(USER_SVC_APPROVE_FOLLOW_REQ)
    public Follower approveFollowRequest(@Body Follower follower);

    @POST(USER_SVC_APPROVE_MULTI_FOLLOW_REQ)
    public List<Follower> approveOrUpdateMultipleFollowRequest(@Body List<Follower> followersList, @Path("userId") String userId);

    @POST(USER_SVC_DENY_FOLLOW_REQ)
    public Boolean denyFollowRequest(@Path("deniedFollowerReqId") String deniedFollowerReqId);
    
    @GET(USER_SVC_PATIENT_USER_LIST_PATH)
    public List<User> getPatientUsersList();
    
    @GET(USER_SVC_FOLLOWERS_LIST_PATH)
    public List<UserBasicInfo> getFollowersList();
    
    @GET(USER_SVC_FOLLOWING_LIST_PATH)
    public List<UserBasicInfo> getFollowingList();

    @GET(USER_SVC_PATIENT_USER_FOR_FOLLOW_LIST)
    public List<UserBasicInfo> getPatientUserForFollowList();    
    
    @GET(CHECKIN_SVC_QSTNS)
    public List<CheckInQuestion> getCheckInQuestions();

    @GET(CHECKIN_SVC_BSLTE)
    public List<BloodSugarLevelTimeEvents> getBloodSugarLevelTimeEvents();

    @GET(USER_SVC_PATH + "/test1/{userId}")
    public User test1(@Path("userId") String userId);

    @POST(USER_SVC_PATH + "/test2")
    public User test2(@Body User userForSignUp);

}
