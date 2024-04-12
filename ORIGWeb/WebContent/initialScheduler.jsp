<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@page import="com.eaf.orig.ulo.pl.service.PLORIGEJBService"%>
<HTML>
<HEAD>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM Software Development Platform">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK href="theme/Master.css" rel="stylesheet"
	type="text/css">
<TITLE>Initial Scheduler</TITLE>
</HEAD>
<%@ page import="com.eaf.orig.scheduler.task.*" %>
<%-- <%@ page import="com.eaf.orig.shared.dao.ORIGDAOFactory" %> --%>
<%-- <%@ page import="com.eaf.orig.scheduler.dao.Scheduler.*" %> --%>
<%@ page import="com.eaf.orig.shared.ejb.ORIGApplicationManager" %>
<%@ page import="com.eaf.orig.shared.service.ORIGEJBService" %>
<%@ page import="javax.naming.*"%>
<BODY>
<%
	//JNDI lookup 
	Context initialContext = new InitialContext();
	Object result = initialContext.lookup("java:comp/env/ejb/orig/scheduler/SchedulerTaskHome");
	
	//Get the home interface
	SchedulerTaskHome schedulerHome =
        (SchedulerTaskHome) javax.rmi.PortableRemoteObject.narrow(result, SchedulerTaskHome.class);
	
	//Create the EJB
	SchedulerTask schedulerTask = schedulerHome.create();
	
// 	SchedulerDAO schedulerDAO = ORIGDAOFactory.getSchedulerDAO();
	ORIGApplicationManager schedManager = ORIGEJBService.getApplicationManager();
	String startTime = "0000";
	try{
		schedManager.deleteOldSchedulerTask("AutoCancel");
// 		startTime = schedulerDAO.loadWorkingTime("AUTO_CANCEL");
		startTime = PLORIGEJBService.getORIGDAOUtilLocal().loadWorkingTime("AUTO_CANCEL");
	}catch(Exception e){
		e.printStackTrace();
	}
	System.out.println(">>> startTime : "+startTime);
	schedulerTask.scheduleTask(startTime,-1,1,"AutoCancel");
	
 %>
</BODY>
</HTML>
