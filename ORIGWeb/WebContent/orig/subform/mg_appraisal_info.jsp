<%@ page import="java.util.*" %>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="com.eaf.orig.shared.model.ApplicationDataM" %>
<%@ page import="com.eaf.orig.shared.model.PersonalInfoDataM" %>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGFormUtil"%>
<%@ page import="com.eaf.orig.shared.model.CollateralDataM" %>
<%@ page import="com.eaf.orig.shared.model.AppraisalDataM" %>
<%@ page import="com.eaf.orig.formcontrol.view.form.ORIGFormHandler" %>


<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.formcontrol.view.form.ORIGFormHandler"/>

<%
	ApplicationDataM applicationDataM = ORIGForm.getAppForm();
	if(applicationDataM == null){
		applicationDataM = new ApplicationDataM();
	}
	CollateralDataM collateralDataM = (CollateralDataM)request.getSession().getAttribute("COLLATERAL_POPUP_DATA");
	if (collateralDataM==null){
		collateralDataM = new CollateralDataM();
	}
	AppraisalDataM appraisalDataM = collateralDataM.getAppraisal();
	if (appraisalDataM==null){
		appraisalDataM = new AppraisalDataM();
	}
	
	ORIGFormUtil formUtil = new ORIGFormUtil();
	String searchType = (String) request.getSession().getAttribute("searchType");
	ORIGFormHandler popupForm = (ORIGFormHandler)ORIGForm.getPopupForm();
	String displayMode = formUtil.getDisplayMode("MG_APPRAISAL_INFO_SUBFORM", ORIGUser.getRoles(), applicationDataM.getJobState(), popupForm.getFormID(), searchType);	

	ORIGUtility utility = new ORIGUtility();
	ORIGCacheUtil cacheUtil = ORIGCacheUtil.getInstance();
	Vector unacceptableVect = cacheUtil.getNaosCacheDataMs("AL_ALL_ALL",33);
	
	String personalType = (String) request.getSession().getAttribute("PersonalType");
	PersonalInfoDataM personalInfoDataM = null;
	if(OrigConstant.PERSONAL_TYPE_GUARANTOR.equals(personalType)){
		personalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("MAIN_POPUP_DATA");
	}else if(OrigConstant.PERSONAL_TYPE_SUP_CARD.equals(personalType)){
		personalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("SUPCARD_POPUP_DATA");
	}else{
		personalInfoDataM = utility.getPersonalInfoByType(ORIGForm.getAppForm(),OrigConstant.PERSONAL_TYPE_APPLICANT);
	}
	if(personalInfoDataM == null){
		personalInfoDataM = new PersonalInfoDataM();
	}
%>

<table cellpadding="" cellspacing="1" width="100%">
	<tr>
		<td class="textColS" width="25%"><%=MessageResourceUtil.getTextDescription(request, "APPRAISAL_IS_PERFORMED_BY") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"MG_APPRAISAL_INFO_SUBFORM","performedBy")%></Font> :</td>
		<td class="inputCol" colspan="3">
			&nbsp;<%=ORIGDisplayFormatUtil.displayRadioTagScriptAction("I", displayMode, "performedBy", ORIGDisplayFormatUtil.displayHTML(appraisalDataM.getAppraisalPerformedBy()), "", "", "") %> Internal Appraiser
			&nbsp;<%=ORIGDisplayFormatUtil.displayRadioTagScriptAction("E", displayMode, "performedBy", ORIGDisplayFormatUtil.displayHTML(appraisalDataM.getAppraisalPerformedBy()), "", "", "") %> External Appraiser
		</td>
	</tr>
	<tr>
		<td class="textColS" width="25%"><%=MessageResourceUtil.getTextDescription(request, "NAME_OF_APPRAISER") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"MG_APPRAISAL_INFO_SUBFORM","appraiserName")%></Font> :</td>
		<td class="inputCol" colspan="3"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(appraisalDataM.getAppraisalName(),displayMode,"15","appraiserName","textbox","","5") %></td>
	</tr>
	<tr><%String sDate = "";
		if (appraisalDataM.getAppraisalDate()!=null){
			sDate = ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(appraisalDataM.getAppraisalDate()));
		} %>
		<td class="textColS" width="25%"><%=MessageResourceUtil.getTextDescription(request, "DATE_OF_APPRAISAL") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"MG_APPRAISAL_INFO_SUBFORM","appraisalDate")%></Font> :</td>
		<td class="inputCol" width="25%"><%=ORIGDisplayFormatUtil.displayInputTagDate("appFormName",sDate,displayMode,"15","appraisalDate","textbox") %></td>
		<td class="textColS" width="20%"><%=MessageResourceUtil.getTextDescription(request, "TOTAL_CONTRACT_PRICE_OF_PROPERTY") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"MG_APPRAISAL_INFO_SUBFORM","contractPrice")%></Font> :</td>
		<td class="inputCol" width="30%"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(appraisalDataM.getTotalContactPrice()),displayMode,"15","contractPrice","textbox","onblur=\"javascript:addComma(this);addDecimalFormat(this);\" onKeyPress=\"isNumberOnKeyPress(this)\" onkeyup=\"isNumberOnkeyUp(this)\" onfocus=\"removeCommaField(this)\"","15") %></td>
	</tr>
	<tr>
		<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "TOTAL_FAIR_MARKET_VALUE") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"MG_APPRAISAL_INFO_SUBFORM","totalFMV")%></Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(appraisalDataM.getTotalFMV()),displayMode,"15","totalFMV","textbox","onblur=\"javascript:addComma(this);addDecimalFormat(this);\" onKeyPress=\"isNumberOnKeyPress(this)\" onkeyup=\"isNumberOnkeyUp(this)\" onfocus=\"removeCommaField(this)\"","15") %></td>
		<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "TOTAL_APPRAISED_VALUE") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"MG_APPRAISAL_INFO_SUBFORM","totalAppraiseValue")%></Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(appraisalDataM.getTotalFMV()),displayMode,"15","totalAppraiseValue","textbox","onblur=\"javascript:addComma(this);addDecimalFormat(this);\" onKeyPress=\"isNumberOnKeyPress(this)\" onkeyup=\"isNumberOnkeyUp(this)\" onfocus=\"removeCommaField(this)\"","15") %></td>
	</tr>
	<tr>
		<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "TOTAL_LOANABLE_VALUE") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"MG_APPRAISAL_INFO_SUBFORM","totalLV")%></Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(appraisalDataM.getTotalLoanableValue()),displayMode,"15","totalLV","textbox","onblur=\"javascript:addComma(this);addDecimalFormat(this);\" onKeyPress=\"isNumberOnKeyPress(this)\" onkeyup=\"isNumberOnkeyUp(this)\" onfocus=\"removeCommaField(this)\"","15") %></td>
		<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "LTV_PERCENT") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"MG_APPRAISAL_INFO_SUBFORM","ltvPercent")%></Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(appraisalDataM.getLtv()),displayMode,"15","ltvPercent","textbox","","15") %></td>
	</tr>
	<tr>
		<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "UNACCEPTABLE_COLLATERAL") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"MG_COLLATERAL_INFO_SUBFORM","unacceptable")%></Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(unacceptableVect,appraisalDataM.getUnacceptableCollateral(),"unacceptable",ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT," style=\"width:80%;\" ") %></td>
		<td class="textColS"></td>
		<td class="inputCol"></td>
	</tr>
</table>