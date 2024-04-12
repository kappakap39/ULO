<%@ page import="java.util.*" %>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="com.eaf.orig.shared.model.ApplicationDataM" %>
<%@ page import="com.eaf.orig.shared.model.PersonalInfoDataM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>
<%@ page import="com.eaf.xrules.shared.model.XRulesVerificationResultDataM" %>
<%@ page import="com.eaf.xrules.shared.model.XRulesLPMDataM" %>
<%@ page import="com.eaf.xrules.shared.constant.XRulesConstant"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>
<%@ page import="com.eaf.orig.shared.utility.OrigXRulesUtil" %> 

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.formcontrol.view.form.ORIGFormHandler"/>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<% 
	ORIGUtility utility = new ORIGUtility();
		ORIGCacheUtil cacheUtil = ORIGCacheUtil.getInstance();
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
     String personalIndex=request.getParameter("personalIndex");     
     // personalInfoDataM=utility.getPersonalInfoByIndex( applicationDataM,personalIndex);     
     } 
      displayMode=ORIGDisplayFormatUtil.DISPLAY_MODE_VIEW;
      chkDisable=" disabled";
    }
    //=============================================
	if(personalInfoDataM == null){
		personalInfoDataM = new PersonalInfoDataM();
	}
   XRulesVerificationResultDataM  xrulesVer=personalInfoDataM.getXrulesVerification();
   Vector vXRulesLPMDataMs=xrulesVer.getVXRulesLMPDataM();
   if(vXRulesLPMDataMs==null){
   vXRulesLPMDataMs=new Vector();
   }
   XRulesLPMDataM  prmXRulesLPMDataM=null;
   String lpmItemIndex=request.getParameter("lpmItemIndex");   
   if(lpmItemIndex!=null&&"".equals(lpmItemIndex)){
     int iLpmItem=Integer.parseInt(lpmItemIndex);
          prmXRulesLPMDataM=(XRulesLPMDataM)vXRulesLPMDataMs.get(iLpmItem);   
   }
   if(prmXRulesLPMDataM==null){
   prmXRulesLPMDataM=new XRulesLPMDataM();
   }
 //  Vector lpmLoanTypeVect = cacheUtil.getNaosCacheDataMs("AL_ALL_ALL",24);
 //  Vector lpmItemStatusVect=OrigXRulesUtil.getInstance().getNCBAccountStatus();
  //String result=xrulesVer.getLPMResult();
  //String lpmComment=xrulesVer.getLPMcomment(); 
%>
	
<HEAD>
<TITLE>LPM Popup Screen</TITLE>
<script type="text/javascript">
<!--
 function saveLpmInput(){  
   var form =window.appFormName;
   form.action.value = "SaveXrulesLPMItemPopup";
   form.handleForm.value = "N";
   form.submit(); 
   //window.opener.reload();
   //window.opener.action="LoadXRlesLPMmainPopup";
    // window.setTimeout("window.opener.reloadPage(),alert('test')",1000);
    //window.opener.reloadFlag.value='Y';
   // window.close();   
 }
 function upperCaseFiled(fieldName){
  fieldName.value=fieldName.value.toUpperCase();
} 
//-->
</script>
</HEAD>
<%	//set current screen to main Form
	com.eaf.j2ee.pattern.control.ScreenFlowManager screenFlowManager =
	(com.eaf.j2ee.pattern.control.ScreenFlowManager) request.getSession(true).getAttribute(
		"screenFlowManager");
	screenFlowManager.setCurrentScreen("MAIN_APPFORM");
%>
<input type="hidden" name="lpmItemIndex" value="<%=lpmItemIndex%>">
<TABLE cellSpacing=0 cellPadding=0 width="100%"  border="0">
	<TR><TD vAlign=top>
	<table cellpadding="0" cellspacing="0" width="100%" align="center" bgcolor="#FFFFFF">
		<tr><td colspan="2" class="sidebar8">
			<table cellSpacing="1" cellPadding="0" width="100%" border="0"> 
		    	<tr><td class="sidebar9">
					<table cellspacing=0 cellpadding=0 width="100%" align=center border=0>	     		
			 			<tr> <td  height="10">
			 				<table width="100%" border="0" cellspacing="0" cellpadding="0">
                          	<tr>
	                          	<td class="text-header-detail">LPM Input </td>
	                            <td width="330">
	                            	<table width="50" border="0" align="right" cellpadding="2" cellspacing="0">
	                              		<tr height="30"><td>
	                              			<INPUT type="button" name="btnSave" class="button_text" value="<%=MessageResourceUtil.getTextDescription(request, "SAVE") %>" onClick="saveLpmInput()"> 
										</td><td>
											<INPUT type="button" name="btnClose" value="<%=MessageResourceUtil.getTextDescription(request, "CLOSE") %>" class="button_text" onclick="window.close()">	
	                                    </td></tr>
	                                </table>
	                            </td></tr>
                            </table>
                        </td></tr>
                        <tr> <td  height="15"></td></tr>                        
						<tr class="sidebar10"> <td align="center">
							<FIELDSET><LEGEND style="color: gray"><B>Input</B></LEGEND>
							<table cellpadding="0" cellspacing="0" width="100%" align="center">	 
							<tr>
								<TD align="right" class="textColS" nowrap="nowrap"><%=MessageResourceUtil.getTextDescription(request, "LOAN_TYPE") %> :</TD>
								<TD class="inputCol" nowrap="nowrap"> <%=ORIGDisplayFormatUtil.displayPopUpTagScriptAction("",displayMode,"5","lpmItemloanType","30","lpmItemloanType_desc","textbox","","30","...","button_text","LoadLPMLoanType","  ","LPMLoanType") %>
									<%--=ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(lpmLoanTypeVect,personalInfoDataM.getOccupation(),"lpmItemloanType",displayMode,"") --%>
							  </TD>
							  <TR>
								<TD nowrap="nowrap" class="textColS" align="right"><%=MessageResourceUtil.getTextDescription(request, "CREDIT_LIMIT") %> :</TD>
								<TD class="inputCol" nowrap="nowrap" ><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(prmXRulesLPMDataM.getCreditLimit()),displayMode,"","lpmItemCreditLimit","textboxCurrency","onblur=\"javascript:addComma(this);addDecimalFormat(this);\" onKeyPress=\"isNumberOnKeyPress(this)\" onkeyup=\"isNumberOnkeyUp(this)\" onfocus=\"removeCommaField(this)\" ","20") %>
								  <td>			
								 </TD>
							</TR>
							<TR>
								<TD nowrap="nowrap" class="textColS" align="right"><%=MessageResourceUtil.getTextDescription(request, "OS_BALANCE") %>(Baht) :</TD>
								<TD class="inputCol" nowrap="nowrap"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(prmXRulesLPMDataM.getOSBalnace()),displayMode,"","lpmItemOutStandingBalane","textboxCurrency","onblur=\"javascript:addComma(this);addDecimalFormat(this);\" onKeyPress=\"isNumberOnKeyPress(this)\" onkeyup=\"isNumberOnkeyUp(this)\" onfocus=\"removeCommaField(this)\" ","20") %>
								 </TD>			 
							</TR>
							<TR>
								<TD nowrap="nowrap" class="textColS" align="right" ><%=MessageResourceUtil.getTextDescription(request, "STATUS") %> :</TD>
								<TD class="inputCol" nowrap="nowrap" ><%=ORIGDisplayFormatUtil.displayPopUpTagScriptAction("",displayMode,"5","lpmItemStatus","30","lpmItemStatus_desc","textbox"," onKeyUP=\"upperCaseFiled(this)\" ","30","...","button_text","LoadLPMStatus","  ","NCBAccountStatus") %>
					 			     <%--=ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(lpmItemStatusVect,personalInfoDataM.getOccupation(),"lpmItemStatus",displayMode,"") --%>
								</TD>			 
							</TR>
							<TR>
								<TD></TD>
								<TD></TD>			 
							</TR>
							</TABLE></FIELDSET>
						</td></tr>
					</table>
				</td></tr>
			</table>
		</td></tr>
		</table>
	</td></tr>
</table>
