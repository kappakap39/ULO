<%@page import="java.math.BigDecimal"%>
<%@ page import="com.eaf.orig.shared.util.OrigUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.PLOrigUtility"%>
<%@ page import="com.eaf.orig.cache.properties.ORIGCacheDataM"%>
<%@ page import="com.eaf.orig.ulo.pl.constant.WorkflowConstant"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.PLMessageResourceUtil" %>
<%@ page import="org.apache.log4j.Logger"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGFormUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLApplicationDataM" %>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM" %>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>
<%@ page import="java.util.Vector" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>
<%@ page import="com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesVerificationResultDataM" %>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.DataFormatUtility" %>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>
<%@ page import="com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesDebtBdDataM" %>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.PLOrigApplicationUtil" %>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="PLORIGForm" scope="session" class="com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler"/>

<script type="text/javascript" src="orig/js/subform/pl/ca_decision.js" ></script>

<% 
	PLOrigApplicationUtil appUtil = new PLOrigApplicationUtil();
	Logger log = Logger.getLogger(this.getClass());
	
	PLApplicationDataM applicationM = PLORIGForm.getAppForm();
	
	String role = ORIGUser.getCurrentRole();
	Vector caDecistioVt = null;
	
	ORIGFormUtil formUtil = new ORIGFormUtil();
	ORIGCacheUtil origc = ORIGCacheUtil.getInstance();
	
	String searchType = (String) request.getSession().getAttribute("searchType");
	String displayMode = formUtil.getDisplayMode("CA_DECISION_SUBFORM", ORIGUser.getRoles(), applicationM.getJobState(), PLORIGForm.getFormID(), searchType);
	
	String appDecision="";
	
	PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(OrigConstant.PERSONAL_TYPE_APPLICANT);
	PLXRulesVerificationResultDataM verificationM = personalM.getXrulesVerification();
	if(null == verificationM) verificationM = new PLXRulesVerificationResultDataM();
	
	log.debug("exceptionCredit:" + applicationM.getExceptionCreditLine());
	
	String decision = null;
	
	String finalCreditMode = displayMode;
	if(OrigConstant.recommendResult.REJECT.equals(verificationM.getRecommendResult())){
		finalCreditMode = HTMLRenderUtil.DISPLAY_MODE_VIEW;
	}
		
	//if final credit line is null, default to recommend credit line
	if((applicationM.getFinalCreditLimit() == null || applicationM.getFinalCreditLimit().doubleValue() == 0) 
		&& !OrigConstant.recommendResult.REJECT.equals(verificationM.getRecommendResult())){
		applicationM.setFinalCreditLimit(applicationM.getRecommentCreditLine());
	}
	//#Vikrom  20121126
	String decisionRole = null;
	if(!OrigUtil.isEmptyString(applicationM.getJobState()) && OrigConstant.roleJobState.CA_I_SUP.equals(applicationM.getJobState())){
		decisionRole = OrigConstant.ROLE_CA_SUP;
		caDecistioVt= origc.GetDecision(OrigConstant.ROLE_CA_SUP, applicationM.getBusinessClassId());
	} else {
		decisionRole = OrigConstant.ROLE_CA;
		caDecistioVt= origc.GetDecision(role, applicationM.getBusinessClassId());
	}
	BigDecimal minCreditLine = appUtil.getMinFinalCreditLine(applicationM);
%>
<%=HTMLRenderUtil.displayHiddenField(applicationM.getDocListResult(), "ca_doc_list_result") %>
<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
	<tr>
		<td colspan="4"><jsp:include page="summary_rule_result.jsp" flush="true" /></td>
	</tr>
	<tr height="10"><td colspan="4"><div id="ca_decision_error_message" align="left"></div></td></tr>
	<tr> 			 
		<td colspan="4">
			<table class="FormFrame" id="decisionResultTable">
			<%if(OrigConstant.ROLE_CA.equals(decisionRole)) { %>
				<tr>
					<td width="20%" class="textBoldGreenR" nowrap="nowrap"><%=PLMessageResourceUtil.getTextDescriptionMessage(request, "RECOMMEND_RESULT")%>&nbsp;:&nbsp;</td>
					<td width="25%" class="textL" nowrap="nowrap">
						<div id="ca_recommend_result" align="right" class="textL"><%= HTMLRenderUtil.displayHTML(origc.getORIGCacheDisplayNameDataM(OrigConstant.fieldId.RECOMMEND_RESULT, verificationM.getRecommendResult()))%></div>
					</td>
					<td width="25%" class="textBoldGreenR" nowrap="nowrap"><%=PLMessageResourceUtil.getTextDescriptionMessage(request, "SCORING_CREDIT_LINE")%>&nbsp;:&nbsp;</td>
					<td width="30%" class="textL" nowrap="nowrap">
						<font color="black"><%= DataFormatUtility.displayCommaNumberNullOrZeroToSpecify(applicationM.getScoringCreditLine(),"0.00")%></font>
					</td>
				</tr>
				<tr>
					<td class="textBoldGreenR" nowrap="nowrap"><%=PLMessageResourceUtil.getTextDescriptionMessage(request, "RECOMMEND_CREDIT_LINE")%>&nbsp;:&nbsp;</td>
					<td class="textL" nowrap="nowrap"><font color="black"><%= DataFormatUtility.displayCommaNumberNullOrZeroToSpecify(applicationM.getRecommentCreditLine(),"0.00")%></font></td>
					<td class="textBoldGreenR" nowrap="nowrap"></td>
					<td class="textL" nowrap="nowrap"></td>
				</tr>
				<tr>
					<td class="textR" nowrap="nowrap">
						<%=PLMessageResourceUtil.getTextDescription(request, "FINAL_CREDIT_LINE",ORIGUser,personalM.getCustomerType(),"CA_DECISION_SUBFORM","ca_final_credit") %><%=HTMLRenderUtil.getMandatory_ORIG(personalM.getCustomerType(),ORIGUser.getRoles(),"CA_DECISION_SUBFORM","ca_final_credit")%>&nbsp;:&nbsp;
					</td>
					<td class="textL" nowrap="nowrap">
						<%=HTMLRenderUtil.displayInputTagScriptAction(DataFormatUtility.displayCommaNumberNullOrZeroToSpecify(applicationM.getFinalCreditLimit(),"0.00"),finalCreditMode,"","ca_final_credit","textbox","","20") %>
					</td>
					<td class="textR" nowrap="nowrap"></td>
					<td class="textL" nowrap="nowrap"></td>
				</tr>
				<tr>
					<td class="textR" nowrap="nowrap">
						<%=PLMessageResourceUtil.getTextDescriptionMessage(request, "REASON")%>&nbsp;:&nbsp;
					</td>
					<td class="textL" nowrap="nowrap">
						<div id="div_final_credit_reason"><%=HTMLRenderUtil.displayHiddenField(applicationM.getFinalCreditLimitReason(), "final_credit_reason") %></div>
					</td>
					<td class="textL" nowrap="nowrap"><%=HTMLRenderUtil.displayInputTagScriptAction(applicationM.getFinalCreditLimitRemark(),displayMode,"","ca_final_credit_remark","textbox","","250") %></td>
					<td class="textL" nowrap="nowrap"></td>
				</tr>
			<%}%>
				<tr>
					<td class="textR" nowrap="nowrap" valign="top">
						<%=PLMessageResourceUtil.getTextDescription(request, "DECISION_RESULT",ORIGUser,personalM.getCustomerType(),"CA_DECISION_SUBFORM","decision_option") %><%=HTMLRenderUtil.getMandatory_ORIG(personalM.getCustomerType(),ORIGUser.getRoles(),"CA_DECISION_SUBFORM","decision_option")%>&nbsp;:&nbsp;
					</td>
					<td class="textL" nowrap="nowrap" colspan="3">
						<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
							<%if(caDecistioVt != null && caDecistioVt.size() > 0) {
								boolean checkShowRef = false;
							   	for(int i=0;i<caDecistioVt.size();i++){
								   	ORIGCacheDataM decisionM = (ORIGCacheDataM)caDecistioVt.get(i);
								   	if(HTMLRenderUtil.DISPLAY_MODE_VIEW.equals(displayMode)){
										decision = "";
									}else{	
								   		decision = PLOrigUtility.GetDecisionModelAppM(applicationM,decisionM);
									}   		
								   //add for ca increase/decrease sup #Vikrom 20121128
								   if(!WorkflowConstant.Action.EXCEPTION.equals(decisionM.getCode()) 
								   		&& (!OrigConstant.ROLE_CA_SUP.equals(decisionRole) || checkShowRef == true)){ 
							%>
							<tr>
							    <td class="textL" nowrap="nowrap" width="30%"><%=HTMLRenderUtil.DisplayRadioMandatory(decisionM,displayMode,"decision_option",decision," onClick=\"changeCADecision();\" ")%></td>
								<td class="textR" nowrap="nowrap" width="32.5%"></td>
								<td class="textL" nowrap="nowrap" width="37.5%"></td>
							</tr>
							<%}else{ 
								checkShowRef = true;
							%>
								<tr>
									<td class="textL" nowrap="nowrap"><%=HTMLRenderUtil.DisplayRadioMandatory(decisionM,displayMode,"decision_option",decision," onClick=\"changeCADecision();\" ")%></font></td>
									<td class="textR" nowrap="nowrap"><%=PLMessageResourceUtil.getTextDescriptionMessage(request, "DOCUMENT_REF_ID")%>&nbsp;:&nbsp;</td>
									<td class="textL" nowrap="nowrap"><%--=HTMLRenderUtil.displayInputTagScriptAction(plapplicationDataM.getPolicyExcDocNo(),displayMode,"","ca_decision_refference","textbox"," onChange=\"defultPolicyException(this);\" ","20") --%>
	                                 <%=HTMLRenderUtil.displayInputTagScriptAction(applicationM.getPolicyExcDocNo(),displayMode,"","ca_decision_refference","textbox"," onBlur=\"defultPolicyException(this);\" ","20") %>
									</td>
								</tr>
							<%}}}%>
						</table>
					</td>
				</tr>
				<tr>
					<td class="textR" nowrap="nowrap" valign="top">
						<%=PLMessageResourceUtil.getTextDescriptionMessage(request, "REASON")%><span id="span_mandate_reason" class="text-normal-red">*</span>&nbsp;:&nbsp;
					</td>
					<td colspan="2" bgcolor="white" align="left"><div style="width:100%;height:100px;overflow:auto;bgcolor:white;" id="div-decision-reason" ></div></td>
					<td class="textL" nowrap="nowrap">
						<%=HTMLRenderUtil.displayHiddenField(displayMode,"displayMode-decision") %> 
					</td>
				</tr>
				<tr>
					<td width="20%" nowrap="nowrap"></td>
					<td width="25%" nowrap="nowrap"></td>
					<td width="25%" nowrap="nowrap"></td>
					<td width="30%" nowrap="nowrap"></td>
				</tr>									
			</table>
			<%=HTMLRenderUtil.displayHiddenField(verificationM.getSummaryOverideRuleResult(),"ca_rec_result") %>
			<%=HTMLRenderUtil.displayHiddenField(DataFormatUtility.displayCommaNumberNullOrZeroToSpecify(applicationM.getFinalCreditLimit(),""),"tmp_final_credit") %>
			<%=HTMLRenderUtil.displayHiddenField(DataFormatUtility.displayCommaNumberNullOrZeroToSpecify(applicationM.getExceptionCreditLine(),"0.00"),"ca_exception_credit") %>
			<%=HTMLRenderUtil.displayHiddenField(DataFormatUtility.displayCommaNumberNullOrZeroToSpecify(applicationM.getRecommentCreditLine(),""),"ca_recommend_credit") %>
			<%=HTMLRenderUtil.displayHiddenField(applicationM.getCaDecision(),"default_decision") %>
			<%=HTMLRenderUtil.displayHiddenField(DataFormatUtility.displayCommaNumberNullOrZeroToSpecify(minCreditLine,"0.00"),"min_credit_line") %>
		</td>
		<td></td>
	</tr>
</table>
<%=HTMLRenderUtil.displayHiddenField(decisionRole, "decisionRole")%>
<%=HTMLRenderUtil.displayHiddenField(applicationM.getJobState(), "jobState")%>
<%=HTMLRenderUtil.displayHiddenField(displayMode,"displayMode-decision") %> 