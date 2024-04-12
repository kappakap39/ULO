<%@ page import="java.util.*" %>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGFormUtil"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>

<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>
<%@ page import="com.eaf.orig.shared.model.ApplicationDataM" %>
<%@ page import="com.eaf.orig.shared.model.PersonalInfoDataM" %>
<%@ page import="com.eaf.orig.shared.model.CardInformationDataM" %>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.formcontrol.view.form.ORIGFormHandler"/>

<%
	ORIGUtility utility = new ORIGUtility();
	
	ApplicationDataM applicationDataM = ORIGForm.getAppForm();
	if(applicationDataM == null){
		applicationDataM = new ApplicationDataM();
	}
	Vector vecCardInformation = applicationDataM.getCardInformation();
	CardInformationDataM cardInformationDataM = new CardInformationDataM();
	if (vecCardInformation != null){
		for (int i=0;i<vecCardInformation.size();i++){
			CardInformationDataM temp = (CardInformationDataM)vecCardInformation.get(i);
			if (OrigConstant.CARD_INFORMATION_APPLICATION_TYPE_MAIN.equalsIgnoreCase(temp.getApplicationType())){
				cardInformationDataM = temp;
			}
		}
	}
	
	//Get Display Mode
	ORIGFormUtil formUtil = new ORIGFormUtil();
	String searchType = (String) request.getSession().getAttribute("searchType");
	String displayMode = formUtil.getDisplayMode("APPLICANT_SUBFORM", ORIGUser.getRoles(), applicationDataM.getJobState(), ORIGForm.getFormID(), searchType);
	
	String personalType = (String) request.getSession().getAttribute("PersonalType");
	PersonalInfoDataM personalInfoDataM = new PersonalInfoDataM();
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
	
	Vector cardTypeVect = utility.loadCacheByNameByBusClass("CardType", applicationDataM.getBusinessClassId());
	//Vector cardFaceVect = utility.loadCacheByName("CardFace");
	Vector cardFaceVect = utility.loadCacheByNameAndCheckStatus("CardFace", cardInformationDataM.getCardFace());
%>

<table cellpadding="0" cellspacing="0" width="100%" align="center">
<tr>
	<td class="textColS" width="15%"><%=MessageResourceUtil.getTextDescription(request, "CARD_NO") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"CARD_INFO_SUBFORM","application_no")%></Font> :</td>
	<td class="inputCol" width="30%"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(cardInformationDataM.getCardNo(),displayMode,"40","cardNo","textboxReadOnly","readOnly","100") %></td>
	<td class="textColS" width="15%"><input type="hidden" name="card_info" value="<%=ORIGDisplayFormatUtil.displayHTML(cardInformationDataM.getIdNo()) %>"></td>
	<td class="inputCol" width="30%"></td>
</tr>
<tr>
	<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "CARD_TYPE") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"CARD_INFO_SUBFORM","cardType")%></Font> :</td>
	<td class="inputCol"><%=ORIGDisplayFormatUtil.displaySelectTagScriptActionAndCode_ORIG(cardTypeVect,cardInformationDataM.getCardType() ,"cardType",displayMode," style=\"width:80%;\" textbox onChange=\"javascript:getCardFace();\"")%></td>
	<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "CARD_FACE") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"CARD_INFO_SUBFORM","cardFace")%></Font> :</td>
	<td class="inputCol"><span id="div_cardFace"><%=ORIGDisplayFormatUtil.displaySelectTagScriptActionAndCode_ORIG(cardFaceVect, cardInformationDataM.getCardFace() ,"cardFace",displayMode," style=\"width:80%;\" textbox")%></span></td>
</tr>
<tr>
	<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "EMBOSS_NAME") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"CARD_INFO_SUBFORM","embossName")%></Font> :</td>
	<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(cardInformationDataM.getEmbossName(),displayMode,"40","embossName","textbox","","100") %></td>
	<td class="inputCol" colspan="2">&nbsp;</td>
</tr>
</table>

<script language="JavaScript" type="text/JavaScript">
function getCardFace(){
	var cardType = appFormName.cardType.value;
	var server = '<%="http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()%>';
	ajax(server+"/AjaxServlet?ClassName=GetCardFaceFromCardType&cardType="+cardType+"&displayMode="+"<%=displayMode%>", displayInnerHtml, "GET");
}
function defaultCardFaceOnload(){
	if (appFormName.cardFace!=undefined && appFormName.cardType!=undefined){
		getCardFace();
		setTimeout("appFormName.cardFace.value = '<%=ORIGDisplayFormatUtil.displayHTML(cardInformationDataM.getCardFace())%>';", 1000);
	}
}
defaultCardFaceOnload();
</script>