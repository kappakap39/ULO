package com.eaf.orig.ulo.pl.app.utility;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.apache.log4j.Logger;

import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.tool.performance.PerformanceLogDataM;
import com.eaf.orig.ulo.pl.app.ejb.ORIGLogManager;
import com.eaf.orig.ulo.pl.config.ORIGConfig;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;

public class Performance {
	
	public static final String START = "S";
	public static final String END	 = "E";
	
	public class Module{
		public static final String SAVE_WEBACTION = "SAVE_WEBACTION";
		public static final String LOAD_APPLICATION = "LOAD_APPLICATION";
		public static final String SAVE_MANAGER_BEAN = "SAVE_MANAGER_BEAN";
		public static final String MANDATORY = "MANDATORY";
		public static final String SEND_KCBS = "SEND_KCBS";
	}
	public class Action{
		public static final String CLAIM = "CLAIM";
		public static final String LOAD_APPLICATION  = "LOAD_APPLICATION";
		public static final String SAVE_WEBACTION = "SAVE_WEBACTION";
		public static final String SAVE_APPLICATION = "SAVE_APPLICATION";
		public static final String SAVE_WORKFLOW = "SAVE_WORKFLOW";
		public static final String MANDATORY = "MANDATORY";
		public static final String SEND_TO_KCBS = "SEND_TO_KCBS";
	}
	
	Logger logger = Logger.getLogger(Performance.class);
	PerformanceLogDataM perfM = null;
	
	public Performance(String className,String moduleName){
		if("Y".equals(ORIGConfig.TRACK_PERFORMANCE)){
			perfM = new PerformanceLogDataM();
			perfM.setClassName(className);
			perfM.setModuleName(moduleName);
			perfM.setPrefixModule(moduleName);
		}
	}
	public void ModuleRole(String role){
		if("Y".equals(ORIGConfig.TRACK_PERFORMANCE)){
			if(null == perfM){
				perfM = new PerformanceLogDataM();
			}
			if(null == perfM.getPrefixModule()){
				perfM.setPrefixModule("");
			}
			String module =  perfM.getPrefixModule();
			if(null != role){
				module = module+"_"+role;
			}
			perfM.setModuleName(module);
		}
	}
	public void init(String method,String transactionID){
		if("Y".equals(ORIGConfig.TRACK_PERFORMANCE)){
			if(null == perfM){
				perfM = new PerformanceLogDataM();
			}
			perfM.setMethodName(method);
			perfM.setTransactionID(transactionID);
		}
	}
	
	public void init(String method,String appRecID,String transactionID,String userName){
		if("Y".equals(ORIGConfig.TRACK_PERFORMANCE)){
			if(null == perfM){
				perfM = new PerformanceLogDataM();
			}
			if(null == transactionID){
				transactionID = GenTrancationID();
			}
			perfM.setMethodName(method);
			perfM.setAppRecID(appRecID);
			perfM.setTransactionID(transactionID);
			perfM.setUserName(userName);
		}
	}
	public void init(String method,String appRecID,String transactionID,UserDetailM userM){
		if("Y".equals(ORIGConfig.TRACK_PERFORMANCE)){
			if(null == perfM){
				perfM = new PerformanceLogDataM();
			}
			if(null == transactionID){
				transactionID = GenTrancationID();
			}
			perfM.setMethodName(method);
			perfM.setAppRecID(appRecID);
			perfM.setTransactionID(transactionID);
			perfM.setUserName(userM.getUserName());
			ModuleRole(userM.getCurrentRole());
		}
	}
	public void track(String action,String message){
		if("Y".equals(ORIGConfig.TRACK_PERFORMANCE)){
			if(null != perfM){
				perfM.setActionName(action);
				perfM.setMessage(message);
				ORIGLogManager bean = PLORIGEJBService.getORIGLogManager();
				bean.SavePerformanceLog(perfM);
			}
		}
	}
	
	public String GenTrancationID(){
		StringBuilder transId = new StringBuilder("");
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		Date currentDate = new Date();
		transId.append(format.format(currentDate));
		Random rand = new Random();
		transId.append(rand.nextInt(9)).append(rand.nextInt(9)).append(rand.nextInt(9));
		transId.append(rand.nextInt(9)).append(rand.nextInt(9)).append(rand.nextInt(9));
		transId.append(rand.nextInt(9)).append(rand.nextInt(9)).append(rand.nextInt(9));
		transId.append(rand.nextInt(9)).append(rand.nextInt(9)).append(rand.nextInt(9));
		return transId.toString();
	}
	
}
