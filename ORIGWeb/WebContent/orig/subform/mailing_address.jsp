

<%@ page import="java.util.*" %>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="com.eaf.orig.shared.model.PersonalInfoDataM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>
<%@ page import="com.eaf.orig.shared.model.ApplicationDataM" %>
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
	String displayMode = formUtil.getDisplayMode("MAILING_SUBFORM", ORIGUser.getRoles(), applicationDataM.getJobState(), ORIGForm.getFormID(), searchType);	
	
	MessageResourceUtil msgUtil = MessageResourceUtil.getInstance();
//	String displayMode = ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT;
	ORIGUtility utility = new ORIGUtility();
	
	String personalType = (String) request.getSession().getAttribute("PersonalType");
	PersonalInfoDataM personalInfoDataM;
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
	System.out.println("personalInfoDataM.getCustomerType() = "+personalInfoDataM.getCustomerType());
	Vector mailingVect = utility.getAddressByCustomerType(personalInfoDataM.getCustomerType());
%>

<table cellpadding="" cellspacing="1" width="100%">
	<tr align="left">
		<td class="textColS" width="20%" nowrap="nowrap"><%=MessageResourceUtil.getTextDescription(request, "MAILING_INDICATION") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"MAILING_SUBFORM","mailing_address")%></Font> :</td>
		<td class="inputCol" width="80%"><%=ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(mailingVect,personalInfoDataM.getMailingAddress(),"mailing_address",displayMode,"style=\"width:32%;\" ") %></td>
	</tr>
</table>