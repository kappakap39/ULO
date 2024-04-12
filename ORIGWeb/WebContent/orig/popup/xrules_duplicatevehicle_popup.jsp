<%@ page import="java.util.Vector" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="com.eaf.orig.shared.model.ApplicationDataM" %>
<%@ page import="com.eaf.orig.shared.model.PersonalInfoDataM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>
<%@ page import="com.eaf.xrules.shared.model.XRulesVerificationResultDataM" %>
<%@ page import="com.eaf.xrules.shared.model.XRulesDedupVehicleDataM " %>
<%@ page import="com.eaf.orig.shared.utility.OrigXRulesUtil" %>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.formcontrol.view.form.ORIGFormHandler"/>

<% 
	//MessageResourceUtil msgUtil = MessageResourceUtil.getInstance();
		ORIGUtility utility = new ORIGUtility();
	  OrigXRulesUtil origXrulesUtil=OrigXRulesUtil.getInstance();
%>

<%
ApplicationDataM  appForm = ORIGForm.getAppForm();
//get Personal
	PersonalInfoDataM personalInfoDataM;
		String personalType = (String) request.getSession().getAttribute("PersonalType");
	if(OrigConstant.PERSONAL_TYPE_GUARANTOR.equals(personalType)){
		personalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("MAIN_POPUP_DATA");
	}else if(OrigConstant.PERSONAL_TYPE_SUP_CARD.equals(personalType)){
		personalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("SUPCARD_POPUP_DATA");
	}else{
		personalInfoDataM = utility.getPersonalInfoByType(ORIGForm.getAppForm(),OrigConstant.PERSONAL_TYPE_APPLICANT);
	}
	 String  displayMode=ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT;
        System.out.println("viewFromReport "+request.getParameter("viewFromReport"));
   String chkDisable="";
    //=============================================
    if(request.getParameter("viewFromReport")!=null){   
    if(session.getAttribute("applicationVerification")!=null){ 
     ApplicationDataM applicationDataM=(ApplicationDataM)session.getAttribute("applicationVerification"); 	     
      String reportPersonalSeq=request.getParameter("reportPersonalSeq");     
      String reportPersonalType=request.getParameter("reportPersonalType");            
      int personalSeq=utility.stringToInt(reportPersonalSeq);
      personalInfoDataM=utility.getPersonalInfoByTypeAndSeq( applicationDataM,reportPersonalType,personalSeq);     
     } 
      displayMode=ORIGDisplayFormatUtil.DISPLAY_MODE_VIEW;
      chkDisable=" disabled";
    }
    //=============================================
	if(personalInfoDataM == null){
		personalInfoDataM = new PersonalInfoDataM();
	}
   XRulesVerificationResultDataM  xrulesVer=personalInfoDataM.getXrulesVerification();
   Vector  xRuesDedupVehicle=xrulesVer.getVXRulesDedupVehicleDataM();
   if(xRuesDedupVehicle==null){
   xRuesDedupVehicle=new Vector();
   }
   HashMap hGear=new HashMap();
   hGear.put("A","Automatic");
   hGear.put("M","Manual");
%>
<HEAD><TITLE>De-Dup : Vehicle Result</TITLE>
<META http-equiv=content-type content="text/html; charset=UTF-8">
<SCRIPT language="JavaScript">
window.onBlur = self.focus();
</SCRIPT>
</HEAD>

<BODY leftMargin=0 topMargin=0 marginwidth="0" marginheight="0">
<table width="100%" border=0 cellpadding="0" cellspacing="0">
	<TR><TD height="5" colspan="3"></TD></TR>
	<TR><TD height="2" bgcolor="#3399cc" colspan="3"></TD></TR>
</table>
<div style="background-color: #F4F4F4">
	<SPAN style="background-color: #3399cc; vertical-align: top; height: 16">&nbsp;&nbsp;<font color="#FFFFFF"><b>Dedup Vehicle Result</b></font>&nbsp;</SPAN><SPAN><img src="en_US/images/SFCorner.gif"></SPAN>&nbsp;
</div>
<table width="100%"   border="0" cellpadding="1" cellspacing="1" bgcolor="#333333">   
  <tr>
    <td class="TableHeader" ><strong>&nbsp;<%=MessageResourceUtil.getTextDescription(request, "APPLICATION_NO1") %> </strong></td>
    <td class="TableHeader" ><strong>&nbsp;<%=MessageResourceUtil.getTextDescription(request, "ENGINE_NO") %> </strong></td>
    <td class="TableHeader" ><strong>&nbsp;<%=MessageResourceUtil.getTextDescription(request, "CLASSIS_NO") %> </strong></td>
    <td class="TableHeader" ><strong>&nbsp;<%=MessageResourceUtil.getTextDescription(request, "VEHICLE_REGIS_NO") %> </strong></td>
    <td class="TableHeader" ><strong>&nbsp;<%=MessageResourceUtil.getTextDescription(request, "BRAND") %> </strong></td>
    <td class="TableHeader" ><strong>&nbsp;<%=MessageResourceUtil.getTextDescription(request, "MODEL") %> </strong></td>
    <td class="TableHeader" ><strong>&nbsp;<%=MessageResourceUtil.getTextDescription(request, "GEAR") %> </strong></td> 
  </tr>
  <%   
   for(int i=0;i<xRuesDedupVehicle.size();i++){
       XRulesDedupVehicleDataM  xRulesDedupVehicleDataM=(XRulesDedupVehicleDataM)xRuesDedupVehicle.get(i);
   %>
  <tr>
    <td bgcolor="#FFFFFF">&nbsp;<%=ORIGDisplayFormatUtil.displayHTML(xRulesDedupVehicleDataM.getApplicationNo())%></td>
    <td bgcolor="#FFFFFF">&nbsp;<%=ORIGDisplayFormatUtil.displayHTML(xRulesDedupVehicleDataM.getEngineNo())%></td>
    <td bgcolor="#FFFFFF">&nbsp;<%=ORIGDisplayFormatUtil.displayHTML(xRulesDedupVehicleDataM.getChassisNo())%></td>
    <td bgcolor="#FFFFFF">&nbsp;<%=ORIGDisplayFormatUtil.displayHTML(xRulesDedupVehicleDataM.getRegisterNo())%></td>
    <td bgcolor="#FFFFFF">&nbsp;<%=ORIGDisplayFormatUtil.displayHTML(origXrulesUtil.getCarBrandDesc(xRulesDedupVehicleDataM.getBrand()))%></td>
    <td bgcolor="#FFFFFF">&nbsp;<%=ORIGDisplayFormatUtil.displayHTML(origXrulesUtil.getCarModel( xRulesDedupVehicleDataM.getModel(),xRulesDedupVehicleDataM.getBrand()))%></td>
    <td bgcolor="#FFFFFF">&nbsp;<%=ORIGDisplayFormatUtil.displayHTML(hGear.get(xRulesDedupVehicleDataM.getGear()))%></td>
  </tr>
  <% }%>
  <tr height="30" >
		<td colspan="7" align="center" bgcolor="#FFFFFF">&nbsp;
				<INPUT class="button_text" type=button value="<%=MessageResourceUtil.getTextDescription(request, "CLOSE") %>" onClick="window.close()">	</td>
  </tr>
</table> 
 

<%	//set current screen to main Form
	com.eaf.j2ee.pattern.control.ScreenFlowManager screenFlowManager =
	(com.eaf.j2ee.pattern.control.ScreenFlowManager) request.getSession(true).getAttribute(
		"screenFlowManager");
	screenFlowManager.setCurrentScreen("MAIN_APPFORM");
%>

