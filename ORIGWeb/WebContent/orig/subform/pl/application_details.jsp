<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.Vector"%>
<%@ page import="org.apache.log4j.Logger"%>
<%@ page import="com.eaf.orig.shared.util.OrigUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.DataFormatUtility"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.PLMessageResourceUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.OrigBusinessClassUtil"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLApplicationDataM" %>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGFormUtil"%>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLSaleInfoDataM"%>
<%@ page import="com.eaf.orig.shared.model.ProcessMenuM"%>
<%@ page import="com.eaf.orig.ulo.pl.constant.WorkflowConstant"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="PLORIGForm" scope="session" class="com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler"/>
<jsp:useBean id="searchType" scope="session" class="java.lang.String"/>

<script type="text/javascript" src="orig/js/subform/pl/applicant_details.js"></script>
<% 
	Logger logger = Logger.getLogger(this.getClass());
	
	ProcessMenuM menuM = (ProcessMenuM) request.getSession().getAttribute("CurrentMenuM");
	if(menuM == null)menuM = new ProcessMenuM();
	PLApplicationDataM applicationM = PLORIGForm.getAppForm();
			
	if(applicationM == null){
		applicationM = new PLApplicationDataM();	
	}
	
	ORIGCacheUtil utility = new ORIGCacheUtil();
	
	String SaleType = applicationM.getSaleType();
		
	PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT); 
	
	//Get Display Mode
	ORIGFormUtil formUtil = new ORIGFormUtil();    
	String displayMode = formUtil.getDisplayMode("APPLICATION_DETAILS_SUBFORM", ORIGUser.getRoles(), applicationM.getJobState(), PLORIGForm.getFormID(), searchType);
	
	String ChannelMode = displayMode;
	String displayDropDownDC = displayMode;
	String productFeatureMode = displayMode;
	
	String role = ORIGUser .getCurrentRole();
	if(ORIGUser.getRoles().contains(OrigConstant.ROLE_DE)){
		ChannelMode = HTMLRenderUtil.DISPLAY_MODE_EDIT;
	}else if(OrigConstant.BusClass.FCP_KEC_IC.equals(applicationM.getBusinessClassId())
				||OrigConstant.BusClass.FCP_KEC_DC.equals(applicationM.getBusinessClassId())){
		ChannelMode = HTMLRenderUtil.DISPLAY_MODE_EDIT;
	}
	if(OrigConstant.ROLE_DC.equalsIgnoreCase(role) && !OrigUtil.isEmptyString(searchType) 
			&& !searchType.equals(OrigConstant.SEARCH_TYPE_ENQUIRY)&& !searchType.equals(OrigConstant.SEARCH_TYPE_CASH_DAY5)){
		displayDropDownDC = HTMLRenderUtil.DISPLAY_MODE_EDIT;
		productFeatureMode = HTMLRenderUtil.DISPLAY_MODE_EDIT;
	}
	PLSaleInfoDataM saleInfoM = applicationM.getSaleInfo();
	if(null==saleInfoM){
		saleInfoM = new PLSaleInfoDataM();
	}
	
	//Praisan Khunkaew for manual check Increase/Decrease
// 	if(role!=null && role.equalsIgnoreCase(OrigConstant.ROLE_DC) && 
// 		OrigBusinessClassUtil.getInstance().isContainsBusGroup(applicationM, OrigConstant.BusGroup.KEC_ICDC_FLOW) && 
// 			!OrigUtil.isEmptyString(applicationM.getProductFeature())){
// 		productFeatureMode = HTMLRenderUtil.DISPLAY_MODE_VIEW;
// 	}	
	
	if(OrigConstant.BusClass.FCP_KEC_IC.equals(applicationM.getBusinessClassId())
		|| OrigConstant.BusClass.FCP_KEC_DC.equals(applicationM.getBusinessClassId())){
		productFeatureMode = HTMLRenderUtil.DISPLAY_MODE_VIEW;
	}else if(WorkflowConstant.TODO_LIST_ID.FU_INBOX.equals(menuM.getMenuID()) 
			|| WorkflowConstant.TODO_LIST_ID.FU_ICDC_INBOX.equals(menuM.getMenuID())){
		productFeatureMode = HTMLRenderUtil.DISPLAY_MODE_EDIT;
	}	
	
	Vector vSaleType = DataFormatUtility.getSaleTypebyGroup(SaleType, displayDropDownDC);
		
	logger.debug("ChannelMode >> "+ChannelMode);
	logger.debug("DisplayDropDownDC >> "+displayDropDownDC);
	logger.debug("productFeatureMode >> "+productFeatureMode);
%>
<table class="FormFrame">
	<tr> 
		<td class="textR" nowrap="nowrap" width="20%"><%=PLMessageResourceUtil.getTextDescription(request, "REFERENCE_ID", ORIGUser, personalM.getCustomerType(), "APPLICATION_DETAILS_SUBFORM", "refNo") %>&nbsp;:&nbsp;</td>
		<td class="inputL"  width="25%" ><%=HTMLRenderUtil.replaceNull(applicationM.getRefNo())%></td> 
		<td class="textR" nowrap="nowrap" width="25%" ><%=PLMessageResourceUtil.getTextDescription(request, "APPLICATION_NO", ORIGUser, personalM.getCustomerType(), "APPLICATION_DETAILS_SUBFORM", "appNo") %>&nbsp;:&nbsp;</td>
		<td class="inputL" width="30%"><%=HTMLRenderUtil.replaceNull(applicationM.getApplicationNo())%></td>
	</tr>
	
	<tr> 
		<td class="textR" nowrap="nowrap"><%=PLMessageResourceUtil.getTextDescription(request, "RECEIVED_TIME", ORIGUser, personalM.getCustomerType(), "APPLICATION_DETAILS_SUBFORM", "receiveTime") %>&nbsp;:&nbsp;</td>
		<td class="inputL"><%=DataFormatUtility.dateTimetoStringForThai(applicationM.getAppDate())%></td>
		<td colspan="2"></td>
	</tr>	
	
	<tr> 
		<td class="textR" nowrap="nowrap"><%=PLMessageResourceUtil.getTextDescription(request, "PRODUCT_DOMAIN", ORIGUser, personalM.getCustomerType(), "APPLICATION_DETAILS_SUBFORM", "productdomain")%><%=HTMLRenderUtil.getMandatory_ORIG(personalM.getCustomerType(),ORIGUser.getRoles(),"APPLICATION_DETAILS_SUBFORM","productdomain")%>&nbsp;:&nbsp;</td>
		<td class="inputCol"><%=HTMLRenderUtil.displaySelectTagScriptActionORIG("MainProductDomain", applicationM.getProductDomain(), "productdomain", "VIEW", "")%></td>
		
		<td class="textR" nowrap="nowrap"><%=PLMessageResourceUtil.getTextDescription(request, "PRODUCT_GROUP", ORIGUser, personalM.getCustomerType(), "APPLICATION_DETAILS_SUBFORM", "productgroup")%><%=HTMLRenderUtil.getMandatory_ORIG(personalM.getCustomerType(),ORIGUser.getRoles(),"APPLICATION_DETAILS_SUBFORM","productgroup")%>&nbsp;:&nbsp;</td>
		<td class="inputL" id="div_productgroup"><%=HTMLRenderUtil.displaySelectTagScriptActionORIG("MainProductGroup", applicationM.getProductGroup(), "productgroup", "VIEW", "")%></td>
		
	</tr>	
	
	<tr>
		<td class="textR" nowrap="nowrap"><%=PLMessageResourceUtil.getTextDescription(request, "PRODUCT_FAMILY", ORIGUser, personalM.getCustomerType(), "APPLICATION_DETAILS_SUBFORM", "productfamily")%><%=HTMLRenderUtil.getMandatory_ORIG(personalM.getCustomerType(),ORIGUser.getRoles(),"APPLICATION_DETAILS_SUBFORM","productfamily")%>&nbsp;:&nbsp;</td>
		<td class="inputL" id="div_productfamily"><%=HTMLRenderUtil.displaySelectTagScriptActionORIG("MainProductFamily", applicationM.getProductFamily(), "productfamily", "VIEW", "")%></td> 
		<td class="textR" nowrap="nowrap"><%=PLMessageResourceUtil.getTextDescription(request, "PRODUCT_PIM", ORIGUser, personalM.getCustomerType(), "APPLICATION_DETAILS_SUBFORM", "product")%><%=HTMLRenderUtil.getMandatory_ORIG(personalM.getCustomerType(),ORIGUser.getRoles(),"APPLICATION_DETAILS_SUBFORM","product")%>&nbsp;:&nbsp;</td>
		<td class="inputL" id="div_product"><%=HTMLRenderUtil.displaySelectTagScriptActionORIG("MainProduct", applicationM.getProduct(), "product", "VIEW", "")%></td>
	</tr>	
	
	<tr> 
		<td class="textR" nowrap="nowrap"><%=PLMessageResourceUtil.getTextDescription(request, "SALE_TYPE", ORIGUser, personalM.getCustomerType(), "APPLICATION_DETAILS_SUBFORM", "saleType") %><%=HTMLRenderUtil.getMandatory_ORIG(personalM.getCustomerType(),ORIGUser.getRoles(),"APPLICATION_DETAILS_SUBFORM","saleType")%>&nbsp;:&nbsp;</td>
		<td class="inputL" id="div_saleType"><%=HTMLRenderUtil.displaySelectTagScriptAction_ORIG(vSaleType, applicationM.getSaleType(),"saleType",displayDropDownDC,"")%></td>
		<td class="textR">
		<%=PLMessageResourceUtil.getTextDescription(request, "PRODUCT_ATTRIBUTE")%><%=HTMLRenderUtil.getMandatory_ORIG(personalM.getCustomerType(), ORIGUser.getRoles(), "APPLICATION_DETAILS_SUBFORM", "product_feature")%>&nbsp;:&nbsp;
		</td>
		<td class="inputL" id="tr-product_feature"><%=HTMLRenderUtil.displaySelectTagScriptAction_ORIG(35, applicationM.getBusinessClassId(), applicationM.getProductFeature(), "product_feature", productFeatureMode, "")%></td>
	</tr>
	
	<tr>
		<td class="textR" nowrap="nowrap"><%=PLMessageResourceUtil.getTextDescription(request, "CHANNEL", ORIGUser, personalM.getCustomerType(), "APPLICATION_DETAILS_SUBFORM","channel") %><%=HTMLRenderUtil.getMandatory_ORIG(personalM.getCustomerType(),ORIGUser.getRoles(),"APPLICATION_DETAILS_SUBFORM","channel")%>&nbsp;:&nbsp;</td>
		<td class="inputL" id="div_customerType"><%=HTMLRenderUtil.displaySelectTagScriptAction_ORIG(50, applicationM.getBusinessClassId(), applicationM.getApplyChannel(), "channel", displayDropDownDC, "")%></td> <%--onChange=\"clearSellInfo()\" --%>
	</tr>		
</table>

<%=HTMLRenderUtil.displayHiddenField(role, "applicant_role")%>
<%=HTMLRenderUtil.displayHiddenField(saleInfoM.getSalesBranchCode(), "cover_sale_branch")%>
<%=HTMLRenderUtil.displayHiddenField(displayMode, "applicant_displaymode")%>
<%=HTMLRenderUtil.displayHiddenField(displayDropDownDC, "applicantDC_displaymode")%>