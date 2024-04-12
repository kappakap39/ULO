package com.eaf.inf.batch.ulo.kemail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.dao.InfBatchObjectDAO;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.task.api.TaskInf;
import com.eaf.core.ulo.common.task.exception.TaskException;
import com.eaf.core.ulo.common.task.model.TaskDataM;
import com.eaf.core.ulo.common.task.model.TaskExecuteDataM;
import com.eaf.core.ulo.common.task.model.TaskObjectDataM;
import com.eaf.core.ulo.common.util.Util;

public class KEmailTask extends InfBatchObjectDAO implements TaskInf{
	private static transient Logger logger = Logger.getLogger(KEmailTask.class);
	String K_EMAIL_MODULE_ID = InfBatchProperty.getInfBatchConfig("K_EMAIL_MODULE_ID");
	String SPECIAL_ADDITIONAL_SERVICE_EMAIL = SystemConstant.getConstant("SPECIAL_ADDITIONAL_SERVICE_EMAIL");
	String JOBSTATE_POST_APPROVED = SystemConstant.getConstant("JOBSTATE_POST_APPROVED");
	@Override
	public ArrayList<TaskObjectDataM> getTaskObjects() throws TaskException {
		ArrayList<TaskObjectDataM> taskObjects = new ArrayList<>();
		try
		{
			ArrayList<KEmailDataM> kEmails = getKEmailList();
			for(KEmailDataM kEmail : kEmails){
				TaskObjectDataM taskObject = new TaskObjectDataM();
				taskObject.setUniqueId(kEmail.getAppRecordId());
				taskObject.setObject(kEmail);
				taskObjects.add(taskObject);
		}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}
		logger.debug("taskObjects size : "+taskObjects.size());
		return taskObjects;
	}

	@Override
	public TaskExecuteDataM onTask(TaskDataM task) throws TaskException, Exception {
		TaskExecuteDataM taskExecute = new TaskExecuteDataM();
		try
		{
			TaskObjectDataM taskObject = task.getTaskObject();
			KEmailDataM kEmail = (KEmailDataM)taskObject.getObject();
			taskExecute.setResponseObject(kEmail);
			taskExecute.setResultCode(InfBatchConstant.ResultCode.SUCCESS);
		}
		catch(Exception e)
		{
			logger.fatal("ERROR ",e);
			taskExecute.setResultCode(InfBatchConstant.ResultCode.FAIL);
			taskExecute.setResultDesc(e.getLocalizedMessage());
			
		}
		return taskExecute;
	}
	
	public ArrayList<KEmailDataM> getKEmailList()
	{
		ArrayList<KEmailDataM> kEmails = new ArrayList<KEmailDataM>();
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT DISTINCT AG.APPLICATION_GROUP_ID, AG.APPLICATION_GROUP_NO, A.APPLICATION_RECORD_ID, ");
			sql.append(" OP.EN_FIRST_NAME, OP.EN_LAST_NAME, OP.TH_FIRST_NAME, OP.TH_LAST_NAME, ");
			sql.append(" OP.CIS_NO, OP.EMAIL_PRIMARY, L.ACCOUNT_NO ");
			sql.append(" FROM ORIG_APPLICATION_GROUP AG ");
			sql.append(" JOIN ORIG_APPLICATION A 				ON A.APPLICATION_GROUP_ID = AG.APPLICATION_GROUP_ID ");
			sql.append(" JOIN BUSINESS_CLASS BC 		 		ON BC.BUS_CLASS_ID = A.BUSINESS_CLASS_ID AND BC.ORG_ID = 'KPL' ");
			sql.append(" JOIN ORIG_PERSONAL_RELATION OPR      	ON OPR.REF_ID = A.APPLICATION_RECORD_ID AND OPR.RELATION_LEVEL = 'A' ");
			sql.append(" JOIN ORIG_PERSONAL_INFO OP           	ON OP.PERSONAL_ID = OPR.PERSONAL_ID ");
			sql.append(" JOIN ORIG_LOAN L                     	ON L.APPLICATION_RECORD_ID = A.APPLICATION_RECORD_ID ");
			sql.append(" JOIN ORIG_ADDITIONAL_SERVICE_MAP ASM 	ON ASM.LOAN_ID = L.LOAN_ID ");
			sql.append(" JOIN ORIG_ADDITIONAL_SERVICE OAS     	ON OAS.SERVICE_ID = ASM.SERVICE_ID ");
			sql.append(" AND NOT EXISTS (SELECT 1 FROM INF_BATCH_LOG BLOG ");
			sql.append(" WHERE BLOG.APPLICATION_GROUP_ID = AG.APPLICATION_GROUP_ID ");
			sql.append(" AND BLOG.INTERFACE_STATUS = ? AND BLOG.INTERFACE_CODE = ? ) ");
			sql.append(" AND OAS.SERVICE_TYPE = ? ");
			sql.append(" AND AG.JOB_STATE = ? ");
			
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, InfBatchConstant.STATUS_COMPLETE);
			ps.setString(2, K_EMAIL_MODULE_ID);
			ps.setString(3, SPECIAL_ADDITIONAL_SERVICE_EMAIL);
			ps.setString(4, JOBSTATE_POST_APPROVED);
			
			rs = ps.executeQuery();
			
			while(rs.next())
			{
				if(!Util.empty(rs.getString("ACCOUNT_NO")))
				{
					String appGroupNo = rs.getString("APPLICATION_GROUP_NO");
					String channel = "";
					if(!Util.empty(appGroupNo))
					{
						if(appGroupNo.startsWith("QR-"))
						{
							channel = "SYS-FLP";
						}
						else if(appGroupNo.startsWith("EA-"))
						{
							channel = "SYS-EAPP";
						}
					}
					
					KEmailDataM kEmail = new KEmailDataM();
					kEmail.setAppGroupId(rs.getString("APPLICATION_GROUP_ID"));
					kEmail.setAppRecordId(rs.getString("APPLICATION_RECORD_ID"));
					
					kEmail.setCustomerName(rs.getString("TH_FIRST_NAME") + " " + rs.getString("TH_LAST_NAME"));
					kEmail.setDocumentPassword(" ");
					kEmail.setChannel(channel);
					kEmail.setCisID(rs.getString("CIS_NO")); 
					kEmail.setCustomerType("I");  //I: Individual, O: Organization
					
					kEmail.setRequestID("TryMe " + System.currentTimeMillis());
					
					kEmail.setAccountNameEnglish(rs.getString("EN_FIRST_NAME") + " " + rs.getString("EN_LAST_NAME"));
					kEmail.setAccountNameThai(rs.getString("TH_FIRST_NAME") + " " + rs.getString("TH_LAST_NAME"));
					kEmail.setAccountNumber(rs.getString("ACCOUNT_NO"));
					kEmail.setCycle("V"); //KPL = V
					kEmail.setEmailAddress(rs.getString("EMAIL_PRIMARY"));
					kEmail.setFormat("P");
					kEmail.setLanguage("T");
					kEmail.setProduct("KPL"); //KPL
					kEmail.setSendMethod("TryMe");
	
					kEmails.add(kEmail);
				}
			}
			
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}
		}
		
		return kEmails;
	}

}
