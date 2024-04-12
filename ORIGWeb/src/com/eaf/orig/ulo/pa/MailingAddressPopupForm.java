package com.eaf.orig.ulo.pa;

import java.sql.Connection;
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
public class MailingAddressPopupForm extends FormHelper implements FormAction{
	private static transient Logger logger = Logger.getLogger(MailingAddressPopupForm.class);
	private String subformId = "MAILING_ADDRESS_POPUP";	

	@Override
	public Object getObjectForm() throws Exception
	{
		//Get param appGroupNo sent from view button of SearchClaim page
		String applicationGroupNo = request.getParameter("appGroupNo");
		String isMyTask = request.getParameter("isMyTask"); 
		logger.debug("Mailing Address popup : " + applicationGroupNo);
		if(!Util.empty(applicationGroupNo))
		{
			//get ApplicationGroupDataM object from QR-NO
			OrigApplicationGroupDAOImpl agDAO = new OrigApplicationGroupDAOImpl();
			ApplicationGroupDataM appGroup = agDAO.loadSingleApplicationGroupByApplicationGroupNo(applicationGroupNo);
			if(appGroup != null)
			{
				appGroup.setClaimBy(isMyTask);
				OrigPersonalInfoDAOImpl personalInfoDAO = new OrigPersonalInfoDAOImpl();
				ArrayList<PersonalInfoDataM> info = personalInfoDAO.loadOrigPersonalInfoM(appGroup.getApplicationGroupId());
				appGroup.setPersonalInfos(info);
			}
			
			return appGroup;
		}
		return new ApplicationGroupDataM();
	}
	
	@Override
	public String processForm() 
	{
		setMailingCompleteFlag();
		return super.processForm();
	}
	
	public void setMailingCompleteFlag()
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;		
		String applicationGroupNo = request.getParameter("appGroupNo");
		UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
		
		OrigObjectDAO orig = new OrigObjectDAO();
		try{
			conn = orig.getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("UPDATE LOAN_SETUP_CLAIM SET COMPLETE_FLAG = 'Y', ");
			sql.append(" LOAN_SETUP_STATUS = 'Completed' , COMPLETE_BY = ? , COMPLETE_DATE = ? ");
			sql.append(" , UPDATE_BY = ? , UPDATE_DATE= ? ");
			sql.append(" WHERE CLAIM_ID = (SELECT CLAIM_ID FROM LOAN_SETUP_CLAIM WHERE TYPE='M' AND APPLICATION_GROUP_NO = ?)");
			String dSql = String.valueOf(sql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, userM.getUserName());
			ps.setDate(2, new java.sql.Date(System.currentTimeMillis()));
			ps.setString(3, userM.getUserName());
			ps.setDate(4, new java.sql.Date(System.currentTimeMillis()));
			ps.setString(5, applicationGroupNo);
			logger.debug("update MailingCompleteFlag for appGroupNo " + applicationGroupNo);
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
