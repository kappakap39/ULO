<%@page import="com.eaf.orig.ulo.pl.app.utility.WorkflowTool"%>
<%@page import="com.eaf.orig.cache.properties.ORIGCacheDataM"%>
<%@page import="com.eaf.orig.shared.constant.OrigConstant"%>
<%@page import="com.eaf.orig.shared.utility.ORIGCacheUtil"%>
<%@page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@page import="com.eaf.orig.shared.utility.ORIGUtility"%>
<%@page import="com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler"%>
<%@page import="com.eaf.orig.ulo.pl.app.utility.DataFormatUtility"%>
<%@page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil"%>
<%@page import="com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesDedupDataM"%>
<%@page import="java.util.Vector"%>
<%@page import="com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesVerificationResultDataM"%>
<%@page import="com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM"%>
<%@page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@page import="com.eaf.orig.ulo.pl.model.app.PLApplicationDataM"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="PLORIGForm" scope="session" class="com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler"/>
<%
	org.apache.log4j.Logger log=org.apache.log4j.Logger.getLogger(this.getClass());	 

	PLApplicationDataM appM  = PLORIGForm.getAppForm();	
		if(null == appM) appM = new PLApplicationDataM();	
	
	PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);

	PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();
	
	if(null == xrulesVerM) xrulesVerM = new PLXRulesVerificationResultDataM();
	
	Vector<PLXRulesDedupDataM> dedupVect = xrulesVerM.getVXRulesDedupDataM();
	
	ORIGCacheUtil origCache = new ORIGCacheUtil();	
	WorkflowTool wfTool = new WorkflowTool();	
%>
<div class="PanelFirst">
	<div class="PanelSecond TextHeaderNormal">
		<div class="PanelThird">
			<table class="FormFrame">
				<tr>
					<td class="textR" nowrap="nowrap" width="25%"><%=MessageResourceUtil.getTextDescription(request, "DUP_NAME_THAI") %>:</td>
					<td class="textL" width="20%"><%=HTMLRenderUtil.displayHTML((String) personalM.getThaiFirstName())%>&nbsp;&nbsp;<%=HTMLRenderUtil.displayHTML((String) personalM.getThaiLastName())%></td>
					<td class="textR" nowrap="nowrap" width="25%"><%=MessageResourceUtil.getTextDescription(request, "DUP_ID_NO") %>:</td>
					<td class="textL" width="30%"><%=HTMLRenderUtil.displayHTML((String) personalM.getIdNo())%></td>
				</tr>
			</table>
			<table class="TableFrame">				
				<tr class="Header">
					<td width="3%"><%=MessageResourceUtil.getTextDescription(request, "DUP_NUM") %></td>
					<td width="14%"><%=MessageResourceUtil.getTextDescription(request, "DUP_APP_NO")%></td>
					<td width="17%"><%=MessageResourceUtil.getTextDescription(request, "DUP_APP_STATUS") %></td>
					<td width="17%"><%=MessageResourceUtil.getTextDescription(request, "DUP_USER") %></td>
					<td width="15%"><%=MessageResourceUtil.getTextDescription(request, "DUP_CREATE_DATE") %></td>
					<td width="16%"><%=MessageResourceUtil.getTextDescription(request, "DUP_CHANAL") %></td>					
					<td width="16%"><%=MessageResourceUtil.getTextDescription(request, "DUP_REASON") %></td>
					<td width="16%"><%=MessageResourceUtil.getTextDescription(request, "DUP_REJECT_DATE") %></td>
				</tr>				
		 <%
		 	int i =1;
			if(!ORIGUtility.isEmptyVector(dedupVect)){			
				for(PLXRulesDedupDataM xRulesDedupM :dedupVect){
		 %>												
					<tr class="ResultData">
						<td><%=HTMLRenderUtil.displayHTML(String.valueOf(i)) %></td>
						<td><%=HTMLRenderUtil.displayHTML((String) xRulesDedupM.getApplicationNo()) %></td>
						<td><%=HTMLRenderUtil.displayHTML(wfTool.GetMessageAppStatus(request, (String) xRulesDedupM.getApplicationStatus())) %></td>
						<td><%=HTMLRenderUtil.displayHTML(origCache.GetUserNameByuserID((String) xRulesDedupM.getLastOwner()))%></td>
						<td><%=DataFormatUtility.DateEnToStringDateTh(xRulesDedupM.getAppDate(),DataFormatUtility.DateFormatType.FORMAT_DDMMYYY_S_HHMMSS)%></td>
						<td><%=HTMLRenderUtil.displayHTML(origCache.GetListboxCacheDataM(50, "ALL_ALL_ALL", (String) xRulesDedupM.getChannel()).getThDesc()) %></td>
						<td><%=HTMLRenderUtil.displayHTML(HTMLRenderUtil.GetDisplayDuplicateReason(xRulesDedupM.getDecisionReason())) %></td>
						<td><%=DataFormatUtility.DateEnToStringDateTh(xRulesDedupM.getDecisionDate(),DataFormatUtility.DateFormatType.FORMAT_DDMMYYY_S_HHMMSS)%></td>
					</tr>													
				<%}%>
			<%i++;%>
		<%}else{%>
			<tr class="ResultNotFound">
				<td colspan="8" >No record found</td>
			</tr>
		<%}%>				
			</table>
		</div>
	</div>
</div>

<%	//set current screen to main Form
	com.eaf.j2ee.pattern.control.ScreenFlowManager screenFlowManager =
	(com.eaf.j2ee.pattern.control.ScreenFlowManager) request.getSession(true).getAttribute("screenFlowManager");
	screenFlowManager.setCurrentScreen(PLOrigFormHandler.PL_MAIN_APPFORM_SCREEN);
%>