<%@ page import="java.util.*" %>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>
<%@ page import="com.eaf.orig.shared.model.ApplicationDataM" %>
<%@ page import="com.eaf.orig.wf.shared.ORIGWFConstant" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>
<%@ page import="com.eaf.orig.cache.properties.ReasonProperties" %>
<%@ page import="com.eaf.orig.shared.model.ReasonDataM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGFormUtil"%>


<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.formcontrol.view.form.ORIGFormHandler"/>

<% 
	ApplicationDataM applicationDataM = ORIGForm.getAppForm();
	if(applicationDataM == null){
		applicationDataM = new ApplicationDataM();
	}
	ORIGFormUtil formUtil = new ORIGFormUtil();
	String searchType = (String) request.getSession().getAttribute("searchType");
	String displayMode = formUtil.getDisplayMode("PD_RESULT_SUBFORM", ORIGUser.getRoles(), applicationDataM.getJobState(), ORIGForm.getFormID(), searchType);	

//	String displayMode = ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT;
	ORIGUtility utility = new ORIGUtility();
	ORIGCacheUtil cacheUtil = ORIGCacheUtil.getInstance();
	
	Vector reasonVect = utility.loadCacheByName("Reason");
//	ApplicationDataM applicationDataM = ORIGForm.getAppForm();
	Vector uwReason = utility.getReasonByRole(applicationDataM.getReasonVect(), OrigConstant.ROLE_PD);
%>
<table cellpadding="" cellspacing="0" width="100%" align="center">
	<tr>
		<td colspan="2">
			<table cellpadding="" cellspacing="1" width="100%" align="center">
				<tr>
					<td class="text-header-action1" width="20%" rowspan="2"><%=MessageResourceUtil.getTextDescription(request, "DECISION") %> :</td>
					<td class="text-header-action1" width="80%">&nbsp;<%=ORIGDisplayFormatUtil.displayRadioTagScriptAction(ORIGWFConstant.ApplicationDecision.WITHDRAW,displayMode,"decision_pd",applicationDataM.getPdDecision(),"","","onClick=\"javascript:checkradio(this);\" ") %> <%=MessageResourceUtil.getTextDescription(request, "WITHDRAW") %> </td>
				</tr>
				<tr>
					<td class="text-header-action1">&nbsp;<%=ORIGDisplayFormatUtil.displayRadioTagScriptAction(ORIGWFConstant.ApplicationDecision.SEND_BACK_TO_DE,displayMode,"decision_pd",applicationDataM.getPdDecision(),"","","onClick=\"javascript:checkradio(this);\" ") %> <%=MessageResourceUtil.getTextDescription(request, "TO_DE_REWORK") %> </td>
				</tr>
				<tr>
					<td class="text-header-action1"><%=MessageResourceUtil.getTextDescription(request, "REASON") %> :</td>
					<td class="text-header-action1">&nbsp;<%=ORIGDisplayFormatUtil.displayInputTextAreaTag(ORIGDisplayFormatUtil.displayHTML(applicationDataM.getPdDecisionReason()),"pd_reason","5","50",displayMode,"onKeyDown=\"textCounter(this,1000)\" onKeyUp=\"textCounter(this,1000)\" ") %></td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#cccccc" colspan="2"></td>
	</tr>
	<!-- <tr>
		<td>
			<table cellpadding="" cellspacing="1" width="100%" align="center">
				<tr>
					<td class="textColS" width="20%"><%//=MessageResourceUtil.getTextDescription(request, "REASON") %> :</td>
					<td class="inputCol" width="80%">&nbsp;<div id="ReasonID" style="OVERFLOW: scroll;width: 300px; height: 100px; background-color: white;">
							<%
								/*if(reasonVect != null){
									ReasonProperties reasonProperties;
									ReasonDataM reasonDataM;
									String check;
									String reasonCode;
									for(int i=0; i<reasonVect.size(); i++){
										check = "";
										reasonProperties = (ReasonProperties) reasonVect.get(i);
										reasonCode = reasonProperties.getCode();
										for(int j=0; j<uwReason.size(); j++){
											reasonDataM  = (ReasonDataM) uwReason.get(j);
											if(reasonCode.equals(reasonDataM.getReasonCode())){
												check = reasonCode;
												break;
											}
										}*/
							%>
											&nbsp;<%//=ORIGDisplayFormatUtil.displayCheckBoxTag(reasonProperties.getREASON(),check,displayMode,"reason_pd", "",reasonProperties.getTHDESC()) %><br>
							<%
									/*}
								}*/
							%>
						</div></td>
				</tr>
			</table>
		</td>
	</tr-->
</table>
<script language="javaScript">
 <%
   if(ORIGWFConstant.ApplicationDecision.WITHDRAW.equals(applicationDataM.getPdDecision())){
   %>
   checkradio(document.appFormName.decision_pd[0]);   
   <%
   }else if (ORIGWFConstant.ApplicationDecision.SEND_BACK_TO_DE.equals(applicationDataM.getPdDecision())){
   %>
   checkradio(document.appFormName.decision_pd[1]);
   <%
   }
 %>
</script>
