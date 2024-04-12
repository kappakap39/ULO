<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.ORIGLogic"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.DataFormatUtility"%>
<%@ page import="org.apache.log4j.Logger"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGFormUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLApplicationDataM"%>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM"%>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant"%>
<%@ page import="com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesVerificationResultDataM"%>
<%@ page import="com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesDebtBdDataM"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.PLMessageResourceUtil"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="PLORIGForm" scope="session" class="com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler"/>

<% 
	Logger log = Logger.getLogger(this.getClass());
	
// 	TooltipResourceUtil tooltipUtil=TooltipResourceUtil.getInstance();	
// 	DecimalFormat changeFormat = new DecimalFormat("#,##0.00");
	
	PLApplicationDataM applicationM = PLORIGForm.getAppForm();
	
// 	log.debug("@@@@@ Popup Rule Details of application no :"+plapplicationDataM.getApplicationNo());	
	
	PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(OrigConstant.PERSONAL_TYPE_APPLICANT);
	
	PLXRulesVerificationResultDataM verResult = personalM.getXrulesVerification();	
	if(verResult == null){ 
		verResult = new PLXRulesVerificationResultDataM();	
	}
	
// 	String contextPath=request.getContextPath(); 
// 	int count=1;
// 	PLXRulesDebtBdDataM debtDBDataM = verResult.getxRulesDebtBdDataM();
// 	if(debtDBDataM == null) debtDBDataM = new PLXRulesDebtBdDataM();
// 	String debtPercent  = "";
// 	if(debtDBDataM.getDebtBurdentScore() != null) debtPercent = String.valueOf(changeFormat.format(debtDBDataM.getDebtBurdentScore().doubleValue()));
// 	String debtBDResult = ORIGCacheUtil.getInstance().getORIGCacheDisplayNameDataM(OrigConstant.fieldId.RESULT_XRULES, verResult.getDebtBdCode()) + debtPercent;

%>

<table class="TableFrame">
	<tr class="Header">
		<td width="20%">
			<%=PLMessageResourceUtil.getTextDescriptionMessage(request, "POLICY_RULE")%>
		</td>
		<td width="20%">
			<%=PLMessageResourceUtil.getTextDescriptionMessage(request, "DEBT_BURDEN")%>
		</td>
		<td width="20%">
			<%=PLMessageResourceUtil.getTextDescriptionMessage(request, "B_SCORE")%>
		</td>
		<td width="20%">
			<%=PLMessageResourceUtil.getTextDescriptionMessage(request, "FICO_SCORE")%>
		</td>
		<td width="20%">
			<%=PLMessageResourceUtil.getTextDescriptionMessage(request, "APP_SCORE")%> 
		</td>
	</tr>
	<tr class="ResultData">
		<%String SummaryOverideRuleClassStyle = ORIGLogic.LogicColorStyleResultDiv(verResult.getSummaryOverideRuleCode(),verResult.getSummaryOverideRuleResult());%>
		<td width="18%" class="<%=SummaryOverideRuleClassStyle %>" id="tr-overiderules">
			<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
				<tr>
					<td id="ca_overriderule"><%=HTMLRenderUtil.displayHTML(verResult.getSummaryOverideRuleResult())%></td>
					<td width="10" align="right" valign="bottom">
						<img src="images/search.png" class="imgButton" name="policyRulePopup" onclick="javascript:popupRuleDetails()">
					</td>
				</tr>
			</table>			
		</td>
		<td width="18%" class="<%=ORIGLogic.LogicColorStyleResultDiv(verResult.getDebtBdCode(),verResult.getDEBT_BDResult()) %>"  id="tr-debtburden">
			<div id="ca_debt_burden"><%=HTMLRenderUtil.DisplayReplaceLineWithNull(verResult.getDEBT_BDResult())%></div>
		</td>
		<td width="18%" class="<%=ORIGLogic.LogicColorStyleResultDiv(verResult.getbScoreCode(),verResult.getbScoreResult())%>"  id="tr-bscore">
			<div id="ca_b_score"><%= HTMLRenderUtil.DisplayReplaceLineWithNull(verResult.getbScoreResult())%></div>
		</td>
		<td width="18%" class="<%=ORIGLogic.LogicColorStyleResultDiv(verResult.getFicoCode(),verResult.getFicoResult())%>"  id="tr-ficoscore">
			<div id="ca_fico_score"><%= HTMLRenderUtil.DisplayReplaceLineWithNull(verResult.getFicoResult())%></div>
		</td>
		<td width="28%" class="<%=ORIGLogic.LogicColorStyleResultDiv(verResult.getAppScoreResultCode(),verResult.getAppScoreResult())%>"  id="tr-appscore">
			<div id="ca_app_score"><%= HTMLRenderUtil.DisplayReplaceLineWithNull(verResult.getAppScoreResult())%></div>
		</td>
	</tr>									
</table>
<%=HTMLRenderUtil.displayHiddenField(OrigConstant.SUMMARY_RULE_DISPLAY,OrigConstant.SUMMARY_RULE_DISPLAY)%>
<script>
function reCalDecisionILog(){
	//alert('reCalDecisionILog');
	var servletName = "RecalculateDebtBurden";
	var dataString = $("#avale-obj-form *").serialize();
	//document.appFormName.decisionRefresh.disabled = true;
	//ajaxDisplayFieldValue(servletName,dataString);
	ajaxDisplayElementJson(servletName,"3",dataString);
	var orResult = $('#ca_overriderule').html();
	if(orResult == RESULT_DESC_OVERRIDE){
		$('#tr-overiderules').attr('class',COLOR_STYLE_YELLOW);
	}else if(orResult == RESULT_DESC_PASS){
		$('#tr-overiderules').attr('class',COLOR_STYLE_GREEN);
	}else if(orResult == RESULT_DESC_FAIL){
		$('#tr-overiderules').attr('class',COLOR_STYLE_RED);
	}
	var debtResult = $('#ca_debt_burden').html();
	if(debtResult.indexOf(RESULT_DESC_OVERRIDE) >-1){
		$('#tr-debtburden').attr('class',COLOR_STYLE_YELLOW);
	}else if(debtResult.indexOf(RESULT_DESC_PASS) > -1){
		$('#tr-debtburden').attr('class',COLOR_STYLE_GREEN);
	}else if(debtResult.indexOf(RESULT_DESC_FAIL) >-1){
		$('#tr-debtburden').attr('class',COLOR_STYLE_RED);
	}
}

function popupRuleDetails(){
	//var url = "<%=request.getContextPath()%>/orig/popup/pl/rulesDetailsPopup.jsp";
	//openDialog(url, 800, 400,scrollbars=0, setPrefs);
	OpenRuleDetailsPopupDialog('RuleDetailsPopup',800,400,'Rules Details');
}

function OpenRuleDetailsPopupDialog(action,width,higth,title) {
	var url = "/ORIGWeb/FrontController?action="+action;	
	$dialog = $dialogEmpty;		
	$(".plan-dialog").remove();	
	$dialog = $('<div class="plan-dialog" id="plan-dialog"/>').load(url);
	$dialog.dialog({
		    resizable : false,
		    modal : true,
		    autoOpen: false,
		    draggable: true,
		    width: width,
		    height: $('.frame-module').height()-71,
		    title:title,
		    position: { 
				        my: 'center',
				        at: 'center',
				        of: $('.frame-module')
		    		},
			buttons : {
			        "Close" : function() {						        	
			        	$dialog.dialog("close");	
			        	closeDialog();
			        }
		    	},
		    close: function(){
		    	closeDialog();
		    }
	    });
	$dialog.dialog('open');	
}
</script>
