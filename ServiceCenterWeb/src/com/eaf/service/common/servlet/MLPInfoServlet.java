package com.eaf.service.common.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.service.common.utils.ResponseUtils;

@WebServlet("/MLPInfo")
public class MLPInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static transient Logger logger = Logger.getLogger(MLPInfoServlet.class);
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
		logger.debug("MLPInfo Servlet");
    	
    	try{
	    		String qrNo = req.getParameter("qrNo");
	    		String idNo = req.getParameter("idNo");
	    		String appStatus = req.getParameter("appStatus");
	    		String cardlinkFlag = req.getParameter("cardlinkFlag");
	    		String createDateFromStr = req.getParameter("createDateFrom");
	    		String createDateToStr = req.getParameter("createDateTo");
	    		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	    		Date createDateFrom = Util.empty(createDateFromStr) ? null : new Date(sdf.parse(createDateFromStr).getTime());
	    		Date createDateTo = Util.empty(createDateToStr) ? null : new Date(sdf.parse(createDateToStr).getTime());
	    		
	    		logger.debug("createDateFrom = " + createDateFromStr);
	    		logger.debug("createDateTo = " + createDateToStr);

	    		HashMap<String,Object> data = new HashMap<String,Object>();			
				data.put("jsonRq", "");
	    		
				HashMap<Integer,HashMap<String,String>> mlpInfoS = getMLPInfoData(qrNo, idNo, appStatus, cardlinkFlag, createDateFrom, createDateTo);
		    	ArrayList<String> appRecordIdList = new ArrayList<String>();
		    	StringBuilder mlpInfoTable = new StringBuilder();
		    	mlpInfoTable.append("<table class='table table-bordered'><th>APPLICATION_GROUP_ID</th><th>APPLICATION_GROUP_NO</th>" +
			    		"<th>CREATE_DATE</th><th>APPLY_TYPE</th><th>CARDLINK_REF_NO</th><th>CARDLINK_FLAG</th><th>IDNO</th>" +
			    		"<th>TH_FIRST_NAME_LAST_NAME</th><th>PERSONAL_TYPE</th><th>CARDLINK_CUST_NO</th><th>CARD_DESC</th><th>LOAN_AMT</th>" +
			    		"<th>PROCESSING_DATE</th><th>CARD_TYPE</th><th>CARD_LEVEL</th><th>CARD_NO_MARK</th><th>CARDLINK_STATUS</th></tr>");
			    		
		    	if(mlpInfoS.size() > 0)
		    	{
			    	for(int i = 0 ; i < mlpInfoS.size() ; i++)
			    	{
			    		HashMap<String,String> mlpInfoHM = mlpInfoS.get(i);
			    		mlpInfoTable.append("<tr>");
			    		mlpInfoTable.append("<td>" + mlpInfoHM.get("APPLICATION_GROUP_ID") + "</td>");
			    		mlpInfoTable.append("<td>" + mlpInfoHM.get("APPLICATION_GROUP_NO") + "</td>");
			    		mlpInfoTable.append("<td>" + mlpInfoHM.get("CREATE_DATE") + "</td>");
			    		mlpInfoTable.append("<td>" + mlpInfoHM.get("APPLICATION_TYPE") + "</td>");
			    		mlpInfoTable.append("<td>" + mlpInfoHM.get("CARDLINK_REF_NO") + "</td>");
			    		mlpInfoTable.append("<td>" + mlpInfoHM.get("CARDLINK_FLAG") + "</td>");
			    		mlpInfoTable.append("<td>" + mlpInfoHM.get("IDNO") + "</td>");
			    		mlpInfoTable.append("<td>" + mlpInfoHM.get("TH_FIRST_NAME_LAST_NAME") + "</td>");
				    	mlpInfoTable.append("<td>" + mlpInfoHM.get("PERSONAL_TYPE") + "</td>");
			    		mlpInfoTable.append("<td>" + mlpInfoHM.get("CARDLINK_CUST_NO") + "</td>");
			    		mlpInfoTable.append("<td>" + mlpInfoHM.get("CARD_DESC") + "</td>");
				    	mlpInfoTable.append("<td>" + mlpInfoHM.get("LOAN_AMT") + "</td>");
				    	mlpInfoTable.append("<td>" + mlpInfoHM.get("PROCESSING_DATE") + "</td>");
				    	mlpInfoTable.append("<td>" + mlpInfoHM.get("CARD_TYPE") + "</td>");
				    	mlpInfoTable.append("<td>" + mlpInfoHM.get("CARD_LEVEL") + "</td>");
				    	mlpInfoTable.append("<td>" + mlpInfoHM.get("CARD_NO_MARK") + "</td>");
				    	mlpInfoTable.append("<td>" + mlpInfoHM.get("CARDLINK_STATUS") + "</td>");
				    	mlpInfoTable.append("</tr>");
				    	String applicationRecordId = mlpInfoHM.get("APPLICATION_RECORD_ID");
				    	if(!Util.empty(applicationRecordId) && !appRecordIdList.contains(applicationRecordId))
				    	{appRecordIdList.add(applicationRecordId);}
			    	}
		    	}
		    	else
		    	{
		    		mlpInfoTable.append("<tr><td>No results found.</td></tr>");
		    	}
		    	mlpInfoTable.append("</table>");
		    	
		    	String clTable = buildInfBatchLogTable("CardLink Batch Log","APPLICATION_RECORD_ID", appRecordIdList, "CL001");
		    	String kmalertTable = buildInfBatchLogTable("KMAlert Batch Log","APPLICATION_RECORD_ID", appRecordIdList, "KM001");
		    	
		    	//logger.debug("tableBuilder = " + tableBuilder);
				data.put("jsonRs",mlpInfoTable.toString() + clTable + kmalertTable);
	    		
				ResponseUtils.sendJsonResponse(resp, data);			
	    	
    	}catch(Exception e){
    		logger.fatal("ERROR ",e);
    	}
	}
	
	private static HashMap<Integer,HashMap<String,String>> getMLPInfoData(String qrNo, String idNo, String appStatus, String cardlinkFlag,
			Date createDateFrom, Date createDateTo)
	{
		String FIELD_ID_CARD_TYPE = SystemConstant.getConstant("FIELD_ID_CARD_TYPE");
		
		OrigObjectDAO orig = new OrigObjectDAO();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;	
		HashMap<Integer,HashMap<String,String>> mlpInfoHMS = new HashMap<>();
		try
		{
			conn = orig.getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT DISTINCT G.APPLICATION_GROUP_ID ,G.APPLICATION_GROUP_NO,  ");
			sql.append(" G.CREATE_DATE, A.CARDLINK_FLAG, A.CARDLINK_REF_NO, ");
			sql.append(" A.APPLICATION_RECORD_ID, ");
			sql.append(" A.APPLICATION_TYPE, ");
			sql.append(" P.IDNO, ");
			sql.append(" P.TH_FIRST_NAME || ' ' ||  P.TH_LAST_NAME AS TH_FIRST_NAME_LAST_NAME , ");
			sql.append(" CC.CARDLINK_CUST_NO, ");
			sql.append(" C.CARD_NO_MARK, ");
			sql.append(" BM.DISPLAY_NAME CARD_DESC, ");
			sql.append(" L.LOAN_AMT, ");
			sql.append(" P.PERSONAL_TYPE, ");
			sql.append(" TO_CHAR(CR.PROCESSIONG_DATE,'dd/MM/yyyy | HH:MM','NLS_CALENDAR=''THAI BUDDHA') AS PROCESSING_DATE, ");
			sql.append(" C.CARD_TYPE, ");
			sql.append(" C.CARD_LEVEL, ");
			sql.append(" CC.CARDLINK_STATUS ");
			sql.append(" FROM MLP_ORIG_APPLICATION_GROUP G ");
			sql.append(" JOIN MLP_ORIG_APPLICATION A ON A.APPLICATION_GROUP_ID = G.APPLICATION_GROUP_ID ");
			sql.append(" JOIN MLP_ORIG_LOAN L ON L.APPLICATION_RECORD_ID = A.APPLICATION_RECORD_ID ");
			sql.append(" JOIN MLP_ORIG_CARD C ON C.LOAN_ID = L.LOAN_ID ");
			sql.append(" JOIN MLP_ORIG_PERSONAL_RELATION PR ON PR.REF_ID = A.APPLICATION_RECORD_ID ");
			sql.append(" JOIN MLP_ORIG_PERSONAL_INFO P ON PR.PERSONAL_ID = P.PERSONAL_ID ");
			sql.append(" JOIN CARD_TYPE CT ON CT.CARD_TYPE_ID = C.CARD_TYPE "); 
			sql.append(" JOIN LIST_BOX_MASTER BM ON BM.FIELD_ID = '"+FIELD_ID_CARD_TYPE+"' AND BM.CHOICE_NO = CT.CARD_CODE ");
			sql.append(" LEFT JOIN MLP_ORIG_CARDLINK_CUSTOMER CC ON CC.CARDLINK_CUST_ID = C.CARDLINK_CUST_ID ");
			sql.append(" LEFT JOIN MLP_INF_CARDLINK_RESULT CR ON CR.REF_ID = A.CARDLINK_REF_NO ");
			sql.append(" WHERE 1=1 ");
			if(!Util.empty(qrNo))
			{
				sql.append(" AND G.APPLICATION_GROUP_NO = ? ");
			}
			if(!Util.empty(idNo))
			{
				sql.append(" AND P.IDNO = ? ");
			}
			if(!Util.empty(appStatus))
			{
				sql.append(" AND G.APPLICATION_STATUS = ? ");
			}
			if(!Util.empty(cardlinkFlag))
			{
				sql.append(" AND A.CARDLINK_FLAG = ? ");
			}
			if(!Util.empty(createDateFrom) && !Util.empty(createDateTo))
			{
				sql.append(" AND G.CREATE_DATE BETWEEN TRUNC( ? ) AND TRUNC( ? )+0.99999 ");
			}
			if(!Util.empty(createDateFrom) && Util.empty(createDateTo))
			{
				sql.append(" AND G.CREATE_DATE BETWEEN TRUNC( ? ) AND TRUNC( ? )+0.99999 ");
			}
			
			ps = conn.prepareStatement(sql.toString());
			int cnt = 1;
			if(!Util.empty(qrNo))
			{
				ps.setString(cnt++, qrNo);
			}
			if(!Util.empty(idNo))
			{
				ps.setString(cnt++, idNo);
			}
			if(!Util.empty(appStatus))
			{
				ps.setString(cnt++, appStatus);
			}
			if(!Util.empty(cardlinkFlag))
			{
				ps.setString(cnt++, cardlinkFlag);
			}
			if(!Util.empty(createDateFrom) && !Util.empty(createDateTo)){
				ps.setDate(cnt++, createDateFrom);
				ps.setDate(cnt++, createDateTo);
			}
			if(!Util.empty(createDateFrom) && Util.empty(createDateTo)){
				ps.setDate(cnt++, createDateFrom);
				ps.setDate(cnt++, createDateFrom);
			}
			
			rs = ps.executeQuery();
			int index = 0;
			while(rs.next())
			{
				HashMap<String,String> mlpInfoHM = new HashMap<>();
				mlpInfoHM.put("APPLICATION_GROUP_ID", FormatUtil.display(rs.getString("APPLICATION_GROUP_ID")));
				mlpInfoHM.put("APPLICATION_RECORD_ID", FormatUtil.display(rs.getString("APPLICATION_RECORD_ID")));
				mlpInfoHM.put("APPLICATION_GROUP_NO", FormatUtil.display(rs.getString("APPLICATION_GROUP_NO")));
				mlpInfoHM.put("CREATE_DATE", FormatUtil.display(rs.getString("CREATE_DATE")));
				mlpInfoHM.put("CARDLINK_FLAG", FormatUtil.display(rs.getString("CARDLINK_FLAG")));
				mlpInfoHM.put("CARDLINK_REF_NO", FormatUtil.display(rs.getString("CARDLINK_REF_NO")));
				mlpInfoHM.put("APPLICATION_TYPE", FormatUtil.display(rs.getString("APPLICATION_TYPE")));
				mlpInfoHM.put("IDNO", FormatUtil.display(rs.getString("IDNO")));
				mlpInfoHM.put("TH_FIRST_NAME_LAST_NAME", FormatUtil.display(rs.getString("TH_FIRST_NAME_LAST_NAME")));
				mlpInfoHM.put("CARDLINK_CUST_NO", FormatUtil.display(rs.getString("CARDLINK_CUST_NO")));
				mlpInfoHM.put("CARD_DESC", FormatUtil.display(rs.getString("CARD_DESC")));
				mlpInfoHM.put("LOAN_AMT", FormatUtil.display(rs.getString("LOAN_AMT")));
				mlpInfoHM.put("PERSONAL_TYPE", FormatUtil.display(rs.getString("PERSONAL_TYPE")));
				mlpInfoHM.put("CARDLINK_FLAG", FormatUtil.display(rs.getString("CARDLINK_FLAG")));
				mlpInfoHM.put("PROCESSING_DATE", FormatUtil.display(rs.getString("PROCESSING_DATE")));
				mlpInfoHM.put("CARD_TYPE", FormatUtil.display(rs.getString("CARD_TYPE")));
				mlpInfoHM.put("CARD_LEVEL", FormatUtil.display(rs.getString("CARD_LEVEL")));
				mlpInfoHM.put("CARDLINK_STATUS", FormatUtil.display(rs.getString("CARDLINK_STATUS")));
				mlpInfoHM.put("CARD_NO_MARK", FormatUtil.display(rs.getString("CARD_NO_MARK")));
				mlpInfoHMS.put(index,mlpInfoHM);
				index++;
			}
		}
		catch(Exception e)
		{
			logger.fatal("ERROR ",e);
		}
		
		return mlpInfoHMS;
	}
	
	private static HashMap<Integer,HashMap<String,String>> getInfBatchLogData(String key, ArrayList<String> valueList, String interfaceCode)
	{
		OrigObjectDAO orig = new OrigObjectDAO();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;	
		HashMap<Integer,HashMap<String,String>> iblHMS = new HashMap<>();
		try
		{
			conn = orig.getConnection();
			StringBuilder sql = new StringBuilder("");
			
			sql.append(" SELECT * FROM MLP_INF_BATCH_LOG ");
			sql.append(" WHERE 1=1 ");
			sql.append(" AND INTERFACE_CODE = ? ");
			if(valueList.size() > 0)
			{
				sql.append(" AND " + key + " IN (");
				String comma="";
				for(String value : valueList){
					sql.append(comma + "'" + value + "'");
					comma=",";
				}
				sql.append(" )");
			}
			
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, interfaceCode);
			rs = ps.executeQuery();
			
			int index = 0;
			while(rs.next())
			{
				HashMap<String,String> iblHM = new HashMap<>();
				iblHM.put("INTERFACE_LOG_ID", FormatUtil.display(rs.getString("INTERFACE_LOG_ID")));
				iblHM.put("APPLICATION_GROUP_ID", FormatUtil.display(rs.getString("APPLICATION_GROUP_ID")));
				iblHM.put("APPLICATION_RECORD_ID", FormatUtil.display(rs.getString("APPLICATION_RECORD_ID")));
				iblHM.put("CREATE_DATE", FormatUtil.display(rs.getString("CREATE_DATE")));
				iblHM.put("INTERFACE_CODE", FormatUtil.display(rs.getString("INTERFACE_CODE")));
				iblHM.put("INTERFACE_STATUS", FormatUtil.display(rs.getString("INTERFACE_STATUS")));
				iblHM.put("REF_ID", FormatUtil.display(rs.getString("REF_ID")));
				iblHM.put("SYSTEM01", FormatUtil.display(rs.getString("SYSTEM01")));
				iblHM.put("SYSTEM02", FormatUtil.display(rs.getString("SYSTEM02")));
				iblHM.put("SYSTEM03", FormatUtil.display(rs.getString("SYSTEM03")));
				iblHM.put("SYSTEM04", FormatUtil.display(rs.getString("SYSTEM04")));
				iblHM.put("SYSTEM05", FormatUtil.display(rs.getString("SYSTEM05")));
				iblHM.put("SYSTEM06", FormatUtil.display(rs.getString("SYSTEM06")));
				iblHM.put("SYSTEM07", FormatUtil.display(rs.getString("SYSTEM07")));
				iblHM.put("SYSTEM08", FormatUtil.display(rs.getString("SYSTEM08")));
				iblHM.put("SYSTEM09", FormatUtil.display(rs.getString("SYSTEM09")));
				iblHM.put("SYSTEM10", FormatUtil.display(rs.getString("SYSTEM10")));
				iblHM.put("LOG_MESSAGE", FormatUtil.display(rs.getString("LOG_MESSAGE")));
				iblHMS.put(index, iblHM);
				index++;
			}
		}
		catch(Exception e)
		{
			logger.fatal("ERROR ",e);
		}
		
		return iblHMS;
	}
	
	public static String buildInfBatchLogTable(String tableName, String key, ArrayList<String> valueList, String interfaceCode)
	{
		StringBuilder tableBuilder = new StringBuilder();
		if(valueList.size() > 0 )
		{
			HashMap<Integer,HashMap<String,String>> iblHMS = getInfBatchLogData(key, valueList, interfaceCode);
			tableBuilder.append("</br><div><strong>" + tableName + "</strong></div></br>");
	    	if(iblHMS.size() > 0)
	    	{
	    		tableBuilder.append("<table class='table table-bordered'><tr><th>INTERFACE_LOG_ID</th><th>APPLICATION_GROUP_ID</th>" +
		    	"<th>APPLICATION_RECORD_ID</th><th>INTERFACE_CODE</th><th>CREATE_DATE</th><th>INTERFACE_STATUS</th><th>REF_ID</th>" +
		    	"<th>SYSTEM_01</th><th>SYSTEM_02</th><th>SYSTEM_03</th><th>SYSTEM_04</th><th>SYSTEM_05</th><th>SYSTEM_06</th>" +
		    	"<th>SYSTEM_07</th><th>SYSTEM_08</th><th>SYSTEM_09</th><th>SYSTEM_10</th><th>LOG_MESSAGE</th></tr>");
		    		
		    	for(int i = 0 ; i < iblHMS.size() ; i++)
		    	{
		    		HashMap<String,String> iblHM = iblHMS.get(i);
		    		String infLogId = iblHM.get("INTERFACE_LOG_ID");
		    		tableBuilder.append("<tr>");
			    	tableBuilder.append("<td>" + infLogId + "</td>");
			    	tableBuilder.append("<td>" + iblHM.get("APPLICATION_GROUP_ID") + "</td>");
			    	tableBuilder.append("<td>" + iblHM.get("APPLICATION_RECORD_ID") + "</td>");
			    	tableBuilder.append("<td>" + iblHM.get("INTERFACE_CODE") + "</td>");
			    	tableBuilder.append("<td>" + iblHM.get("CREATE_DATE") + "</td>");
			    	tableBuilder.append("<td>" + iblHM.get("INTERFACE_STATUS") + "</td>");
			    	tableBuilder.append("<td>" + iblHM.get("REF_ID") + "</td>");
			    	tableBuilder.append("<td>" + iblHM.get("SYSTEM01") + "</td>");
			    	tableBuilder.append("<td>" + iblHM.get("SYSTEM02") + "</td>");
			    	tableBuilder.append("<td>" + iblHM.get("SYSTEM03") + "</td>");
			    	tableBuilder.append("<td>" + iblHM.get("SYSTEM04") + "</td>");
			    	tableBuilder.append("<td>" + iblHM.get("SYSTEM05") + "</td>");
			    	tableBuilder.append("<td>" + iblHM.get("SYSTEM06") + "</td>");
			    	tableBuilder.append("<td>" + iblHM.get("SYSTEM07") + "</td>");
			    	tableBuilder.append("<td>" + iblHM.get("SYSTEM08") + "</td>");
			    	tableBuilder.append("<td>" + iblHM.get("SYSTEM09") + "</td>");
			    	tableBuilder.append("<td>" + iblHM.get("SYSTEM10") + "</td>");
			    	tableBuilder.append("<td>" + iblHM.get("LOG_MESSAGE") + "</td>");
			    	tableBuilder.append("</tr>");
		    	}
	    	}
	    	else
	    	{
	    		tableBuilder.append("<table>No results found.");
	    	}
	    	tableBuilder.append("</table>");
		}
		return tableBuilder.toString();
	}
}
