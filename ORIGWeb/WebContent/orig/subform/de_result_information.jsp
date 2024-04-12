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
	String displayMode = formUtil.getDisplayMode("DE_RESULT_SUBFORM", ORIGUser.getRoles(), applicationDataM.getJobState(), ORIGForm.getFormID(), searchType);	

//	String displayMode = ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT;
	ORIGUtility utility = new ORIGUtility();
	ORIGCacheUtil cacheUtil = ORIGCacheUtil.getInstance();
	
	Vector reasonVect = utility.getReasonByDesicion(OrigConstant.Decision.PENDING);
//	ApplicationDataM applicationDataM = ORIGForm.getAppForm();
	Vector deReason = utility.getReasonByRole(applicationDataM.getReasonVect(), OrigConstant.ROLE_DE);
%>
<table cellpadding="" cellspacing="0" width="100%" align="center">
	<tr>
		<td>
			<table cellpadding="" cellspacing="1" width="100%" align="center">
				<tr>
					<td class="text-header-action1" width="20%" rowspan="2"><%=MessageResourceUtil.getTextDescription(request, "DECISION") %> :</td>
					<td class="text-header-action1" width="80%">&nbsp;<%=ORIGDisplayFormatUtil.displayCheckBoxTagDesc(ORIGWFConstant.ApplicationDecision.DE_PENDING,applicationDataM.getDeDecision(),displayMode,"decision_de","onclick=\"clearReason('reason_de');deisabledReason('reason_de', 'decision_de');\"",MessageResourceUtil.getTextDescription(request, "PENDING")) %> </td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#cccccc"></td>
	</tr>
	<tr>
		<td>
			<table cellpadding="" cellspacing="1" width="100%" align="center">
				<tr>
					<td class="text-header-action1" width="20%"><%=MessageResourceUtil.getTextDescription(request, "REASON") %> :</td>
					<td class="text-header-action1" width="80%">&nbsp;
						<div id="ReasonID" class="inputformnew2" style="OVERFLOW: scroll;width: 400px; height: 200px;">
							<%
								if(reasonVect != null){
									ReasonProperties reasonProperties;
									ReasonDataM reasonDataM;
									String check;
									String reasonCode;
									for(int i=0; i<reasonVect.size(); i++){
										check = "";
										reasonProperties = (ReasonProperties) reasonVect.get(i);
										reasonCode = reasonProperties.getCode();
										for(int j=0; j<deReason.size(); j++){
											reasonDataM  = (ReasonDataM) deReason.get(j);
											if(reasonCode.equals(reasonDataM.getReasonCode())){
												check = reasonCode;
												break;
											}
										}
							%>
											&nbsp;<%=ORIGDisplayFormatUtil.displayCheckBoxTagDesc(reasonProperties.getREASON(),check,displayMode,"reason_de", "",reasonProperties.getTHDESC()) %><br>
							<%
									}
								}
							%>
						</div></td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<script language="JavaScript">
function clearReason(reasonField){
	//alert(document.getElementById(reasonField).name);
	var obj = eval("appFormName."+reasonField);
	if(obj!=null){
		if(!isUndefined(obj.length)){
			for(var i=0; i<obj.length; i++){
				obj[i].checked = false;
			}
		}
	}
}
deisabledReason('reason_de', 'decision_de');
</script>