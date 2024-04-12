<%@ page import="java.util.Vector" %>
<%@ page import="org.apache.log4j.Logger"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.PLMessageResourceUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.PLOrigUtility"%>
<%@ page import="com.eaf.orig.cache.properties.ORIGCacheDataM"%>
<%@ page import="com.eaf.orig.cache.properties.ListboxProperties"%>
<%@ page import="com.eaf.orig.ulo.pl.constant.WorkflowConstant"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGFormUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLApplicationDataM" %>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="PLORIGForm" scope="session" class="com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler"/>

<script type="text/javascript" src="orig/js/subform/pl/decision.js"></script>
<%   
	Logger log = Logger.getLogger(this.getClass());	
	PLApplicationDataM applicationM = PLORIGForm.getAppForm();
		if(null == applicationM) applicationM = new PLApplicationDataM();	
			
	PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(OrigConstant.PERSONAL_TYPE_APPLICANT);
	
	ORIGFormUtil formUtil = new ORIGFormUtil();
	ORIGCacheUtil cacheUtil = ORIGCacheUtil.getInstance();
	String searchType = (String) request.getSession().getAttribute("searchType");
	
	String displayMode = formUtil.getDisplayMode("DECISION_SUBFORM", ORIGUser.getRoles(), applicationM.getJobState(), PLORIGForm.getFormID(), searchType);
//	#septemwi comment not used
// 	PLOrigUtility origUtil = PLOrigUtility.getInstance();

	String role = ORIGUser.getCurrentRole();
	
	//for ca sup increase  #Vikrom  20121117
	if(OrigConstant.roleJobState.CA_I_SUP.equals(applicationM.getJobState())){
		role = OrigConstant.ROLE_CA_SUP;
	}
	
	String refDisplayMode = displayMode;
	if((role.indexOf("SUP") == -1) && (!OrigConstant.ROLE_PO.equals(role))){
		refDisplayMode = HTMLRenderUtil.DISPLAY_MODE_VIEW;
	}

	Vector<ORIGCacheDataM> decisionVect = cacheUtil.GetDecision(role , applicationM.getBusinessClassId());
	decisionVect = PLOrigUtility.filterDecision(applicationM,decisionVect,role); 
	String decision = null; 		
%>
<table class="FormFrame" id="table-decision-result">
	<%if(role.equals(OrigConstant.ROLE_PO) || (role.indexOf("SUP") != -1) ){
		%><tr>
			<td colspan="4"><jsp:include page="summary_rule_result.jsp" flush="true" /></td>
		</tr>
		<tr></tr>
	<%}%>
	<tr>
		<td width="20%" class="textR" nowrap="nowrap" valign="top">
			<%=PLMessageResourceUtil.getTextDescription(request, "DECISION",ORIGUser,personalM.getCustomerType(),"DECISION_SUBFORM","decision_option")%><%=HTMLRenderUtil.getMandatory_ORIG(personalM.getCustomerType(),ORIGUser.getRoles(),"DECISION_SUBFORM","decision_option")%>&nbsp;:&nbsp;
		</td>
		<td width="25%" class="textL" nowrap="nowrap" valign="top">
			<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
				<% if(!ORIGUtility.isEmptyVector(decisionVect)){			
						for(ORIGCacheDataM listBoxM : decisionVect){
							if(HTMLRenderUtil.DISPLAY_MODE_VIEW.equals(displayMode)){
								decision = "";
							}else{
								decision = PLOrigUtility.GetDecisionModelAppM(applicationM, listBoxM);
							}
				%>	
					<tr>
						<td class="inputCol" align="left">
							<%=HTMLRenderUtil.DisplayRadioMandatory(listBoxM, displayMode, "decision_option", decision, " onClick=\"GetDecisionReason(this);\" ")%>
						</td>
					</tr>
						<%}%>
				<%}%>
			</table>
		</td>
		<% if(OrigConstant.ROLE_FU.equals(role) || OrigConstant.ROLE_FU_SUP.equals(role)){ %>
			<td width="25%" class="textR" nowrap="nowrap" valign="top"></td>
			<td width="30%" class="textL" nowrap="nowrap" valign="top"></td>
		<%}else{%>
			<td width="25%" class="textR" nowrap="nowrap" valign="top">
				<%=PLMessageResourceUtil.getTextDescription(request, "DOCUMENT_REF_ID",ORIGUser,personalM.getCustomerType(),"DECISION_SUBFORM","decision_ref_no")%>
				<%=HTMLRenderUtil.getMandatory_ORIG(personalM.getCustomerType(),ORIGUser.getRoles(),"DECISION_SUBFORM","decision_ref_no")%>&nbsp;:&nbsp;
			</td>
			<td width="30%" class="textL" nowrap="nowrap" valign="top">
				<%=HTMLRenderUtil.displayInputTagScriptAction(applicationM.getPolicyExcDocNo(),refDisplayMode,"","decision_ref_no","textbox","","30") %>				
			</td>
		<%}%>
	</tr>
	<tr>
		<td class="textR" nowrap="nowrap" valign="top">
			<%=PLMessageResourceUtil.getTextDescriptionMessage(request, "REASON")%><span id="span_mandate_reason" class="text-normal-red">*</span>&nbsp;:&nbsp;
		</td>
		<td class="textL" nowrap="nowrap" colspan="2" width="130px">
			<div id="div-decision-reason"></div>
		</td>
		<td class="textL" nowrap="nowrap">
			<%=HTMLRenderUtil.displayHiddenField(displayMode,"displayMode-decision") %> 
		</td>
	</tr>
</table>

<%=HTMLRenderUtil.displayHiddenField("", "thDescDecision") %>
