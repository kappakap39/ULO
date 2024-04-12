package com.eaf.service.common.qrinfo;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.dao.InfBatchObjectDAO;
import com.eaf.core.ulo.common.dao.InfBatchServiceLocator;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.shared.service.OrigServiceLocator;
import com.eaf.orig.ulo.constant.MConstant;

@WebServlet("/QRInfo")
public class QRInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static transient Logger logger = Logger.getLogger(QRInfoServlet.class);
	private static String SUCCESS_CODE = SystemConstant.getConstant("SUCCESS_CODE");
	private static ArrayList<String> CJD_SOURCE = SystemConfig.getArrayListGeneralParam("CJD_SOURCE");
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
		logger.debug("Call QRInfo");
    	//String process = req.getParameter("process");
    	//String SYSTEM_USER = SystemConstant.getConstant("SYSTEM_USER");
    	
    	try{
    			HashMap<String,String> respHtmlList = new HashMap<String,String>();
    			
	    		//http://ODM_Server:9080/res/api/v1/decisiontraces/{executeion_id}?parts=execution_request|execution_response&date=iso8601&accept=application%2Fxml&accept-language=en
	    		String QRNo = req.getParameter("QRNo");
	    		logger.debug("QRNo = " + QRNo);
	    		logger.debug("Mode = " + req.getParameter("Mode"));
	    		
	    		if(req.getParameter("Mode") != null)
	    		{
	    			List<String> Mode = Arrays.asList(req.getParameter("Mode").split(","));
	    			
	    			if(Mode.contains("ULOAL"))
	    			{
	    				logger.debug("Get ULO Application Log");
	    				respHtmlList.put("ULOAL", uloApplicationLog(QRNo));
	    			}
	    			if(Mode.contains("ULOAH"))
	    			{
	    				logger.debug("Get ULO Application History");
	    				respHtmlList.put("ULOAH", uloApplicationHistory(QRNo));
	    			}
	    			if(Mode.contains("ULOOII"))
	    			{
	    				logger.debug("Get ULO Online Interface Info");
	    				respHtmlList.put("ULOOII", uloOnlineInterfaceInfo(QRNo));
	    			}
	    			if(Mode.contains("ULOBII"))
	    			{
	    				logger.debug("ULO Batch Interface Info");
	    				respHtmlList.put("ULOBII", uloBatchInterfaceInfo(QRNo));
	    			}
	    			if(Mode.contains("ODMET"))
	    			{
	    				logger.debug("Get ODM Execution Trace");
	    				respHtmlList.put("ODMET", odmExecutionTrace(QRNo));
	    			}
	    			if(Mode.contains("OLCI"))
	    			{
	    				logger.debug("Get OL Cache Info");
	    				respHtmlList.put("OLCI", olCacheInfo(QRNo));
	    			}
	    			if(Mode.contains("IMOII"))
	    			{
	    				logger.debug("Get IM Online Interface Info");
	    				respHtmlList.put("IMOII", imOnlineInterfaceInfo(QRNo));
	    			}
	    			if(Mode.contains("IMOJI"))
	    			{
	    				logger.debug("Get IM Online Job Info");
	    				respHtmlList.put("IMOJI", imOnlineJobInfo(QRNo));
	    			}
	    			if(Mode.contains("OLTL"))
	    			{
	    				logger.debug("Get OL Transaction Log Info");
	    				respHtmlList.put("OLTL", olTransactionLogInfo(QRNo));
	    			}
	    		}
	    		
	    		String respHtmlListJson = QRInfoUtil.getJSONFromStringHashMap(respHtmlList);
	    		logger.debug("respHtmlListJson = " + respHtmlListJson);
	    		
				resp.setCharacterEncoding("UTF-8");
				resp.setContentType("text/plain");
				PrintWriter pw = resp.getWriter();
				pw.print(respHtmlListJson);
				pw.flush();
				pw.close();
	    	
    	}catch(Exception e){
    		logger.fatal("ERROR ",e);
    	}
	}
	
	public static String uloApplicationLog(String QRNo)
	{
		String respHTML = "";
		OrigServiceLocator origService = OrigServiceLocator.getInstance();
		Connection conn;
		try 
		{
			conn = origService.getConnection(OrigServiceLocator.ORIG_DB);
			respHTML += ULOService.extractAppLogData(conn, QRNo);
			conn.close();
		} 
		catch(Exception e) 
		{
			logger.fatal("[ERROR]",e);
			respHTML = "<div>" + e.getMessage() + "</div>";
		}
		return respHTML;
	}
	
	public static String uloApplicationHistory(String QRNo)
	{
		String respHTML = "";
		OrigServiceLocator origService = OrigServiceLocator.getInstance();
		Connection conn;
		try 
		{
			conn = origService.getConnection(OrigServiceLocator.ORIG_DB);
			respHTML += ULOService.extractAppHistData(conn, QRNo);
			conn.close();
		} 
		catch(Exception e) 
		{
			logger.fatal("[ERROR]",e);
			respHTML = "<div>" + e.getMessage() + "</div>";
		}
		return respHTML;
	}
	
	public static String uloOnlineInterfaceInfo(String QRNo)
	{
		String respHTML = "";
		OrigServiceLocator origService = OrigServiceLocator.getInstance();
		Connection conn;
		try 
		{
			conn = origService.getConnection(OrigServiceLocator.ORIG_DB);
			respHTML += ULOService.extractULOData(conn, QRNo);
			conn.close();
		} 
		catch(Exception e) 
		{
			logger.fatal("[ERROR]",e);
			respHTML = "<div>" + e.getMessage() + "</div>";
		}
		return respHTML;
	}
	
	public static String uloBatchInterfaceInfo(String QRNo)
	{
		String respHTML = "";
		OrigServiceLocator origService = OrigServiceLocator.getInstance();
		Connection conn;
		try 
		{
			conn = origService.getConnection(OrigServiceLocator.ORIG_DB);
			respHTML += ULOService.extractInfBatchLog(conn, QRNo);
			conn.close();
		} 
		catch(Exception e) 
		{
			logger.fatal("[ERROR]",e);
			respHTML = "<div>" + e.getMessage() + "</div>";
		}
		return respHTML;
	}
	
	public static String odmExecutionTrace(String QRNo)
	{
		String respHTML = "";

		//Use QRNo to find ExecutionID
		//{executeion_id} = CONCAT(SERVICE_REQ_RESP.SERVICE_DATA,SERVICE_REQ_RESP.TRANSACTION_ID)
		try
		{
			ArrayList<String> execIdList;
			String qrType = (!Util.empty(QRNo) && QRNo.length()>2)?QRNo.substring(0,2):"";
			if(!"EA".equals(qrType) && !isQRNoCJDApplication(QRNo)){
				execIdList = getExecutionIdListByQRNo(QRNo);
			}else{
				execIdList = getExecutionIdListByQRNoEapp(QRNo);
			}
			logger.debug("execIdList.size() = " + execIdList.size());
			
			//Get data from RESDB.EXECUTION_TRACES
			
			if(execIdList.size() > 0)
			{
				PreparedStatement ps = null;
				ResultSet rs = null;
				OrigObjectDAO orig = new OrigObjectDAO();
				Connection conn = orig.getConnection(OrigServiceLocator.XRULES_DB);
				try
				{
					StringBuilder sql = new StringBuilder("");
					sql.append(" SELECT TIME_STAMP, INPUT_PARAMS, OUTPUT_PARAMS, EXECUTION_TRACE_TREE ");
					sql.append(" FROM EXECUTION_TRACES ");
					sql.append(" WHERE EXECUTION_ID = ? ");
					ps = conn.prepareStatement(sql.toString());
					
					int execIdCount = 0;
	
		    		//get decisionTrace for each executionID
					for(String executionId : execIdList)
					{
						logger.debug("exectionId : " + executionId);
						ps.setString(1, executionId);
						rs = ps.executeQuery();
						
						if(rs.next())
						{
							//Build Response HTML table
							Clob ipClob = rs.getClob("INPUT_PARAMS");
							Clob opCLob = rs.getClob("OUTPUT_PARAMS");
							Clob ettClob = rs.getClob("EXECUTION_TRACE_TREE");
							String ipClobStr = ipClob == null ? "" : ipClob.getSubString(1, (int) ipClob.length()).replaceAll("^\\[\\[", "{").replaceAll("\\]\\]$", "}");
							String opClobStr = opCLob == null ? "" : opCLob.getSubString(1, (int) opCLob.length()).replaceAll("^\\[\\[", "{").replaceAll("\\]\\]$", "}");
							String ettClobStr = ettClob == null ? "" : ettClob.getSubString(1, (int) ettClob.length());
							
							respHTML += "<tr>";
				    		respHTML += "<td>" + executionId + "</td>";
				    		respHTML += "<td>" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(rs.getLong("TIME_STAMP"))) + "</td>";
				    		respHTML += "<td><a id=\"Req" + execIdCount + "\" onclick=\"displayDiv(this);\"> Req </a><div id=\"divReq" + execIdCount + "\" style=\"display: none;\"><pre>" + QRInfoUtil.prettyJSON(ipClobStr) + "</pre></div></td>";
				    		respHTML += "<td><a id=\"Resp" + execIdCount + "\" onclick=\"displayDiv(this);\"> Resp </a><div id=\"divResp" + execIdCount + "\" style=\"display: none;\"><pre>" + QRInfoUtil.prettyJSON(opClobStr) + "</pre></div></td>";
				    		respHTML += "<td><a id=\"ExecTree" + execIdCount + "\" onclick=\"displayDiv(this);\"> Trace Tree </a><div id=\"divExecTree" + execIdCount + "\" style=\"display: none;\"><pre>" + writeTraceTree(ettClobStr) + "</pre></div></td>";
				    		respHTML += "</tr>";
				    		execIdCount++;
						}
			    		
					}
				}
				catch(Exception e){respHTML = e.getMessage();}
				finally
				{
					
					try{orig.closeConnection(conn, rs, ps);}
					catch(Exception e) 
					{logger.fatal(e.getLocalizedMessage());}
				}
				
	    		//logger.debug("respHTML = " + respHTML);
			}		
			
			if(Util.empty(respHTML))
			{
				respHTML = "No Results found for " + QRNo;
			}
		}
		catch(Exception e)
		{
			respHTML += "[Error]" + e.getMessage();
		}
		
		return respHTML;
	}
	
	public static String olCacheInfo(String QRNo)
	{
		String respHTML = "";
		OrigServiceLocator origService = OrigServiceLocator.getInstance();
		Connection conn;
		try 
		{
			conn = origService.getConnection(OrigServiceLocator.OL_DB);
			respHTML += OLService.extractOLCache(conn,QRNo);
			conn.close();
		} 
		catch(Exception e) 
		{
			logger.fatal("[ERROR]",e);
			respHTML = "<div>" + e.getMessage() + "</div>";
		}
		return respHTML;
	}
	
	public static String olTransactionLogInfo(String QRNo)
	{
		String respHTML = "";
		OrigServiceLocator origService = OrigServiceLocator.getInstance();
		Connection conn;
		try 
		{
			conn = origService.getConnection(OrigServiceLocator.OL_DB);
			respHTML += OLService.extractOLTransactionLog(conn,QRNo);
			conn.close();
		} 
		catch(Exception e) 
		{
			logger.fatal("[ERROR]",e);
			respHTML = "<div>" + e.getMessage() + "</div>";
		}
		return respHTML;
	}
	
	public static String imOnlineInterfaceInfo(String QRNo)
	{
		String respHTML = "";
		OrigServiceLocator origService = OrigServiceLocator.getInstance();
		Connection conn;
		try 
		{
			conn = origService.getConnection(OrigServiceLocator.IM_DB);
			respHTML += IMService.extractIMData(conn,QRNo);
			conn.close();
		} 
		catch(Exception e) 
		{
			logger.fatal("[ERROR]",e);
			respHTML = "<div>" + e.getMessage() + "</div>";
		}
		return respHTML;
	}
	
	public static String imOnlineJobInfo(String QRNo)
	{
		String respHTML = "";
		OrigServiceLocator origService = OrigServiceLocator.getInstance();
		Connection conn;
		try 
		{
			conn = origService.getConnection(OrigServiceLocator.IM_DB);
			respHTML += IMService.extractIMJob(conn,QRNo);
			conn.close();
		} 
		catch(Exception e) 
		{
			logger.fatal("[ERROR]",e);
			respHTML = "<div>" + e.getMessage() + "</div>";
		}
		return respHTML;
	}
	
	public static String parseExecDate(String execDate)
	{
		if(execDate != null)
	    {
	    	try
	    	{
	    		execDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(Long.parseLong(execDate)));
	    	}
	    	catch(Exception e)
	    	{
	    		logger.fatal("Unable to parse executionDate : " + execDate);
	    	}
	    }
		return execDate;
	}
	
	public static ArrayList<String> getExecutionIdListByQRNo(String QRNo)
	{
		ArrayList<String> executionIdList = new ArrayList<String>();
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;	
		
		OrigObjectDAO orig = new OrigObjectDAO();
		try
		{
			conn = orig.getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append(" SELECT DISTINCT TRANSACTION_ID, SERVICE_DATA, CREATE_DATE ");
			sql.append(" FROM SERVICE_REQ_RESP ");
			sql.append(" WHERE REF_CODE = ? AND SERVICE_ID = 'DecisionService' AND ACTIVITY_TYPE = ? ");
			sql.append(" ORDER BY CREATE_DATE DESC ");
			
			String dSql = String.valueOf(sql);
			logger.debug("dSql = " + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, QRNo);
			ps.setString(2, MConstant.FLAG_O);
			
			rs = ps.executeQuery();
			
			while(rs.next())
			{
				String service_data = rs.getString("SERVICE_DATA");
				String transaction_id = rs.getString("TRANSACTION_ID");
				executionIdList.add(service_data + transaction_id);
				executionIdList.add(service_data + "RE" + transaction_id); //Hidden apply type transform call
				if ("PB1".equals(service_data)) 
				{
					executionIdList.add("PB2" + transaction_id); // Hidden PB2 call
					executionIdList.add("PB2RE" + transaction_id); // Hidden apply type transform call
				}
			}
		}
		catch(Exception e)
		{
			logger.fatal("ERROR ",e);
		}
		finally 
		{
			try{orig.closeConnection(conn, rs, ps);}
			catch(Exception e) 
			{logger.fatal(e.getLocalizedMessage());}
		}
		return executionIdList;
	}
	
	public static ArrayList<String> getExecutionIdListByQRNoEapp(String QRNo)
	{
		ArrayList<String> executionIdList = new ArrayList<String>();
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;	
		OrigObjectDAO orig = new OrigObjectDAO();
		try
		{
			conn = orig.getConnection(OrigServiceLocator.OL_DB);
			StringBuilder sql = new StringBuilder("");
			sql.append(" SELECT DISTINCT CONCAT(PARAM1, TRANSACTION_ID) AS EXEC_ID, CREATE_DATE, ");
			sql.append(" CONCAT(CONCAT(PARAM1, 'RE'), TRANSACTION_ID) AS EXEC_ID_RE ");
			sql.append(" FROM OL_TRANSACTION_LOG ");
			sql.append(" WHERE REF_CODE = ? AND SERVICE_ID = 'DecisionService' ");
			sql.append(" AND ACTIVITY_TYPE = 'CALL_ODM' AND TERMINAL_TYPE = 'IN' ");
			sql.append(" ORDER BY CREATE_DATE DESC ");
			String dSql = String.valueOf(sql);
			logger.debug("dSql = " + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, QRNo);
			
			rs = ps.executeQuery();
			
			while(rs.next())
			{
				String execId = rs.getString("EXEC_ID");
				String execIdRe = rs.getString("EXEC_ID_RE");
				executionIdList.add(execId);
				executionIdList.add(execIdRe);
			}
		}
		catch(Exception e)
		{
			logger.fatal("ERROR ",e);
		}
		finally 
		{
			try{orig.closeConnection(conn, rs, ps);}
			catch(Exception e) 
			{logger.fatal(e.getLocalizedMessage());}
		}
		return executionIdList;
	}
	
	public static String writeTraceTree(String inputString) {
		StringWriter writer = new StringWriter();
		try {
			InputStream xsltFile = QRInfoServlet.class.getClassLoader().getResourceAsStream("com/eaf/service/common/qrinfo/exectrace.xslt");
			StreamSource xsltSource = new StreamSource(xsltFile);
			TransformerFactory factory = TransformerFactory.newInstance();
		    Transformer transformer = factory.newTransformer(xsltSource);
		    // Source of transformation
			StreamSource execTraceSource = new StreamSource(new StringReader(inputString));
			
			// Transform
			StreamResult result = new StreamResult(writer);
		    transformer.transform(execTraceSource, result);
		} catch (Exception e) {
			logger.fatal("Error while writing trace tree. ", e);
		}
		return writer.toString();
	}
	
	public static boolean isQRNoCJDApplication(String applicationGroupNo)
	{
		boolean isCJD = false;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		OrigObjectDAO orig = new OrigObjectDAO();
		
		try{
			conn = orig.getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT SOURCE FROM ORIG_APPLICATION_GROUP WHERE APPLICATION_GROUP_NO = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("applicationGroupNo" + applicationGroupNo);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, applicationGroupNo);			
			rs = ps.executeQuery();			
			if(rs.next())
			{
				if(!Util.empty(rs.getString("SOURCE"))
					&& CJD_SOURCE.contains(rs.getString("SOURCE")))
				{
					isCJD = true;
				}
			}
		}catch(Exception e)
		{
			logger.fatal("ERROR ",e);
		}
		finally 
		{
			try{orig.closeConnection(conn, rs, ps);}
			catch(Exception e) 
			{logger.fatal(e.getLocalizedMessage());}
		}
		return isCJD;
	}
	
}
