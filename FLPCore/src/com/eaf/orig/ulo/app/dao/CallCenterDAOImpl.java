package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.orig.profile.model.UserM;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.ReasonDataM;


public class CallCenterDAOImpl extends OrigObjectDAO implements CallCenterDAO {
	String FILED_ID_DISPLAY_REJECT_REASON = SystemConstant.getConstant("FILED_ID_DISPLAY_REJECT_REASON");
	String RELATION_APPLICATION_LEVEL = SystemConstant.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
	String JOB_STATE_CANCELLED = SystemConfig.getGeneralParam("JOB_STATE_CANCELLED");
	String JOB_STATE_REJECTED = SystemConfig.getGeneralParam("JOB_STATE_REJECTED");
	String JOB_STATE_DE2_REJECT = SystemConfig.getGeneralParam("JOB_STATE_DE2_REJECT");
	String FIELD_ID_CANCELLED = SystemConstant.getConstant("FIELD_ID_CANCELLED");
	String DECISION_FINAL_DECISION_REJECT = SystemConstant.getConstant("DECISION_FINAL_DECISION_REJECT");
	String DECISION_FINAL_DECISION_CANCEL = SystemConstant.getConstant("DECISION_FINAL_DECISION_CANCEL");
	String FIELD_ID_CASH_TRANSFER_TYPE = SystemConstant.getConstant("FIELD_ID_CASH_TRANSFER_TYPE");
	ArrayList<String> CALLCENTER_CANCEL_REASON = SystemConstant.getArrayListConstant("CALLCENTER_CANCEL_REASON");
	public CallCenterDAOImpl(String userId){
		this.userId = userId;
	}
	public CallCenterDAOImpl(){
		
	}
	private String userId = "";
	String DECISION_ACTION_CANCEL = SystemConstant.getConstant("DECISION_ACTION_CANCEL");

	@Override
	public ArrayList<HashMap<String, Object>> SearchSubSQL(String applicationGroupId,int lifeCycle,String jobState)throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		ArrayList<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
		try{
			//#UAT Defect : I198 Fix Change SQL for KEC information
			conn = getConnection();
			String dSql;			
			dSql = "SELECT " +
//					"CT.CARD_TYPE_DESC, CT.CARD_LEVEL, " +
					"A.PROJECT_CODE, " +
					"A.APPLICATION_NO, " +
					"A.REF_NO, " +
					"A.APPLICATION_GROUP_ID, " +
					"A.APPLICATION_RECORD_ID APPLICATION_RECORD_ID," +
					"A.FINAL_APP_DECISION APPLICATION_STATUS, " +
					"A.FINAL_APP_DECISION_DATE APPROVE_DATE, " +
					"A.FINAL_APP_DECISION, " +
					"CURRENT_CREDIT_LIMIT, " +
					"LOAN_AMT, " +
					"PI.PERSONAL_ID, PI.PERSONAL_TYPE, " +
					"PI.TH_TITLE_CODE, " +
					"PI.TH_FIRST_NAME TH_FIRST_NAME, " +
					"PI.TH_LAST_NAME, " +
					"PI.EN_TITLE_CODE, " +
					"PI.EN_FIRST_NAME, " +
					"PI.EN_LAST_NAME, " +
					"PI.GENDER, " +
					"PI.IDNO, " +
					"L.LOAN_ID, PM.DUE_CYCLE, " +
					"DCL.REQUEST_DOC, " +
					"UUD.THAI_FIRSTNAME||' '||UUD.THAI_LASTNAME THAI_NAME, " +
					"PKA_SEARCH_INFO.GET_ROLES_DESC(UUD.USER_NAME) AS  ROLE_DESC, " +
					"C.CARD_TYPE "+
				"FROM " +
					"ORIG_APPLICATION A " +
				"JOIN " +
					"ORIG_PERSONAL_INFO PI ON PI.PERSONAL_ID = ( " +
						"SELECT " +
							"PR.PERSONAL_ID " +
						"FROM " +
							"ORIG_PERSONAL_RELATION PR " +
						"WHERE " +
							"PR.REF_ID = A.APPLICATION_RECORD_ID " +
							"AND PR.RELATION_LEVEL = 'A' " +
							"AND ROWNUM = 1) " +
				"LEFT JOIN ORIG_LOAN L ON A.APPLICATION_RECORD_ID = L.APPLICATION_RECORD_ID " +
				"LEFT JOIN ORIG_PAYMENT_METHOD PM ON PM.PAYMENT_METHOD_ID = L.PAYMENT_METHOD_ID " +
				"LEFT JOIN ORIG_CARD C ON L.LOAN_ID = C.LOAN_ID " +
				"LEFT JOIN ORIG_DOC_CHECK_LIST DCL ON DCL.APPLICATION_GROUP_ID = A.APPLICATION_GROUP_ID " +
				"LEFT JOIN US_USER_DETAIL UUD ON LOWER(UUD.USER_NAME) = LOWER(A.FINAL_APP_DECISION_BY) " +
				"WHERE " +
					"A.APPLICATION_GROUP_ID = ? AND A.LIFE_CYCLE = ?" +
				"GROUP BY " +
					"A.PROJECT_CODE, " +
					"A.APPLICATION_NO, " +
					"A.REF_NO, " +
					"A.APPLICATION_GROUP_ID, " +
					"A.APPLICATION_RECORD_ID, " +
					"A.FINAL_APP_DECISION, " +
					"A.FINAL_APP_DECISION_DATE, " +
					"A.FINAL_APP_DECISION, " +
					"CURRENT_CREDIT_LIMIT, " +
					"LOAN_AMT, " +
					"PI.PERSONAL_ID, " +
					"PI.PERSONAL_TYPE, " +
					"PI.TH_TITLE_CODE, " +
					"PI.TH_FIRST_NAME, " +
					"PI.TH_LAST_NAME, " +
					"PI.EN_TITLE_CODE, " +
					"PI.EN_FIRST_NAME, " +
					"PI.EN_LAST_NAME, " +
					"PI.GENDER, " +
					"PI.IDNO, " +
					"L.LOAN_ID, " +
					"PM.DUE_CYCLE, " +
					"DCL.REQUEST_DOC, " +
					"UUD.THAI_FIRSTNAME, " +
					"UUD.THAI_LASTNAME, " +
					"UUD.USER_NAME, " +
					"C.CARD_TYPE "+
				"ORDER BY PI.PERSONAL_TYPE, A.APPLICATION_NO";

			logger.debug("Sql : " + dSql);
			logger.debug("applicationGroupId : " + applicationGroupId);
			ps = conn.prepareStatement(dSql);
			rs = null;
			setInteger(ps, 1, applicationGroupId);
			ps.setInt(2, lifeCycle);
			rs = ps.executeQuery();
			
			ResultSetMetaData rsMeta = rs.getMetaData();
			logger.debug("rsMeta : " + rsMeta.getColumnCount());
			
			while (rs.next()) {
				HashMap<String, Object> dataResult = new HashMap<String, Object>();	
				
				for(int i = 1, count = rsMeta.getColumnCount();i <= count; i++) {
					String colName = rsMeta.getColumnName(i);
					logger.debug("colName : "+colName);
					dataResult.put(colName, (rs.getString(colName) == null) ? "-" : rs.getString(colName));
				}
				
				// additional
				dataResult.put("CASH_TRANSFER", getCashTransfer(rs.getString("LOAN_ID")));
				dataResult.put("ADDITIONAL_SERVICES", getAdditionalServices(rs.getString("LOAN_ID")));
				if(DECISION_FINAL_DECISION_REJECT.equals(rs.getString("FINAL_APP_DECISION"))){
					dataResult.put("REASONS", getReasonDesc(rs.getString("APPLICATION_RECORD_ID")));	
				}else if(DECISION_FINAL_DECISION_CANCEL.equals(rs.getString("FINAL_APP_DECISION"))){
					dataResult.put("REASONS", getReasonDescCancleJobState(rs.getString("APPLICATION_RECORD_ID")));
				}else{
					dataResult.put("REASONS","-");
				}
				dataResult.put("APPLICATION_RECORD_ID", rs.getString("APPLICATION_RECORD_ID"));
				resultList.add(dataResult);
			}

			logger.debug("SearchSubSQL Return Data :"+resultList.size());
			return resultList;
		} catch (Exception e) {
			e.printStackTrace();
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}
	
	private List< HashMap<String, Object> > getCashTransfer(String loanId) {
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		
		String cashtSql = "SELECT " +
				"LBM.DISPLAY_NAME AS CASH_TRANSFER_TYPE, " +
				"CTR.TRANSFER_ACCOUNT ," +
				"CTR.FIRST_TRANSFER_AMOUNT, " +
				"CTR.PERCENT_TRANSFER " +
			"FROM ORIG_CASH_TRANSFER CTR " +
			"LEFT JOIN LIST_BOX_MASTER LBM ON (CTR.CASH_TRANSFER_TYPE = LBM.CHOICE_NO AND LBM.FIELD_ID = '"+FIELD_ID_CASH_TRANSFER_TYPE+"') " +
			"WHERE CTR.LOAN_ID = ? ";
		
		try {
			conn = getConnection();
			ps = conn.prepareStatement(cashtSql);
			setInteger(ps, 1, loanId);
			logger.debug("getCashTransfer("+ loanId + ") sql : " + cashtSql);
			
			rs = ps.executeQuery();
			while (rs.next()) {
				HashMap<String, Object> dataResult = new HashMap<String, Object>();
				dataResult.put("CASH_TRANSFER_TYPE", rs.getString("CASH_TRANSFER_TYPE"));
				dataResult.put("TRANSFER_ACCOUNT", rs.getString("TRANSFER_ACCOUNT"));
				dataResult.put("FIRST_TRANSFER_AMOUNT", rs.getString("FIRST_TRANSFER_AMOUNT"));
				dataResult.put("PERCENT_TRANSFER", rs.getString("PERCENT_TRANSFER"));
				
				list.add(dataResult);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.fatal(e.getLocalizedMessage());
			//throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
		return list;
	}
	
	private List< HashMap<String, Object> > getAdditionalServices(String loanId) {
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		
		String cashtSql = "SELECT LBM.DISPLAY_NAME " +
				"FROM ORIG_ADDITIONAL_SERVICE ADDS " +
				"LEFT JOIN LIST_BOX_MASTER LBM ON (ADDS.SERVICE_TYPE = LBM.CHOICE_NO AND LBM.FIELD_ID = 69) " +
				"WHERE ADDS.LOAN_ID = ? ";
		
		try {
			conn = getConnection();
			ps = conn.prepareStatement(cashtSql);
			setInteger(ps, 1, loanId);
			logger.debug("getAdditionalServices("+ loanId + ") sql : " + cashtSql);
			
			rs = ps.executeQuery();
			while (rs.next()) {
				HashMap<String, Object> dataResult = new HashMap<String, Object>();
				dataResult.put("DISPLAY_NAME", rs.getString("DISPLAY_NAME"));
				list.add(dataResult);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.fatal(e.getLocalizedMessage());
			//throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
		return list;
	}

	@Override
	public void saveReason(ReasonDataM reasonDataM ,  UserM userM) throws Exception {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" INSERT ");
			sql.append(" INTO ");
			sql.append("     ORIG_REASON ");
			sql.append("     ( ");
			sql.append("         APPLICATION_RECORD_ID, ");
			sql.append("         REASON_TYPE, ");
			sql.append("         REASON_CODE, ");
			sql.append("         CREATE_DATE, ");
			sql.append("         CREATE_BY, ");
			sql.append("         UPDATE_DATE, ");
			sql.append("         UPDATE_BY, ");
			sql.append("         ROLE, ");
			sql.append("         REMARK ");
			sql.append("     ) ");
			sql.append("     VALUES ");
			sql.append("    (?,?,?,?,?,?,?,?,?) ");
		
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, reasonDataM.getApplicationRecordId());
			ps.setString(cnt++, reasonDataM.getReasonType());
			ps.setString(cnt++, reasonDataM.getReasonCode());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, userM.getLogonID());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, userM.getLogonID());
			ps.setString(cnt++, reasonDataM.getRole());
			ps.setString(cnt++, reasonDataM.getRemark());
			
			ps.executeUpdate();
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
		
	}

	@Override
	public ArrayList<HashMap<String, Object>> SearchSalesSQL(String applicationGroupId) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String SALE_TYPE_NORMAL_SALES = SystemConstant.getConstant("SALE_TYPE_NORMAL_SALES");
		ArrayList<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
		try {
			// conn = Get Connection
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			
			sql.append("SELECT SALES_ID, SALES_NAME ");
			sql.append(" FROM  ORIG_SALE_INFO ");
			sql.append(" WHERE APPLICATION_GROUP_ID = ? AND SALES_TYPE = ? ");

			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			setInteger(ps, 1, applicationGroupId);
			ps.setString(2,SALE_TYPE_NORMAL_SALES);

			rs = ps.executeQuery();
			
			ResultSetMetaData rsMeta = rs.getMetaData();
			logger.debug("rsMeta : " + rsMeta.getColumnCount());
			
			while (rs.next()) {
				HashMap<String, Object> dataResult = new HashMap<String, Object>();
				for(int i = 1, count = rsMeta.getColumnCount();i <= count; i++) {
					String colName = rsMeta.getColumnName(i);
					logger.debug("colName : "+colName);
					dataResult.put(colName, rs.getString(colName));
				}
				
				resultList.add(dataResult);
			}

			logger.debug("SearchSubSQL Return Data :"+resultList.size());
			return resultList;
		} catch (Exception e) {
			e.printStackTrace();
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}

	private String getReasonDescCancleJobState(String appRecID){
		PreparedStatement ps = null;
		ResultSet rs = null;		
		String STR = "-";
		Connection conn = null;
		try{
			conn = getConnection();
			StringBuilder SQL = new StringBuilder("");
			SQL.append("  SELECT RC.REASON_CODE,LI.DISPLAY_NAME ");
			SQL.append("  FROM ORIG_REASON RC ");
			SQL.append(" INNER JOIN  LIST_BOX_MASTER LI ON RC.REASON_CODE = LI.CHOICE_NO AND LI.FIELD_ID='"+FIELD_ID_CANCELLED+"' ");
			SQL.append(" WHERE RC.APPLICATION_RECORD_ID = ? AND REASON_CODE IN ( ");
			
			String COMMA="";
			for(String reasonCode :CALLCENTER_CANCEL_REASON){
				SQL.append(COMMA+"?");
				COMMA=",";
			}
			SQL.append(" ) ");
			
			logger.debug("SQL >>"+SQL);
			ps = conn.prepareStatement(SQL.toString());
			int index = 1;
			ps.setString(index++,appRecID);
			for(String reasonCode :CALLCENTER_CANCEL_REASON){
				ps.setString(index++,reasonCode); 
			}
			rs = ps.executeQuery();			
			if(rs.next()){
				String REASON_CODE = rs.getString("REASON_CODE");
				STR = rs.getString("DISPLAY_NAME");
				logger.debug("REASON_CODE>>"+REASON_CODE);
			}
		}catch(Exception e) {
			logger.error("ERROR"+e.getMessage());
		}finally{
			try{
				closeConnection(conn,rs, ps);
			}catch(Exception e){
				logger.error("ERROR"+e.getMessage());
			}
		}
		if(null == STR) STR = "-";
		return STR;
	}
	
	private String getReasonDesc(String appRecID){
		PreparedStatement ps = null;
		ResultSet rs = null;		
		String STR = "-";
		Connection conn = null;
		try{
			conn = getConnection();
			StringBuilder SQL = new StringBuilder("");
			SQL.append("  SELECT PO.REASON,LI.DISPLAY_NAME ");
			SQL.append(" FROM ORIG_APPLICATION  APP ");
			SQL.append(" INNER JOIN  XRULES_VERIFICATION_RESULT XRULE ON  APP.APPLICATION_RECORD_ID = XRULE.REF_ID AND XRULE.VER_LEVEL ='"+RELATION_APPLICATION_LEVEL+"' ");
			SQL.append(" INNER JOIN  XRULES_POLICY_RULES PO ON   PO.VER_RESULT_ID =XRULE.VER_RESULT_ID ");
			SQL.append(" INNER JOIN  MS_REJECT_LETTER_REASON RE ON  PO.REASON = RE.REASON_CODE ");
			SQL.append(" INNER JOIN  LIST_BOX_MASTER LI ON RE.TEMPLATE_CODE = LI.CHOICE_NO AND LI.FIELD_ID='"+FILED_ID_DISPLAY_REJECT_REASON+"' ");
			SQL.append(" WHERE EXISTS (SELECT MIN(RANK) FROM XRULES_POLICY_RULES PO2   WHERE  PO2.VER_RESULT_ID = XRULE.VER_RESULT_ID  ");
			SQL.append("               HAVING MIN(PO2.RANK) = PO.RANK ) ");
			SQL.append(" AND APP.APPLICATION_RECORD_ID =? ");
			
			ps = conn.prepareStatement(SQL.toString());
			int index = 1;
			ps.setString(index++,appRecID);			
			rs = ps.executeQuery();			
			if(rs.next()){
				String REASON_CODE = rs.getString("REASON");
				STR = rs.getString("DISPLAY_NAME");
				logger.debug("REASON_CODE>>"+REASON_CODE);
			}
			
			
			
			/*StringBuilder SQL = new StringBuilder("");
				SQL.append(" SELECT ");
				SQL.append("     LISTAGG(X.REASON||' ,') WITHIN GROUP (ORDER BY X.REASON) REASON_DESC ");
				SQL.append(" FROM ");
				SQL.append("     ( ");
				SQL.append("         SELECT DISTINCT ");
				SQL.append("             (  ");
				SQL.append("                 SELECT DISPLAY_NAME FROM LIST_BOX_MASTER ");
				SQL.append("                 WHERE FIELD_ID = R.REASON_TYPE AND CHOICE_NO = R.REASON_CODE ");
				SQL.append("             ) ");
				SQL.append("             REASON ");
				SQL.append("         FROM ");
				SQL.append("             ORIG_REASON R, ");
				SQL.append("             (SELECT ROLE, APPLICATION_RECORD_ID ");
				SQL.append("                 FROM (SELECT ROLE, APPLICATION_RECORD_ID ");
				SQL.append("                         FROM ORIG_REASON TR ");
				SQL.append("                         WHERE TR.APPLICATION_RECORD_ID = ? ");
				SQL.append("                         ORDER BY TR.CREATE_DATE DESC ");
				SQL.append("                         ) ");
				SQL.append("                  WHERE  ROWNUM = 1 ");
				SQL.append("             ) ");
				SQL.append("             TMP ");
				SQL.append("         WHERE ");
				SQL.append("             R.APPLICATION_RECORD_ID = TMP.APPLICATION_RECORD_ID ");
				SQL.append("         AND TMP.ROLE = R.ROLE ");
				SQL.append("         AND R.APPLICATION_RECORD_ID = ? ");
				SQL.append("     ) ");
				SQL.append("     X ");
			
			ps = conn.prepareStatement(SQL.toString());
			
			int index = 1;
			ps.setString(index++,appRecID);
			ps.setString(index++,appRecID);
			
			rs = ps.executeQuery();			
			if(rs.next()){
				STR = rs.getString("REASON_DESC");
			}
			if(null != STR && STR.length() >1){
				STR = STR.substring(0,STR.length()-1);
			}*/
		}catch(Exception e) {
			logger.error("ERROR"+e.getMessage());
		}finally{
			try{
				closeConnection(conn,rs, ps);
			}catch(Exception e){
				logger.error("ERROR"+e.getMessage());
			}
		}
		if(null == STR) STR = "-";
		return STR;
	}
	
	@Override
	public ArrayList<HashMap<String, Object>> SearchDocRequestSQL(String applicationGroupId)
			throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		ArrayList<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
		try {
			// conn = Get Connection
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			
			sql.append("SELECT DOC_CHECK_LIST_ID, REQUEST_DOC, TH_DESC ");
			sql.append(" FROM  ORIG_DOC_CHECK_LIST ");
			sql.append(" LEFT JOIN MS_DOC_LIST ");
			sql.append(" ON MS_DOC_LIST.DOCUMENT_CODE = ORIG_DOC_CHECK_LIST.DOCUMENT_CODE ");
			sql.append(" WHERE APPLICATION_GROUP_ID = ? ");

			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			setInteger(ps, 1, applicationGroupId);

			rs = ps.executeQuery();
			
			ResultSetMetaData rsMeta = rs.getMetaData();
			logger.debug("rsMeta : " + rsMeta.getColumnCount());
			
			while (rs.next()) {
				HashMap<String, Object> dataResult = new HashMap<String, Object>();
				for(int i = 1, count = rsMeta.getColumnCount();i <= count; i++) {
					String colName = rsMeta.getColumnName(i);
					logger.debug("colName : "+colName);
					dataResult.put(colName, rs.getString(colName));
				}
				
				resultList.add(dataResult);
			}

			logger.debug("SearchSubSQL Return Data :"+resultList.size());
			return resultList;
		} catch (Exception e) {
			e.printStackTrace();
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}

}
