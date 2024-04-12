<%@ page import="java.util.*" %>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>
<%@ page import="com.eaf.orig.shared.model.ApplicationDataM" %>
<%@ page import="com.eaf.orig.wf.shared.ORIGWFConstant" %>
<%@ page import="com.eaf.orig.shared.model.ReasonDataM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGFormUtil"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>
<%@ page import="com.eaf.xrules.shared.model.XRulesVerificationResultDataM"%>
<%@ page import="com.eaf.orig.shared.model.PersonalInfoDataM" %>
<%@ page import=" com.eaf.xrules.shared.model.XRulesPolicyRulesDataM" %>
<%@ page import="com.eaf.orig.shared.utility.OrigXRulesUtil"%>


<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.formcontrol.view.form.ORIGFormHandler"/>

<% 
	ORIGCacheUtil cacheUtil = ORIGCacheUtil.getInstance();
	ApplicationDataM applicationDataM = ORIGForm.getAppForm();
	if(applicationDataM == null){
		applicationDataM = new ApplicationDataM();
	}
	ORIGFormUtil formUtil = new ORIGFormUtil();
	String searchType = (String) request.getSession().getAttribute("searchType");

	System.out.println("ORIGUser.getRoles() = "+ORIGUser.getRoles().elementAt(0));
	System.out.println("applicationDataM.getJobState() = "+applicationDataM.getJobState());
	String userRole=(String)ORIGUser.getRoles().elementAt(0);
	String displayMode=ORIGDisplayFormatUtil.DISPLAY_MODE_VIEW;
	if(OrigConstant.ROLE_XUW.equals(userRole)&& OrigConstant.SEARCH_TYPE_OVERRIDE.equals(searchType)){
	  displayMode = ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT;
	}
	String personalType = (String) request.getSession().getAttribute("PersonalType");
	PersonalInfoDataM personalInfoDataM;
	ORIGUtility utility = new ORIGUtility();
	if(OrigConstant.PERSONAL_TYPE_GUARANTOR.equals(personalType)){
		personalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("MAIN_POPUP_DATA");
	}else if(OrigConstant.PERSONAL_TYPE_SUP_CARD.equals(personalType)){
		personalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("SUPCARD_POPUP_DATA");
	}else{
		personalInfoDataM = utility.getPersonalInfoByType(ORIGForm.getAppForm(),OrigConstant.PERSONAL_TYPE_APPLICANT);
	}
	if(personalInfoDataM == null){
		personalInfoDataM = new PersonalInfoDataM();		
	}
	XRulesVerificationResultDataM xRulesVerification=personalInfoDataM.getXrulesVerification();	
	if(xRulesVerification==null){
	   xRulesVerification=new XRulesVerificationResultDataM();
	}	
	 
 
    Vector vPolicyException=utility.getPolicyExcetption(applicationDataM.getPolicyVersion(),xRulesVerification.getVXRulesPolicyRulesDataM());    
	String reason = "";
	String reasonDesc = "";
	ReasonDataM reasonDataM; 
	String sDate = null;
	if(applicationDataM.getXuwOverrideDate()==null){
		sDate="";
	}else{
		sDate=ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(applicationDataM.getXuwOverrideDate()));
	}
	OrigXRulesUtil     origXRulesUtil=OrigXRulesUtil.getInstance();
	String userName = ORIGUser.getUserName();
	String currentDate = ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(new Date(System.currentTimeMillis())));
%>
<table cellpadding="" cellspacing="1" width="100%" align="center">
	<tr>
		<td>
			<table cellpadding="" cellspacing="1" width="100%" align="center">
			     <tr>
					<td class="textColS">Policy Exception : </td>
					<td class="inputCol" colspan="3">
					<TABLE>
					<%
					for(int i=0;i<vPolicyException.size();i++) { 
					XRulesPolicyRulesDataM xrulesPolicyRulesDataM=(XRulesPolicyRulesDataM)vPolicyException.get(i);
					String policyType="";
					if( com.eaf.xrules.shared.constant.XRulesConstant.PolicyRulesType.AUTO.equals(xrulesPolicyRulesDataM.getPolicyType())){
					   policyType="Auto";
					}else{
					  policyType="Manual";
					}
					 // xrulesPolicyRulesDataM.getPolicyType()
					 //String 					 
					 %>
					<TR><TD><%=policyType%> : <%=ORIGDisplayFormatUtil.displayHTML(origXRulesUtil.getPolicyDescription(xrulesPolicyRulesDataM.getPolicyCode()))%></TD></TR>
					<%}%>
					</TABLE>
					</td> 
				</tr>
				<tr>
					<td class="textColS"></td>
					<td class="inputCol" colspan="4"><%=ORIGDisplayFormatUtil.displayRadioTagScriptAction(OrigConstant.ApplicationDecision.OVERIDED_POLICY,displayMode,"exuw_decision",applicationDataM.getXuwDecision(),"","","onClick=\"javascript:setOverrid();\"") %> <%=MessageResourceUtil.getTextDescription(request, "OVERIDE_POLICY") %> </td>
				</tr>
				<tr>
					<td class="textColS"></td>
					<td class="inputCol" colspan="4"><%=ORIGDisplayFormatUtil.displayRadioTagScriptAction(OrigConstant.ApplicationDecision.NOT_OVERIDED_POLICY,displayMode,"exuw_decision",applicationDataM.getXuwDecision(),"","","onClick=\"javascript:setOverrid();\"") %> <%=MessageResourceUtil.getTextDescription(request, "NOT_OVERIDE_POLICY") %> </td>
				</tr>
				<tr>
					<td class="textColS" width="20%"><%=MessageResourceUtil.getTextDescription(request, "DECISION_BY") %> :</td>
					<td class="inputCol" width="20%"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(applicationDataM.getXuwOverrideBy(),displayMode,"","exuw_decision_by","textboxReadOnly","readOnly","50") %></td>
					<td class="textColS" width="10%"><%=MessageResourceUtil.getTextDescription(request, "DATE") %> :</td>
					<td class="inputCol" width="50%"><%=ORIGDisplayFormatUtil.displayInputTagJsDate("appFormName",sDate,displayMode,"","exuw_decision_date","textboxReadOnly","right","readOnly") %></td>
				</tr>				 
				<tr>
					<td class="textColS" ><%=MessageResourceUtil.getTextDescription(request, "REASON_POLICY") %><Font color="#ff0000">*</Font> :</td>
					<td class="inputCol" colspan="3"><%=ORIGDisplayFormatUtil.displayInputTextAreaTag_Orig(ORIGDisplayFormatUtil.displayHTML(applicationDataM.getXuwDecisionReason()) ,"exuw_reason","3","40",displayMode,"")%></td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<script language="JavaScript">
function setOverrid(){
	form = document.appFormName;
	form.exuw_decision_by.value = '<%=userName%>';
	form.exuw_decision_date.value = '<%=currentDate%>';
	 
} 
</script>