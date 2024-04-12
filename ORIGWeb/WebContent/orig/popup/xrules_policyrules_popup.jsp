<%@ page import="java.util.Vector" %>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="com.eaf.orig.shared.model.ApplicationDataM" %>
<%@ page import="com.eaf.orig.shared.model.PersonalInfoDataM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>
<%@ page import="com.eaf.xrules.shared.model.XRulesVerificationResultDataM" %>
<%@ page import=" com.eaf.xrules.shared.model.XRulesPolicyRulesDataM" %>
<%@ page import="com.eaf.xrules.shared.constant.XRulesConstant"%>
<%@ page import="com.eaf.orig.shared.utility.OrigXRulesUtil"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.formcontrol.view.form.ORIGFormHandler"/>
<% 
	//MessageResourceUtil msgUtil = MessageResourceUtil.getInstance();
		ORIGUtility utility = new ORIGUtility();
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
   Vector  xRuesPolicyRules=xrulesVer.getVXRulesPolicyRulesDataM();
   if(xRuesPolicyRules==null){
   System.out.println("XRuesPolicyRules is null");
   xRuesPolicyRules=new Vector();
   }
     // String  displayMode=ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT;
      String policyRulesResult=xrulesVer.getPolicyRulesResult();
      Vector xRuesPolicyRulesAuto=new Vector();
      Vector xRuesPolicyRulesManual=new Vector();
      for(int i=0;i<xRuesPolicyRules.size();i++){
        XRulesPolicyRulesDataM prmXRulesPolicyRulesDataM=(XRulesPolicyRulesDataM)xRuesPolicyRules.get(i);
      if( XRulesConstant.PolicyRulesType.AUTO.equals(prmXRulesPolicyRulesDataM.getPolicyType())){
        xRuesPolicyRulesAuto.add(prmXRulesPolicyRulesDataM);
        }else if( XRulesConstant.PolicyRulesType.MANUAL.equals(prmXRulesPolicyRulesDataM.getPolicyType())){
        xRuesPolicyRulesManual.add(prmXRulesPolicyRulesDataM);
        }
      }
       OrigXRulesUtil     origXRulesUtil=OrigXRulesUtil.getInstance();
      if(xRuesPolicyRulesAuto.size()==0){
      System.out.println("!! xRuesPolicyRulesAuto Size=0");
      xRuesPolicyRulesAuto=origXRulesUtil.getPolicyRules(XRulesConstant.PolicyRulesType.AUTO);      
      }
      if(xRuesPolicyRulesManual.size()==0){
         xRuesPolicyRulesManual=origXRulesUtil.getPolicyRules(XRulesConstant.PolicyRulesType.MANUAL);
         xRuesPolicyRules.addAll(xRuesPolicyRulesManual);
      }
      xrulesVer.getPolicyRulesResult();
      String txtResultName=request.getParameter("txtResultName");
%> 
<HEAD>   
<TITLE>Policy Rules Popup Result</TITLE>
<script language="javascript">
 window.onBlur = self.focus();
var maxCheckbox=<%=xRuesPolicyRulesAuto.size()%>;
function savePolicyRulesVerification(){  
    var  form = document.appFormName;
	form.action.value ="SaveXrulesPolicyRulesPopup";
	form.handleForm.value = "N";
	if(form.policyRulesFinalResult[0].checked ||form.policyRulesFinalResult[1].checked){
	form.submit();
	}else{
	 alert("Please Verify Result");
	}
}
function checkAllPolicyClick(){ 
    var   form = document.appFormName;      
    var result=form.policy_manual_all.checked
    <%
     for(int i=0;i<xRuesPolicyRulesManual.size();i++){
          XRulesPolicyRulesDataM  prmXRulesPolicyRulesDataM=(XRulesPolicyRulesDataM)xRuesPolicyRulesManual.get(i);
          out.println("form."+prmXRulesPolicyRulesDataM.getPolicyCode()+".checked=result"); 
     }%>     
}
</script>
</HEAD>
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
	                          	<td class="text-header-detail" nowrap="nowrap">Policy Rules Result Screen</td>
	                            <td width="330">
	                            	<table width="60" border="0" align="right" cellpadding="2" cellspacing="0">
	                              		<tr height="30"><td>
		                              		<%if("".equals(chkDisable)){ %>
												<INPUT class="button" type="button" value="<%=MessageResourceUtil.getTextDescription(request, "SAVE") %>" onClick="savePolicyRulesVerification()">
											<%} %>
	                              			 </td><td>
												<INPUT class="button" type="button" value="<%=MessageResourceUtil.getTextDescription(request, "CLOSE") %>" onClick="window.close()">
	                                    </td></tr>
	                                </table>
	                            </td></tr>
                            </table>
                        </td></tr>
                        <tr> <td  height="15"></td></tr>
                        <tr class="sidebar10"> <td align="center">
							<table cellpadding="0" cellspacing="0" width="100%" align="center">	 
								<tr>
									<td class="textColS" width="50%" align="right"><b><%=MessageResourceUtil.getTextDescription(request, "FINAL_RESULT") %></b></td>
									<td class="textColS" width="20%">&nbsp;</td>
									<td class="textColS" align="left" width="30%">
										<%if("".equals(chkDisable)){ %>
										 	<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
										 	<tr><td><font class="textColS">
									           Pass</font> </td> 
									        <td>             
									        	<%=ORIGDisplayFormatUtil.displayRadioTag(XRulesConstant.ExecutionResultString.RESULT_PASS,displayMode,"policyRulesFinalResult",policyRulesResult,
									        			XRulesConstant.ExecutionResultString.RESULT_PASS,XRulesConstant.ExecutionResultString.RESULT_PASS) %>
									        </td><td><font class="textColS">
									        	Fail</font>
									        </td><td>
									        	<%=ORIGDisplayFormatUtil.displayRadioTag(XRulesConstant.ExecutionResultString.RESULT_FAIL,displayMode,"policyRulesFinalResult",policyRulesResult,
									        			XRulesConstant.ExecutionResultString.RESULT_FAIL,XRulesConstant.ExecutionResultString.RESULT_FAIL) %>
									        </td></tr></table> 
									     <%}else{ %>
									     	<%=ORIGDisplayFormatUtil.displayHTML(policyRulesResult) %>
									     <%} %> 
									</td>
								</tr>
								<tr><td>&nbsp;</td></tr>
							</table>							
 							<table class="gumayframe3" id="KLTable" cellpadding="0" cellspacing="0" width="100%" align="center" border="0" bgcolor="#FFFFFF">
								<tr><td class="TableHeader">
									<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
										<tr>
											<TD align="center" class="Bigtodotext3"><%=MessageResourceUtil.getTextDescription(request, "CRITERIA") %></TD>
											<TD width="200" align="center" class="Bigtodotext3"><%=MessageResourceUtil.getTextDescription(request, "SYSTEM_RESULT") %></TD>
										</tr>
									</table>
								</td></TR>		
						       <%
						          for(int i=0;i<xRuesPolicyRulesAuto.size();i++){
							          XRulesPolicyRulesDataM  prmXRulesPolicyRulesDataM=(XRulesPolicyRulesDataM)xRuesPolicyRulesAuto.get(i);
							          String bgColor="bgColor=\"#FFFFFF\"";          
							          if(i%2==0){
							          bgColor="bgColor=\"#FAFAFA\"";
							          }
							           
							          if(XRulesConstant.PolicyRulesResult.RESULT_FAIL.equalsIgnoreCase(prmXRulesPolicyRulesDataM.getResult())){
							           bgColor="bgColor=\"yellow\"";
							          }
						       %>
								<tr <%=bgColor%>><td align="center" class="gumaygrey2">
									<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
										<tr>
											<TD class="jobopening2" nowrap ><%=ORIGDisplayFormatUtil.displayHTML(origXRulesUtil.getPolicyDescription(prmXRulesPolicyRulesDataM.getPolicyCode())) %></TD>
											<TD class="jobopening2" width="200" nowrap> <%=ORIGDisplayFormatUtil.displayHTML(prmXRulesPolicyRulesDataM.getResult()) %></TD>
										</TR>
									</table>
								</td></tr>
							<% }
							%>								
							</TABLE>	
						 </td></tr>
                        <tr> <td  height="15"></td></tr>
                        <tr class="sidebar10"> <td align="center">	
							<table class="gumayframe3" cellpadding="0" cellspacing="0" width="100%" align="center" border="0" bgcolor="#FFFFFF">
								<tr><td class="TableHeader">
									<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
										<tr>
											<TD width="29" class="Bigtodotext3" >
												<INPUT type="checkbox" name="policy_manual_all" value="Y" onClick="checkAllPolicyClick()" <%=chkDisable%> ></TD>
											<TD width="465" class="Bigtodotext3" align="center" ><b> PolicyRules Manual Check List</b></TD>
										</TR>
									</table>
								</td></tr>
								 <%
							          for(int i=0;i<xRuesPolicyRulesManual.size();i++){
							          XRulesPolicyRulesDataM  prmXRulesPolicyRulesDataM=(XRulesPolicyRulesDataM)xRuesPolicyRulesManual.get(i);
							          String bgColor="bgColor=\"#FFFFFF\"";
							          if(i%2==0){
							          bgColor="bgColor=\"#FAFAFA\"";
							          }
							     %>
								<tr <%=bgColor%>><td align="center" class="gumaygrey2">
									<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
										<tr>
											<TD class="textColS" width="29">
												<%=ORIGDisplayFormatUtil.displayCheckBox( prmXRulesPolicyRulesDataM.getResult(),prmXRulesPolicyRulesDataM.getPolicyCode(),
															XRulesConstant.PolicyRulesResult.RESULT_SELECTED," "+chkDisable) %>
					 						</TD>
											<TD class="textColS" width="465">
												<%=ORIGDisplayFormatUtil.displayHTML(origXRulesUtil.getPolicyDescription(prmXRulesPolicyRulesDataM.getPolicyCode()))%></TD>
										</TR>
									</table>
								</td></tr>
								 <%
								   }
								  %>
							</TABLE>
						</TD></TR>
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

