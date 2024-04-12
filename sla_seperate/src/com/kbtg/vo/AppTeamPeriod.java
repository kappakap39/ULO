/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kbtg.vo;

/**
 *
 * @author Kritsakorn.c
 */
public class AppTeamPeriod {
    
    private String appGroupId;
    private String jobOwner;
    private String startDate;
    private String endDate;

    /**
     * @return the appGroupId
     */
    public String getAppGroupId() {
        return appGroupId;
    }

    /**
     * @param appGroupId the appGroupId to set
     */
    public void setAppGroupId(String appGroupId) {
        this.appGroupId = appGroupId;
    }

    /**
     * @return the jobOwner
     */
    public String getJobOwner() {
        return jobOwner;
    }

    /**
     * @param jobOwner the jobOwner to set
     */
    public void setJobOwner(String jobOwner) {
        this.jobOwner = jobOwner;
    }

    /**
     * @return the startDate
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the endDate
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    
    @Override
    public String toString(){
        return "appGroupId: " +appGroupId+", jobOwner: " +jobOwner+", startDate: " +startDate+", endDate: " +endDate;
    }
    
}
