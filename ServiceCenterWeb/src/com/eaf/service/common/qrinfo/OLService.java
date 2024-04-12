package com.eaf.service.common.qrinfo;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import org.apache.log4j.Logger;



public class OLService {
	private static String olCacheFormat = "%-10s|%-14s|%-8s|%-26s|%-26s|%s|%-20s";
	private static transient Logger logger = Logger.getLogger(OLService.class);
	
	public static String prettyXML(String xml_msg, int indent) {
	    try {
	        // Turn xml string into a document
	        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new ByteArrayInputStream(xml_msg.getBytes("utf-8"))));

	        // Setup pretty print options
	        TransformerFactory transformerFactory = TransformerFactory.newInstance();
	        transformerFactory.setAttribute("indent-number", indent);
	        Transformer transformer = transformerFactory.newTransformer();
	        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
	        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
	        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

	        // Return pretty print xml string
	        StringWriter stringWriter = new StringWriter();
	        transformer.transform(new DOMSource(document), new StreamResult(stringWriter));
	        return stringWriter.toString();
	    }
	    catch (Exception e) {
	    	logger.fatal("Error while formatting XML. Exception is:" + e);
	        return xml_msg;
	    }
	}
	
	public static String extractOLCache(Connection olConnection, String qr) {
	    Statement olStmt = null;
	    String htmlResp = "";
	    
	    if (olConnection != null) {
	    	ResultSet olResultSet = null;
	        String olSQL = "SELECT * FROM ol_data.ol_service_cache WHERE key1='" + qr + "' ORDER BY create_date ASC";
	        try {
	            olStmt = olConnection.createStatement();
	            olResultSet = olStmt.executeQuery(olSQL);
	            //htmlResp += "<div>" + String.format("\n<======== OL Cache for %s ========>", qr) + "</div>";
	            //htmlResp += "<div>" + String.format(olCacheFormat, "QR", "ID_NO", "LIFE_CYC", "CREATE_DATE", "EXPIRE_DATE", "SERVICE", "MSG") + "</div>";
	            int index = 0;
	            while (olResultSet.next()) {
	                // Fetch data
	                String idno = olResultSet.getString("key2");
	                String life_cycle = olResultSet.getString("key3");
	                String createDttm = olResultSet.getString("create_date"); 
	                String expireDttm = olResultSet.getString("expire_date");
	                String service = olResultSet.getString("service_name");
	                String xml_data = olResultSet.getString("interface_data");
	                String msg = QRInfoUtil.toMsgLink("OLCI", index, StringEscapeUtils.escapeHtml4(prettyXML(xml_data, 2)));
	                //htmlResp += "<div>" + String.format(olCacheFormat, qr, idno, life_cycle, createDttm, expireDttm, service, msg) + "</div>";
	                htmlResp += QRInfoUtil.tdFormatSimple(qr, idno, life_cycle, createDttm, expireDttm, service, msg);
	                index++;
	            }
	            olResultSet.close();
	        }
	        catch (Exception e) {
	        	logger.fatal("Error while retrieve data from OL. Exception is:" + e);
	        }
	    }
	    // Free resources
	    try {
	        if (olStmt != null) 
	            olStmt.close();
	    }
	    catch (Exception e) {
	        logger.fatal("Error while retrieve data from OL. Exception is:" + e);
	    }
	    return htmlResp;
	}
	
	public static String extractOLTransactionLog(Connection olConnection, String qr) {
	    Statement olStmt = null;
	    String htmlResp = "";
	    
	    if (olConnection != null) {
	    	ResultSet olResultSet = null;
	        String olSQL = "SELECT * FROM ol_data.OL_TRANSACTION_LOG WHERE REF_CODE='" + qr + "' ORDER BY create_date ASC";
	        try {
	            olStmt = olConnection.createStatement();
	            olResultSet = olStmt.executeQuery(olSQL);
	            //htmlResp += "<div>" + String.format("\n<======== OL Cache for %s ========>", qr) + "</div>";
	            //htmlResp += "<div>" + String.format(olCacheFormat, "QR", "ID_NO", "LIFE_CYC", "CREATE_DATE", "EXPIRE_DATE", "SERVICE", "MSG") + "</div>";
	            logger.debug("olSQL: "+olSQL);
	            int index = 0;
	            while (olResultSet.next()) {
	                // Fetch data
	                String service_id = olResultSet.getString("service_id");
	                String activity_type = olResultSet.getString("activity_type");
	                String createDttm = olResultSet.getString("create_date"); 
	                String service = olResultSet.getString("param1");
	                String terminal_type = olResultSet.getString("terminal_type");
	                String xml_data = olResultSet.getString("binary_data");
	                String msg = QRInfoUtil.toMsgLink("OLTL", index, StringEscapeUtils.escapeHtml4(prettyXML(xml_data, 2)));
	                //htmlResp += "<div>" + String.format(olCacheFormat, qr, idno, life_cycle, createDttm, expireDttm, service, msg) + "</div>";
	                htmlResp += QRInfoUtil.tdFormatSimple(qr, service_id, activity_type, createDttm, service, terminal_type, msg);
	                index++;
	            }
	            olResultSet.close();
	        }
	        catch (Exception e) {
	        	logger.fatal("Error while retrieve data from OL. Exception is:" + e);
	        }
	    }
	    // Free resources
	    try {
	        if (olStmt != null) 
	            olStmt.close();
	    }
	    catch (Exception e) {
	        logger.fatal("Error while retrieve data from OL. Exception is:" + e);
	    }
	    return htmlResp;
	}
}
