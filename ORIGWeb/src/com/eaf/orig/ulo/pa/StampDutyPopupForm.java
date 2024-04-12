package com.eaf.orig.ulo.pa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.FormAction;
import com.eaf.j2ee.pattern.control.FormHelper;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.app.dao.OrigApplicationGroupDAOImpl;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;

@SuppressWarnings("serial")
public class StampDutyPopupForm extends FormHelper implements FormAction{
	private static transient Logger logger = Logger.getLogger(StampDutyPopupForm.class);
	private String subformId = "STAMP_DUTY_POPUP";	

	@Override
	public Object getObjectForm() throws Exception
	{
		//Get param appGroupNo sent from view button of SearchClaim page
		String applicationGroupNo = request.getParameter("appGroupNo"); 
		String isMyTask = request.getParameter("isMyTask"); 
		//logger.debug("Stamp Duty popup : " + applicationGroupNo);
		if(!Util.empty(applicationGroupNo))
		{
			//get ApplicationGroupDataM object from QR-NO
			OrigApplicationGroupDAOImpl agDAO = new OrigApplicationGroupDAOImpl();
			ApplicationGroupDataM appGroup = agDAO.loadSingleApplicationGroupByApplicationGroupNo(applicationGroupNo);
			appGroup.setClaimBy(isMyTask);
			return appGroup;
		}
		return new ApplicationGroupDataM();
	}
	
	@Override
	public String processForm() 
	{
		logger.debug("StampDutyPopupForm - processForm");
		//TODO Set complete flag to Completed
		String stampAction = request.getParameter("stampAction");
		
		logger.debug("setRequesterInfo");
		setRequesterInfo();
		
		if("Complete".equalsIgnoreCase(stampAction))
		{
			logger.debug("setStampdutyCompleteFlag");
			setStampdutyCompleteFlag();
		}
		
		return super.processForm();
	}
	
	public void setStampdutyCompleteFlag()
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;		
		String applicationGroupNo = request.getParameter("appGroupNo");
		UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
		
		OrigObjectDAO orig = new OrigObjectDAO();
		try{
			conn = orig.getConnection();
			Date today = new Date(System.currentTimeMillis());
			StringBuilder sql = new StringBuilder("");
			sql.append("UPDATE LOAN_SETUP_CLAIM SET COMPLETE_FLAG = 'Y', ");
			sql.append(" LOAN_SETUP_STATUS = 'Completed' , COMPLETE_BY = ? , COMPLETE_DATE = ? ");
			sql.append(" , UPDATE_BY = ? , UPDATE_DATE = ? ");
			sql.append(" WHERE CLAIM_ID = (SELECT CLAIM_ID FROM LOAN_SETUP_CLAIM WHERE TYPE='S' AND APPLICATION_GROUP_NO = ?)");
			String dSql = String.valueOf(sql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, userM.getUserName());
			ps.setDate(cnt++, today);
			ps.setString(cnt++, userM.getUserName());
			ps.setDate(cnt++, today);
			ps.setString(cnt++, applicationGroupNo);
			logger.debug("update StampdutyCompleteFlag for appGroupNo " + applicationGroupNo);
			ps.executeUpdate();
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage());
		}finally{
			try{
				orig.closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}
	
	public void setRequesterInfo()
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;		
		String applicationGroupNo = request.getParameter("appGroupNo");
		String REQUESTOR_NAME = request.getParameter("REQUESTOR_NAME");
		String REQUESTOR_POSITION = request.getParameter("REQUESTOR_POSITION");
		
		OrigObjectDAO orig = new OrigObjectDAO();
		try{

			conn = orig.getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("UPDATE LOAN_SETUP_STAMP_DUTY SET ");
			if(!Util.empty(REQUESTOR_NAME))
			{
				sql.append(" REQUESTOR_NAME = ? ");
				if(!Util.empty(REQUESTOR_POSITION))
				{sql.append(" , ");}
			}
			if(!Util.empty(REQUESTOR_POSITION))
			{sql.append(" REQUESTOR_POSITION = ? ");}
			sql.append(" WHERE CLAIM_ID = (SELECT CLAIM_ID FROM LOAN_SETUP_CLAIM WHERE TYPE='S' AND APPLICATION_GROUP_NO = ?)");
			String dSql = String.valueOf(sql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			if(!Util.empty(REQUESTOR_NAME))
			{ps.setString(cnt++, REQUESTOR_NAME);}
			if(!Util.empty(REQUESTOR_POSITION))
			{ps.setString(cnt++,REQUESTOR_POSITION);}
			ps.setString(cnt++, applicationGroupNo);
			logger.debug("update setRequesterInfo for appGroupNo " + applicationGroupNo);
			ps.executeUpdate();
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage());
		}finally{
			try{
				orig.closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}
	
}
