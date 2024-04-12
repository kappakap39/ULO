<%@ page import="java.util.Vector" %>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="com.eaf.orig.shared.model.ApplicationDataM" %>
<%@ page import="com.eaf.orig.shared.model.PersonalInfoDataM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>
<%@ page import="com.eaf.xrules.shared.model.XRulesVerificationResultDataM" %>
<%@ page import="com.eaf.xrules.shared.model.XRulesDedupDataM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.formcontrol.view.form.ORIGFormHandler"/>

<% 
	//MessageResourceUtil msgUtil = MessageResourceUtil.getInstance();
		ORIGUtility utility = new ORIGUtility();
		ORIGCacheUtil  origCacheUtil=ORIGCacheUtil.getInstance();
%>

<%
System.out.println("==> in Xrules dedup.jsp");

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
   Vector  vXrulesDedup=xrulesVer.getVXRulesDedupDataM();
   if(vXrulesDedup==null){
   vXrulesDedup=new Vector();
   }
%>
<HEAD><TITLE>De-Dup : Application Result</TITLE> 
<SCRIPT language="JavaScript"> 
window.onBlur = self.focus(); 
</SCRIPT>
</HEAD>

<table cellpadding="0" cellspacing="0" width="100%" align="center" bgcolor="#FFFFFF">
	<tr><td colspan="2" class="sidebar8">
		<table cellSpacing="1" cellPadding="0" width="100%" border="0"> 
	    	<tr><td class="sidebar9">
				<table cellspacing=0 cellpadding=0 width="100%" align=center border=0>	     		
		 			<tr> <td  height="10">
		 				<table width="100%" border="0" cellspacing="0" cellpadding="0">
                          	<tr>
	                          	<td class="text-header-detail"><%=MessageResourceUtil.getTextDescription(request, "XRULES_BUTTON_5") %> </td>
	                            <td width="330">
	                            	<table width="50" border="0" align="right" cellpadding="2" cellspacing="0">
	                              		<tr height="30"><td>
	                              			<INPUT class="button" type=button value="<%=MessageResourceUtil.getTextDescription(request, "CLOSE") %>" onClick="window.close()">
	                                    </td></tr>
	                                </table>
	                            </td></tr>
                        </table>
                    </td></tr>
                    <tr> <td  height="15"></td></tr>                        
					<tr class="sidebar10"> <td align="center">
					 	 <table cellpadding="" cellspacing="0" width="100%" align="center" bgcolor="#FFFFFF">
					 	 	<tr><td>
					 	 		<table class="gumayframe3" cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
									<tr><td class="TableHeader">
										<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
											<tr> 
											    <td class="Bigtodotext3" width="8%"><%=MessageResourceUtil.getTextDescription(request, "OFFICE")%></td>
											    <td class="Bigtodotext3" width="8%"><%=MessageResourceUtil.getTextDescription(request, "APPLICATION_NO")%></td>
											    <td class="Bigtodotext3" width="10%"><%=MessageResourceUtil.getTextDescription(request, "MAIN_CUSTOMERS_TYPE")%></td>
											    <td class="Bigtodotext3" width="8%"><%=MessageResourceUtil.getTextDescription(request, "MAIN_CUSTOMER_NAME")%></td>
											    <td class="Bigtodotext3" width="10%"><%=MessageResourceUtil.getTextDescription(request, "CITIZEN_ID")%></td>
											    <td class="Bigtodotext3" width="8%"><%=MessageResourceUtil.getTextDescription(request, "CUSTOMER_TYPE")%></td>
											    <td class="Bigtodotext3" width="16%"><%=MessageResourceUtil.getTextDescription(request, "CMR_NAME")%></td>
											    <td class="Bigtodotext3" width="12%"><%=MessageResourceUtil.getTextDescription(request, "APPLICATION_STATUS")%></td>
											    <td class="Bigtodotext3" width="10%"><%=MessageResourceUtil.getTextDescription(request, "CREATE_DATE")%></td>
											    <td class="Bigtodotext3" width="10%"><%=MessageResourceUtil.getTextDescription(request, "TOTAL_FINANCE_AMT")%></td>
											</tr>
		                                </table>
		                            </td></tr>
		                            
					  <%  
					   for(int i=0;i<vXrulesDedup.size();i++){
					      XRulesDedupDataM  xrulesDedupDataM=(XRulesDedupDataM)vXrulesDedup.get(i);
					   %>
									<tr><td align="center" class="gumaygrey2">
										<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
											<tr>
											    <td class="jobopening2" width="8%" nowrap><%=ORIGDisplayFormatUtil.displayHTML(origCacheUtil.getOfficeNameEng(  xrulesDedupDataM.getOfficeCode()))%></td>
											    <td class="jobopening2" width="8%" nowrap><%=ORIGDisplayFormatUtil.displayHTML(xrulesDedupDataM.getApplicationNo())%></td>
											    <td class="jobopening2" width="10%" nowrap><%=ORIGDisplayFormatUtil.displayHTML(origCacheUtil.getORIGMasterDisplayNameDataM( "CustomerType",xrulesDedupDataM.getCustomerType()))%></td>
											    <td class="jobopening2" width="8%"><%=ORIGDisplayFormatUtil.displayHTML(xrulesDedupDataM.getThaiFirstName())+"  "+ORIGDisplayFormatUtil.displayHTML(xrulesDedupDataM.getThaiLastName() )%></td>
											    <td class="jobopening2" width="10%" nowrap><%=ORIGDisplayFormatUtil.displayHTML(xrulesDedupDataM.getDupCitizenId())%></td>
											    <td class="jobopening2" width="8%" nowrap><%=ORIGDisplayFormatUtil.displayHTML(utility.getPersonalTypeDesc(  xrulesDedupDataM.getPersonalType()))%></td>
											    <td class="jobopening2" width="16%"><%=ORIGDisplayFormatUtil.displayHTML(origCacheUtil.getORIGMasterDisplayNameDataM( "LoanOfficer", xrulesDedupDataM.getMktCode()))%></td>
											    <td class="jobopening2" width="12%" nowrap><%=ORIGDisplayFormatUtil.displayHTML(xrulesDedupDataM.getApplicationStatus())%></td>
											    <td class="jobopening2" width="10%" nowrap><%=ORIGDisplayFormatUtil.dateTimetoStringForThai(xrulesDedupDataM.getCreatedDate())%></td>
											    <td class="jobopening2" width="10%" nowrap align="right"><%=ORIGDisplayFormatUtil.displayCommaNumber(xrulesDedupDataM.getTotalAmountFinanced())%></td>
											</tr>
		                                </table>
		                            </td></tr>
  						<% }%>
  
								</table> 
							</td></tr> 
						</table>
					</td></tr>
				</table>
			</td></tr>
		</table>
	</td></tr>
</table>

<%	//set current screen to main Form
	com.eaf.j2ee.pattern.control.ScreenFlowManager screenFlowManager =
	(com.eaf.j2ee.pattern.control.ScreenFlowManager) request.getSession(true).getAttribute(
		"screenFlowManager");
	screenFlowManager.setCurrentScreen("MAIN_APPFORM");
%>
