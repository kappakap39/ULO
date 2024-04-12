/*
 * Created on Nov 16, 2007
 * Created by Prawit Limwattanachai
 * 
 * Copyright (c) 2007 Avalant Co.,Ltd.
 * 20 North Sathorn Road, 15-16th Floor Bubhajit Bldg., Silom, Bangrak, Bangkok 10500, Thailand
 * All rights reserved.
 *
 * This software is the confidential and prorietary infomation of
 * Avalant Co.,Ltd. ("Confidential Infomation"). You shall not
 * disclose such Confidential Infomation and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Avalant Co.,Ltd.
 * 
 */
package com.eaf.orig.shared.dao;

/**
 * @author Administrator
 *
 * Type: OrigMasterDAOFactory
 */
public class OrigMasterDAOFactory {
	
	public static OrigMasterUserDetailDAO getOrigMasterUserDetailDAO() {
		return new OrigMasterUserDetailDAOImpl();
	}	
	public static OrigMasterListBoxDAO getOrigMasterListBoxDAO() {
		return new OrigMasterListBoxDAOImpl();
	}
	public static OrigMasterFieldIdDAO getOrigMasterFieldIdDAO() {
		return new OrigMasterFieldIdDAOImpl();
	}
	public static OrigMasterRunParamDAO getOrigMasterRunParamDAO() {
		return new OrigMasterRunParamDAOImpl();
	}
	public static OrigMasterGenParamDAO getOrigMasterGenParamDAO() {
		return new OrigMasterGenParamDAOImpl();
	}
	public static OrigMasterExceptActDAO getOrigMasterExceptActDAO() {
		return new OrigMasterExceptActDAOImpl();
	}
	public static OrigMasterMandFieldDAO getOrigMasterMandFieldDAO() {
		return new OrigMasterMandFieldDAOImpl();
	}
	public static OrigMasterApprovAuthorDAO getOrigMasterApprovAuthorDAO() {
		return new OrigMasterApprovAuthorDAOImpl();
	}
	public static OrigMasterCarBlacklistDAO getOrigMasterCarBlacklistDAO() {
		return new OrigMasterCarBlacklistDAOImpl();
	}
	public static OrigMasterQTimeDAO getOrigMasterQTimeDAO() {
		return new OrigMasterQTimeDAOImpl();
	}
	public static OrigMasterHolidayDAO getOrigMasterHolidayDAO() {
		return new OrigMasterHolidayDAOImpl();
	}
	public static OrigMasterUserTeamDAO getOrigMasterUserTeamDAO() {
		return new OrigMasterUserTeamDAOImpl();
	}
	public static OrigMasterAppScoreDAO getOrigMasterAppScoreDAO() {
		return new OrigMasterAppScoreDAOImpl();
	}
	public static OrigMasterFICODAO getOrigMasterFICODAO() {
		return new OrigMasterFICODAOImpl();
	}
	public static WorkflowUtilDAO getWorkflowUtilDAO(){
	    return new WorkflowUtilDAOImpl();
	}
	public static OrigMasterSLADAO getOrigMasterSLADAO(){
	    return new OrigMasterSLADAOImpl();
	}
	public static OrigMasterPolicyRulesDAO getOrigMasterPolicyRulesDAO() {
		return new OrigMasterPolicyRulesDAOImpl();
	}
}
