package com.eaf.orig.ulo.app.view.util.capport;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.shared.service.OrigServiceLocator;
import com.eaf.orig.ulo.app.util.GenerateUnique;
import com.eaf.orig.ulo.app.view.util.kpl.KPLUtil;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.ApplicationUtil;
import com.eaf.orig.ulo.model.app.AddressDataM;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.CapportLoanDataM;

public class CapPortTransactionUtil 
{
	private static transient Logger logger = Logger.getLogger(CapPortTransactionUtil.class);
	
	public static void createCapPortTransaction(ApplicationGroupDataM applicationGroup)
	{
		try
		{
			String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
			String APPLICATION_GROUP_ID = applicationGroup.getApplicationGroupId();
			//Date DE2_SUBMIT_DATE = applicationGroup.getLastDecisionDate();
			Date DE2_SUBMIT_DATE = new Date(Calendar.getInstance().getTimeInMillis());
			
			ArrayList<ApplicationDataM> apps = applicationGroup.getApplications();
			for(ApplicationDataM app : apps)
			{
				String APPLICATION_RECORD_ID = app.getApplicationRecordId();
				
				LoanDataM loan = app.getLoan();
				BigDecimal APPROVE_AMOUNT = loan.getLoanAmt();
				
				CapportLoanDataM cap = null;
				if(loan != null){cap = loan.getCapport();}
				else
				{cap = new CapportLoanDataM();}
				
				//BigDecimal APPROVE_NUMBER_OF_APP = cap.getApproveNumberOfApp();
				BigDecimal APPROVE_NUMBER_OF_APP = cap.getGrantedApplication();

				String ADJUST_FLAG = MConstant.FLAG.NO;
				
				//String GRANT_AMOUNT_CAP_NAME = cap.getGrantAmountCapName();
				//String GRANT_NUMBER_CAP_NAME = cap.getGrantNumberCapName();
				String GRANT_AMOUNT_CAP_NAME = cap.getAmountCapportId();
				String GRANT_NUMBER_CAP_NAME = cap.getApplicationCapportId();
				
				String[] gacn_array = {};
				String[] gncn_array = {};
				if(GRANT_AMOUNT_CAP_NAME != null)
				{ gacn_array = GRANT_AMOUNT_CAP_NAME.split(",");}
				if(GRANT_NUMBER_CAP_NAME != null)
				{ gncn_array = GRANT_NUMBER_CAP_NAME.split(",");}
				
				PreparedStatement ps = null;
				ResultSet rs = null;
				Connection conn = null;		
				String claimStatus = null;
				String dSql = buildCPT_Insert_Query();
				
				OrigObjectDAO orig = new OrigObjectDAO();
				try {
					conn = orig.getConnection();
					for(String gacn : gacn_array)
					{
						ps = conn.prepareStatement(dSql);
						int cnt = 1;
						ps.setString(cnt++, GenerateUnique.generate("CPT_CAP_PORT_ID_PK",OrigServiceLocator.OL_DB));
						ps.setString(cnt++, gacn);
						ps.setString(cnt++, APPLICATION_GROUP_ID);
						ps.setString(cnt++, APPLICATION_RECORD_ID);
						ps.setString(cnt++, "AMT");
						ps.setBigDecimal(cnt++, APPROVE_AMOUNT);
						ps.setBigDecimal(cnt++, null);
						ps.setString(cnt++, ADJUST_FLAG);
						ps.setString(cnt++, applicationGroup.getUpdateBy());
						ps.setDate(cnt++, DE2_SUBMIT_DATE);
						ps.setString(cnt++, applicationGroup.getCreateBy());
						ps.setDate(cnt++, DE2_SUBMIT_DATE);
						ps.executeUpdate();
					}
					
					for(String gncn : gncn_array)
					{
						ps = conn.prepareStatement(dSql);
						int cnt = 1;
						ps.setString(cnt++, GenerateUnique.generate("CPT_CAP_PORT_ID_PK",OrigServiceLocator.OL_DB));
						ps.setString(cnt++, gncn);
						ps.setString(cnt++, APPLICATION_GROUP_ID);
						ps.setString(cnt++, APPLICATION_RECORD_ID);
						ps.setString(cnt++, "NUM");
						ps.setBigDecimal(cnt++, null);
						ps.setBigDecimal(cnt++, APPROVE_NUMBER_OF_APP);
						ps.setString(cnt++, ADJUST_FLAG);
						ps.setString(cnt++, applicationGroup.getUpdateBy());
						ps.setDate(cnt++, DE2_SUBMIT_DATE);
						ps.setString(cnt++, applicationGroup.getCreateBy());
						ps.setDate(cnt++, DE2_SUBMIT_DATE);
						ps.executeUpdate();
					}
					
				} catch (Exception e) {
					logger.fatal(e.getLocalizedMessage());
				} finally {
					try {
						orig.closeConnection(conn, ps);
					} catch (Exception e) {
						logger.fatal(e.getLocalizedMessage());
					}
				}
			}
		}
		catch(Exception e)
		{
			logger.debug(e.getMessage() + " # Fail to create Capport Transaction");
		}
		
	}
	
	public static String buildCPT_Insert_Query()
	{
		StringBuffer sql = new StringBuffer("");
		sql.append("INSERT INTO ULO_CAP_PORT_TRANSACTION ");
		sql.append("(CAP_PORT_ID, CAP_PORT_NAME, APPLICATION_GROUP_ID, "); 
		sql.append(" APPLICATION_RECORD_ID, GRANT_TYPE, APPROVE_AMOUNT, APPROVE_NUMBER_OF_APP, ADJUST_FLAG, "); 
		sql.append(" UPDATE_DATE, UPDATE_BY, CREATE_DATE, CREATE_BY) ");
		sql.append(" VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
		String dSql = String.valueOf(sql);
		logger.debug("Sql=" + dSql);
		return dSql;
	}

}
