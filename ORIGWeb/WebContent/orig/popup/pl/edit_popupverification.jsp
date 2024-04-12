<%-- <%@page contentType="text/html;charset=UTF-8"%> --%>
<%-- <%@page import="com.eaf.orig.shared.util.OrigUtil"%> --%>
<%-- <%@page import="com.eaf.orig.shared.constant.OrigConstant"%> --%>
<%-- <%@page import="com.eaf.orig.ulo.pl.app.utility.ORIGXRulesTool"%> --%>
<%-- <%@page import="com.eaf.orig.shared.utility.ORIGCacheUtil"%> --%>
<%-- <%@page import="com.eaf.orig.ulo.pl.app.utility.DataFormatUtility"%> --%>
<%-- <%@page import="com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesVerificationResultDataM"%> --%>
<%-- <%@page import="com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM"%> --%>
<%-- <%@page import="com.eaf.orig.ulo.pl.model.app.PLApplicationDataM"%> --%>
<%-- <%@page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%> --%>
<%-- <%@page import="com.eaf.j2ee.pattern.util.MessageResourceUtil"%> --%>
<%-- <%@page import="com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler"%> --%>
<%-- <%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %> --%>
<%-- <%@ page import="com.eaf.orig.shared.utility.ORIGFormUtil"%> --%>
<%-- <jsp:useBean id="searchType" scope="session" class="java.lang.String"/> --%>
<%-- <jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/> --%>
<%-- <jsp:useBean id="PLORIGForm" scope="session" class="com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler"/> --%>
<!-- <script type="text/javascript" src="orig/js/popup/pl/editverification.js"></script> -->
<%-- <% --%>
// 	PLApplicationDataM applicationM = PLORIGForm.getAppForm();
// 	if(null == applicationM) applicationM = new PLApplicationDataM();
// 	PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
// 	PLXRulesVerificationResultDataM xrulesVer = personalM.getXrulesVerification();
// 	if(null == xrulesVer){
// 		xrulesVer = new PLXRulesVerificationResultDataM();
// 		personalM.setXrulesVerification(xrulesVer);
// 	}
// 	ORIGCacheUtil cacheUtil = new ORIGCacheUtil();	
// 	ORIGXRulesTool xrulesTool = new ORIGXRulesTool();
	
// 	String displayModeCCBlockCode = xrulesTool.DisplayModeCCBlockCode(applicationM);	
// 	String displayMOdeKecBlockCode = xrulesTool.DisplayModeKECBlockCode(applicationM);
	
// 	String displayModeCisNo = HTMLRenderUtil.DISPLAY_MODE_VIEW;
// 	String displayModeClassifyLevel = HTMLRenderUtil.DISPLAY_MODE_VIEW;
// 	String displayModeWorstBlockCode = HTMLRenderUtil.DISPLAY_MODE_VIEW;
// 	String displayModeNpl = HTMLRenderUtil.DISPLAY_MODE_VIEW;
// 	String displayModeAmcTamc = HTMLRenderUtil.DISPLAY_MODE_VIEW;
// 	String displayModeBankruptcy = HTMLRenderUtil.DISPLAY_MODE_VIEW;
	
// 	String valueCisNo =  personalM.getCisNo();
// 	String valueClassifyLevel = xrulesVer.getClassifyLevel();
// 	String valueWorstBlockCode = xrulesVer.getBlockCodeCc();
// 	String valueNpl = xrulesVer.getNplLpm();
// 	String valueAmcTamc = xrulesVer.getAmcTamc();
// 	String valueBankruptcy = xrulesVer.getBankRuptcy();
	
// 	EAILogic eaiLogic = new EAILogic();
// 	if(eaiLogic.LogicServiceTFB0137I01(xrulesVer.getCodeServiceCisNo())){
// 		if(!OrigUtil.isEmptyString(personalM.getCisNo())&& OrigUtil.isEmptyString(xrulesVer.getEditCisNo())){
// 			displayModeCisNo = HTMLRenderUtil.DISPLAY_MODE_VIEW;
// 			valueCisNo = personalM.getCisNo();
// 		}else{
// 			displayModeCisNo = HTMLRenderUtil.DISPLAY_MODE_EDIT;
// 			valueCisNo = xrulesVer.getEditCisNo();
// 		}
// 	}
// 	if(eaiLogic.LogicServiceTFB0137I01(xrulesVer.getCodeServiceclassifyLevel())){
// 		if(!OrigUtil.isEmptyString(xrulesVer.getClassifyLevel())){
// 			displayModeClassifyLevel = HTMLRenderUtil.DISPLAY_MODE_VIEW;
// 			valueClassifyLevel = xrulesVer.getClassifyLevel();
// 		}else{
// 			displayModeClassifyLevel = HTMLRenderUtil.DISPLAY_MODE_EDIT;
// 			valueClassifyLevel = xrulesVer.getEditClassifyLevel();
// 		}
// 	}
// 	if(eaiLogic.LogicServiceTFB0137I01(xrulesVer.getCodeServiceBlockcode())){
// 		if(!OrigUtil.isEmptyString(xrulesVer.getBlockCodeCc())){
// 			displayModeWorstBlockCode = HTMLRenderUtil.DISPLAY_MODE_VIEW;
// 			valueWorstBlockCode = xrulesVer.getBlockCodeCc();
// 		}else{
// 			displayModeWorstBlockCode = HTMLRenderUtil.DISPLAY_MODE_EDIT;
// 			valueWorstBlockCode = xrulesVer.getEditBlockCode();
// 		}
// 	}
// 	if(eaiLogic.LogicServiceTFB0137I01(xrulesVer.getCodeServiceNplLpm())){
// 		if(!OrigUtil.isEmptyString(xrulesVer.getNplLpm())){
// 			displayModeNpl = HTMLRenderUtil.DISPLAY_MODE_VIEW;
// 			valueNpl = xrulesVer.getNplLpm();
// 		}else{
// 			displayModeNpl = HTMLRenderUtil.DISPLAY_MODE_EDIT;
// 			valueNpl = xrulesVer.getEditNpl();
// 		}
// 	}
// 	if(eaiLogic.LogicServiceTFB0137I01(xrulesVer.getCodeServiceAmcTamc())){
// 		if(!OrigUtil.isEmptyString(xrulesVer.getAmcTamc())){
// 			displayModeAmcTamc = HTMLRenderUtil.DISPLAY_MODE_VIEW;
// 			valueAmcTamc = xrulesVer.getAmcTamc();
// 		}else{
// 			displayModeAmcTamc = HTMLRenderUtil.DISPLAY_MODE_EDIT;
// 			valueAmcTamc = xrulesVer.getEditAmcTamc();
// 		}
// 	}
// 	if(eaiLogic.LogicServiceTFB0137I01(xrulesVer.getCodeServiceBankRuptcy())){
// 		if(!OrigUtil.isEmptyString(xrulesVer.getBankRuptcy())){
// 			displayModeBankruptcy = HTMLRenderUtil.DISPLAY_MODE_VIEW;
// 			valueBankruptcy = xrulesVer.getBankRuptcy();
// 		}else{
// 			displayModeBankruptcy = HTMLRenderUtil.DISPLAY_MODE_EDIT;
// 			valueBankruptcy = xrulesVer.getEditBankruptcy();
// 		}
// 	}
// 	ORIGFormUtil formUtil = new ORIGFormUtil();
// 	String displayMode = formUtil.getDisplayMode("VERIFICATION_SUBFORM", ORIGUser.getRoles(), applicationM.getJobState(), PLORIGForm.getFormID(), searchType);	
// 	if(HTMLRenderUtil.DISPLAY_MODE_VIEW.equals(displayMode)){
// 		displayModeCCBlockCode = HTMLRenderUtil.DISPLAY_MODE_VIEW;
// 		displayMOdeKecBlockCode = HTMLRenderUtil.DISPLAY_MODE_VIEW;
// 		displayModeCisNo = HTMLRenderUtil.DISPLAY_MODE_VIEW;
// 		displayModeClassifyLevel = HTMLRenderUtil.DISPLAY_MODE_VIEW;
// 		displayModeWorstBlockCode = HTMLRenderUtil.DISPLAY_MODE_VIEW;
// 		displayModeNpl = HTMLRenderUtil.DISPLAY_MODE_VIEW;
// 		displayModeAmcTamc = HTMLRenderUtil.DISPLAY_MODE_VIEW;
// 		displayModeBankruptcy = HTMLRenderUtil.DISPLAY_MODE_VIEW;
// 	}
	
<%--  %> --%>
<!-- <div id="edit-verfication-popup"> -->
<%-- <%=HTMLRenderUtil.displayHiddenField(displayModeCCBlockCode,"display-mode-cc-blockcode")%> --%>
<%-- <%=HTMLRenderUtil.displayHiddenField(displayMOdeKecBlockCode,"display-mode-kec-blockcode")%> --%>

<%-- <%=HTMLRenderUtil.displayHiddenField(displayModeCisNo,"display-mode-cisno")%> --%>
<%-- <%=HTMLRenderUtil.displayHiddenField(displayModeClassifyLevel,"display-mode-classifylevel")%> --%>
<%-- <%=HTMLRenderUtil.displayHiddenField(displayModeWorstBlockCode,"display-mode-worstblockcode")%> --%>
<%-- <%=HTMLRenderUtil.displayHiddenField(displayModeNpl,"display-mode-npl")%> --%>
<%-- <%=HTMLRenderUtil.displayHiddenField(displayModeAmcTamc,"display-mode-amctamc")%> --%>
<%-- <%=HTMLRenderUtil.displayHiddenField(displayModeBankruptcy,"display-mode-bankruptcy")%> --%>

<!-- <div class="PanelFirst">		 -->
<!-- 		<div class="PanelSecond TextHeaderNormal"> -->
<!-- 			<div class="div-error-mandatory" id="div-editverify-mandatory"></div> -->
<!-- 			<div class="PanelThird"> -->
<!-- 				<table class="FormFrame"> -->
<!-- 					<tr> -->
<!-- 						<td class="textL" nowrap="nowrap" width="25%"></td> -->
<!-- 						<td class="textL" width="25%"></td> -->
<!-- 						<td class="textR" nowrap="nowrap" width="25%"></td> -->
<!-- 						<td class="textL" width="25%"></td> -->
<!-- 					</tr> -->
<!-- 					<tr> -->
<%-- 						<td class="textL" nowrap="nowrap" width="25%"><b><%=MessageResourceUtil.getTextDescription(request, "EDIT_CUS_INFORMATION") %></b></td> --%>
<!-- 						<td class="textL" width="25%"></td> -->
<!-- 						<td class="textR" nowrap="nowrap" width="25%"></td> -->
<!-- 						<td class="textL" width="25%"></td> -->
<!-- 					</tr> -->
<!-- 					<tr> -->
<%-- 						<td class="textR" nowrap="nowrap" width="25%"><%=MessageResourceUtil.getTextDescription(request, "EDIT_CIS_NO") %>&nbsp;:&nbsp;</td> --%>
<%-- 						<td class="textL" width="25%"><%=HTMLRenderUtil.displayInputTagScriptAction(valueCisNo,displayModeCisNo,"","edit-cis-no","textbox","","") %></td> --%>
<%-- 						<td class="textR" nowrap="nowrap" width="25%"><%=MessageResourceUtil.getTextDescription(request, "EDIT_CLASSIFY_LEVEL") %>&nbsp;:&nbsp;</td> --%>
<%-- 						<td class="textL" width="25%"><%=HTMLRenderUtil.displaySelectTagScriptActionBusClassFieldID(applicationM.getBusinessClassId(),115,valueClassifyLevel,"edit-classify-level",displayModeClassifyLevel,"") %></td> --%>
<!-- 					</tr> -->
<!-- 					<tr> -->
<%-- 						<td class="textR" nowrap="nowrap" width="25%"><%=MessageResourceUtil.getTextDescription(request, "EDIT_WORST_CC_BLOCKCODE") %>&nbsp;:&nbsp;</td> --%>
<%-- 						<td class="textL" width="25%"><%=HTMLRenderUtil.DisplayBlockCodeSelectTag(applicationM.getBusinessClassId(),116,valueWorstBlockCode,"edit-cc-blockcode",displayModeWorstBlockCode,"",xrulesVer.getCodeServiceBlockcode()) %></td> --%>
<%-- 						<td class="textR" nowrap="nowrap" width="25%"><%=MessageResourceUtil.getTextDescription(request, "EDIT_CURRENT_NPL_FLAG") %>&nbsp;:&nbsp;</td> --%>
<%-- 						<td class="textL" width="25%"><%=HTMLRenderUtil.displaySelectTagScriptActionBusClassFieldID(applicationM.getBusinessClassId(),117,valueNpl,"edit-npl",displayModeNpl,"") %></td> --%>
<!-- 					</tr> -->
<!-- 					<tr> -->
<%-- 						<td class="textR" nowrap="nowrap" width="25%"><%=MessageResourceUtil.getTextDescription(request, "EDIT_AMCTAMC_FLAG") %>&nbsp;:&nbsp;</td> --%>
<%-- 						<td class="textL" width="25%"><%=HTMLRenderUtil.displaySelectTagScriptActionBusClassFieldID(applicationM.getBusinessClassId(),118,valueAmcTamc,"edit-amctamc",displayModeAmcTamc,"") %></td> --%>
<%-- 						<td class="textR" nowrap="nowrap" width="25%"><%=MessageResourceUtil.getTextDescription(request, "EDIT_BANKRUPTCY_FLAG") %>&nbsp;:&nbsp;</td> --%>
<%-- 						<td class="textL" width="25%"><%=HTMLRenderUtil.displaySelectTagScriptActionBusClassFieldID(applicationM.getBusinessClassId(),119,valueBankruptcy,"edit-bankruptcy",displayModeBankruptcy,"") %></td> --%>
<!-- 					</tr> -->
<!-- 					<tr> -->
<!-- 						<td class="textL" nowrap="nowrap" width="25%"></td> -->
<!-- 						<td class="textL" width="25%"></td> -->
<!-- 						<td class="textR" nowrap="nowrap" width="25%"></td> -->
<!-- 						<td class="textL" width="25%"></td> -->
<!-- 					</tr> -->
<!-- 					<tr> -->
<%-- 						<td class="textL" nowrap="nowrap" width="25%"><b><%=MessageResourceUtil.getTextDescription(request, "EDIT_CC_BLOCKCODE") %></b></td> --%>
<!-- 						<td class="textL" width="25%"></td> -->
<!-- 						<td class="textR" nowrap="nowrap" width="25%"></td> -->
<!-- 						<td class="textL" width="25%"></td> -->
<!-- 					</tr> -->
<!-- 					<tr> -->
<%-- 						<td class="textR" nowrap="nowrap" width="25%"><%=MessageResourceUtil.getTextDescription(request, "EDIT_CC_CURRENT_BALANCE") %>&nbsp;:&nbsp;</td> --%>
<%-- 						<td class="textL" width="25%" id="td-edit-cc-current-balance"><%=HTMLRenderUtil.DisplayInputCurrency(xrulesVer.getcCCurrentBalance(),displayModeCCBlockCode,"########0.00","edit-cc-current-balance","textbox-currency","","12", true)%></td> --%>
<%-- 						<td class="textR" nowrap="nowrap" width="25%"><%=MessageResourceUtil.getTextDescription(request, "EDIT_CC_LASTPAYMENT_DATE") %>&nbsp;:&nbsp;</td> --%>
<!-- 						<td class="textL" width="25%" id="td-edit-cc-lastpayment-date"> -->
<%-- 						<%=HTMLRenderUtil.displayInputTagDate("appFormName",DataFormatUtility.dateEnToStringDateEn(xrulesVer.getcCLastPaymentDate() --%>
// 										,DataFormatUtility.DateFormatType.FORMAT_DDMMYYY_S), displayModeCCBlockCode,"15","edit-cc-lastpayment-date"
<%-- 													,"right","currentDate","" )%></td> --%>
<!-- 					</tr> -->
<!-- 					<tr> -->
<!-- 						<td class="textL" nowrap="nowrap" width="25%"></td> -->
<!-- 						<td class="textL" width="25%"></td> -->
<!-- 						<td class="textR" nowrap="nowrap" width="25%"></td> -->
<!-- 						<td class="textL" width="25%"></td> -->
<!-- 					</tr> -->
<!-- 					<tr> -->
<%-- 						<td class="textL" nowrap="nowrap" width="25%"><b><%=MessageResourceUtil.getTextDescription(request, "EDIT_KEC_BLOCKCODE") %></b></td> --%>
<!-- 						<td class="textL" width="25%"></td> -->
<!-- 						<td class="textR" nowrap="nowrap" width="25%"></td> -->
<!-- 						<td class="textL" width="25%"></td> -->
<!-- 					</tr> -->
<!-- 					<tr> -->
<%-- 						<td class="textR" nowrap="nowrap" width="25%"><%=MessageResourceUtil.getTextDescription(request, "EDIT_KEC_CURRENT_BALANCE") %>&nbsp;:&nbsp;</td> --%>
<%-- 						<td class="textL" width="25%"><%=HTMLRenderUtil.DisplayInputCurrency(xrulesVer.getKecCurrentBalance(),displayMOdeKecBlockCode,"########0.00","edit-kec-current-balance","textbox-currency","","12", true)%></td> --%>
<%-- 						<td class="textR" nowrap="nowrap" width="25%"><%=MessageResourceUtil.getTextDescription(request, "EDIT_KEC_LAST_PAYMENT_DATE") %>&nbsp;:&nbsp;</td> --%>
<!-- 						<td class="textL" width="25%"> -->
<%-- 						<%=HTMLRenderUtil.displayInputTagDate("appFormName" --%>
// 										,DataFormatUtility.dateEnToStringDateEn(xrulesVer.getKecLastPaymentDate()
// 											,DataFormatUtility.DateFormatType.FORMAT_DDMMYYY_S), displayMOdeKecBlockCode,"15","edit-kec-lastpayment-date"
<%-- 													,"right","currentDate","" )%> --%>
<!-- 						</td> -->
<!-- 					</tr> -->
<!-- 					<tr> -->
<%-- 						<td class="textR" nowrap="nowrap" width="25%"><%=MessageResourceUtil.getTextDescription(request, "EDIT_LAST_UPDATE_DATE") %>&nbsp;:&nbsp;</td> --%>
<%-- 						<td class="textL" width="25%"><%=DataFormatUtility.dateEnToStringDateEn(xrulesVer.getEditUpdateDate(),DataFormatUtility.DateFormatType.FORMAT_DDMMYYY_S) %></td> --%>
<%-- 						<td class="textR" nowrap="nowrap" width="25%"><%=MessageResourceUtil.getTextDescription(request, "EDIT_LAST_UPDATE_BY") %>&nbsp;:&nbsp;</td> --%>
<%-- 						<td class="textL" width="25%"><%=HTMLRenderUtil.replaceNull(cacheUtil.GetUserNameByuserID(xrulesVer.getEditUpdateBy()))%></td> --%>
<!-- 					</tr> -->
<!-- 					<tr> -->
<!-- 						<td class="textL" nowrap="nowrap" width="25%"></td> -->
<!-- 						<td class="textL" width="25%"></td> -->
<!-- 						<td class="textR" nowrap="nowrap" width="25%"></td> -->
<!-- 						<td class="textL" width="25%"></td> -->
<!-- 					</tr> -->
<!-- 				</table> -->
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 	</div> -->
<!-- </div> -->
<%-- <%	//set current screen to main Form --%>
// 	com.eaf.j2ee.pattern.control.ScreenFlowManager screenFlowManager =
// 	(com.eaf.j2ee.pattern.control.ScreenFlowManager) request.getSession(true).getAttribute("screenFlowManager");
// 	screenFlowManager.setCurrentScreen(PLOrigFormHandler.PL_MAIN_APPFORM_SCREEN);
<%-- %> --%>