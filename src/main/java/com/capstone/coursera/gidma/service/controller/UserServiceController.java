package com.capstone.coursera.gidma.service.controller;

import static com.capstone.coursera.gidma.service.utils.ControllerServicePathConstants.USER_SVC_ALL_USER_LIST_PATH;
import static com.capstone.coursera.gidma.service.utils.ControllerServicePathConstants.USER_SVC_APPROVE_FOLLOW_REQ;
import static com.capstone.coursera.gidma.service.utils.ControllerServicePathConstants.USER_SVC_APPROVE_MULTI_FOLLOW_REQ;
import static com.capstone.coursera.gidma.service.utils.ControllerServicePathConstants.USER_SVC_FOLLOWERS_LIST_PATH;
import static com.capstone.coursera.gidma.service.utils.ControllerServicePathConstants.USER_SVC_FOLLOWING_LIST_PATH;
import static com.capstone.coursera.gidma.service.utils.ControllerServicePathConstants.USER_SVC_FOLLOW_REQ;
import static com.capstone.coursera.gidma.service.utils.ControllerServicePathConstants.USER_SVC_INFO_PATH;
import static com.capstone.coursera.gidma.service.utils.ControllerServicePathConstants.USER_SVC_PATH;
import static com.capstone.coursera.gidma.service.utils.ControllerServicePathConstants.USER_SVC_PATIENT_USER_FOR_FOLLOW_LIST;
import static com.capstone.coursera.gidma.service.utils.ControllerServicePathConstants.USER_SVC_PATIENT_USER_LIST_PATH;
import static com.capstone.coursera.gidma.service.utils.ControllerServicePathConstants.USER_SVC_SIGN_UP_PATH;
import static com.capstone.coursera.gidma.service.utils.ControllerServicePathConstants.USER_SVC_DENY_FOLLOW_REQ;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.capstone.coursera.gidma.service.model.Follower;
import com.capstone.coursera.gidma.service.model.User;
import com.capstone.coursera.gidma.service.model.UserBasicInfo;
import com.capstone.coursera.gidma.service.model.repository.BloodSugarLevelTimeEventsRepository;
import com.capstone.coursera.gidma.service.model.repository.FollowerRepository;
import com.capstone.coursera.gidma.service.model.repository.UserRepository;
import com.google.common.collect.Lists;

@Controller
public class UserServiceController {

    Logger LOG = LoggerFactory.getLogger(UserServiceController.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    BloodSugarLevelTimeEventsRepository bslteRepo;

    @Autowired
    FollowerRepository followerRepository;

    @ExceptionHandler(Exception.class)
    public void handleExceptions(Exception anExc) {
        anExc.printStackTrace();
    }

    @RequestMapping(value = USER_SVC_SIGN_UP_PATH, method = RequestMethod.POST)
    public @ResponseBody User signUpUser(@RequestBody User userForSignUp) {
        LOG.info("\n\n INVOKED: signUpUser : userForSignUp : " + userForSignUp);
        // Check if user already exists with same User Id.
        if (!userRepository.exists(userForSignUp.getUserId())) {
            userRepository.save(userForSignUp);
        }

        return userForSignUp;
    }

    @RequestMapping(value = USER_SVC_INFO_PATH, method = RequestMethod.GET)
    public @ResponseBody User getUser(@PathVariable("userId") String userId) {
        LOG.info("\n\n INVOKED: getUser : userId : " + userId);
        User user = userRepository.findOne(userId);

        if (user != null) {
            // patient User may be follower of other patient too... so get that list too and merge...
            List<Follower> followers = Lists.newArrayList(followerRepository.findByFollowerUserId(user.getUserId()));

            LOG.info("\n\n getUser : followers : " + followers);

            if (followers != null && !followers.isEmpty()) {
                user.getFollowers().addAll(followers);
            }
        }

        LOG.info("\n\n getUser : user : " + user);
        return user;
    }

    @PreAuthorize("#followerUserId == p.name")
    @RequestMapping(value = USER_SVC_FOLLOW_REQ, method = RequestMethod.POST)
    public @ResponseBody Follower placeFollowRequest(@PathVariable("followerUserId") String followerUserId,
            @PathVariable("patientUserId") String patientUserId, Principal p) {
        LOG.info("\n\n INVOKED: placeFollowRequest : followerUserId : " + followerUserId + " : patientUserId : " + patientUserId);

        Follower follower = followerRepository.findByUserIdAndFollowerUserId(patientUserId, followerUserId);

        if(follower == null){
            User patientUser = userRepository.findOne(patientUserId);

            // check the patient user which is to be followed exists and IS PATIENT.....SHOULD NOT BE FOLLOWER.....
            if (patientUser != null && StringUtils.isNotBlank(patientUser.getMedicalRecordNumber())) {
                follower = followerRepository.save(new Follower(patientUserId, followerUserId));

                LOG.info("\n\n placeFollowRequest : follower : " + follower);
                
                patientUser.getFollowers().add(follower);
                userRepository.save(patientUser);
            }
            LOG.info("\n\n placeFollowRequest : patientUser : " + patientUser);
        } else {
            LOG.info("\n\n placeFollowRequest : follower Already Exists....: " + follower);
        }

        return follower;
    }

    @PreAuthorize("#follower.userId == p.name")
    @RequestMapping(value = USER_SVC_APPROVE_FOLLOW_REQ, method = RequestMethod.POST)
    public @ResponseBody Follower approveFollowRequest(@RequestBody Follower follower, Principal p) {
        LOG.info("\n\n INVOKED: approveFollowRequest : follower : " + follower);
        Follower followerDb = followerRepository.findOne(follower.getId());

        // check the followed request exists.
        if (followerDb != null) {
            followerDb.setConfirmed(true);

            followerDb.setBloodSugarLvlFasting(follower.isBloodSugarLvlFasting());
            followerDb.setBloodSugarLvlMT(follower.isBloodSugarLvlMT());
            followerDb.setBloodSugarLvlBT(follower.isBloodSugarLvlBT());
            followerDb.setBloodSugarLvlTime(follower.isBloodSugarLvlTime());
            followerDb.setEatMT(follower.isEatMT());
            followerDb.setInsulin(follower.isInsulin());
            followerDb.setWhoWithYou(follower.isWhoWithYou());
            followerDb.setWhereWereYou(follower.isWhereWereYou());
            followerDb.setMood(follower.isMood());
            followerDb.setStress(follower.isStress());
            followerDb.setEnergyLvl(follower.isEnergyLvl());
            followerDb.setBloodSugarLvlTimeEvent(follower.isBloodSugarLvlTimeEvent());

            followerDb = followerRepository.save(followerDb);
        }

        LOG.info("\n\n approveFollowRequest : followerDb : " + followerDb);
        return followerDb;
    }

    //@PreAuthorize("#follower.userId == p.name")
    @RequestMapping(value = USER_SVC_DENY_FOLLOW_REQ, method = RequestMethod.POST)
    public @ResponseBody String denyFollowRequest(@PathVariable("deniedFollowerReqId") String deniedFollowerReqId, Principal p) {
        LOG.info("\n\n INVOKED: deneyFollowRequest : deniedFollowerReqId : " + deniedFollowerReqId);
        String deletedFollowerId = "";
        try {
            Follower followerDb = followerRepository.findOne(Long.valueOf(deniedFollowerReqId));
            LOG.info(" deneyFollowRequest : followerDb : " + followerDb);
            if(followerDb != null) {
                deletedFollowerId = deniedFollowerReqId;
                followerRepository.delete(followerDb);
            }
        } catch (Exception eX) {
            deletedFollowerId = "";
            eX.printStackTrace();
        }
        LOG.info(" deneyFollowRequest : deletedFollowerId : " + deletedFollowerId);
        return deletedFollowerId;
    }

    @PreAuthorize("#userId == p.name")
    @RequestMapping(value = USER_SVC_APPROVE_MULTI_FOLLOW_REQ, method = RequestMethod.POST)
    public @ResponseBody List<Follower> approveOrUpdateMultipleFollowRequest(@RequestBody List<Follower> followersList,
            @PathVariable("userId") String userId, Principal p) {
        LOG.info("\n\n INVOKED: approveOrUpdateMultipleFollowRequest : followersList : " + followersList);

        List<Follower> followersListDb = new ArrayList<Follower>();

        for (Follower follower : followersList) {
            Follower followerDb = followerRepository.findOne(follower.getId());

            // check the followed request exists.
            if (followerDb != null) {
                followerDb.setConfirmed(true);

                followerDb.setBloodSugarLvlFasting(follower.isBloodSugarLvlFasting());
                followerDb.setBloodSugarLvlMT(follower.isBloodSugarLvlMT());
                followerDb.setBloodSugarLvlBT(follower.isBloodSugarLvlBT());
                followerDb.setBloodSugarLvlTime(follower.isBloodSugarLvlTime());
                followerDb.setEatMT(follower.isEatMT());
                followerDb.setInsulin(follower.isInsulin());
                followerDb.setWhoWithYou(follower.isWhoWithYou());
                followerDb.setWhereWereYou(follower.isWhereWereYou());
                followerDb.setMood(follower.isMood());
                followerDb.setStress(follower.isStress());
                followerDb.setEnergyLvl(follower.isEnergyLvl());
                followerDb.setBloodSugarLvlTimeEvent(follower.isBloodSugarLvlTimeEvent());

                followerDb = followerRepository.save(followerDb);
                followersListDb.add(followerDb);
            }
        }

        return followersListDb;
    }

    @RequestMapping(value = USER_SVC_PATIENT_USER_LIST_PATH, method = RequestMethod.GET)
    public @ResponseBody List<User> getPatientUsersList(Principal p) {
        LOG.info("\n\n INVOKED: getPatientUsersList.... who want to be followed...");
        List<User> patientUserList = new ArrayList<User>();
        List<User> allUserList = Lists.newArrayList(userRepository.findAll());
        if (allUserList != null && !allUserList.isEmpty()) {
            for (User user : allUserList) {
                if (StringUtils.isNotBlank(user.getMedicalRecordNumber()) && !p.getName().equals(user.getUserId())) {
                    patientUserList.add(user);
                }
            }
        }

        return patientUserList;
    }

    @RequestMapping(value = USER_SVC_FOLLOWERS_LIST_PATH, method = RequestMethod.GET)
    public @ResponseBody List<UserBasicInfo> getFollowersList(Principal p) {
        LOG.info("\n\n INVOKED: getFollowersList For User....p.getName() : " + p.getName());
        List<UserBasicInfo> followerUserList = new ArrayList<UserBasicInfo>();

        if (StringUtils.isNotBlank(p.getName())) {
            List<Follower> followersList = Lists.newArrayList(followerRepository.findByUserId(p.getName()));

            if (CollectionUtils.isNotEmpty(followersList)) {
                for (Follower follower : followersList) {
                    User followerUserInfo = userRepository.findOne(follower.getFollowerUserId());

                    UserBasicInfo userBasicInfo = new UserBasicInfo();
                    setUserBasicInfo(userBasicInfo, followerUserInfo, follower);
                    followerUserList.add(userBasicInfo);
                }
            }
        }
        return followerUserList;
    }

    @RequestMapping(value = USER_SVC_FOLLOWING_LIST_PATH, method = RequestMethod.GET)
    public @ResponseBody List<UserBasicInfo> getFollowingList(Principal p) {
        LOG.info("\n\n INVOKED: getFollowingList For User....p.getName() : " + p.getName());
        List<UserBasicInfo> followingUserList = new ArrayList<UserBasicInfo>();

        if (StringUtils.isNotBlank(p.getName())) {
            List<Follower> followingList = Lists.newArrayList(followerRepository.findByFollowerUserId(p.getName()));

            if (CollectionUtils.isNotEmpty(followingList)) {
                for (Follower follower : followingList) {
                    User followiongUserInfo = userRepository.findOne(follower.getUserId());

                    UserBasicInfo userBasicInfo = new UserBasicInfo();
                    setUserBasicInfo(userBasicInfo, followiongUserInfo, follower);
                    followingUserList.add(userBasicInfo);
                }
            }
        }
        return followingUserList;
    }

    @RequestMapping(value = USER_SVC_PATIENT_USER_FOR_FOLLOW_LIST, method = RequestMethod.GET)
    public @ResponseBody List<UserBasicInfo> getPatientUserForFollowList(Principal p) {
        LOG.info("\n\n INVOKED: getPatientUserForFollowList For User....p.getName() : " + p.getName());
        List<UserBasicInfo> patientUserForFollowList = new ArrayList<UserBasicInfo>();

        if (StringUtils.isNotBlank(p.getName())) {

            List<UserBasicInfo> alreadyInFollowingList = getFollowingList(p);
            List<String> userIdsOfAlreadyInFollowingList = new ArrayList<String>();
            for (UserBasicInfo userBasicInfo : alreadyInFollowingList) {
                userIdsOfAlreadyInFollowingList.add(userBasicInfo.getUserId());
            }

            List<User> allUserList = Lists.newArrayList(userRepository.findAll());

            if (CollectionUtils.isNotEmpty(allUserList)) {
                for (User userInfo : allUserList) {// it is patient User, not in already following List and Not the loggedin User.
                    if (StringUtils.isNotBlank(userInfo.getMedicalRecordNumber())
                            && !userIdsOfAlreadyInFollowingList.contains(userInfo.getUserId()) && !userInfo.getUserId().equals(p.getName())) {
                        UserBasicInfo userBasicInfo = new UserBasicInfo();
                        setUserBasicInfo(userBasicInfo, userInfo);

                        patientUserForFollowList.add(userBasicInfo);
                    }
                }
            }
        }
        return patientUserForFollowList;
    }

    @RequestMapping(value = USER_SVC_ALL_USER_LIST_PATH, method = RequestMethod.GET)
    public @ResponseBody List<User> allUserList() {
        LOG.info("\n\n INVOKED: allUserList....");
        return Lists.newArrayList(userRepository.findAll());
    }

    @RequestMapping(value = USER_SVC_PATH + "/test1/{userId}", method = RequestMethod.GET)
    public @ResponseBody User test1(@PathVariable("userId") String userId) {
        LOG.info("\n\n INVOKED: test1 : userId : " + userId);
        User user = new User();
        user.setUserId(userId);
        return user;
    }

    @RequestMapping(value = USER_SVC_PATH + "/test2", method = RequestMethod.POST)
    public @ResponseBody User test2(@RequestBody User userForSignUp) {
        LOG.info("\n\n INVOKED: test2 : userId : " + userForSignUp);
        return userForSignUp;
    }

    private void setUserBasicInfo(UserBasicInfo userBasicInfo, User userInfo, Follower follower) {
        userBasicInfo.setFollowerUniqueid(follower.getId());
        userBasicInfo.setConfirmed(follower.isConfirmed());

        setUserBasicInfo(userBasicInfo, userInfo);
    }

    private void setUserBasicInfo(UserBasicInfo userBasicInfo, User userInfo) {
        userBasicInfo.setUserId(userInfo.getUserId());
        userBasicInfo.setFirstName(userInfo.getFirstName());
        userBasicInfo.setLastName(userInfo.getLastName());
        userBasicInfo.setDateOfBirth(userInfo.getDateOfBirth());
        userBasicInfo.setFollowed(userInfo.isFollowed());
        userBasicInfo.setMedicalRecordNumber(userInfo.getMedicalRecordNumber());
    }

}
