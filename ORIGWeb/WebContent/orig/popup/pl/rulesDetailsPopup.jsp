<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.Vector" %>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.ORIGLogic"%>
<%@ page import="org.apache.log4j.Logger"%>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLApplicationDataM" %>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM" %>
<%@ page import="com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesVerificationResultDataM" %>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.DataFormatUtility" %>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil" %>
<%@ page import="com.eaf.orig.shared.dao.utility.ORIGRuleDetailsUtil" %>
<%@ page import="com.eaf.orig.ulo.pl.model.app.RulesDetailsDataM" %>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLProjectCodeDataM" %>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.PLOrigApplicationUtil" %>
<%@ page import="com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesPolicyRulesDataM" %>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="PLORIGForm" scope="session" class="com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler"/>

<% 
	Logger log = Logger.getLogger(this.getClass());
	PLOrigApplicationUtil appUtil = new PLOrigApplicationUtil();
	
	PLApplicationDataM applicationM = PLORIGForm.getAppForm();	
	
	ORIGRuleDetailsUtil rulesUtil = ORIGRuleDetailsUtil.getInstance();
	Vector<RulesDetailsDataM> ruleDetailVT = rulesUtil.getRulesDetailsConfig(applicationM.getBusinessClassId());

	String groupId = "";
	String classStyle = "";
	
	PLProjectCodeDataM projectM = appUtil.getProjectCodeDataM(applicationM.getProjectCode());
	if(projectM == null) projectM = new PLProjectCodeDataM();
	
	PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
	PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();
	if(null == xrulesVerM){
		xrulesVerM = new PLXRulesVerificationResultDataM();
		personalM.setXrulesVerification(xrulesVerM);
	}
%>
<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">	
	<tr height="15"><td class="Bigtodotext3"></td></tr>
	<tr>
		<td>
			<table class="TableFrame">				
				<tr class="Header">
					<td width="10%" class="textC" nowrap="nowrap">No.</td>
					<td width="60%" class="textC" nowrap="nowrap">Criteria</td>
					<td width="30%" class="textC" nowrap="nowrap">Result</td>
				</tr>
				<%
				if(null !=ruleDetailVT && ruleDetailVT.size() >0){ 
					for(int i=0; i<ruleDetailVT.size(); i++){
						RulesDetailsDataM ruleDetailM = (RulesDetailsDataM)ruleDetailVT.get(i);
						//get policy rules result by policy rule id
						PLXRulesPolicyRulesDataM policyM = xrulesVerM.getPLPolicyRuleByPolicyID(ruleDetailM.getPolicyRuleID());
						if(policyM == null) policyM = new PLXRulesPolicyRulesDataM();
						//get style to display from result code (red, yellow, green)
						classStyle = ORIGLogic.LogicColorStyleResult(policyM.getResultCode(),policyM.getResultDesc());
				%>
					<%	
						if(!groupId.equals(ruleDetailM.getPolicyGroupID())){
							groupId = ruleDetailM.getPolicyGroupID();
					%>
						<tr class="Header">
							<td colspan="3"><%=MessageResourceUtil.getTextDescription(request,ruleDetailM.getPolicyGroupName())%></td>
						</tr>
						<tr class="ResultData">
							<td width="10%"><%=i+1 %></td>
							<td width="60%"><div class="textL"><%=HTMLRenderUtil.displayHTML(ruleDetailM.getPolicyRuleDesc())%></div></td>
							<td width="30%"><div class="<%=classStyle%>"><%=DataFormatUtility.StringNullToSpecific(policyM.getResultDesc(),"-")%></div></td>
						</tr>
					<%}else{%>
						<tr class="ResultData">
							<td width="10%"><%=i+1 %></td>
							<td width="60%"><div class="textL"><%=HTMLRenderUtil.displayHTML(ruleDetailM.getPolicyRuleDesc())%></div></td>
							<td width="30%"><div class="<%=classStyle%>"><%=DataFormatUtility.StringNullToSpecific(policyM.getResultDesc(),"-")%></div></td>
						</tr>
					<%}%>	
				<%}%>
			<%}else{%>
				<tr class="ResultNotFound">
					<td class="textC" nowrap="nowrap">Not found Configuration, please contact Administrator.</td>
				</tr>
			<%}%>				
			</table>
		</td>
	</tr>
	<tr height="15">
		<td class="Bigtodotext3"></td>
	</tr>
	<tr>
		<td class="Bigtodotext3">
			<div align="left"><%=MessageResourceUtil.getTextDescription(request, "OTHER_CONDITION")%>&nbsp;:&nbsp;<font color="black"><%=DataFormatUtility.StringNullToSpecific(projectM.getOverideRules(),"-") %></font></div>
		</td>
	</tr>
</table>

<%
	com.eaf.j2ee.pattern.control.ScreenFlowManager screenFlowManager =
	(com.eaf.j2ee.pattern.control.ScreenFlowManager) request.getSession(true).getAttribute("screenFlowManager");
	screenFlowManager.setCurrentScreen("PL_MAIN_APPFORM");
%>
