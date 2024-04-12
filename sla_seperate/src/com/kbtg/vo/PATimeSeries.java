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
public class PATimeSeries {

    private String appGroupId;
    private String fromAction;
    private String toAction;
    private String fromJobState;
    private String toJobState;
    private String receiveDate;
    private String submitDate;
    private String sendOutBy;
    private String receiveBy;
    private String fromRole;
    private String toRole;

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
     * @return the fromAction
     */
    public String getFromAction() {
        return fromAction;
    }

    /**
     * @param fromAction the fromAction to set
     */
    public void setFromAction(String fromAction) {
        this.fromAction = fromAction;
    }

    /**
     * @return the toAction
     */
    public String getToAction() {
        return toAction;
    }

    /**
     * @param toAction the toAction to set
     */
    public void setToAction(String toAction) {
        this.toAction = toAction;
    }

    /**
     * @return the fromJobState
     */
    public String getFromJobState() {
        return fromJobState;
    }

    /**
     * @param fromJobState the fromJobState to set
     */
    public void setFromJobState(String fromJobState) {
        this.fromJobState = fromJobState;
    }

    /**
     * @return the toJobState
     */
    public String getToJobState() {
        return toJobState;
    }

    /**
     * @param toJobState the toJobState to set
     */
    public void setToJobState(String toJobState) {
        this.toJobState = toJobState;
    }

    /**
     * @return the receiveDate
     */
    public String getReceiveDate() {
        return receiveDate;
    }

    /**
     * @param receiveDate the receiveDate to set
     */
    public void setReceiveDate(String receiveDate) {
        this.receiveDate = receiveDate;
    }

    /**
     * @return the submitDate
     */
    public String getSubmitDate() {
        return submitDate;
    }

    /**
     * @param submitDate the submitDate to set
     */
    public void setSubmitDate(String submitDate) {
        this.submitDate = submitDate;
    }

    /**
     * @return the sendOutBy
     */
    public String getSendOutBy() {
        return sendOutBy;
    }

    /**
     * @param sendOutBy the sendOutBy to set
     */
    public void setSendOutBy(String sendOutBy) {
        this.sendOutBy = sendOutBy;
    }

    /**
     * @return the receiveBy
     */
    public String getReceiveBy() {
        return receiveBy;
    }

    /**
     * @param receiveBy the receiveBy to set
     */
    public void setReceiveBy(String receiveBy) {
        this.receiveBy = receiveBy;
    }

    /**
     * @return the fromRole
     */
    public String getFromRole() {
        return fromRole;
    }

    /**
     * @param fromRole the fromRole to set
     */
    public void setFromRole(String fromRole) {
        this.fromRole = fromRole;
    }

    /**
     * @return the toRole
     */
    public String getToRole() {
        return toRole;
    }

    /**
     * @param toRole the toRole to set
     */
    public void setToRole(String toRole) {
        this.toRole = toRole;
    }
    
    @Override
    public String toString(){
        return "appGroupId: " +appGroupId+", fromAction: " +fromAction+", toAction: " +toAction
                +", fromJobState: " +fromJobState+", toJobState: " +toJobState+", receiveDate: " +receiveDate
                +", submitDate: " +submitDate+", sendOutBy: " +sendOutBy+", receiveBy: " +receiveBy
                +", fromRole: " +fromRole+", toRole: " +toRole;
    }
    
}
