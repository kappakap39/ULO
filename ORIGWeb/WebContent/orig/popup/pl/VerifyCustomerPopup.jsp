<%@page import="com.eaf.orig.shared.utility.ORIGFormUtil"%>
<%@page import="com.eaf.j2ee.pattern.util.MessageResourceUtil"%>
<%@page import="com.eaf.orig.shared.utility.ORIGCacheUtil"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="com.eaf.orig.shared.constant.OrigConstant"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesDataPhoneVerificationDataM"%>
<%@page import="com.eaf.orig.ulo.pl.app.utility.DataFormatUtility"%>
<%@page import="com.eaf.orig.shared.utility.ORIGUtility"%>
<%@page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@page import="com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesPhoneVerificationDataM"%>
<%@page import="java.util.Vector"%>
<%@page import="com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesVerificationResultDataM"%>
<%@page import="com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM"%>
<%@page import="com.eaf.orig.ulo.pl.model.app.PLApplicationDataM"%>
<%@page import="com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="searchType" scope="session" class="java.lang.String"/>
<jsp:useBean id="PLORIGForm" scope="session" class="com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler"/>
<script type="text/javascript" src="orig/js/popup/pl/verifyCustomerPopup.js"></script>
<%
	Logger logger = Logger.getLogger(this.getClass());	
	PLApplicationDataM appM = PLORIGForm.getAppForm();	
	if(null == appM ) appM = new PLApplicationDataM();
	
	PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		
	PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();
	
	if(null == xrulesVerM){
	 	xrulesVerM = new PLXRulesVerificationResultDataM();
	 	personalM.setXrulesVerification(xrulesVerM);
	}
	
	Vector<PLXRulesPhoneVerificationDataM> phoneVerVect = xrulesVerM.getVXRulesPhoneVerificationDataM();
		
	Vector<PLXRulesDataPhoneVerificationDataM> dataPhoneVerVect = xrulesVerM.getxRulesDataPhoneVerificationDataMs();
	
	ORIGCacheUtil origCache = new ORIGCacheUtil();
	
	ORIGFormUtil formUtil = new ORIGFormUtil();
	String displayMode = formUtil.getDisplayMode("VERIFICATION_SUBFORM", ORIGUser.getRoles(), appM.getJobState(), PLORIGForm.getFormID(), searchType);
	logger.debug("DisplayMode >> "+displayMode);
	
%>
<div id="div-vercus-info">
	<div class="PanelFirst">
		<div class="PanelSecond TextHeaderNormal">
			<div class="div-error-mandatory" id="div-vercus-madatory"></div>
			<div class="PanelThird">
				<table class="FormFrame">
					<tr>
						<td class="textR" nowrap="nowrap" width="20%"><%=MessageResourceUtil.getTextDescription(request, "CUS_PERSONAL")%><div class="mandatory-box">*</div>:</td>
						<td class="textL" width="15%" id="div-vercus-personal-type"><%=HTMLRenderUtil.displaySelectTagScriptActionBusClassFieldID(appM.getBusinessClassId(),66,"A","vercus-personal-type",displayMode," onchange=\"javascript:initVerCustomer();\" ") %></td>													
						<td class="textR" nowrap="nowrap" width="15%"><%=MessageResourceUtil.getTextDescription(request, "CUS_NAME_SURNAME") %>:</td>
						<td class="textL" width="15%" id="div-vercus-name">
							<%= HTMLRenderUtil.displayHTML(personalM.getThaiFirstName())+" "+HTMLRenderUtil.displayHTML(personalM.getThaiLastName()) %>
						</td>
						<td class="textR" nowrap="nowrap" width="15%"><%=MessageResourceUtil.getTextDescription(request, "CUS_ADDRESS_TYPE") %><div class="mandatory-box">*</div>:</td>
						<td class="textL" width="20%" id="div-vercus-address-type"><%= HTMLRenderUtil.displaySelectTagScriptAction_ORIG(new Vector(),"","vercus-address-type",displayMode," onchange=\"javascript:loadTelephone();\" ") %></td>
					</tr>
					<tr>
						<td class="textR" nowrap="nowrap" width="20%"><%=MessageResourceUtil.getTextDescription(request, "CUS_TEL") %><div class="mandatory-box">*</div>:</td>
						<td class="textL" width="15%" id="div-vercus-phoneno"><%= HTMLRenderUtil.displaySelectTagScriptAction_ORIG(new Vector(),"","vercus-phoneno",displayMode,"") %></td>
						<td class="textR" nowrap="nowrap" width="15%"><%=MessageResourceUtil.getTextDescription(request, "CUS_TEL_STATUS") %><div class="mandatory-box">*</div>:</td>
						<td class="textL" width="15%" id="div-vercus-phonestatus"><%=HTMLRenderUtil.displaySelectTagScriptActionBusClassFieldID(appM.getBusinessClassId(),68,"","vercus-phonestatus",displayMode,"") %></td>
						<td class="textR" nowrap="nowrap" width="15%"><%=MessageResourceUtil.getTextDescription(request, "CUS_REMARK") %>:</td>
						<td class="textL" width="20%" id="div-vercus-remark"><%=HTMLRenderUtil.displayInputTagScriptAction("",displayMode,"","vercus-remark","textbox","","200") %></td>
					</tr>
					<tr>
						<td colspan="6" class="textC">
							<%=HTMLRenderUtil.displayButtonTagScriptAction("Add", displayMode,"button","addButton", "button", "onclick=\"addVerifyCustomerData();\"", "") %>
						</td>
					</tr>
				</table>
			</div>
			<div class="PanelThird">
				<table class="TableFrame" id="verify-cutomer-table">
					<tr class="Header">
						<td width="3%"><%=MessageResourceUtil.getTextDescription(request, "CUS_NUM") %></td>
						<td width="15%"><%=MessageResourceUtil.getTextDescription(request, "CUS_CALL_TIME")%></td>
						<td width="17%"><%=MessageResourceUtil.getTextDescription(request, "CUS_TEL_STATUS") %></td>
						<td width="10%"><%=MessageResourceUtil.getTextDescription(request, "CUS_PERSONAL") %></td>
						<td width="15%"><%=MessageResourceUtil.getTextDescription(request, "CUS_ADDRESS_TYPE") %></td>
						<td width="15%"><%=MessageResourceUtil.getTextDescription(request, "CUS_TEL") %></td>					
						<td width="15%"><%=MessageResourceUtil.getTextDescription(request, "CUS_REMARK") %></td>
						<td width="15%"><%=MessageResourceUtil.getTextDescription(request, "CUS_USER") %></td>						
					</tr>
					<%	
						String styleTr = null;													
						if(!ORIGUtility.isEmptyVector(phoneVerVect)){
							for(int i=0; i<phoneVerVect.size(); i++){
							PLXRulesPhoneVerificationDataM plXrulesPhoneM = (PLXRulesPhoneVerificationDataM) phoneVerVect.get(i);
							styleTr = (i%2==0)?"ResultEven":"ResultOdd";
					%>
							<tr class="<%=styleTr%>">
								<td><%=String.valueOf(plXrulesPhoneM.getSeq())%></td>
								<td><%=DataFormatUtility.DateEnToStringDateTh(plXrulesPhoneM.getCreateDate(), DataFormatUtility.DateFormatType.FORMAT_DDMMYYY_S_HHMMSS24) %></td>
								<td><%=HTMLRenderUtil.displayHTMLFieldIDDesc(plXrulesPhoneM.getPhoneVerStatus(),68) %></td>
								<td><%=HTMLRenderUtil.displayHTMLFieldIDDesc(plXrulesPhoneM.getContactType(),66) %></td>
								<td><%=HTMLRenderUtil.displayHTMLFieldIDDesc(plXrulesPhoneM.getAddressType(),12) %></td>
								<td><%=HTMLRenderUtil.displayHTML(plXrulesPhoneM.getTelno()) %></td>					
								<td><div class='textL'><%=HTMLRenderUtil.displayHTML(plXrulesPhoneM.getRemark()) %></div></td>
								<td><%=HTMLRenderUtil.displayHTML(origCache.GetUserNameByuserID(plXrulesPhoneM.getCreateBy())) %></td>
							</tr>
						<% }%>												
					<%}else{ %>
						<tr id="verify-cutomer-notfound" class="ResultNotFound">
							<td colspan="8">No record found</td>
						</tr>
					<% }%>	
				</table>											
			</div>
			<div class="div-error-mandatory" id="div-vercus-result-madatory"></div>
			<%=MessageResourceUtil.getTextDescription(request, "CUS_VERIFY")%>
			<div class="PanelThird">
				<table class="TableFrame" id="table-vercus-result">
					<tr class="Header">
						<td width="3%"><%=MessageResourceUtil.getTextDescription(request, "CUS_NUM") %></td>
						<td width="20%"><%=MessageResourceUtil.getTextDescription(request, "CUS_FIELD")%></td>
						<td width="20%"><%=MessageResourceUtil.getTextDescription(request, "CUS_DATA") %></td>
						<td width="15%"><%=MessageResourceUtil.getTextDescription(request, "CUS_NOT_CHECK") %></td>
						<td width="15%"><%=MessageResourceUtil.getTextDescription(request, "CUS_TRUE") %></td>
						<td width="15%"><%=MessageResourceUtil.getTextDescription(request, "CUS_FALSE") %></td>					
						<td width="20%"><%=MessageResourceUtil.getTextDescription(request, "CUS_REMARK") %></td>						
					</tr>
					<%
					if(!ORIGUtility.isEmptyVector(dataPhoneVerVect)){					
						String styleTrVer = null;
						for(int i=0; i<dataPhoneVerVect.size(); i++){													
							PLXRulesDataPhoneVerificationDataM xruleDataVerM = (PLXRulesDataPhoneVerificationDataM) dataPhoneVerVect.get(i);
							styleTrVer = "obj-vercus "+((i%2==0)?"ResultEven":"ResultOdd");
							if(OrigConstant.FLAG_Y.equalsIgnoreCase(xruleDataVerM.getIsMandatory()))
									styleTrVer = styleTrVer+" mandatory-cus "+xruleDataVerM.getFieldId();			
					%>
							<tr class="<%=styleTrVer%>">
								<td width="3%"><%=xruleDataVerM.getFieldId() %></td>
								<td width="25%">
									<div class="textL">
										<%=xruleDataVerM.getFieldDesc() %>
										<%if(OrigConstant.FLAG_Y.equalsIgnoreCase(xruleDataVerM.getIsMandatory())){ %>
											<div class="mandatory-box">*</div>
										<%} %>
									</div>
								</td>
								<td width="20%"><div class="textL"><%=xruleDataVerM.getFieldValue() %></div></td>
								<td width="15%"><%=HTMLRenderUtil.displayRadioTagScriptAction(xruleDataVerM.getResult(),displayMode,"verdata-"+xruleDataVerM.getFieldId(),HTMLRenderUtil.RadioBoxCompare.NotCheck,"")%></td>
								<td width="15%"><%=HTMLRenderUtil.displayRadioTagScriptAction(xruleDataVerM.getResult(),displayMode,"verdata-"+xruleDataVerM.getFieldId(),HTMLRenderUtil.RadioBoxCompare.True,"")%></td>
								<td width="15%"><%=HTMLRenderUtil.displayRadioTagScriptAction(xruleDataVerM.getResult(),displayMode,"verdata-"+xruleDataVerM.getFieldId(),HTMLRenderUtil.RadioBoxCompare.False,"")%></td>					
								<td width="15%"><div class='textL'><%=HTMLRenderUtil.displayInputTagScriptAction(xruleDataVerM.getRemark(),displayMode,"","verdata-remark-"+xruleDataVerM.getFieldId(),"textbox","","500") %></div></td>							
							</tr>
						<%} %>
					<%}else{%>											 
					 	<tr>
							<td colspan="7" class="ResultNotFound">No record found</td>
						</tr>
					<%} %>
				</table>
			</div>
			
			<div class="PanelThird">
				<table class="FormFrame">
					<tr>
						<td class="textR" width="40%" align="right" colspan="3" ><%=MessageResourceUtil.getTextDescription(request, "CUS_SUMMARY")%>&nbsp;:</td>
						<td class="textL" colspan="5" id="td-vercus-final-staus">
							<%=HTMLRenderUtil.displaySelectTagScriptAction_ORIG(new Vector(),xrulesVerM.getVerCusResultCode(),"vercus-final-status",displayMode,"") %>
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
 