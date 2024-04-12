<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.orig.ulo.pl.constant.WorkflowConstant"%>
<%@page import="com.eaf.orig.ulo.pl.app.utility.ORIGLogic"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLSaleInfoDataM"%>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM"%>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLApplicationDataM"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil"%>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGFormUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.PLMessageResourceUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@ page import="com.eaf.orig.shared.model.ProcessMenuM"%>

<script type="text/javascript" src="orig/js/subform/pl/sell_information.js"></script>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM" />
<jsp:useBean id="PLORIGForm" scope="session" class="com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler" /> 
<% 
	org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger("orig/subform/pl/sell_information.jsp");
	
	PLApplicationDataM applicationM = PLORIGForm.getAppForm();
	PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
	  
	ORIGFormUtil formUtil = new ORIGFormUtil();
	ORIGCacheUtil origc = ORIGCacheUtil.getInstance();
	String searchType = (String) request.getSession().getAttribute("searchType");
	ProcessMenuM menuM = (ProcessMenuM) request.getSession().getAttribute("CurrentMenuM");
	if(menuM == null)menuM = new ProcessMenuM();
	String displayMode = formUtil.getDisplayMode("SELL_INFORMATION_SUBFORM", ORIGUser.getRoles(), applicationM.getJobState(), PLORIGForm.getFormID(), searchType);
    String displayModeCashDay5 = displayMode;
	PLSaleInfoDataM saleInfoM = applicationM.getSaleInfo();
	
	if(null == saleInfoM){
		saleInfoM = new PLSaleInfoDataM();
	}
	String CACHE_SALE_INFO = SystemConstant.getConstant("CACHE_SALE_INFO");
	String refName = origc.getORIGCacheDisplayNameFormDB(saleInfoM.getRefName(), CACHE_SALE_INFO);
	String BranchRefName = origc.getORIGCacheDisplayNameFormDB(saleInfoM.getRefBranchCode(), "BranchInfo");
	String sellerName = origc.getORIGCacheDisplayNameFormDB(saleInfoM.getSalesName(), CACHE_SALE_INFO);
	String sellerBranchName = origc.getORIGCacheDisplayNameFormDB(saleInfoM.getSalesBranchCode(), "BranchInfo");
	String cashDayoneName =  origc.getORIGCacheDisplayNameFormDB(saleInfoM.getCashDayOneName(), CACHE_SALE_INFO);
	String cashDayOneBranchName = origc.getORIGCacheDisplayNameFormDB(saleInfoM.getCashDayOneBranchCode(), "BranchInfo");
	  	
	if(OrigConstant.SEARCH_TYPE_CASH_DAY5.equals(searchType)){
		displayMode = HTMLRenderUtil.DISPLAY_MODE_VIEW;
	} 
	String displayModeBranch = displayMode;
	if(WorkflowConstant.TODO_LIST_ID.FU_INBOX.equals(menuM.getMenuID()) 
			|| WorkflowConstant.TODO_LIST_ID.FU_ICDC_INBOX.equals(menuM.getMenuID())){
		displayModeBranch = HTMLRenderUtil.DISPLAY_MODE_VIEW;
	}
	log.debug("@@@@@ displayModeBranch:" + displayModeBranch);     
%>
<table class="FormFrame" id="sell_information">
	<tr>
    	<td></td>
        <td class="inputL" colspan="3">
        	<%=PLMessageResourceUtil.getTextDescription(request, "CUSTOMER_WARRANTY")%>&nbsp;:&nbsp;
        	<%=HTMLRenderUtil.displayCheckBoxTagDesc(saleInfoM.getSalesGuarantee(),"Y", displayMode, "certification", " ", MessageResourceUtil.getTextDescription(request,"DOC_APPLICANT_CHECK_DESC"))%>
        </td>
    </tr>
    <tr>                                    
		<td class="textR"  nowrap="nowrap" width="15%" >
			<%=PLMessageResourceUtil.getTextDescription(request, "RECOMMEND_NAME",ORIGUser,personalM.getCustomerType(),"SELL_INFORMATION_SUBFORM","ref_recommend_title")%>
			<%=HTMLRenderUtil.getMandatory_ORIG(personalM.getCustomerType(),ORIGUser.getRoles(),"SELL_INFORMATION_SUBFORM","ref_recommend_title")%>&nbsp;:&nbsp;
		</td>
		<td class="inputL" nowrap="nowrap" width="25%" >
			<%=HTMLRenderUtil.displayPopUpTagNotSetScriptAction(saleInfoM.getRefName(),displayMode,"8","ref_recommend_title","textbox_code_sell","","8","...","buttonNew") %>&nbsp;
			<%=HTMLRenderUtil.displayInputTagScriptAction(refName, "VIEW", "25", "ref_name_th","textbox textboxReadOnly","","25")%>
			<%=HTMLRenderUtil.displayHiddenField(saleInfoM.getRefTitlename(), "ref_code") %>
		</td>
		<td class="textR"  nowrap="nowrap" width="20%" >
			<%=PLMessageResourceUtil.getTextDescription(request, "BRANCH_CODE",ORIGUser,personalM.getCustomerType(),"SELL_INFORMATION_SUBFORM","ref_branch_code")%>
			<%=HTMLRenderUtil.getMandatory_ORIG(personalM.getCustomerType(),ORIGUser.getRoles(),"SELL_INFORMATION_SUBFORM","ref_branch_code")%>&nbsp;:&nbsp;
		</td>
		<td class="inputL" nowrap="nowrap" width="30%" >
			<%=HTMLRenderUtil.displayPopUpTagNotSetScriptAction(saleInfoM.getRefBranchCode(),displayMode,"8","ref_branch_code","textboxCode","","8","...","buttonNew")%>&nbsp;
			<%=HTMLRenderUtil.displayInputTagScriptAction(BranchRefName, "VIEW", "25", "ref_branch_name_th","textbox textboxReadOnly","","")%>
			<%=HTMLRenderUtil.displayHiddenField("", "ref_branch_Title") %>
		</td>
	</tr> 
    <tr>
		<td class="textR"  nowrap="nowrap"><%=MessageResourceUtil.getTextDescription(request, "TELEPHONE") %>&nbsp;:&nbsp;</td>
		<td class="inputL" nowrap="nowrap"><%=HTMLRenderUtil.displayInputTagScriptAction(saleInfoM.getRefPhoneNo(), displayMode, "10", "ref_telephone","textbox"," onkeypress= \"return keyPressInteger(event)\" ","10")%></td>
		<td class="textR"  nowrap="nowrap"><%=MessageResourceUtil.getTextDescription(request, "SELL_FAX") %>&nbsp;:&nbsp;</td>
		<td class="inputL" nowrap="nowrap"><%=HTMLRenderUtil.displayInputTagScriptAction(saleInfoM.getRefFaxNo(), displayMode, "9", "ref_fax_no","textbox"," onkeypress= \"return keyPressInteger(event)\" ","9")%></td>
	</tr>
	<tr>
		<td class="textR" nowrap="nowrap" class="seller_mandatory">
			<%=PLMessageResourceUtil.getTextDescription(request,"SELLER",ORIGUser,personalM.getCustomerType(),"SELL_INFORMATION_SUBFORM","seller_title")%>
<%-- 		<%=HTMLRenderUtil.getMandatory_ORIG(personalM.getCustomerType(),ORIGUser.getRoles(),"SELL_INFORMATION_SUBFORM","seller_title")%> --%>
			<%=ORIGLogic.MandatoryFieldSeller(applicationM.getBusinessClassId(), ORIGUser.getCurrentRole())%>
			<span class="seller_mandatory_check"></span>&nbsp;:&nbsp;
		</td>
		<td class="inputL" nowrap="nowrap">
			<%=HTMLRenderUtil.displayPopUpTagNotSetScriptAction(saleInfoM.getSalesName(),displayMode,"8","seller_title","textbox_code_sell","","8","...","buttonNew") %>&nbsp;
			<%=HTMLRenderUtil.displayInputTagScriptAction(sellerName, "VIEW", "25", "seller_name_th","textbox textboxReadOnly","","25")%>
		</td>
		<td class="textR" nowrap="nowrap" class="seller_mandatory">
			<%=PLMessageResourceUtil.getTextDescription(request, "BRANCH_CODE",ORIGUser,personalM.getCustomerType(),"SELL_INFORMATION_SUBFORM","seller_branch_code")%>
<%-- 		<%=HTMLRenderUtil.getMandatory_ORIG(personalM.getCustomerType(),ORIGUser.getRoles(),"SELL_INFORMATION_SUBFORM","seller_branch_code")%> --%>
			<%=ORIGLogic.ManadatoryFiledSellerBranchCode(applicationM.getBusinessClassId(), ORIGUser.getCurrentRole())%>
			<span class="seller_mandatory_check"></span>&nbsp;:&nbsp;
		</td>
		<td class="inputL" nowrap="nowrap">
			<%=HTMLRenderUtil.displayPopUpTagNotSetScriptAction(saleInfoM.getSalesBranchCode(),displayModeBranch,"8","seller_branch_code","textboxCode","","8","...","buttonNew")%>&nbsp;
			<%=HTMLRenderUtil.displayInputTagScriptAction(sellerBranchName, "VIEW", "25", "seller_branch_name","textbox textboxReadOnly","","25")%>
			<%=HTMLRenderUtil.displayHiddenField("", "seller_branch_Title") %>
		</td>
	</tr>
	<tr>
		<td class="textR" nowrap="nowrap"><%=MessageResourceUtil.getTextDescription(request, "TELEPHONE") %>&nbsp;:&nbsp;</td>
		<td class="inputL" nowrap="nowrap"><%=HTMLRenderUtil.displayInputTagScriptAction(saleInfoM.getSalesPhoneNo(), displayMode, "10", "sell_telephone","textbox"," onkeypress= \"return keyPressInteger(event)\" ","10")%></td>
		<td class="textR" nowrap="nowrap"><%=MessageResourceUtil.getTextDescription(request, "SELL_FAX") %>&nbsp;:&nbsp;</td>
		<td class="inputL" nowrap="nowrap"><%=HTMLRenderUtil.displayInputTagScriptAction(saleInfoM.getSalesFaxNo(), displayMode, "9", "sell_fax_no","textbox"," onkeypress= \"return keyPressInteger(event)\" ","9")%></td>
	</tr>
	<tr>
		<td class="textR" nowrap="nowrap">
			<%=PLMessageResourceUtil.getTextDescription(request, "SERVICE_SALE_NAME",ORIGUser,personalM.getCustomerType(),"SELL_INFORMATION_SUBFORM","service_seller_title")%>
			<a class="service_check"></a>&nbsp;:&nbsp;
		</td>
		<td class="inputL" nowrap="nowrap">
			<%=HTMLRenderUtil.displayPopUpTagNotSetScriptAction(saleInfoM.getCashDayOneName(),displayModeCashDay5,"8","service_seller_title","textbox_code_sell","","8","...","buttonNew") %>&nbsp;
			<%=HTMLRenderUtil.displayInputTagScriptAction(cashDayoneName, "VIEW", "25", "service_seller_name_th","textbox textboxReadOnly","","25")%>
		</td>
		<td class="textR" nowrap="nowrap">
			<%=PLMessageResourceUtil.getTextDescription(request, "BRANCH_CODE",ORIGUser,personalM.getCustomerType(),"SELL_INFORMATION_SUBFORM","service_seller_branch_code")%><a class="service_check"></a>&nbsp;:&nbsp;
		</td>
		<td class="inputL" nowrap="nowrap">
			<%=HTMLRenderUtil.displayPopUpTagNotSetScriptAction(saleInfoM.getCashDayOneBranchCode(),displayModeCashDay5,"8","service_seller_branch_code","textboxCode","","8","...","buttonNew")%>&nbsp;
			<%=HTMLRenderUtil.displayInputTagScriptAction(cashDayOneBranchName, "VIEW", "25", "service_seller_branch_name","textbox textboxReadOnly","","25")%>
			<%=HTMLRenderUtil.displayHiddenField("", "seller_branch_Title") %>
		</td>
	</tr>
	<tr>
		<td class="textR" nowrap="nowrap"><%=MessageResourceUtil.getTextDescription(request, "TELEPHONE") %>&nbsp;:&nbsp;</td>
		<td class="inputL" nowrap="nowrap"><%=HTMLRenderUtil.displayInputTagScriptAction(saleInfoM.getCashDayOnePhoneNo(), displayModeCashDay5, "10", "service_telephone","textbox"," onkeypress= \"return keyPressInteger(event)\" ","10")%></td>
		<td class="textR" nowrap="nowrap"><%=MessageResourceUtil.getTextDescription(request, "SELL_FAX") %>&nbsp;:&nbsp;</td>
		<td class="inputL" nowrap="nowrap"><%=HTMLRenderUtil.displayInputTagScriptAction(saleInfoM.getCashDayOneFax(), displayModeCashDay5, "9", "service_fax","textbox"," onkeypress= \"return keyPressInteger(event)\" ","9")%></td>
	</tr> 
	<tr>
		<td class="textR" nowrap="nowrap"><%=MessageResourceUtil.getTextDescription(request, "MORE_INFO") %>&nbsp;:&nbsp;</td>
		<td class="inputL" nowrap="nowrap"><%=HTMLRenderUtil.displayInputTag(saleInfoM.getRemark(), displayModeCashDay5,"250", "more_info","textbox")%></td>
		<td class="textR" nowrap="nowrap"></td>
		<td class="inputL" nowrap="nowrap"></td>
	</tr>                  
</table>
<%=HTMLRenderUtil.displayHiddenField(applicationM.getBusinessClassId(), "sell_bussclass")%>