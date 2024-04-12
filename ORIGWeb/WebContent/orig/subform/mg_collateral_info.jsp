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
	ORIGFormUtil formUtil = new ORIGFormUtil();
	String searchType = (String) request.getSession().getAttribute("searchType");
	ORIGFormHandler popupForm = (ORIGFormHandler)ORIGForm.getPopupForm();
	String displayMode = formUtil.getDisplayMode("MG_COLLATERAL_INFO_SUBFORM", ORIGUser.getRoles(), applicationDataM.getJobState(), popupForm.getFormID(), searchType);	

	ORIGUtility utility = new ORIGUtility();
	ORIGCacheUtil cacheUtil = ORIGCacheUtil.getInstance();
	Vector collateralTypeVect = cacheUtil.getNaosCacheDataMs("AL_ALL_ALL",28);
	Vector propertyTypeVect = cacheUtil.getNaosCacheDataMs("AL_ALL_ALL",29);
	Vector propertySubTypeVect = cacheUtil.getNaosCacheDataMs("AL_ALL_ALL",30);
	Vector propertyDevSubTypeVect = cacheUtil.getNaosCacheDataMs("AL_ALL_ALL",35);
	
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

<table cellpadding="0" cellspacing="0" width="100%" align="center">
	<tr>
		<td class="textColS" width="25%"><%=MessageResourceUtil.getTextDescription(request, "COLLATERAL_TYPE") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"MG_COLLATERAL_INFO_SUBFORM","collateralType")%></Font> :</td>
		<td class="inputCol" width="25%"><%=ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(collateralTypeVect,collateralDataM.getCollateralType(),"collateralType",displayMode,"onChange=\"javascript:getPropertyOnDemand(); \" style=\"width:80%;\" ") %></td>
		<td class="textColS" width="20%"></td>
		<td class="inputCol" width="30%"></td>
	</tr>
	<tr>
		<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "PROPERTY_TYPE") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"MG_COLLATERAL_INFO_SUBFORM","propertyType")%></Font> :</td>
		<td class="inputCol"><span id="div_propertyType"><%=ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(propertyTypeVect,collateralDataM.getPropertyType(),"propertyType",displayMode,"onChange=\"javascript:getPropertyOnDemand(); \" style=\"width:80%;\" ") %></span></td>
		<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "PROPERTY_SUB_TYPE") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"MG_COLLATERAL_INFO_SUBFORM","propertySubType")%></Font> :</td>
		<td class="inputCol"><span id="div_propertySubType"><%=ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(propertySubTypeVect,collateralDataM.getPropertySubType(),"propertySubType",displayMode," style=\"width:80%;\" ") %></span></td>
	</tr>
	<tr>
		<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "ACCREDITED_PROPERTY") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"MG_COLLATERAL_INFO_SUBFORM","accreditedProperty")%></Font> :</td>
		<td class="inputCol">
			<table cellspacing="0" cellpadding="0" width="100%" align="center" border="0">
				<tr><td width="7%"><%=ORIGDisplayFormatUtil.displayRadioTagScriptAction("Y", displayMode, "accreditedProperty", ORIGDisplayFormatUtil.displayHTML(collateralDataM.getAccreditedProperty()), "", "", "") %> </td>
				<td class="textColS" width="15%"> Yes</td>
				<td width="7%"><%=ORIGDisplayFormatUtil.displayRadioTagScriptAction("N", displayMode, "accreditedProperty", ORIGDisplayFormatUtil.displayHTML(collateralDataM.getAccreditedProperty()), "", "", "") %></td>
				<td class="textColS" width="15%"> No</td>
				<td>&nbsp;</td>
			</tr></table>
		<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "WITH_UNDERTAKING") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"MG_COLLATERAL_INFO_SUBFORM","withUnderTaking")%></Font> :</td>
		<td class="inputCol">
			<table cellspacing="0" cellpadding="0" width="100%" align="center" border="0">
				<tr><td width="7%"><%=ORIGDisplayFormatUtil.displayRadioTagScriptAction("Y", displayMode, "withUnderTaking", ORIGDisplayFormatUtil.displayHTML(collateralDataM.getWithUndertaking()), "", "", "") %></td>
				<td class="textColS" width="15%"> Yes</td>
				<td width="7%"><%=ORIGDisplayFormatUtil.displayRadioTagScriptAction("N", displayMode, "withUnderTaking", ORIGDisplayFormatUtil.displayHTML(collateralDataM.getWithUndertaking()), "", "", "") %></td>
				<td class="textColS" width="15%"> No</td>
				<td>&nbsp;</td>
			</tr></table>
	</tr>
	<tr>
		<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "AREA_SQUARE_METRES") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"MG_COLLATERAL_INFO_SUBFORM","area")%></Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(collateralDataM.getArea()),displayMode,"","area","textboxCurrency","onblur=\"javascript:chcekMaxDecimalValue(this);addComma(this);addDecimalFormat(this);\" onKeyPress=\"isNumberOnKeyPress(this)\" onkeyup=\"isNumberOnkeyUp(this)\" onfocus=\"removeCommaField(this)\" style=\"width:80%;\" ","16") %></td>
		<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "PROPERTY_DEVELOPERS") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"MG_COLLATERAL_INFO_SUBFORM","area")%></Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(propertyDevSubTypeVect,collateralDataM.getPropertyDevelopers(),"propertyDev",displayMode," style=\"width:80%;\" ") %></td>
	</tr>
</table>
<script language="JavaScript" type="text/JavaScript">
function getPropertyType(){
	var collateralType = appFormName.collateralType.value;
	var propertyType = appFormName.propertyType.value;
	var server = '<%="http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()%>';
	ajax(server+"/AjaxServlet?ClassName=GetPropertyType&collateralType="+collateralType+"&propertyType="+propertyType+"&displayMode="+"<%=displayMode%>", displayInnerHtml, "GET");
}
function getPropertySubType(){
	var collateralType = appFormName.collateralType.value;
	var propertyType = appFormName.propertyType.value;
	var propertySubType = appFormName.propertySubType.value;
	var server = '<%="http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()%>';
	ajax(server+"/AjaxServlet?ClassName=GetPropertySubType&collateralType="+collateralType+"&propertyType="+propertyType+"&propertySubType="+propertySubType+"&displayMode="+"<%=displayMode%>", displayInnerHtml, "GET");
}
function getPropertyOnDemand(){
	if (appFormName.collateralType!=undefined && appFormName.propertyType!=undefined && appFormName.propertySubType!=undefined){
		getPropertyType();
		setTimeout("getPropertySubType();", 1000);
	}
}
function defaultPropertyOnload(){
	if (appFormName.collateralType!=undefined && appFormName.propertyType!=undefined && appFormName.propertySubType!=undefined){
		getPropertyType();
		setTimeout("getPropertySubType();", 1000);
		setTimeout("appFormName.propertyType.value = '<%=ORIGDisplayFormatUtil.displayHTML(collateralDataM.getPropertyType())%>';", 2000);
		setTimeout("appFormName.propertySubType.value = '<%=ORIGDisplayFormatUtil.displayHTML(collateralDataM.getPropertySubType())%>';", 2200);
	}
}
defaultPropertyOnload();
</script>