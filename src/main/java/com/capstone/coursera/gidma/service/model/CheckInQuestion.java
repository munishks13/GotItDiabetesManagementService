package com.capstone.coursera.gidma.service.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity
public class CheckInQuestion implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -377155097907251071L;

    @Id
    private String qstnId;

    private String qstnDesc;

    public CheckInQuestion() {
        // TODO Auto-generated constructor stub
    }

    public CheckInQuestion(String qstnId, String qstnDesc) {
        this.qstnId = qstnId;
        this.qstnDesc = qstnDesc;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    public String getQstnId() {
        return qstnId;
    }

    public void setQstnId(String qstnId) {
        this.qstnId = qstnId;
    }

    public String getQstnDesc() {
        return qstnDesc;
    }

    public void setQstnDesc(String qstnDesc) {
        this.qstnDesc = qstnDesc;
    }
}
