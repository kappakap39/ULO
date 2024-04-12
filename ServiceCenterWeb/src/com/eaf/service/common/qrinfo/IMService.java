package com.eaf.service.common.qrinfo;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.log4j.Logger;

import org.apache.commons.io.FileUtils;

public class IMService {
	private static transient Logger logger = Logger.getLogger(IMService.class);
	private static String	imInterfaceFormat = "%-10s|%-26s|%-30s|%-2s|%-6s|%-20s|%-20s|%-20s";
	private static String	imJobFormat = "%-7s|%-20s|%-10s|%-22s|%-5s|%-7s|%-23s|%2s|%-20s";
	
	public static String extractIMData(Connection imConnection, String qr) {
		
		// Open connection to ULO database
		//Connection uloConnection = DatabaseUtil.getUloDBConnection();
		Statement imStmt = null;
		String htmlResp = "";

		// Retrieve data
		if (imConnection != null) {
			ResultSet imResultSet = null;
			String imSQL = "SELECT * FROM im_app.service_req_resp WHERE ref_code='" + qr + "' ORDER BY create_date DESC";
		    try {
		        imStmt = imConnection.createStatement();
		        //InquirySupport.saveOutput("Execute SQL: " + uloSQL);
		        imResultSet = imStmt.executeQuery(imSQL);
		        //htmlResp += "<div>" + String.format("\n<======== IM interfaes for %s ========>", qr) + "</div>";
		        //htmlResp += "<div>" + String.format(imInterfaceFormat, "QR", "Create Datetime", "Service", "AC", "Status", "Desc", "Error Msg", "Content Msg") + "</div>";
		        int index = 0;
		        while (imResultSet.next()) {
		            String serviceId = imResultSet.getString("service_id");	// service name
		            String action = imResultSet.getString("activity_type");	// inbound or outbound
		            String serviceData;
		            if ("DecisionService".equals(serviceId))
		            	serviceData = imResultSet.getString("service_data");
		            else
		            	serviceData = "";
		            String transId = imResultSet.getString("transaction_id");
		            String rsStatus = imResultSet.getString("resp_code");		// interface response code
		            String rsDesc = imResultSet.getString("resp_desc");
		            String rsError = imResultSet.getString("error_message");
		            String msg = imResultSet.getString("content_msg");			// interface message
		            String createDttm = imResultSet.getString("create_date");
		            //htmlResp += "<div>" + String.format(imInterfaceFormat, qr, createDttm, serviceId + serviceData, action, rsStatus, rsDesc, rsError, QRInfoUtil.prettyJSON(msg)) + "</div>";
		            htmlResp += QRInfoUtil.tdFormatSimple(qr, createDttm, serviceId + serviceData, action, rsStatus, rsDesc, rsError, QRInfoUtil.toMsgLink("IMOII", index, msg));
		            index++;
		        }
		        imResultSet.close();
		    } 
			catch (SQLException e ) {
				logger.fatal("Error while retrieve data from IM. Exception is:" + e);
		    }
		}

		// Free resources
		try {
    		if (imStmt != null) 
    			imStmt.close();
    		//if (uloConnection != null)
    		//	uloConnection.close();
		} 
    	catch (SQLException e) {
			logger.fatal("Error while retrieve data from IM. Exception is:" + e);
		}
	    return htmlResp;
	}

	public static String extractIMJob(Connection imConnection, String qr) {
		
		// Open connection to ULO database
		//Connection uloConnection = DatabaseUtil.getUloDBConnection();
		Statement imStmt = null;
		String htmlResp = "";
	
		// Retrieve data
		if (imConnection != null) {
			ResultSet imResultSet = null;
			StringBuilder _imSQL = new StringBuilder();
			_imSQL.append("SELECT j.pkid, j.transactionid, j.param1 AS form, j.param16 AS rc, j.param17 AS channel, j.createddate, j.param18, t.jobstatus ");
			_imSQL.append("FROM im_app.im_job j ");
			_imSQL.append("JOIN im_app.im_transaction t ON j.transactionid = t.transactionid ");
			_imSQL.append("WHERE j.param3='").append(qr).append("' ");
			String imSQL = _imSQL.toString();
		    try {
		        imStmt = imConnection.createStatement();
		        //InquirySupport.saveOutput("Execute SQL: " + uloSQL);
		        imResultSet = imStmt.executeQuery(imSQL);
		        //htmlResp += "<div>" + String.format("\n<======== IM Job for %s ========>", qr) + "</div>";
		        //htmlResp += "<div>" + String.format(imJobFormat, "PKID", "TranID", "QR", "Form", "RC", "Channel", "CrateDate", "Status", "Msg") + "</div>";
		        int index = 0;
		        while (imResultSet.next()) {
		            String createDttm = imResultSet.getString("createddate");	
		            String pkid = imResultSet.getString("pkid");
		            String transactionid = imResultSet.getString("transactionid");
		            String form = imResultSet.getString("form");
		            String rc = imResultSet.getString("rc");
		            String channel = imResultSet.getString("channel");
		            String jobstatus = imResultSet.getString("jobstatus");
		            String msg = imResultSet.getString("param18");
		            //htmlResp += "<div>" + String.format(imJobFormat, pkid, transactionid, qr, form, rc, channel, createDttm, jobstatus, QRInfoUtil.prettyJSON(msg)) + "</div>";
		            htmlResp += QRInfoUtil.tdFormatSimple(pkid, transactionid, qr, form, rc, channel, createDttm, jobstatus, QRInfoUtil.toMsgLink("IMOJI", index, msg));
		            index++;
		        }
		        imResultSet.close();
		    } 
			catch (SQLException e ) {
				logger.fatal("Error while retrieve data from IM. Exception is:" + e);
		    }
		}
	
		// Free resources
		try {
			if (imStmt != null) 
				imStmt.close();
			//if (uloConnection != null)
			//	uloConnection.close();
		} 
		catch (SQLException e) {
			logger.fatal("Error while retrieve data from IM. Exception is:" + e);
		}
	    return htmlResp;
	}

}
