package com.capstone.coursera.gidma.service.controller;

import static com.capstone.coursera.gidma.service.utils.ControllerServicePathConstants.CHECKIN_SVC_BSLTE;
import static com.capstone.coursera.gidma.service.utils.ControllerServicePathConstants.CHECKIN_SVC_GET_INFO;
import static com.capstone.coursera.gidma.service.utils.ControllerServicePathConstants.CHECKIN_SVC_PATH;
import static com.capstone.coursera.gidma.service.utils.ControllerServicePathConstants.CHECKIN_SVC_QSTNS;
import static com.capstone.coursera.gidma.service.utils.Utils.DATE_FORMAT_2;
import static com.capstone.coursera.gidma.service.utils.Utils.getDate;
import static com.capstone.coursera.gidma.service.utils.Utils.getDateTimeLastInDay;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.coursera.gidma.service.model.BloodSugarLevelTimeEvents;
import com.capstone.coursera.gidma.service.model.CheckInQuestion;
import com.capstone.coursera.gidma.service.model.Follower;
import com.capstone.coursera.gidma.service.model.UserCheckIn;
import com.capstone.coursera.gidma.service.model.repository.BloodSugarLevelTimeEventsRepository;
import com.capstone.coursera.gidma.service.model.repository.CheckInQuestionRepository;
import com.capstone.coursera.gidma.service.model.repository.UserCheckInRepository;
import com.capstone.coursera.gidma.service.model.repository.UserMedicalRecordRepository;
import com.capstone.coursera.gidma.service.model.repository.UserRepository;
import com.capstone.coursera.gidma.service.utils.CustomQueryParams;
import com.google.common.collect.Lists;

@RestController
public class CheckInServiceController {

    Logger LOG = LoggerFactory.getLogger(CheckInServiceController.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserMedicalRecordRepository userMedicalRecordRepository;

    @Autowired
    BloodSugarLevelTimeEventsRepository bslteRepo;

    @Autowired
    CheckInQuestionRepository checkInQstnRepository;

    @Autowired
    UserCheckInRepository userCheckInRepository;

    @ExceptionHandler(Exception.class)
    public void handleExceptions(Exception anExc) {
        anExc.printStackTrace();
    }

    @PreAuthorize("#userCheckIn.userId == p.name")
    @RequestMapping(value = CHECKIN_SVC_PATH, method = RequestMethod.POST)
    public @ResponseBody UserCheckIn userCheckIn(@RequestBody UserCheckIn userCheckIn, Principal p) {
        LOG.info("\n\n INVOKED: userCheckIn...." + userCheckIn);

        UserCheckIn userCheckInSaved = userCheckInRepository.save(userCheckIn);

        LOG.info("\n\n userCheckIn : userCheckInSaved...." + userCheckInSaved);
        return userCheckInSaved;
    }

    @RequestMapping(value = CHECKIN_SVC_GET_INFO, method = RequestMethod.POST)
    public @ResponseBody List<UserCheckIn> getUserCheckInInfo(@RequestBody CustomQueryParams queryParams, Principal p)
            throws IllegalAccessException {
        LOG.info("\n\n INVOKED: getUserCheckInInfo.... for userId :" + queryParams.getUserId() + " : By p.getName : " + p.getName());
        LOG.info("getUserCheckInInfo.... CustomQueryParams : queryParams :" + queryParams);

        List<UserCheckIn> userCheckInList = new ArrayList<UserCheckIn>();
        boolean isFollower = false;

        List<Follower> followerList = userRepository.findOne(queryParams.getUserId()).getFollowers();
        // LOG.info("\n\n getUserCheckInInfo....followerList : " + followerList);
        Follower followerInfo = null;
        for (Follower follower : followerList) {
            if (follower.getFollowerUserId().equals(p.getName())) {
                followerInfo = follower;
                isFollower = true;
                break;
            }
        }

        if (isFollower || queryParams.getUserId().equals(p.getName())) { // should be a follower or him/herself.

            Date fromDate = getDate(queryParams.getFromDate(), DATE_FORMAT_2);
            Date toDate = getDateTimeLastInDay(getDate(queryParams.getToDate(), DATE_FORMAT_2));

            // since date's time starts from 00:00:00 so to have data of toDate to be included, we should update its time to 23:59:29

            if (fromDate != null && toDate != null) {
                userCheckInList = Lists.newArrayList(userCheckInRepository.findByUserIdAndDateRange(queryParams.getUserId(), fromDate,
                        toDate));
            } else if (fromDate != null && toDate == null) {
                userCheckInList = Lists.newArrayList(userCheckInRepository.findByUserIdAndFromDate(queryParams.getUserId(), fromDate));
            } else if (fromDate == null && toDate != null) {
                userCheckInList = Lists.newArrayList(userCheckInRepository.findByUserIdAndUpToDate(queryParams.getUserId(), toDate));
            } else {
                userCheckInList = Lists.newArrayList(userCheckInRepository.findByUserId(queryParams.getUserId()));
            }

        } else {
            throw new IllegalAccessException("Not Authorize to view " + queryParams.getUserId() + " check In info....");
        }

        LOG.info("\n\n getUserCheckInInfo....userCheckInList.size : " + userCheckInList.size());

        if (isFollower) { // then FILTER data... for which user do not want to share data....
            for (UserCheckIn userCheckIn : userCheckInList) {

                if(!followerInfo.isBloodSugarLvlFasting()) userCheckIn.setBloodSugarLvlFasting("");
                if(!followerInfo.isBloodSugarLvlMT()) userCheckIn.setBloodSugarLvlMT("");
                if(!followerInfo.isBloodSugarLvlBT()) userCheckIn.setBloodSugarLvlBT("");
                if(!followerInfo.isBloodSugarLvlTime()) userCheckIn.setBloodSugarLvlTime("");
                if(!followerInfo.isEatMT()) userCheckIn.setEatMT("");
                if(!followerInfo.isInsulin()) userCheckIn.setInsulin("");
                if(!followerInfo.isWhoWithYou()) userCheckIn.setWhoWithYou("");
                if(!followerInfo.isWhereWereYou()) userCheckIn.setWhereWereYou("");
                if(!followerInfo.isMood()) userCheckIn.setMood("");
                if(!followerInfo.isStress()) userCheckIn.setStress("");
                if(!followerInfo.isEnergyLvl()) userCheckIn.setEnergyLvl("");
                if(!followerInfo.isBloodSugarLvlTimeEvent()) userCheckIn.setBloodSugarLvlTimeEvent("");

            }
        }

        return userCheckInList;
    }

    @RequestMapping(value = CHECKIN_SVC_QSTNS, method = RequestMethod.GET)
    public @ResponseBody List<CheckInQuestion> getCheckInQuestions() {
        return Lists.newArrayList(checkInQstnRepository.findAll());
    }

    @RequestMapping(value = CHECKIN_SVC_BSLTE, method = RequestMethod.GET)
    public @ResponseBody List<BloodSugarLevelTimeEvents> getBloodSugarLevelTimeEvents() {
        return Lists.newArrayList(bslteRepo.findAll());
    }

}
