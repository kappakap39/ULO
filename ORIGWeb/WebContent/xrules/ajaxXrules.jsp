<%@page import="com.eaf.xrules.moduleservice.model.ModuleServiceOutputDataM"%>
<%                   
response.setHeader("Pragma", "No-cache");
response.setHeader("Cache-Control", "no-cache,no-store");
response.setDateHeader("Expires", 0);
response.setCharacterEncoding("UTF-8");

%>
<%@ page contentType="text/xml;charset=UTF-8"%>
<%@ page import="com.eaf.orig.shared.utility.OrigXRulesUtil"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="com.eaf.orig.profile.model.UserDetailM" %>
<%@ page import="com.eaf.orig.shared.model.ApplicationDataM" %>
<%@ page import="com.eaf.orig.shared.model.PersonalInfoDataM" %>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>
<%@ page import="com.eaf.xrules.shared.model.XRulesDataM" %>
<%@ page import="java.util.Vector" %>
<%@ page import="com.eaf.orig.shared.model.VehicleDataM" %>
<%@ page import="java.net.URLDecoder"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.formcontrol.view.form.ORIGFormHandler"/><% System.out.println("Ajax Call"); %>
<% //get Parameter  
     String idNo=request.getParameter("identification_no");
     String engineNo=request.getParameter("car_engine_no"); 
     String registrationNo=request.getParameter("car_register_no");
     String chassicNo=request.getParameter("car_classis_no");
     String  thaiFirstName=request.getParameter("name_thai");
     String  thaiSurName=request.getParameter("surname_thai");
     System.out.println("idNo="+idNo);
     System.out.println("engineNo="+engineNo);
     System.out.println("registrationNo="+registrationNo);
     System.out.println("chassicNo="+chassicNo);
     System.out.println("thaiName="+ thaiFirstName);
     System.out.println("thaiName="+ thaiFirstName.getBytes("UTF-8"));
     System.out.println("thaiName="+ new String(thaiFirstName.getBytes("UTF-8"),"UTF-8"));
     System.out.println("thaiSurname="+thaiSurName);
     System.out.println("ภาษาไทย");
     System.out.println(thaiFirstName.replace('%','\\'));
     //java.nio.charset.Charset cs=new java.nio.charset.Charset.forName("UTF-8");
    
     //System.out.println( cs.encode(  thaiFirstName.replace('%','\\')));

     //thaiFirstName=thaiFirstName.replace('%','\\');
     try  {  
     System.out.println("thaiName Decode="+ URLDecoder.decode(thaiFirstName,"UTF-8"));
     System.out.println("thaiSurname Decode="+URLDecoder.decode(thaiSurName,"UTF-8"));
     } catch  (  Exception e )   
	 {  
	System.out.println ( e ) ;         
	 } 
%><%	

   ApplicationDataM applicationDataM = ORIGForm.getAppForm();
	if(applicationDataM == null){
		applicationDataM = new ApplicationDataM();
	}
	ORIGUtility utility = new ORIGUtility();
	String personalType = (String) request.getSession().getAttribute("PersonalType");
	PersonalInfoDataM personalInfoDataM;
	if(OrigConstant.PERSONAL_TYPE_GUARANTOR.equals(personalType)){
		personalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("MAIN_POPUP_DATA");
	}else{
		personalInfoDataM = utility.getPersonalInfoByType(ORIGForm.getAppForm(),OrigConstant.PERSONAL_TYPE_APPLICANT);
	}
	if(personalInfoDataM == null){
		personalInfoDataM = new PersonalInfoDataM();
		personalInfoDataM.setPersonalType(personalType);
		applicationDataM.getPersonalInfoVect().add(personalInfoDataM);
	}
	VehicleDataM vehicleDataM = applicationDataM.getVehicleDataM();
    if(vehicleDataM==null){
    vehicleDataM=new VehicleDataM();
    applicationDataM.setVehicleDataM(vehicleDataM);
    }
	//set value data
	personalInfoDataM.setIdNo(idNo);
	//personalInfoDataM.setThaiFirstName(new String(thaiFirstName.getBytes("UTF-8")));
	//personalInfoDataM.setThaiLastName(new String(thaiSurName.getBytes("UTF-8")));
	personalInfoDataM.setThaiFirstName( thaiFirstName);
	personalInfoDataM.setThaiLastName(thaiSurName);
	vehicleDataM.setChassisNo(chassicNo);
    vehicleDataM.setRegisterNo(registrationNo);
	vehicleDataM.setEngineNo(engineNo);	
	if(applicationDataM.getJobState()==null){
 	   applicationDataM.setJobState("ST200");
	}    
	String strXrulesService=request.getParameter("xrulseExecuteService");
	int serviceID=0;
	try{
	serviceID=Integer.parseInt(strXrulesService.trim());
	}catch (Exception ex){
	 ex.printStackTrace();
	}	
	 System.out.println("Call Service "+serviceID);
	OrigXRulesUtil xrulesUtil=OrigXRulesUtil.getInstance();
	XRulesDataM xruleDataM=null;
	if(serviceID!=0){
	  xruleDataM=xrulesUtil.getXRulesDecision(applicationDataM,ORIGUser,serviceID,personalInfoDataM);
	}else{
	  System.out.println("Invalid Service Id");
	}
	ModuleServiceOutputDataM moduleServiceOutput=null;
	String xrulesExecuteResult="";
	if(xruleDataM!=null){
//  	   moduleServiceOutput=xrulesUtil.getMouduleServiceOutput(xruleDataM,serviceID); 	   	  
	}
	if(moduleServiceOutput!=null){
// 	 	xrulesExecuteResult=moduleServiceOutput.getExecutionResultString();
	}else{
//  	 System.out.println("moduleServiceOutput==null");
	}
   //result=< %--=xrulesExecuteResult% >
   out.clear();
   out.print("<XRULES_RESULT>"+xrulesExecuteResult+"</XRULES_RESULT>");
%>