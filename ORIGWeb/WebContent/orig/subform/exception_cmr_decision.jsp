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
	String displayMode = formUtil.getDisplayMode("CMR_DECISION_SUBFORM", ORIGUser.getRoles(), applicationDataM.getJobState(), ORIGForm.getFormID(), searchType);	

	ORIGUtility utility = new ORIGUtility();
//	ApplicationDataM applicationDataM = ORIGForm.getAppForm();
	Vector xcmrReason = utility.getReasonByRole(applicationDataM.getReasonVect(), OrigConstant.ROLE_XCMR);
	String reason = "";
	String reasonDesc = "";
	ReasonDataM reasonDataM;
	if(xcmrReason != null && xcmrReason.size() >0){
		reasonDataM = (ReasonDataM) xcmrReason.get(0);
		reason = reasonDataM.getReasonCode();
		reasonDesc = cacheUtil.getORIGCacheDisplayNameDataMByType(reason, "CMPREASON");
	}
	String sDate = null;
	if(applicationDataM.getXcmrOverrideDate()==null){
		sDate="";
	}else{
		sDate=ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(applicationDataM.getXcmrOverrideDate()));
	}
	
	String userName = ORIGUser.getUserName();
	String currentDate = ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(new Date(System.currentTimeMillis())));
%>
<table cellpadding="" cellspacing="1" width="100%" align="center">
	<tr>
		<td>
			<table cellpadding="" cellspacing="1" width="100%" align="center">
				<tr>
					<td class="textColS"></td>
					<td class="inputCol" colspan="4"><%=ORIGDisplayFormatUtil.displayRadioTagScriptAction(ORIGWFConstant.ApplicationDecision.OVERRIDE_CAMPAIGN,displayMode,"cmr_decision",applicationDataM.getXcmrDecision(),"","","onClick=\"javascript:setOverrid();deisabledReasonXCMR('cmr_decision');\"") %> <%=MessageResourceUtil.getTextDescription(request, "OVERRIDE") %> </td>
				</tr>
				<tr>
					<td class="textColS"></td>
					<td class="inputCol" colspan="4"><%=ORIGDisplayFormatUtil.displayRadioTagScriptAction(ORIGWFConstant.ApplicationDecision.NOT_OVERRIDE_CAMPAIGN_TO_DE,displayMode,"cmr_decision",applicationDataM.getXcmrDecision(),"","","onClick=\"javascript:setOverrid();deisabledReasonXCMR('cmr_decision');\"") %> <%=MessageResourceUtil.getTextDescription(request, "NOT_OVERRIDE_REWORK") %> </td>
				</tr>
				<tr>
					<td class="textColS"></td>
					<td class="inputCol" colspan="4"><%=ORIGDisplayFormatUtil.displayRadioTagScriptAction(ORIGWFConstant.ApplicationDecision.NOT_OVERRIDE_CAMPAIGN_TO_UW,displayMode,"cmr_decision",applicationDataM.getXcmrDecision(),"","","onClick=\"javascript:setOverrid();deisabledReasonXCMR('cmr_decision');\"") %> <%=MessageResourceUtil.getTextDescription(request, "NOT_OVERRIDE_UW") %> </td>
				</tr>
				<tr>
					<td class="textColS" width="20%"><%=MessageResourceUtil.getTextDescription(request, "DECISION_BY") %> :</td>
					<td class="inputCol" width="20%"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(applicationDataM.getXcmrOverrideBy(),displayMode,"","decision_by","textboxReadOnly","readOnly","50") %></td>
					<td class="textColS" width="10%"><%=MessageResourceUtil.getTextDescription(request, "DATE") %> :</td>
					<td class="inputCol" width="50%"><%=ORIGDisplayFormatUtil.displayInputTagJsDate("appFormName",sDate,displayMode,"","decision_date","textboxReadOnly","right","readOnly") %></td>
				</tr>
				<tr>
					<td class="textColS" id="reasonXCMR1" style="display:"><%=MessageResourceUtil.getTextDescription(request, "REASON_CAMPAIGN") %><Font color="#ff0000">*</Font> :</td>
					<td class="inputCol" colspan="3" id="reasonXCMR2" style="display:"><%=ORIGDisplayFormatUtil.displayPopUpTagScriptAction(reason,displayMode,"5","cmr_reason","72","cmr_reason_desc","textbox","","30","...","button_text","LoadReasonCampaign",reasonDesc,"CMPREASON") %></td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<script language="JavaScript">
function setOverrid(){
	form = document.appFormName;
	form.decision_by.value = '<%=userName%>';
	form.decision_date.value = '<%=currentDate%>';
	//setReasonForXCMR();
}
if(document.appFormName.cmr_reason != null){
	<%if(reason != null && !("").equals(reason)){%>
		setReasonForXCMR();
	<%}%>
}
function setReasonForXCMR(){
	var reasonObj = document.getElementById("reasonXCMR1");
	reasonObj.style.display = "";
	reasonObj = document.getElementById("reasonXCMR2");
	reasonObj.style.display = "";
	form = document.appFormName;
	form.cmr_reason.disable = false;
	form.cmr_reasonPopup.disable = false;
}
function deisabledReasonXCMR(decisionField){
	var form = document.appFormName;
	var decision = eval("appFormName."+decisionField);
	if(!isUndefined(decision.length)){
		if (decision.length > 0) {
			for (var i = 0; i < decision.length ; i++) {
				if(!decision[0].checked && !decision[1].checked && !decision[2].checked){
					if(form.cmr_reason!=null){
						form.cmr_reason.disabled = true;
						form.cmr_reasonPopup.disabled = true;
					}
				}else{
					form.cmr_reason.disabled = false;
					form.cmr_reasonPopup.disabled = false;
				}
			}
		}
	}
}
deisabledReasonXCMR('cmr_decision');
</script>