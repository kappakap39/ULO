<%@page import="com.eaf.orig.shared.utility.ORIGFormUtil"%>
<%@page import="com.eaf.j2ee.pattern.util.MessageResourceUtil"%>
<%@page import="com.eaf.orig.shared.utility.ORIGCacheUtil"%>
<%@page import="com.eaf.orig.ulo.pl.app.utility.DataFormatUtility"%>
<%@page import="com.eaf.orig.shared.utility.ORIGUtility"%>
<%@page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@page import="com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler"%>
<%@page import="com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesHRVerificationDataM"%>
<%@page import="java.util.Vector"%>
<%@page import="com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesVerificationResultDataM"%>
<%@page import="com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM"%>
<%@page import="com.eaf.orig.ulo.pl.model.app.PLApplicationDataM"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<jsp:useBean id="searchType" scope="session" class="java.lang.String"/>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="PLORIGForm" scope="session" class="com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler"/>

<script type="text/javascript" src="orig/js/popup/pl/verifyHrPopup.js"></script>

<%
	Logger logger = Logger.getLogger(this.getClass());
	
	PLApplicationDataM appM = PLORIGForm.getAppForm();
	
	if(null == appM) appM = new PLApplicationDataM();
	
	PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
	
	PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();
	
	if(null == xrulesVerM){
		xrulesVerM = new PLXRulesVerificationResultDataM();
		personalM.setXrulesVerification(xrulesVerM);
	}
	
	Vector<PLXRulesHRVerificationDataM> xrulesVerHRVect = xrulesVerM.getxRulesHRVerificationDataMs();
	
	ORIGCacheUtil origCache = new ORIGCacheUtil();	
	
	ORIGFormUtil formUtil = new ORIGFormUtil();
	String displayMode = formUtil.getDisplayMode("VERIFICATION_SUBFORM", ORIGUser.getRoles(), appM.getJobState(), PLORIGForm.getFormID(), searchType);
	logger.debug("DisplayMode >> "+displayMode);
	
%>

<div id="div-verhr">
	<div class="PanelFirst">
		<div class="PanelSecond TextHeaderNormal">
			<div class="div-error-mandatory" id="div-verhr-madatory"></div>
			<div class="PanelThird">
				<table class="FormFrame">
					<tr>
						<td class="textR" nowrap="nowrap" width="25%"><%=MessageResourceUtil.getTextDescription(request, "WORKPLACE_NAME") %>:</td>
						<td class="textL" width="25%"><%= origCache.getORIGCacheDisplayNameDataM(17, personalM.getCompanyTitle())+" "+HTMLRenderUtil.displayHTML(personalM.getCompanyName()) %></td>
						<td class="textR" nowrap="nowrap" width="25%"></td>
						<td class="textL" width="25%"></td>
					</tr>
					<tr>
						<td class="textR" nowrap="nowrap" width="25%"><%=MessageResourceUtil.getTextDescription(request, "HR_NAME_SURNAME") %>:</td>
						<td class="textL" width="25%" id="div-verhr-name"><%= HTMLRenderUtil.displayHTML(personalM.getThaiFirstName())+" "+HTMLRenderUtil.displayHTML(personalM.getThaiLastName()) %></td>
						<td class="textR" nowrap="nowrap" width="25%"><%=MessageResourceUtil.getTextDescription(request, "HR_TEL") %><div class="mandatory-box">*</div>:</td>
						<td class="textL" width="25%" id="div-verhr-phoneno">
							<%=HTMLRenderUtil.displaySelectTagScriptAction_ORIG(new Vector(),"","verhr-phoneno",displayMode," onchange=\"javascript:otherTelNumber();\" ")%>
						</td>
					</tr>
					<tr>
						<td class="textR" nowrap="nowrap" width="25%"><%=MessageResourceUtil.getTextDescription(request, "HR_OTHER_PHONENO") %>:</td>
						<td class="textL" width="25%" id="div-verhr-other-phoneno"><%=HTMLRenderUtil.displayInputTagScriptAction("",displayMode,"","verhr-other-phoneno","textbox-tel-view","","10") %></td>
						<td class="textR" nowrap="nowrap" width="25%"><%=MessageResourceUtil.getTextDescription(request, "HR_TEL_STATUS") %><div class="mandatory-box">*</div>:</td>
						<td class="textL" width="25%" id="div-verhr-phonestatus"><%=HTMLRenderUtil.displaySelectTagScriptActionBusClassFieldID(appM.getBusinessClassId(),68,"","verhr-phonestatus",displayMode,"") %></td>
					</tr>
					<tr>
						<td class="textR" nowrap="nowrap" width="25%"><%=MessageResourceUtil.getTextDescription(request, "HR_REMARK") %>:</td>
						<td class="textL" width="25%" id="div-verhr-remark"><%=HTMLRenderUtil.displayInputTagScriptAction("",displayMode,"","verhr-remark","textbox","","500") %></td>
						<td class="textR" nowrap="nowrap" width="25%"></td>
						<td class="textL" width="25%" ><%=HTMLRenderUtil.displayButtonTagScriptAction("Add", displayMode,"button","addButton", "button", "onclick=\"addVerifyHRData();\"", "") %></td>
					</tr>
				</table>
				<table class="TableFrame" id="table-verhr-result">
					<tr class="Header">
						<td width="3%"><%=MessageResourceUtil.getTextDescription(request, "HR_NUM") %></td>
						<td width="20%"><%=MessageResourceUtil.getTextDescription(request, "HR_CALL_TIME")%></td>
						<td width="20%"><%=MessageResourceUtil.getTextDescription(request, "HR_TEL_STATUS") %></td>
						<td width="20%"><%=MessageResourceUtil.getTextDescription(request, "HR_TEL") %></td>					
						<td width="20%"><%=MessageResourceUtil.getTextDescription(request, "HR_REMARK") %></td>
						<td width="15%"><%=MessageResourceUtil.getTextDescription(request, "HR_USER") %></td>
					</tr>
					<%if(!ORIGUtility.isEmptyVector(xrulesVerHRVect)){
						String styleTr = null;							
						 for(int i=0 ;i< xrulesVerHRVect.size(); i++){	
						 	PLXRulesHRVerificationDataM xrulesHrM = (PLXRulesHRVerificationDataM) xrulesVerHRVect.get(i);				 
						 	styleTr = (i%2==0)?"ResultEven":"ResultOdd";					 
					%>														
							<tr class="<%=styleTr%>">
								<td><%=String.valueOf(xrulesHrM.getSeq())%></td>
								<td><%=DataFormatUtility.DateEnToStringDateTh(xrulesHrM.getCreateDate(), DataFormatUtility.DateFormatType.FORMAT_DDMMYYY_S_HHMMSS24) %></td>
								<td><%=HTMLRenderUtil.displayHTMLFieldIDDesc(xrulesHrM.getPhoneVerStatus(),68) %></td>
								<td><%=HTMLRenderUtil.displayHTML(xrulesHrM.getPhoneNo()) %></td>					
								<td><div class='textL'><%=HTMLRenderUtil.displayHTML(xrulesHrM.getRemark()) %></div></td>
								<td><%=HTMLRenderUtil.displayHTML(origCache.GetUserNameByuserID(xrulesHrM.getCreateBy()))%></td>
							</tr>
						<%} %>														
					<% }else{%>	
						<tr id="verhr-notfound" class="ResultNotFound">
							<td colspan="8" >No record found</td>
						</tr>
					<%} %>
				</table>
			</div>
			
			<div class="PanelThird">
				<table class="FormFrame">
					<tr>
						<td class="textR" width="40%" align="right" colspan="3" ><%=MessageResourceUtil.getTextDescription(request, "CUS_SUMMARY")%>&nbsp;:</td>
						<td class="textL" colspan="5" id="td-verhr-final-staus">
							<%=HTMLRenderUtil.displaySelectTagScriptAction_ORIG(new Vector(),xrulesVerM.getVerHRResultCode(),"verhr-final-staus",displayMode,"") %>
						</td>
					</tr>
				</table>
			</div>
			
		</div>
	</div>
</div>
<%	//set current screen to main Form
	com.eaf.j2ee.pattern.control.ScreenFlowManager screenFlowManager =
	(com.eaf.j2ee.pattern.control.ScreenFlowManager) request.getSession(true).getAttribute("screenFlowManager");
	screenFlowManager.setCurrentScreen(PLOrigFormHandler.PL_MAIN_APPFORM_SCREEN);
%>
 