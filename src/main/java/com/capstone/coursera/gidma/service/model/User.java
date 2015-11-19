package com.capstone.coursera.gidma.service.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class User implements Serializable {

    /**
     * Generated Serial Version Id.
     */
    private static final long serialVersionUID = -3364062910192842668L;

    @Id
    @Column(name = "USER_ID")
    private String userId;

    private String password;
    private String firstName;
    private String lastName;
    private String dateOfBirth;

    private String medicalRecordNumber;
    private boolean followed;

    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
    List<Follower> followers = new ArrayList<Follower>();

    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
    List<UserAlarmTimes> userAlarmTimes = new ArrayList<UserAlarmTimes>();

    @Override
    public String toString() {
        return "User [userId=" + userId 
                + ", password=******" 
                + ", firstName=" + firstName 
                + ", lastName=" + lastName 
                + ", dateOfBirth=" + dateOfBirth 
                + ", medicalRecordNumber : " + medicalRecordNumber
                + ", followed : " + followed 
                + ", followers : " + followers 
                + ", userAlarmTimes : " + userAlarmTimes 
                + "]";
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public List<Follower> getFollowers() {
        return followers;
    }

    public void setFollowers(List<Follower> followers) {
        this.followers = followers;
    }

    public List<UserAlarmTimes> getUserAlarmTimes() {
        return userAlarmTimes;
    }

    public void setUserAlarmTimes(List<UserAlarmTimes> userAlarmTimes) {
        this.userAlarmTimes = userAlarmTimes;
    }

    /**
     * @return the medicalRecordNumber
     */
    public String getMedicalRecordNumber() {
        return medicalRecordNumber;
    }

    /**
     * @param medicalRecordNumber
     *            the medicalRecordNumber to set
     */
    public void setMedicalRecordNumber(String medicalRecordNumber) {
        this.medicalRecordNumber = medicalRecordNumber;
    }

    /**
     * @return the followed
     */
    public boolean isFollowed() {
        return followed;
    }

    /**
     * @param followed
     *            the followed to set
     */
    public void setFollowed(boolean followed) {
        this.followed = followed;
    }

}
