package com.eaf.orig.ulo.pa;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.FormAction;
import com.eaf.j2ee.pattern.control.FormHelper;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.app.dao.OrigApplicationGroupDAOImpl;
import com.eaf.orig.ulo.app.dao.OrigPersonalAddressDAOImpl;
import com.eaf.orig.ulo.app.dao.OrigPersonalInfoDAOImpl;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

@SuppressWarnings("serial")
public class CancelReasonPopupForm extends FormHelper implements FormAction{
	private static transient Logger logger = Logger.getLogger(CancelReasonPopupForm.class);
	private String subformId = "AS_CANCEL_REASON_POPUP";	

	@Override
	public Object getObjectForm() throws Exception
	{
		//Pass param appGroupNo from searchClaim page to popup JSP page
		logger.debug("CancelReasonPopupForm - getObjectForm");
		return request.getParameter("appGroupNo");
	}
	
	@Override
	public String processForm() 
	{
		cancelAccountSetup();
		return super.processForm();
	}
	
	public void cancelAccountSetup()
	{
		logger.debug("cancelAccountSetup");
		UserDetailM userM =	(UserDetailM) request.getSession().getAttribute("ORIGUser");
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;		
		String userName = userM.getUserName();
		String appGroupNo = request.getParameter("appGroupNo");
		String REASON = request.getParameter("REASON");
		String OTHER_REASON = request.getParameter("OTHER_REASON");
		String CANCEL_REMARK = request.getParameter("CANCEL_REMARK");
		
		logger.debug("appGroupNo = " + appGroupNo);
		logger.debug("REASON = " + REASON);
		logger.debug("OTHER_REASON = " + OTHER_REASON);
		logger.debug("CANCEL_REMARK = " + CANCEL_REMARK);
		
		OrigObjectDAO orig = new OrigObjectDAO();
		Date datenow = new java.sql.Date(System.currentTimeMillis());
		
		try
		{
			conn = orig.getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("UPDATE LOAN_SETUP_CLAIM SET ");
			sql.append(" LOAN_SETUP_STATUS = 'Cancelled' , UPDATE_BY = ? , UPDATE_DATE = ? ");
			sql.append(" WHERE APPLICATION_GROUP_NO = ?");
			
			int cnt=1;
			String dSql = String.valueOf(sql);
			ps = conn.prepareStatement(dSql);
			ps.setString(cnt++, userName);
			ps.setDate(cnt++, datenow);
			ps.setString(cnt++, appGroupNo);
			
			logger.debug("CancelAccoutnSetup by user: " + userName + " - appGroupNo: " + appGroupNo);
			ps.executeUpdate();
		}
		catch(Exception e)
		{
			logger.fatal("ERROR ",e);
		}
		finally{
			try{
				orig.closeConnection(conn, ps);
			}catch(Exception e){
				logger.fatal("ERROR "+e.getLocalizedMessage());
			}
		}
		
	}
	
}
