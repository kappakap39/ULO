<%@page import="com.eaf.orig.shared.constant.OrigConstant"%>
<%@page import="com.eaf.orig.shared.utility.ORIGFormUtil"%>
<%@page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil"%>
<%@page import="com.eaf.orig.ulo.pl.app.utility.PLMessageResourceUtil"%>
<%@page import="com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesFUVerificationDataM"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>
<%@page import="com.eaf.orig.ulo.pl.app.utility.DataFormatUtility"%>
<%@page import="com.eaf.orig.shared.utility.ORIGUtility"%>
<%@page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@page import="java.util.Vector"%>
<%@page import="com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesVerificationResultDataM"%>
<%@page import="com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM"%>
<%@page import="com.eaf.orig.ulo.pl.model.app.PLApplicationDataM"%>
<%@page import="com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler"%>
<%@page import="com.eaf.orig.ulo.pl.model.app.PLSaleInfoDataM"%>
<jsp:useBean id="searchType" scope="session" class="java.lang.String"/>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="PLORIGForm" scope="session" class="com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler"/>
<script type="text/javascript" src="orig/js/popup/pl/followDetailPopup.js"></script>

<%
	ORIGCacheUtil origCache = ORIGCacheUtil.getInstance();
	Logger logger = Logger.getLogger(this.getClass());	
	PLApplicationDataM appM = PLORIGForm.getAppForm();	
	if(null == appM) appM = new PLApplicationDataM();
	Vector vUser = (Vector)origCache.loadUserNameCache(OrigConstant.ACTIVE_FLAG);
	
	PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);		
	PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();	
	if(null == xrulesVerM) xrulesVerM = new PLXRulesVerificationResultDataM();
	Vector<PLXRulesFUVerificationDataM> xRulesFUVerVect = xrulesVerM.getxRulesFUVerificationDataMs();
	
	PLSaleInfoDataM saleInfoM = appM.getSaleInfo();
	if(null == saleInfoM){
		saleInfoM = new PLSaleInfoDataM();
	}
	
	ORIGFormUtil formUtil = new ORIGFormUtil();
	String displayMode = formUtil.getDisplayMode("VERIFICATION_SUBFORM", ORIGUser.getRoles(), appM.getJobState(), PLORIGForm.getFormID(), searchType);
	logger.debug("DisplayMode >> "+displayMode);
%>
<div class="PanelFirst">
	<div class="PanelSecond TextHeaderNormal">
		<div class="PanelThird">
			<table class="FormFrame" id="followDetail">
				<tr>
					<td class="textR" nowrap="nowrap" width="20%"><%=PLMessageResourceUtil.getTextDescription(request, "FOLLOW_PERSONAL")%><div class="mandatory-box">*</div>:</td>
					<td class="textL" width="15%" id="element_personalType"><%=HTMLRenderUtil.displaySelectTagScriptActionBusClassFieldID(appM.getBusinessClassId(),83,"","fdPersonalType",displayMode," onchange=\"javascript:initFollowDetail();\" ") %></td>													
					<td class="textR" nowrap="nowrap" width="15%"><%=PLMessageResourceUtil.getTextDescription(request, "FOLLOW_SURNAME") %>:</td>
					<td class="textL" width="15%" id="element_name"></td>
					<td class="textR" nowrap="nowrap" width="15%"><%=PLMessageResourceUtil.getTextDescription(request, "FOLLOW_ADDRESS_TYPE") %>:</td>
					<td class="textL" width="20%" id="element_addressType"><%= HTMLRenderUtil.displaySelectTagScriptAction_ORIG(new Vector(),"","fdAddressType",displayMode," onchange=\"javascript:loadTelephone();\" ") %></td>
				</tr>
				<tr>
					<td class="textR" nowrap="nowrap" width="20%"><%=PLMessageResourceUtil.getTextDescription(request, "FOLLOW_TEL") %><div class="mandatory-box">*</div>:</td>
					<td class="textL" width="15%" id="element_phoneNo"><%= HTMLRenderUtil.displaySelectTagScriptAction_ORIG(new Vector(),"","fdPhoneNo",displayMode," onchange=\"javascript:otherTelNumber();\" ") %></td>
					<td class="textColS" nowrap="nowrap" align="right" width="15%">&nbsp;<%=PLMessageResourceUtil.getTextDescription(request, "FOLLOW_OTHER_PHONENO") %>:</td>
					<td class="inputCol" width="15%" id="element_OtherPhoneNo"><%=HTMLRenderUtil.displayInputTagScriptAction("",HTMLRenderUtil.DISPLAY_MODE_VIEW,"","fdOtherPhoneNo","textbox-tel-view","","10") %></td>					
					<td class="textR" nowrap="nowrap" width="15%"><%=PLMessageResourceUtil.getTextDescription(request, "FOLLOW_TEL_STATUS") %><div class="mandatory-box">*</div>:</td>
					<td class="textL" width="20%" id="element_phoneStatus"><%=HTMLRenderUtil.displaySelectTagScriptActionBusClassFieldID(appM.getBusinessClassId(),68,"","fdPhoneStatus",displayMode,"") %></td>
				</tr>
				<tr>
					<td class="textR" nowrap="nowrap" width="20%"><%=PLMessageResourceUtil.getTextDescription(request, "FOLLOW_REMARK") %>:</td>
					<td class="textL" width="15%" id="element_remark" ><%=HTMLRenderUtil.displayInputTagScriptAction("",displayMode,"","fdRemark","textbox","","250") %></td>
					<td class="textColS"></td>					
					<td class="textL"> <%=ORIGDisplayFormatUtil.displayButtonTagScriptAction("Add", displayMode,"button","addButton", "button", "onclick=\"addFollowDetailData();\"", "") %></td>
					<td class="textR"></td>
					<td class="textL"></td>
				</tr>
			</table>			
			<table class="TableFrame" id="TableFrame">
				<tr class="Header">
					<td width="3%"><%=PLMessageResourceUtil.getTextDescription(request, "NO") %></td>
					<td width="15%"><%=PLMessageResourceUtil.getTextDescription(request, "FOLLOW_CALL_TIME")%></td>
					<td width="17%"><%=PLMessageResourceUtil.getTextDescription(request, "FOLLOW_TEL_STATUS") %></td>
					<td width="10%"><%=PLMessageResourceUtil.getTextDescription(request, "FOLLOW_PERSONAL") %></td>
					<td width="15%"><%=PLMessageResourceUtil.getTextDescription(request, "FOLLOW_ADDRESS_TYPE") %></td>
					<td width="15%"><%=PLMessageResourceUtil.getTextDescription(request, "FOLLOW_PHONE_MAIL") %></td>					
					<td width="15%"><%=PLMessageResourceUtil.getTextDescription(request, "FOLLOW_REMARK") %></td>
					<td width="15%"><%=PLMessageResourceUtil.getTextDescription(request, "FOLLOW_USER") %></td>
				</tr>
					<%				
						if(!ORIGUtility.isEmptyVector(xRulesFUVerVect)){
							for(PLXRulesFUVerificationDataM fuVerifyM : xRulesFUVerVect){
							String status = "";
							if(OrigConstant.EmailSMS.CONTACT_TYPE_EMAIL.equals(fuVerifyM.getPhoneVerStatus())){
								status = PLMessageResourceUtil.getTextDescription(request, "FOLLOW_STATUS_EMAIL");
							}else if(OrigConstant.EmailSMS.CONTACT_TYPE_SMS.equals(fuVerifyM.getPhoneVerStatus())){
								status = PLMessageResourceUtil.getTextDescription(request, "FOLLOW_STATUS_SMS");
							}else{
								status = HTMLRenderUtil.displayHTMLFieldIDDesc(fuVerifyM.getPhoneVerStatus(),68);
							}
					%>
					 <tr class="ResultData">
						<td width="3%"><%=String.valueOf(fuVerifyM.getSeq())%></td>
						<td width="15%"><%=DataFormatUtility.DateEnToStringDateTh(fuVerifyM.getCreateDate(), DataFormatUtility.DateFormatType.FORMAT_DDMMYYY_S_HHMMSS24) %></td>
						<td width="17%"><%=status%></td>
						<td width="10%"><%=HTMLRenderUtil.displayHTMLFieldIDDesc(fuVerifyM.getContactType(),83) %></td>
						<td width="15%"><%=HTMLRenderUtil.displayHTMLFieldIDDesc(fuVerifyM.getAddressType(),12) %></td>
						<td width="15%"><%=HTMLRenderUtil.displayHTML(fuVerifyM.getPhoneNo()) %></td>					
						<td width="15%"><%=HTMLRenderUtil.displayHTML(fuVerifyM.getRemark()) %></td>
						<td width="15%"><%=HTMLRenderUtil.displayHTML(HTMLRenderUtil.displayThFullName(fuVerifyM.getCreateBy(), vUser)) %></td>
					</tr>
					<% }%>												
				<%}else{ %>
				<tr class="ResultNotFound" id="FollowDetailNotFound">
					<td colspan="8">No record found</td>
				</tr>
				<% }%>
			</table>
		</div>
	</div>
</div>
<%	//set current screen to main Form
	com.eaf.j2ee.pattern.control.ScreenFlowManager screenFlowManager =
	(com.eaf.j2ee.pattern.control.ScreenFlowManager) request.getSession(true).getAttribute("screenFlowManager");
	screenFlowManager.setCurrentScreen(PLOrigFormHandler.PL_MAIN_APPFORM_SCREEN);
%>