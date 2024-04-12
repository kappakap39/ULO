package com.eaf.service.common.qrinfo;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

public class ULOService {
	private static String	uloInterfaceFormat = "%-10s|%-26s|%-30s|%-2s|%-6s|%-20s|%-20s";
	private static String	uloAppLogFormat = "%-10s|%-26s|%-10s|%-20s|%-25s|%-10s|%-10s|%-5s";
	private static String	uloAppHistFormat = "%-6s|%-26s|%-10s|%-20s";
	private static String	uloInfBatchLogFormat = "%-12s|%-10s|%-13s|%-15s|%-6s|%-20s|%-12s|%-22s|%-45s";
	private static transient Logger logger = Logger.getLogger(ULOService.class);
	
	public static String extractULOData(Connection uloConnection, String qr) {
		// Open connection to ULO database
		//Connection uloConnection = DatabaseUtil.getUloDBConnection();
		Statement uloStmt = null;
		String htmlResp = "";

		// Retrieve data
		if (uloConnection != null) {
			ResultSet uloResultSet = null;
			String uloSQL = "SELECT * FROM orig_app.service_req_resp WHERE ref_code='" + qr + "' ORDER BY create_date DESC";
		    try {
		        uloStmt = uloConnection.createStatement();
		        //InquirySupport.saveOutput("Execute SQL: " + uloSQL);
		        uloResultSet = uloStmt.executeQuery(uloSQL);
		        //htmlResp += "<div>" + String.format("\n<======== ULO interfaes for %s ========>", qr) + "</div>";
		        //htmlResp += "<div>" + String.format(uloInterfaceFormat, "QR", "Create Datetime", "Service", "AC", "Status", "Desc", "Error Msg") + "</div>";
		        int index = 0;
		        while (uloResultSet.next()) {
		            String serviceId = uloResultSet.getString("service_id");	// service name
		            String action = uloResultSet.getString("activity_type");	// inbound or outbound
		            String serviceData;
		            if ("DecisionService".equals(serviceId))
		            	serviceData = uloResultSet.getString("service_data");
		            else
		            	serviceData = "";
		            String transId = uloResultSet.getString("transaction_id");
		            String rsStatus = uloResultSet.getString("resp_code");		// interface response code
		            String rsDesc = uloResultSet.getString("resp_desc");
		            String rsError = uloResultSet.getString("error_message");
		            String msg = uloResultSet.getString("content_msg");			// interface message
		            String createDttm = uloResultSet.getString("create_date");
		            //htmlResp += "<div>" + String.format(uloInterfaceFormat, qr, createDttm, serviceId + serviceData, action, QRInfoUtil.prettyJSON(msg), rsStatus, rsDesc, rsError) + "</div>";
		            htmlResp += QRInfoUtil.tdFormatSimple(qr, createDttm, serviceId + serviceData, action, QRInfoUtil.toMsgLink("ULOOII", index, msg), rsStatus, rsDesc, rsError);
		            index++;
		        }
		        uloResultSet.close();
		    } 
			catch (SQLException e ) {
				logger.fatal("Error while retrieve data from ULO. Exception is:" + e);
		    }
		}

		// Free resources
		try {
    		if (uloStmt != null) 
				uloStmt.close();
    		//if (uloConnection != null)
    		//	uloConnection.close();
		} 
    	catch (SQLException e) {
			logger.fatal("Error while retrieve data from ULO. Exception is:" + e);
		}
	    return htmlResp;
	}

	public static String extractAppLogData(Connection uloConnection, String qr) {
		// Open connection to ULO database
		//Connection uloConnection = DatabaseUtil.getUloDBConnection();
		Statement uloStmt = null;
		String htmlResp = "";
	
		// Retrieve data
		if (uloConnection != null) {
			ResultSet uloResultSet = null;
			StringBuilder _uloSQL = new StringBuilder();
			_uloSQL.append("SELECT al.create_date AS create_date, al.create_by AS create_by, al.action_desc AS action_desc");
			_uloSQL.append(", al.application_status AS application_status, al.life_cycle AS life_cycle");
			_uloSQL.append(", al.job_state AS job_state, lb.system_id1 AS station ");
			_uloSQL.append("FROM orig_app.orig_application_group ag JOIN orig_app.orig_application_log al ON ag.application_group_id=al.application_group_id ");
			_uloSQL.append("JOIN orig_app.list_box_master lb ON al.job_state=lb.choice_no AND lb.field_id=123 ");
			_uloSQL.append("WHERE ag.application_group_no='").append(qr).append("' ");
			_uloSQL.append("ORDER BY al.create_date ASC");
			String uloSQL = _uloSQL.toString();
		    try {
		        uloStmt = uloConnection.createStatement();
		        //InquirySupport.saveOutput("Execute SQL: " + uloSQL);
		        uloResultSet = uloStmt.executeQuery(uloSQL);
		        //htmlResp += "<div>" + String.format("\n<======== ULO Application Log for %s ========>", qr) + "</div>";
		        //htmlResp += "<div>" + String.format(uloAppLogFormat, "QR", "Create Datetime", "User", "Action", "App Status", "Life Cycle", "Next State", "Next Station") + "</div>";
		        while (uloResultSet.next()) {
		            String createDttm = uloResultSet.getString("create_date");	
		            String user = uloResultSet.getString("create_by");			// User performs action
		            String action = uloResultSet.getString("action_desc");		// Action
		            String appStatus = uloResultSet.getString("application_status");
		            String lifeCycle = uloResultSet.getString("life_cycle");
		            String nextState = uloResultSet.getString("job_state");
		            String nextStation = uloResultSet.getString("station");		// Next station
		            //htmlResp += "<div>" + String.format(uloAppLogFormat, qr, createDttm, user, action, appStatus, lifeCycle, nextState, nextStation) + "</div>";
		            htmlResp += QRInfoUtil.tdFormatSimple(qr, createDttm, user, action, appStatus, lifeCycle, nextState, nextStation);
		        }
		        uloResultSet.close();
		    } 
			catch (SQLException e ) {
				logger.fatal("Error while retrieve data from ULO. Exception is:" + e);
		    }
		}
	
		// Free resources
		try {
			if (uloStmt != null) 
				uloStmt.close();
			//if (uloConnection != null)
			//	uloConnection.close();
		} 
		catch (SQLException e) {
			logger.fatal("Error while retrieve data from ULO. Exception is:" + e);
		}
	    return htmlResp;
	}

	public static String extractAppHistData(Connection uloConnection, String qr) {

		// Open connection to ULO database
		//Connection uloConnection = DatabaseUtil.getUloDBConnection();
		Statement uloStmt = null;
		String htmlResp = "";
		
		// Retrieve data
		if (uloConnection != null) {
			ResultSet uloResultSet = null;
			StringBuilder _uloSQL = new StringBuilder();
	        _uloSQL.append("SELECT hist.role AS role, hist.app_data AS data, hist.create_date AS create_date, hist.create_by AS create_by ");
	        _uloSQL.append("FROM orig_app.orig_application_group ag JOIN orig_app.orig_history_data hist ON ag.application_group_id=hist.application_group_id ");
	        _uloSQL.append("WHERE ag.application_group_no='").append(qr).append("' ");
	        _uloSQL.append("ORDER BY hist.create_date ASC");
			String uloSQL = _uloSQL.toString();
		    try {
		        uloStmt = uloConnection.createStatement();
		        //InquirySupport.saveOutput("Execute SQL: " + uloSQL);
		        uloResultSet = uloStmt.executeQuery(uloSQL);
		        //htmlResp += "<div>" + String.format("\n<======== ULO Application History Log for %s ========>", qr) + "</div>";
		        //htmlResp += "<div>" + String.format(uloAppHistFormat, "Role", "Create Datetime", "Create By", "Msg") + "</div>";
		        int index = 0 ;
		        while (uloResultSet.next()) {
		            String createDttm = uloResultSet.getString("create_date");	
		            String user = uloResultSet.getString("create_by");			// User performs action
		            String role = uloResultSet.getString("role");		// Action
		            String msg = QRInfoUtil.toMsgLink("ULOAH" ,index, uloResultSet.getString("data"));
		            //htmlResp += "<div>" + String.format(uloAppHistFormat, qr, createDttm, user, role, msg) + "</div>";
		            htmlResp += QRInfoUtil.tdFormatSimple(qr, createDttm, user, role, msg);
		            index++;
		        }
		        uloResultSet.close();
		    } 
			catch (SQLException e ) {
				logger.fatal("Error while retrieve data from ULO. Exception is:" + e);
		    }
		}
	
		// Free resources
		try {
			if (uloStmt != null) 
				uloStmt.close();
			//if (uloConnection != null)
			//	uloConnection.close();
		} 
		catch (SQLException e) {
			logger.fatal("Error while retrieve data from ULO. Exception is:" + e);
		}
	    return htmlResp;
	}

	public static String extractInfBatchLog(Connection uloConnection, String qr) {
		// Open connection to ULO database
		//Connection uloConnection = DatabaseUtil.getUloDBConnection();
		Statement uloStmt = null;
		String htmlResp = "";
		
		// Retrieve data
		if (uloConnection != null) {
			ResultSet uloResultSet = null;
			StringBuilder _uloSQL = new StringBuilder();
			_uloSQL.append("SELECT * FROM (");
			_uloSQL.append("SELECT log1.* FROM inf_batch_log log1 WHERE	log1.ref_id='").append(qr).append("' ");
			_uloSQL.append("UNION ");
			_uloSQL.append("SELECT log2.* FROM orig_application_group ag2 JOIN inf_batch_log log2 ON log2.application_group_id=ag2.application_group_id WHERE ag2.application_group_no='").append(qr).append("' ");
			_uloSQL.append("UNION ");
			_uloSQL.append("SELECT log3.* FROM inf_batch_log log3 WHERE	log3.interface_code='EDOCGEN001' and log3.system01='").append(qr).append("' ");
	        _uloSQL.append(") ORDER BY create_date DESC");
			String uloSQL = _uloSQL.toString();
		    try {
		        uloStmt = uloConnection.createStatement();
		        //InquirySupport.saveOutput("Execute SQL: " + uloSQL);
		        uloResultSet = uloStmt.executeQuery(uloSQL);
		        //htmlResp += "<div>" + String.format("\n<======== ULO Interface Batch Log for %s ========>", qr) + "</div>";
		        //htmlResp += "<div>" + String.format(uloInfBatchLogFormat, "AppGroupId", "LifeCycle", "AppRecordId", "Interface", "Status", "RefId", "System01", "CreateDate", "Message") + "</div>";
		        
		        while (uloResultSet.next()) {
		        	String appGrpId = uloResultSet.getString("application_group_id");
		        	String appRcrdId = uloResultSet.getString("application_record_id");
		        	String lifeCycle = uloResultSet.getString("life_cycle");
		        	String interfaceCode = uloResultSet.getString("interface_code");
		        	String interfaceStatus = uloResultSet.getString("interface_status");
		        	String refId = uloResultSet.getString("ref_id");
		        	String system01 = uloResultSet.getString("system01");
		            String createDttm = uloResultSet.getString("create_date");	
		            String msg = QRInfoUtil.prettyJSON(uloResultSet.getString("log_message"));
		            //htmlResp += "<div>" + String.format(uloInfBatchLogFormat, qr, appGrpId, lifeCycle, appRcrdId, interfaceCode, interfaceStatus, refId, system01, createDttm, msg) + "</div>";
		            htmlResp += QRInfoUtil.tdFormatSimple(qr, appGrpId, lifeCycle, appRcrdId, interfaceCode, interfaceStatus, refId, system01, createDttm, msg);
		        }
		        uloResultSet.close();
		    } 
			catch (SQLException e ) {
				logger.fatal("Error while retrieve data from ULO. Exception is:" + e);
		    }
		}
	
		// Free resources
		try {
			if (uloStmt != null) 
				uloStmt.close();
			//if (uloConnection != null)
			//	uloConnection.close();
		} 
		catch (SQLException e) {
			logger.fatal("Error while retrieve data from ULO. Exception is:" + e);
		}
	    return htmlResp;
	}
}
