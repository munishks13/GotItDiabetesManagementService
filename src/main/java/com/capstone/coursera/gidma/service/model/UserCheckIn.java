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
public class UserCheckIn implements Serializable {

    /**
     * Generated Serial Version Id.
     */
    private static final long serialVersionUID = 7653082721747213467L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(updatable = false, nullable = false)
    private String userId;

    @Column(updatable = false, nullable = false, name = "CHECKIN_DATE_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date checkInDateTime = new Date();

    private String bloodSugarLvlFasting;
    private String bloodSugarLvlMT;
    private String bloodSugarLvlBT;
    private String bloodSugarLvlTime;

    private String eatMT;
    private String insulin;
    private String whoWithYou;
    private String whereWereYou;
    private String mood;
    private String stress;
    private String energyLvl;
    private String bloodSugarLvlTimeEvent;

    @JsonSerialize(using = JsonDateSerializer.class)
    public Date getCheckInDateTime() {
        if (this.checkInDateTime == null) {
            return new Date();
        }
        return this.checkInDateTime;
    }

    public void setCheckInDateTime(Date checkInDateTime) {
        this.checkInDateTime = checkInDateTime;
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

    /**
     * @return the bloodSugarLvlFasting
     */
    public String getBloodSugarLvlFasting() {
        return bloodSugarLvlFasting;
    }

    /**
     * @param bloodSugarLvlFasting
     *            the bloodSugarLvlFasting to set
     */
    public void setBloodSugarLvlFasting(String bloodSugarLvlFasting) {
        this.bloodSugarLvlFasting = bloodSugarLvlFasting;
    }

    /**
     * @return the bloodSugarLvlMT
     */
    public String getBloodSugarLvlMT() {
        return bloodSugarLvlMT;
    }

    /**
     * @param bloodSugarLvlMT
     *            the bloodSugarLvlMT to set
     */
    public void setBloodSugarLvlMT(String bloodSugarLvlMT) {
        this.bloodSugarLvlMT = bloodSugarLvlMT;
    }

    /**
     * @return the bloodSugarLvlBT
     */
    public String getBloodSugarLvlBT() {
        return bloodSugarLvlBT;
    }

    /**
     * @param bloodSugarLvlBT
     *            the bloodSugarLvlBT to set
     */
    public void setBloodSugarLvlBT(String bloodSugarLvlBT) {
        this.bloodSugarLvlBT = bloodSugarLvlBT;
    }

    /**
     * @return the bloodSugarLvlTime
     */
    public String getBloodSugarLvlTime() {
        return bloodSugarLvlTime;
    }

    /**
     * @param bloodSugarLvlTime
     *            the bloodSugarLvlTime to set
     */
    public void setBloodSugarLvlTime(String bloodSugarLvlTime) {
        this.bloodSugarLvlTime = bloodSugarLvlTime;
    }

    /**
     * @return the eatMT
     */
    public String getEatMT() {
        return eatMT;
    }

    /**
     * @param eatMT
     *            the eatMT to set
     */
    public void setEatMT(String eatMT) {
        this.eatMT = eatMT;
    }

    /**
     * @return the insulin
     */
    public String getInsulin() {
        return insulin;
    }

    /**
     * @param insulin
     *            the insulin to set
     */
    public void setInsulin(String insulin) {
        this.insulin = insulin;
    }

    /**
     * @return the whoWithYou
     */
    public String getWhoWithYou() {
        return whoWithYou;
    }

    /**
     * @param whoWithYou
     *            the whoWithYou to set
     */
    public void setWhoWithYou(String whoWithYou) {
        this.whoWithYou = whoWithYou;
    }

    /**
     * @return the whereWereYou
     */
    public String getWhereWereYou() {
        return whereWereYou;
    }

    /**
     * @param whereWereYou
     *            the whereWereYou to set
     */
    public void setWhereWereYou(String whereWereYou) {
        this.whereWereYou = whereWereYou;
    }

    /**
     * @return the mood
     */
    public String getMood() {
        return mood;
    }

    /**
     * @param mood
     *            the mood to set
     */
    public void setMood(String mood) {
        this.mood = mood;
    }

    /**
     * @return the stress
     */
    public String getStress() {
        return stress;
    }

    /**
     * @param stress
     *            the stress to set
     */
    public void setStress(String stress) {
        this.stress = stress;
    }

    /**
     * @return the energyLvl
     */
    public String getEnergyLvl() {
        return energyLvl;
    }

    /**
     * @param energyLvl
     *            the energyLvl to set
     */
    public void setEnergyLvl(String energyLvl) {
        this.energyLvl = energyLvl;
    }

    /**
     * @return the bloodSugarLvlTimeEvent
     */
    public String getBloodSugarLvlTimeEvent() {
        return bloodSugarLvlTimeEvent;
    }

    /**
     * @param bloodSugarLvlTimeEvent
     *            the bloodSugarLvlTimeEvent to set
     */
    public void setBloodSugarLvlTimeEvent(String bloodSugarLvlTimeEvent) {
        this.bloodSugarLvlTimeEvent = bloodSugarLvlTimeEvent;
    }

}
