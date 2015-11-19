package com.capstone.coursera.gidma.service.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.capstone.coursera.gidma.service.utils.json.JsonDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
public class Follower implements Serializable {

    /**
     * Generated Serial Version Id.
     */
    private static final long serialVersionUID = -3725124002145568743L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String userId;
    private String followerUserId;

    private boolean confirmed;

    private boolean bloodSugarLvlFasting;
    private boolean bloodSugarLvlMT;
    private boolean bloodSugarLvlBT;
    private boolean bloodSugarLvlTime;

    private boolean eatMT;
    private boolean insulin;
    private boolean whoWithYou;
    private boolean whereWereYou;
    private boolean mood;
    private boolean stress;
    private boolean energyLvl;
    private boolean bloodSugarLvlTimeEvent;

    @Column(nullable = false, name = "LAST_UPDATED_DATE_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdatedDateTime = new Date();

    public Follower() {
        // TODO Auto-generated constructor stub
    }

    public Follower(String userId, String followerUserId) {
        this.userId = userId;
        this.followerUserId = followerUserId;
    }

    @JsonSerialize(using = JsonDateSerializer.class)
    public Date getLastUpdatedDateTime() {
        if (this.lastUpdatedDateTime == null) {
            return new Date();
        }
        return this.lastUpdatedDateTime;
    }

    public void setLastUpdatedDateTime(Date lastUpdatedDateTime) {
        this.lastUpdatedDateTime = lastUpdatedDateTime;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFollowerUserId() {
        return followerUserId;
    }

    public void setFollowerUserId(String followerUserId) {
        this.followerUserId = followerUserId;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    /**
     * @return the bloodSugarLvlFasting
     */
    public boolean isBloodSugarLvlFasting() {
        return bloodSugarLvlFasting;
    }

    /**
     * @param bloodSugarLvlFasting
     *            the bloodSugarLvlFasting to set
     */
    public void setBloodSugarLvlFasting(boolean bloodSugarLvlFasting) {
        this.bloodSugarLvlFasting = bloodSugarLvlFasting;
    }

    /**
     * @return the bloodSugarLvlMT
     */
    public boolean isBloodSugarLvlMT() {
        return bloodSugarLvlMT;
    }

    /**
     * @param bloodSugarLvlMT
     *            the bloodSugarLvlMT to set
     */
    public void setBloodSugarLvlMT(boolean bloodSugarLvlMT) {
        this.bloodSugarLvlMT = bloodSugarLvlMT;
    }

    /**
     * @return the bloodSugarLvlBT
     */
    public boolean isBloodSugarLvlBT() {
        return bloodSugarLvlBT;
    }

    /**
     * @param bloodSugarLvlBT
     *            the bloodSugarLvlBT to set
     */
    public void setBloodSugarLvlBT(boolean bloodSugarLvlBT) {
        this.bloodSugarLvlBT = bloodSugarLvlBT;
    }

    /**
     * @return the bloodSugarLvlTime
     */
    public boolean isBloodSugarLvlTime() {
        return bloodSugarLvlTime;
    }

    /**
     * @param bloodSugarLvlTime
     *            the bloodSugarLvlTime to set
     */
    public void setBloodSugarLvlTime(boolean bloodSugarLvlTime) {
        this.bloodSugarLvlTime = bloodSugarLvlTime;
    }

    /**
     * @return the eatMT
     */
    public boolean isEatMT() {
        return eatMT;
    }

    /**
     * @param eatMT
     *            the eatMT to set
     */
    public void setEatMT(boolean eatMT) {
        this.eatMT = eatMT;
    }

    /**
     * @return the insulin
     */
    public boolean isInsulin() {
        return insulin;
    }

    /**
     * @param insulin
     *            the insulin to set
     */
    public void setInsulin(boolean insulin) {
        this.insulin = insulin;
    }

    /**
     * @return the whoWithYou
     */
    public boolean isWhoWithYou() {
        return whoWithYou;
    }

    /**
     * @param whoWithYou
     *            the whoWithYou to set
     */
    public void setWhoWithYou(boolean whoWithYou) {
        this.whoWithYou = whoWithYou;
    }

    /**
     * @return the whereWereYou
     */
    public boolean isWhereWereYou() {
        return whereWereYou;
    }

    /**
     * @param whereWereYou
     *            the whereWereYou to set
     */
    public void setWhereWereYou(boolean whereWereYou) {
        this.whereWereYou = whereWereYou;
    }

    /**
     * @return the mood
     */
    public boolean isMood() {
        return mood;
    }

    /**
     * @param mood
     *            the mood to set
     */
    public void setMood(boolean mood) {
        this.mood = mood;
    }

    /**
     * @return the stress
     */
    public boolean isStress() {
        return stress;
    }

    /**
     * @param stress
     *            the stress to set
     */
    public void setStress(boolean stress) {
        this.stress = stress;
    }

    /**
     * @return the energyLvl
     */
    public boolean isEnergyLvl() {
        return energyLvl;
    }

    /**
     * @param energyLvl
     *            the energyLvl to set
     */
    public void setEnergyLvl(boolean energyLvl) {
        this.energyLvl = energyLvl;
    }

    /**
     * @return the bloodSugarLvlTimeEvent
     */
    public boolean isBloodSugarLvlTimeEvent() {
        return bloodSugarLvlTimeEvent;
    }

    /**
     * @param bloodSugarLvlTimeEvent
     *            the bloodSugarLvlTimeEvent to set
     */
    public void setBloodSugarLvlTimeEvent(boolean bloodSugarLvlTimeEvent) {
        this.bloodSugarLvlTimeEvent = bloodSugarLvlTimeEvent;
    }

}
