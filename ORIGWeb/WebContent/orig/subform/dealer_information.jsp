<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="com.eaf.orig.shared.model.ApplicationDataM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="com.eaf.orig.shared.model.PersonalInfoDataM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGFormUtil"%>
<%@ page import="com.eaf.orig.profile.model.UserDetailM"%>
<%@ page import="java.util.*" %>


<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.formcontrol.view.form.ORIGFormHandler"/>

<% 
	ApplicationDataM applicationDataM = ORIGForm.getAppForm();
	if(applicationDataM == null){
		applicationDataM = new ApplicationDataM();
	}
	ORIGFormUtil formUtil = new ORIGFormUtil();
	String searchType = (String) request.getSession().getAttribute("searchType");
	String displayMode = formUtil.getDisplayMode("DRALER_SUBFORM", ORIGUser.getRoles(), applicationDataM.getJobState(), ORIGForm.getFormID(), searchType);	

	ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
	ORIGUtility utility = new ORIGUtility();
	
	String dealer = cacheUtil.getORIGCacheDisplayNameFormDB(applicationDataM.getDealerCode(), OrigConstant.CacheName.CACHE_NAME_DEALER);
	String salesMan = cacheUtil.getORIGCacheDisplayNameFormDB(applicationDataM.getSalesManCode(), OrigConstant.CacheName.CACHE_NAME_SELLER);
	String seller = cacheUtil.getORIGCacheDisplayNameFormDB(applicationDataM.getSellerCode(), OrigConstant.CacheName.CACHE_NAME_SELLER);
	
	PersonalInfoDataM personalInfoDataM = utility.getPersonalInfoByType(applicationDataM, com.eaf.orig.shared.constant.OrigConstant.PERSONAL_TYPE_APPLICANT);
	
	UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
	Vector userRoles = userM.getRoles();
	String role = (String)userRoles.get(0);
%>

<table cellpadding="" cellspacing="1" width="100%" align="center">
	<tr>
		<td class="textColS" width="20%"><%=MessageResourceUtil.getTextDescription(request, "DEALER") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"DRALER_SUBFORM","dealer")%></Font> :</td>
		<td class="inputCol" width="80%"><%=ORIGDisplayFormatUtil.displayPopUpTagScriptAction(applicationDataM.getDealerCode(),displayMode,"5","dealer","50","dealer_desc","textbox","","50","...","button_text","LoadDealer",ORIGDisplayFormatUtil.displayHTML(dealer),OrigConstant.CacheName.CACHE_NAME_DEALER) %></td>
	</tr>
	<%if(!OrigConstant.ROLE_CMR.equalsIgnoreCase(role)){ 
	%>
	<tr>
		<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "SELLER") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"DRALER_SUBFORM","seller")%></Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displayPopUpTagScriptAction(applicationDataM.getSellerCode(),displayMode,"5","seller","50","seller_desc","textbox","","50","...","button_text","LoadSeller",ORIGDisplayFormatUtil.displayHTML(seller),OrigConstant.CacheName.CACHE_NAME_SELLER) %></td>
	</tr>
	<tr>
		<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "SALESMAN") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"DRALER_SUBFORM","salesman")%></Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displayPopUpTagScriptAction(applicationDataM.getSalesManCode(),displayMode,"5","salesman","50","salesman_desc","textbox","","50","...","button_text","LoadSalesman",ORIGDisplayFormatUtil.displayHTML(salesMan),OrigConstant.CacheName.CACHE_NAME_SELLER) %></td>
	</tr>
	<%} %>
</table>