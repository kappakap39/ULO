package com.eaf.orig.common.db.control;

import java.sql.Connection;
import java.sql.Savepoint;

import org.apache.log4j.Logger;

import com.eaf.service.common.master.ObjectDAO;


public class DatabaseController extends ObjectDAO{
	private static transient Logger logger = Logger.getLogger(DatabaseController.class);
	public DatabaseController(){
		super();
	}
	public void processControl(Object objectModel,TransactionInf transactionManagement) throws Exception{
		Connection conn = null;
	    Savepoint savepoint = null;
	    try{
	    	conn = getConnection();
	        conn.setAutoCommit(false);
	        savepoint = conn.setSavepoint("PROCESSCONTROL_SAVEPOINT");
	        transactionManagement.preTransaction(objectModel, conn);
	        transactionManagement.processTransaction(objectModel, conn);
	        transactionManagement.postTransaction(objectModel, conn);
	    	conn.commit();
	    }catch(Exception e){
	    	logger.fatal("ERROR",e);
	    	conn.rollback(savepoint);
	    	throw new Exception(e.getLocalizedMessage());
	    }finally{
	    	try{
	    		if(null != conn){
	    			conn.close();
	    		}
	        }catch(Exception e){
	        	logger.fatal("ERROR",e);
	        	throw new Exception(e.getLocalizedMessage());
	        }
	        conn = null;
	    }
	}
}
