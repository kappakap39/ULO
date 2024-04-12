package flp.iib;

import flp.controller.ResponseStatController;
import flp.db.DBConnector;
import flp.model.log.Data;
import flp.model.log.DataSegment;
import flp.model.log.TransactionLogInput;
import flp.model.report.ReportInput;
import flp.model.report.StatType;
import flp.model.report.TimeType;
import flp.utils.DataUtil;
import flp.utils.QueryStringBuilder;
import flp.utils.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.eaf.orig.shared.service.OrigServiceLocator;

public class IIBResponseTime {

	private static transient Logger logger = Logger.getLogger(IIBResponseTime.class);
	
    public String ADDITIONAL_NAME = "";
    public static final double WARNING_THRESHOLD = 3; // Second

    public ResponseStatController getProcessTimeResponse(ReportInput condition) {
        
        ResponseStatController statController = new ResponseStatController();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {

        	QueryStringBuilder queryStr = new QueryStringBuilder();
        	
        	queryStr = queryStr.select("MIN(ACTIVITY_TYPE) AS ACTIVITY_TYPE")
                    .select("MIN(TRANSACTION_ID) AS TRANSACTION_ID")
                    .select("MIN(CREATE_DATE) AS REQUEST_TIME")
                    .select("MAX(CREATE_DATE) AS FINISH_TIME")
                    .select("MAX(CASE TERMINAL_TYPE " +
                                    "WHEN 'IN' THEN 1 WHEN 'OUT' THEN 2 WHEN 'ERROR' THEN 3 " +
                                "END) AS TERMINAL_TYPE")
                    .select("MIN(PARAM1) AS CALL_ACTION")
                    .select("MAX(PARAM3) AS MSG_ID")
                    .select("MIN(PARAM4) AS SERVICE_GROUP_ID")
                    .from("OL_TRANSACTION_LOG")
                    .where("PARAM1 IS NOT NULL")
                    .andWhere("PARAM4 IS NOT NULL")
                    .andWhere("TRANSACTION_ID <> 'string'")
                    .andWhere("REF_CODE <> 'string'")
                    .andWhere("PARAM1 <> 'string'")
                    .andWhere("ACTIVITY_TYPE <> 'CALL_HTTP_REQUEST'")
                    .andWhere("TERMINAL_ID IS NOT NULL")
                    .andWhere("LENGTH(TERMINAL_TYPE) < 6");
        	if ( null != condition.getRefCode() ) {
        		queryStr = queryStr.andWhere(" REF_CODE = '" + condition.getRefCode() + "' ");
            }
        	
        	if ( null != condition.getTransactionId() ) {
        		queryStr = queryStr.andWhere(" TRANSACTION_ID = '" + condition.getTransactionId() + "' ");
            }
            
    		if ( null != condition.getStartDate() && null != condition.getEndDate() ) {
    			queryStr = queryStr.andWhere("CREATE_DATE BETWEEN to_timestamp('" + condition.getStartDate() + "', 'dd-mm-yyyy hh24:mi:ss') "
    									+ "AND to_timestamp('" + condition.getEndDate() + "', 'dd-mm-yyyy hh24:mi:ss')");
            } else {
            	queryStr = queryStr.andWhere("CREATE_DATE > to_timestamp('" + condition.getStartDate() + "', 'dd-mm-yyyy hh24:mi:ss') ");
            }
           
    		queryStr = queryStr.groupBy("TERMINAL_ID")
				               .orderBy("SERVICE_GROUP_ID")
				               .orderBy("ACTIVITY_TYPE");
    		
    		String queryString = queryStr.build();
    		
    		System.out.println( "queryString : " + queryString );
    		
//    		DBConnector connector = new DBConnector();
//    		
//    		conn = connector.getConnection( DBConnector.OLDB_DATA );
    		
    		OrigServiceLocator origService = OrigServiceLocator.getInstance();
        	conn = origService.getConnection(OrigServiceLocator.OL_DB);
    		
            ps = conn.prepareStatement( queryString );
            rs = ps.executeQuery();
            String currentServiceGroupId = null;
            Double callDuration = null;
            
            while (rs.next()) {
                String activityType = rs.getString("ACTIVITY_TYPE");
                String transactionId = rs.getString("TRANSACTION_ID");
                Timestamp requestTime = rs.getTimestamp("REQUEST_TIME");
                Timestamp finishTime = rs.getTimestamp("FINISH_TIME");
                String serviceGroupId = rs.getString("SERVICE_GROUP_ID");
                String callAction = rs.getString("CALL_ACTION");
                String msgId = rs.getString("MSG_ID");
                String terminalType = rs.getString("TERMINAL_TYPE");
                boolean isPass;
                switch (terminalType) {
                    case "1":
                    case "3":
                    default:
                        isPass = false;
                        break;
                    case "2":
                        isPass = true;
                        break;
                }
                double duration = (finishTime.getTime() - requestTime.getTime()) / 1000d;

                if (duration >= WARNING_THRESHOLD) {
                    Map<String, List<String>> warningTransactionIdMap = statController.getWarningTransactionIdMap();
                    if (!warningTransactionIdMap.containsKey(transactionId)) {
                        warningTransactionIdMap.put(transactionId, new ArrayList<String>());
                    }
                    List<String> warningActivityTypeList = warningTransactionIdMap.get(transactionId);
                    if (!warningActivityTypeList.contains(activityType)) {
                        warningActivityTypeList.add(activityType);
                    }
                }

                // Add Async -> Queue total duration to the result list
                if (msgId != null && (activityType.startsWith("REST_ASYNC_") || activityType.startsWith("QUEUE_"))) {
                    if (activityType.startsWith("REST_ASYNC_")) {
                        statController.checkMsgId(msgId, activityType, TimeType.REQUEST_TIME, requestTime);
                    } else {
                        statController.checkMsgId(msgId, activityType, TimeType.FINISH_TIME, finishTime);
                    }
                    if (statController.isFoundMsgIdPair(msgId)) {
                        String asyncActivityType;
                        if (statController.getAsyncActivityType(msgId).contains("APPLICANT")) {
                            asyncActivityType = "ASYNC+QUEUE_APPLICANT";
                        } else {
                            asyncActivityType = "ASYNC+QUEUE_DECISION";
                        }
                        double asyncDuration = statController.getAsyncDuration(msgId);
                        statController.setValue(callAction, StatType.REST, asyncActivityType, asyncDuration, isPass);
                        statController.setValue(callAction, StatType.RESTOnly , asyncActivityType, asyncDuration, isPass);
                    }
                }

                if (activityType.startsWith("CALL_")) {
                    if (currentServiceGroupId == null || !currentServiceGroupId.equals(serviceGroupId)) {
                        currentServiceGroupId = serviceGroupId;
                        callDuration = duration;
                    } else {
                        if (callDuration == null) {
                            callDuration = 0d;
                        }
                        callDuration += duration;
                    }
                    statController.setValue(callAction, StatType.Legacy, activityType, duration, isPass);
                } else {
                    statController.setValue(callAction, StatType.REST, activityType, duration, isPass);

                    if (callDuration != null && currentServiceGroupId != null &&
                            currentServiceGroupId.equals(serviceGroupId)) {
                        double RESTOnlyDuration = duration - callDuration;
                        statController.setValue(callAction, StatType.RESTOnly, activityType, RESTOnlyDuration, isPass);
                    } else {
                        statController.setValue(callAction, StatType.RESTOnly, activityType, duration, isPass);
                    }
                    callDuration = null;
                    currentServiceGroupId = null;
                }
            }
            
            statController.populate();

        } catch (Exception e) {
        	logger.fatal("ERROR getProcessTimeResponse() : ",e);
        } finally{
			try{
				if(null != rs) rs.close();
				if(null != ps) ps.close();
				if(null != conn) conn.close();
			}catch(Exception e){
				logger.fatal(" close connection exception : ",e);
			}
		}
        
        return statController;
        
    }
    
    public DataSegment getTransactionResponse(TransactionLogInput condition){
    	//Prepare Model
    	DataSegment datasegment = new DataSegment();
    	datasegment.setDatas(new ArrayList<Data>());
    	//Prepare connection
    	Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
        	
        	StringBuilder query = new StringBuilder("SELECT * FROM OL_TRANSACTION_LOG WHERE 1 = 1 AND ROWNUM < 1000 ");
        	if( null != condition.getRefCode()){
        		query.append(" AND REF_CODE = ? ");
        	}
        	if( null != condition.getTransactionId()){
        		query.append(" AND TRANSACTION_ID = ? ");
        	}
        	if( null != condition.getIdNo()){
        		query.append(" AND PARAM2 = ? ");
        	}
        	if( null != condition.getDecisionService()){
        		query.append(" AND PARAM1 = ? ");
        	}
        	if( null != condition.getServiceId()){
        		query.append(" AND SERVICE_ID = ? ");
        	}
        	
        	query.append(" ORDER BY CREATE_DATE DESC, TRANSACTION_ID DESC, TERMINAL_TYPE ASC");
        	System.out.println( "queryString : " + query.toString() );
//        	DBConnector connector = new DBConnector();
//    		conn = connector.getConnection( DBConnector.OLDB_DATA );
        	
        	OrigServiceLocator origService = OrigServiceLocator.getInstance();
        	conn = origService.getConnection(OrigServiceLocator.OL_DB);
        	
            ps = conn.prepareStatement( query.toString() );
           
            int indexQuery = 1;
        	if( null != condition.getRefCode()){
        		ps.setString(indexQuery++, condition.getRefCode());
        	}
        	if( null != condition.getTransactionId()){
        		ps.setString(indexQuery++, condition.getTransactionId());
        	}
        	if( null != condition.getIdNo()){
        		ps.setString(indexQuery++, condition.getIdNo());
        	}
        	if( null != condition.getDecisionService()){
        		ps.setString(indexQuery++, condition.getDecisionService());
        	}
        	if( null != condition.getServiceId()){
        		ps.setString(indexQuery++, condition.getServiceId());
        	}
        	
        	rs = ps.executeQuery();
           
        	while(rs.next()){
        		String transactionLogId = rs.getString("TRANSACTION_LOG_ID");
        		String transactionId = rs.getString("TRANSACTION_ID");
        		String terminalType = rs.getString("TERMINAL_TYPE");
        		String terminalId = rs.getString("TERMINAL_ID");
        		String serviceId = rs.getString("SERVICE_ID");
        		String refCode = rs.getString("REF_CODE");
        		String param4 = rs.getString("PARAM4");
        		String param3 = rs.getString("PARAM3");
        		String param2 = rs.getString("PARAM2");
        		String param1 = rs.getString("PARAM1");
        		Timestamp createDate = rs.getTimestamp("CREATE_DATE");
                String activityType = rs.getString("ACTIVITY_TYPE");
                
                
                Data cur_record = new Data();
                cur_record.setTransactionLogId(transactionLogId);
                cur_record.setTransactionId(transactionId);
                cur_record.setTerminalType(terminalType);
                cur_record.setTerminalId(terminalId);
                cur_record.setServiceId(serviceId);
                cur_record.setRefCode(refCode);
                cur_record.setParam4(param4);
                cur_record.setParam3(param3);
                cur_record.setParam2(param2);
                cur_record.setParam1(param1);
                cur_record.setCreateDate(createDate);
                cur_record.setActivityType(activityType);
                
                datasegment.getDatas().add(cur_record);
        	}
		} catch (Exception e) {
			logger.fatal("ERROR getProcessTimeResponse() : ",e);
		}finally{
			try{
				if(null != rs) rs.close();
				if(null != ps) ps.close();
				if(null != conn) conn.close();
				rs = null;
				ps = null;
				conn = null;
			}catch(Exception e){
				logger.fatal(" close connection exception : ",e);
			}
		}
        
    	return datasegment;
    }
    public DataSegment getBlobData(TransactionLogInput condition){
    	//Prepare Model
    	DataSegment datasegment = new DataSegment();
    	datasegment.setDatas(new ArrayList<Data>());
    	//Prepare connection
    	Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
        	
        	StringBuilder query = new StringBuilder("SELECT TRANSACTION_LOG_ID, BINARY_DATA FROM OL_TRANSACTION_LOG WHERE 1 = 1 AND ROWNUM < 10 ");
        	
        	if(null!= condition.getTransactionLogId()){
        		query.append(" AND TRANSACTION_LOG_ID = '"+condition.getTransactionLogId()+"' ");
        	}
        	
        	System.out.println( "queryBinary : " + query.toString() );
//        	DBConnector connector = new DBConnector();
//    		conn = connector.getConnection( DBConnector.OLDB_DATA );
        	
        	OrigServiceLocator origService = OrigServiceLocator.getInstance();
        	conn = origService.getConnection(OrigServiceLocator.OL_DB);
        	
            ps = conn.prepareStatement( query.toString());
            rs = ps.executeQuery();
           
        	while(rs.next()){
        		String transactionLogId = rs.getString("TRANSACTION_LOG_ID");
                Clob binaryData = rs.getClob("BINARY_DATA");
                
                Data cur_record = new Data();
                cur_record.setTransactionLogId(transactionLogId);
                cur_record.setBinaryData(binaryData.getSubString(1,(int) binaryData.length()));
                
                datasegment.getDatas().add(cur_record);
        	}
		} catch (Exception e) {
			logger.fatal("ERROR getProcessTimeResponse() : ",e);
		}finally{
			try{
				if(null != rs) rs.close();
				if(null != ps) ps.close();
				if(null != conn) conn.close();
			}catch(Exception e){
				logger.fatal(" close connection exception : ",e);
				rs = null;
				ps = null;
				conn = null;
			}
		}
        
        return datasegment;
    }
}
