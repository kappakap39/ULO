<%@ page import="com.eaf.orig.shared.utility.OrigXRulesUtil"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="com.eaf.orig.profile.model.UserDetailM" %>
<%@ page import="com.eaf.orig.shared.model.ApplicationDataM" %>
<%@ page import="java.util.Vector" %>
<%@ page import="com.eaf.xrules.shared.model.XRulesDataM" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%
   ApplicationDataM appM=new  ApplicationDataM();
   appM.setBusinessClassId("ALL_ALL_ALL");
   appM.setJobState("ST400"); 
  com.eaf.orig.shared.model.PersonalInfoDataM  person=new com.eaf.orig.shared.model.PersonalInfoDataM();
  person.setIdNo("1111");
  person.setCustomerType("01"); 
  if(appM.getPersonalInfoVect()==null){
  appM.setPersonalInfoVect(new Vector());
  }
  appM.setAppRecordID("1111");
  appM.getPersonalInfoVect().add(person);
   System.out.println("appM.getPersonalInfoVect()="+  appM.getPersonalInfoVect().size());
//  Vector  vRequire=OrigXRulesUtil.getInstance().getRequiredModuleServices(appM,new UserDetailM(),person);  
 // System.out.println("Vrequire CMP indivdual "+vRequire.size());
  // appM.setJobState("ST200");
 //  vRequire=OrigXRulesUtil.getInstance().getRequiredModuleServices(appM,new UserDetailM(),person);  
  //System.out.println("Vrequire DE indivdual "+vRequire.size());
  //  appM.setJobState("ST400");
  //  vRequire=OrigXRulesUtil.getInstance().getRequiredModuleServices(appM,new UserDetailM(),person);  
 //System.out.println("Vrequire UW indivdual "+vRequire.size());
 // System.out.println("NCB");
  UserDetailM  userM=new UserDetailM();
  userM.setUserName("testMatrix");
    appM.setApplicationNo("xxxxx");
   person.setThaiFirstName("xxxxx");
   person.setThaiLastName("xxxxx");
   person.setBirthDate(new java.util.Date());
   XRulesDataM xrulesDataM=OrigXRulesUtil.getInstance().getXRulesDecision(appM,userM,9,person);  

   ///person.getXrulesVerification().getVXRulesNCBDataM();
   //if(person.getXrulesVerification().getVXRulesNCBDataM()!=null){
   // System.out.println("NCB size="+person.getXrulesVerification().getVXRulesNCBDataM().size());
   //}
  //System.out.println("Blacklist");
  // OrigXRulesUtil.getInstance().getXRulesDecision(appM,new UserDetailM(),2);
//  System.out.println("Blacklist Vehicle");
   // OrigXRulesUtil.getInstance().getXRulesDecision(appM,new UserDetailM(),3);
  //System.out.println("Dedup "); 
  // OrigXRulesUtil.getInstance().getXRulesDecision(appM,new UserDetailM(),4);
  //System.out.println("Dedup Vehicle"); 
  // OrigXRulesUtil.getInstance().getXRulesDecision(appM,new UserDetailM(),5);
 //  System.out.println("Existing Customer"); 
//  OrigXRulesUtil.getInstance().getXRulesDecision(appM,new UserDetailM(),6); 
   
 %>
<HTML>
<HEAD>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<META name="GENERATOR" content="IBM Software Development Platform">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK href=../theme/Master.csss" rel="stylesheet" type="text/css">
<TITLE>testMatrix.jsp</TITLE>
</HEAD>
<BODY>
<P>Matrix Verification Test</P><FORM> appRecordID <INPUT
	type="text" name="appRecID" size="20"> <INPUT type="submit"
	name="Load Data" value="Load Data"></FORM><HR>
<FIELDSET><LEGEND>Applicatin Data</LEGEND>Application No<BR>
Application Status</FIELDSET>
<P></P>
<HR>
<FIELDSET><LEGEND>Verification List</LEGEND>
<FORM><INPUT type="button" name="ncb" value="NCB"><INPUT type="text"
	name="ncbResult" size="20"><BR>
	<% %>
<BR>
<BR>
<BR></FORM></FIELDSET>
</BODY>
</HTML>
