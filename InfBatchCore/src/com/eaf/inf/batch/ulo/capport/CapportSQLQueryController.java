package com.eaf.inf.batch.ulo.capport;


import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.eaf.ulo.cache.dao.CacheObjectDAO;

public class CapportSQLQueryController extends CacheObjectDAO {
	private static transient Logger logger = Logger.getLogger(CapportSQLQueryController.class);

	public ArrayList<String> getSumCapport_ByCapNameAndGrantType( String capportName) throws Exception {
		  Connection conn = null;
		    ArrayList rsArrayList = new ArrayList();
		    ResultSet rs = null;
		    PreparedStatement ps = null;
		    try {
		    	conn = getConnection(1); // 1 = jdbc/orig, 12 = jdbc/ol
		    	StringBuilder SQL = new StringBuilder();
		    	SQL.append("SELECT"										
							+"    CAP_PORT_NAME,"
							+"    GRANT_TYPE,"
							+"    ("
							+"        CASE"
							+"            WHEN GRANT_TYPE = 'AMT'"
							+"            THEN SUM(APPROVE_AMOUNT)"
							+"            ELSE 0"
							+"        END ) AS GRANTED_AMOUNT ,"
							+"    ("
							+"        CASE"
							+"            WHEN GRANT_TYPE = 'NUM'"
							+"            THEN SUM(APPROVE_NUMBER_OF_APP)"
							+"            ELSE 0"
							+"        END ) AS GRANTED_APPLICATION"
							+" FROM"
							+"    ULO_CAP_PORT_TRANSACTION"
							+" WHERE ADJUST_FLAG = 'N'"
							+" GROUP BY"
							+"    CAP_PORT_NAME,"
							+"    GRANT_TYPE"
							+" Having CAP_PORT_NAME =  ?"
							+" ORDER BY"
							+"    CAP_PORT_NAME,"
							+"    GRANT_TYPE");
		      
		    	logger.debug("SQL >> " + SQL);
		    	ps = conn.prepareStatement(SQL.toString());
		    	ps.setString(1, capportName);
		    	rs = ps.executeQuery();

		    	while (rs.next()){
		    	  HashMap<String, Object> listHashMap = new HashMap<String, Object>();
				    
//			      listHashMap.put("CAP_PORT_ID",  rs.getString("CAP_PORT_ID"));
			      listHashMap.put("CAP_PORT_NAME",  rs.getString("CAP_PORT_NAME"));
//			      listHashMap.put("APPLICATION_GROUP_ID",  rs.getString("APPLICATION_GROUP_ID"));
//			      listHashMap.put("APPLICATION_RECORD_ID",  rs.getString("APPLICATION_RECORD_ID"));
			      listHashMap.put("GRANT_TYPE",  rs.getString("GRANT_TYPE"));
			      listHashMap.put("GRANTED_AMOUNT",  rs.getBigDecimal("GRANTED_AMOUNT"));
			      listHashMap.put("GRANTED_APPLICATION",  rs.getBigDecimal("GRANTED_APPLICATION"));
			      
//			      listHashMap.put("APPROVE_AMOUNT",  rs.getBigDecimal("APPROVE_AMOUNT"));
//			      listHashMap.put("APPROVE_NUMBER_OF_APP",  rs.getInt("APPROVE_NUMBER_OF_APP"));
//			      listHashMap.put("ADJUST_FLAG",  rs.getString("ADJUST_FLAG"));
//			      listHashMap.put("UPDATE_DATE",  rs.getDate("UPDATE_DATE"));
//			      listHashMap.put("UPDATE_BY",  rs.getString("UPDATE_BY"));
//			      listHashMap.put("CREATE_DATE",  rs.getDate("CREATE_DATE"));
//			      listHashMap.put("CREATE_BY",  rs.getString("CREATE_BY"));	
			      
			      logger.debug("listHashMap >> " + listHashMap);
			      rsArrayList.add(listHashMap);		
			   
			    }
		    }
		    catch (Exception e) {
		      logger.fatal("ERROR ", e);
		      throw new Exception(e.getLocalizedMessage());
		    } finally {
		      try {
		        closeConnection(ps, rs);
		      } catch (Exception e) {
		        logger.fatal("ERROR ", e);
		        throw new Exception(e.getMessage());
		      }
		    }
		    logger.debug("rsArrayList >> " + rsArrayList);
		    return rsArrayList;
		  }	 
	public ArrayList<String> getCapportManualFile( ) throws Exception {
		  Connection conn = null;
		    ArrayList rsArrayList = new ArrayList();
		    ResultSet rs = null;
		    PreparedStatement ps = null;
		    try {
		    	conn = getConnection(12); // 1 = jdbc/orig, 12 = jdbc/ol
		    	StringBuilder SQL = new StringBuilder();
		    	SQL.append("SELECT CAP_PORT_NAME,CAP_AMOUNT,GRANTED_AMOUNT,CAP_APPLICATION,GRANTED_APPLICATION,WARNING_POINT,DECLINE_POINT,RECIPIENT_EMAILS ");
		    	SQL.append(" FROM MF_CAP_PORT " );
		    	SQL.append(" WHERE EXISTS (SELECT IMPORT_ID FROM OL_IMPORT WHERE IMPORT_ID = MF_CAP_PORT.IMPORT_ID AND STATUS = 'A') ");
		      
		    	logger.debug("SQL >> " + SQL);
		    	ps = conn.prepareStatement(SQL.toString());
		    	rs = ps.executeQuery();

		    	while (rs.next()){
		    	  HashMap<String, Object> listHashMap = new HashMap<String, Object>();
				    
//			      listHashMap.put("CAP_PORT_ID",  rs.getInt("CAP_PORT_ID"));
//				  listHashMap.put("IMPORT_ID",  rs.getInt("IMPORT_ID"));
				  listHashMap.put("CAP_PORT_NAME",  rs.getString("CAP_PORT_NAME"));
				  listHashMap.put("CAP_AMOUNT",  rs.getBigDecimal("CAP_AMOUNT"));
				  listHashMap.put("GRANTED_AMOUNT",  rs.getBigDecimal("GRANTED_AMOUNT"));
				  listHashMap.put("CAP_APPLICATION",  rs.getBigDecimal("CAP_APPLICATION"));
				  listHashMap.put("GRANTED_APPLICATION",  rs.getBigDecimal("GRANTED_APPLICATION"));
				  listHashMap.put("WARNING_POINT",  rs.getBigDecimal("WARNING_POINT"));
				  listHashMap.put("DECLINE_POINT",  rs.getBigDecimal("DECLINE_POINT"));
				  listHashMap.put("RECIPIENT_EMAILS",  rs.getString("RECIPIENT_EMAILS"));
//				  listHashMap.put("DESCRIPTION",  rs.getString("DESCRIPTION"));
			      
			      logger.debug("listHashMap >> " + listHashMap);
			      rsArrayList.add(listHashMap);		
			   
			    }
		    }
		    catch (Exception e) {
		      logger.fatal("ERROR ", e);
		      throw new Exception(e.getLocalizedMessage());
		    } finally {
		      try {
		        closeConnection(ps, rs);
		      } catch (Exception e) {
		        logger.fatal("ERROR ", e);
		        throw new Exception(e.getMessage());
		      }
		    }
		    logger.debug("rsArrayList >> " + rsArrayList);
		    return rsArrayList;
		  }	 
	public int adjustCapport(String capportName, BigDecimal grantedAmount, BigDecimal grantedApplication) throws Exception {
		  Connection connOLDb = null;
		  Connection connULODb = null;
		    int rsOLdb = 0;
		    int rsULOdb = 0;
		    PreparedStatement psOLDb = null;
		    PreparedStatement psULODb = null;
		    try {
//				[...............Begin Transaction.................]
//				[OLDB] UPDATE GRANTED AMT, APP TO MANUAL TABLE WHERE THIS CAPPORT
		    	connOLDb = getConnection(12); // 1 = jdbc/orig, 12 = jdbc/ol
		    	connOLDb.setAutoCommit(false);
		    	StringBuilder SQL = new StringBuilder();
		    	SQL.append("UPDATE MF_CAP_PORT SET GRANTED_AMOUNT = ( GRANTED_AMOUNT + ? ), GRANTED_APPLICATION = ( GRANTED_APPLICATION + ? )");
		    	SQL.append(" WHERE CAP_PORT_NAME = ? ");
		    	SQL.append(" AND EXISTS (SELECT IMPORT_ID FROM OL_IMPORT WHERE IMPORT_ID = MF_CAP_PORT.IMPORT_ID AND STATUS = 'A') ");
		      
		    	logger.debug("SQL >> " + SQL);
		    	psOLDb = connOLDb.prepareStatement(SQL.toString());
		    	psOLDb.setDouble(1, (grantedAmount == null) ? 0 : grantedAmount.doubleValue());
		    	psOLDb.setDouble(2, (grantedApplication == null) ? 0 : grantedApplication.doubleValue());
		    	psOLDb.setString(3, capportName);
		    	
		    	rsOLdb = psOLDb.executeUpdate();
		    	logger.debug("rsOLdb >> " + rsOLdb);
		    	
		    	if(rsOLdb>0){
		    		try{
			    		connULODb = getConnection(1); // 1 = jdbc/orig, 12 = jdbc/ol
			    		connULODb.setAutoCommit(false);
	//					[ULODB] IF OLDB SUCCESSS, UPDATE GRATED_SUM_FLAG = Y to Cap Port Transaction WHERE THIS CAPPORT
	//					IF ULODB FAILED, THEN ROOLBACK OLDB
			    		StringBuilder SQLUlo = new StringBuilder();
			    		SQLUlo.append("UPDATE ULO_CAP_PORT_TRANSACTION SET ADJUST_FLAG = 'Y' WHERE CAP_PORT_NAME = ?");
		 
				      
				    	logger.debug("SQLUlo >> " + SQLUlo);
				    	psULODb = connULODb.prepareStatement(SQLUlo.toString());
				    	psULODb.setString(1, capportName);
				    	
				    	rsULOdb = psULODb.executeUpdate();
				    	logger.debug("rsULOdb >> " + rsULOdb);
				    	if(rsULOdb>0){
				    		connOLDb.commit();
				    		logger.debug("connOLDb.commit()");
				    		connULODb.commit();
				    		logger.debug("connULODb.commit()");
				    	}else{
				    		connOLDb.rollback();
				    		logger.debug("connOLDb.rollback()");
				    	}
		
		    		} catch (Exception e) {
		    			logger.fatal("ERROR ", e);
		    			if(connULODb!=null){
		    				connOLDb.rollback();
		    				logger.debug("connOLDb.rollback()");
		    			}
		    			if(connULODb!=null){
		    				connULODb.rollback();
		    				logger.debug("connULODb.rollback()");
		    			}
		    			
		    			throw new Exception(e.getLocalizedMessage());
		    		}
			
//			[...............End Transaction.................]	
		    	}//end if if(rsOLdb>0)
				
		    	
		    }
		    catch (Exception e) {
		      logger.fatal("ERROR ", e);
		      throw new Exception(e.getLocalizedMessage());
		    } finally {
		      try {
		        closeConnection(connOLDb);
		        closeConnection(connULODb);
		      } catch (Exception e) {
		        logger.fatal("ERROR ", e);
		        throw new Exception(e.getMessage());
		      }
		    }
		   
		    return rsULOdb;
		  }	 	
}
