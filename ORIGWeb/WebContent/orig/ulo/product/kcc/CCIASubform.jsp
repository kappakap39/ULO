<%@page import="com.eaf.core.ulo.common.util.SessionControl"%>
<%@page import="com.eaf.orig.ulo.model.control.FlowControlDataM"%>
<%@page import="com.eaf.core.ulo.common.message.MessageUtil"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Vector"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<%@page import="com.eaf.core.ulo.common.properties.ListBoxControl"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.engine.SQLQueryEngine"%>
<%@page import="com.eaf.core.ulo.common.properties.CacheControl"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.LoanDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.CardDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalRelationDataM"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.util.FormEffects"%>
<%@page
	import="com.eaf.orig.ulo.app.view.form.subform.product.manual.CardInfoUtil"%>
<jsp:useBean id="ORIGUser" scope="session"
	class="com.eaf.orig.profile.model.UserDetailM" />
<jsp:useBean id="ORIGForm" scope="session"
	class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler" />
<!-- <script type="text/javascript" src="orig/ulo/product/js/KCraditCardSubForm.js"></script> -->
<%
	String subformId = "K_CREDIT_CARD_SUBFORM";
	Logger logger = Logger.getLogger(this.getClass());
	FlowControlDataM flowControl = (FlowControlDataM) request.getSession().getAttribute(SessionControl.FlowControl);
	String ACTION_TYPE = flowControl.getActionType();
	ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
	if (null == applicationGroup) {
		applicationGroup = new ApplicationGroupDataM();
	}
	String DISPLAY_PRODUCT_FINAL_APP_DECISION[] = SystemConstant.getArrayConstant("DISPLAY_PRODUCT_FINAL_APP_DECISION");
	String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
	String APPLICATION_LEVEL = SystemConstant.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
	String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	String APPLICATION_CARD_TYPE_BORROWER = SystemConstant.getConstant("APPLICATION_CARD_TYPE_BORROWER");
	String CACHE_CARD_INFO = SystemConstant.getConstant("CACHE_CARD_INFO");
	String CACHE_BUSINESS_CLASS =SystemConstant.getConstant("CACHE_BUSINESS_CLASS");
	String FIELD_ID_CARD_LEVEL = SystemConstant.getConstant("FIELD_ID_CARD_LEVEL");
	String FIELD_ID_APPLICATION_CARD_TYPE = SystemConstant.getConstant("FIELD_ID_APPLICATION_CARD_TYPE");
	String FIELD_ID_CARD_TYPE = SystemConstant.getConstant("FIELD_ID_CARD_TYPE");
	String FIELD_ID_PRODUCT_TYPE = SystemConstant.getConstant("FIELD_ID_PRODUCT_TYPE");
	String DECISION_FINAL_DECISION_CANCEL = SystemConstant.getConstant("DECISION_FINAL_DECISION_CANCEL");
	String DECISION_FINAL_DECISION_REJECT = SystemConstant.getConstant("DECISION_FINAL_DECISION_REJECT");
	
	ArrayList<ApplicationDataM> borrowerApplications = new ArrayList<ApplicationDataM>();
	if(SystemConstant.lookup("ACTION_TYPE_ENQUIRY",ACTION_TYPE)){
		borrowerApplications=applicationGroup.filterDisplayEnquiryApplicationsProduct(PRODUCT_CRADIT_CARD, APPLICATION_CARD_TYPE_BORROWER);
	}else{
		borrowerApplications= applicationGroup.filterDisplayApplicationsProduct(PRODUCT_CRADIT_CARD,APPLICATION_CARD_TYPE_BORROWER);

	}
	FormUtil formUtil = new FormUtil(subformId, request);
	FormEffects formEffect = new FormEffects(subformId,request);
%>

	<%
		if (!Util.empty(borrowerApplications)) {
			int row = 0;
			int size = borrowerApplications.size();
			for (ApplicationDataM borrowerItem : borrowerApplications) {
				row++;
// 				if (borrowerItem.isDeleteFlag()) {
// 					continue;
// 				}
				CardDataM borrowerCard = borrowerItem.getCard();
				String borrowerApplicationCardType = borrowerCard.getApplicationType();
				String borrowerUniqueId = borrowerItem.getApplicationRecordId();
				String borrowerCardTypeId = borrowerCard.getCardType();
				
				HashMap<String, Object> borrowerCardInfo = CardInfoUtil.getCardInfo(borrowerCardTypeId);
				logger.debug("borrowerCardInfo >>" + borrowerCardInfo);
				String cardCodeborrower = (String) borrowerCardInfo.get("CARD_CODE");
				String borrowerCardCode = SQLQueryEngine.display(borrowerCardInfo, "CARD_CODE");
				String borrowerCardLevel = SQLQueryEngine.display(borrowerCardInfo, "CARD_LEVEL");
				String borrowerDeleteCardActionJS = "onclick=deleteProductActionJS('" + borrowerUniqueId + "')";
// 				String productDesc = CacheControl.getName(CACHE_BUSINESS_CLASS, "BUS_CLASS_ID", borrowerItem.getBusinessClassId(), "BUS_CLASS_DESC") ;
				String productDesc = ListBoxControl.getName(FIELD_ID_PRODUCT_TYPE, borrowerItem.getProduct());
				String finalDecision = borrowerItem.getFinalAppDecision();
	%>
	<tr class="cc product<%= row == 1 ? " top" : "" %><%= row == size ? " bottom" : "" %>">
	<% if(!DECISION_FINAL_DECISION_CANCEL.equals(finalDecision) && !DECISION_FINAL_DECISION_REJECT.equals(finalDecision)){  %>
		<td>
			<%=HtmlUtil.icon("DEL_CARDINFO","DEL_CARDINFO","btnsmall_delete",borrowerDeleteCardActionJS,formEffect) %>
		</td>
	<%}else{ %>
		<td></td>
	<%} %>
		
		<td><%= productDesc %></td>
		<td class="nowrap"><%=CacheControl.getName(FIELD_ID_CARD_TYPE, borrowerCardCode, "DISPLAY_NAME")%></td>
		<td class="text-center"><%=CacheControl.getName(FIELD_ID_CARD_LEVEL, borrowerCardLevel, "DISPLAY_NAME")%></td>
		<td><%= LabelUtil.getText(request, "PROJECT_CODE") %> : <%= borrowerItem.getProjectCode() != null ? borrowerItem.getProjectCode() : "" %></td>
		<td class="text-center"><%=CacheControl.displayName(FIELD_ID_APPLICATION_CARD_TYPE, borrowerApplicationCardType)%></td>
	</tr>
	<%
			}
		}
	%>
