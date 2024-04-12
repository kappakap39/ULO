/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kbtg.vo;

import java.sql.Timestamp;

/**
 *
 * @author Kritsakorn.c
 */
public class AppSlaPaVO {
    
    private String applicationGroupId;
    private Timestamp startDate;
    private Timestamp endDate;
    private int workday;
    private int rqRound;
    private int rqDay;
    private int rqOvertime;
    private int paRound;
    private int paDay;
    private int paSameday;
    private int paPass;

    /**
     * @return the applicationGroupId
     */
    public String getApplicationGroupId() {
        return applicationGroupId;
    }

    /**
     * @param applicationGroupId the applicationGroupId to set
     */
    public void setApplicationGroupId(String applicationGroupId) {
        this.applicationGroupId = applicationGroupId;
    }

    /**
     * @return the startDate
     */
    public Timestamp getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the endDate
     */
    public Timestamp getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    /**
     * @return the workday
     */
    public int getWorkday() {
        return workday;
    }

    /**
     * @param workday the workday to set
     */
    public void setWorkday(int workday) {
        this.workday = workday;
    }

    /**
     * @return the rqRound
     */
    public int getRqRound() {
        return rqRound;
    }

    /**
     * @param rqRound the rqRound to set
     */
    public void setRqRound(int rqRound) {
        this.rqRound = rqRound;
    }

    /**
     * @return the rqDay
     */
    public int getRqDay() {
        return rqDay;
    }

    /**
     * @param rqDay the rqDay to set
     */
    public void setRqDay(int rqDay) {
        this.rqDay = rqDay;
    }

    /**
     * @return the rqOvertime
     */
    public int getRqOvertime() {
        return rqOvertime;
    }

    /**
     * @param rqOvertime the rqOvertime to set
     */
    public void setRqOvertime(int rqOvertime) {
        this.rqOvertime = rqOvertime;
    }

    /**
     * @return the paRound
     */
    public int getPaRound() {
        return paRound;
    }

    /**
     * @param paRound the paRound to set
     */
    public void setPaRound(int paRound) {
        this.paRound = paRound;
    }

    /**
     * @return the paDay
     */
    public int getPaDay() {
        return paDay;
    }

    /**
     * @param paDay the paDay to set
     */
    public void setPaDay(int paDay) {
        this.paDay = paDay;
    }

    /**
     * @return the paSameday
     */
    public int getPaSameday() {
        return paSameday;
    }

    /**
     * @param paSameday the paSameday to set
     */
    public void setPaSameday(int paSameday) {
        this.paSameday = paSameday;
    }

    /**
     * @return the paPass
     */
    public int getPaPass() {
        return paPass;
    }

    /**
     * @param paPass the paPass to set
     */
    public void setPaPass(int paPass) {
        this.paPass = paPass;
    }
    
    public String toString(){
        
        return "appGroupId: "+ applicationGroupId +" ,startDate: "+ startDate 
                        +" ,stopDate: "+ endDate 
                        +" ,workDay: "+ workday 
                        +" ,RQround: "+ rqRound 
                        +" ,RQday: "+ rqDay 
                        +" ,RQovertime: "+ rqOvertime 
                        +" ,PAround: "+ paRound 
                        +" ,PAday: "+ paDay 
                        +" ,PAsameday: "+ paSameday
                        +" ,PA_Pass: "+ paPass;
    }
    
}
