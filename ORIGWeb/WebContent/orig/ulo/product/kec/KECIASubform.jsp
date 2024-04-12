<%@page import="com.eaf.core.ulo.common.util.SessionControl"%>
<%@page import="com.eaf.orig.ulo.model.control.FlowControlDataM"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.ListBoxControl"%>
<%@page import="com.eaf.core.ulo.common.properties.CacheControl"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.CardDataM"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.util.FormEffects"%>
<%@page import="com.eaf.orig.ulo.app.view.form.subform.product.manual.CardInfoUtil"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>
<!-- <script type="text/javascript" src="orig/ulo/product/js/KExpressCashSubForm.js"></script> -->
<%
	String subformId = "K_EXPRESS_CACH_SUBFORM";
	Logger logger = Logger.getLogger(this.getClass());
	FlowControlDataM flowControl = (FlowControlDataM) request.getSession().getAttribute(SessionControl.FlowControl);
	String ACTION_TYPE = flowControl.getActionType();
	String DISPLAY_PRODUCT_FINAL_APP_DECISION[] = SystemConstant.getArrayConstant("DISPLAY_PRODUCT_FINAL_APP_DECISION");
	String PRODUCT_K_EXPRESS_CASH = SystemConstant.getConstant("PRODUCT_K_EXPRESS_CASH");
	String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	String APPLICATION_LEVEL = SystemConstant.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
	String CACHE_BUSINESS_CLASS =SystemConstant.getConstant("CACHE_BUSINESS_CLASS");
	String FIELD_ID_PRODUCT_TYPE = SystemConstant.getConstant("FIELD_ID_PRODUCT_TYPE");
	String DECISION_FINAL_DECISION_CANCEL = SystemConstant.getConstant("DECISION_FINAL_DECISION_CANCEL");
	String DECISION_FINAL_DECISION_REJECT = SystemConstant.getConstant("DECISION_FINAL_DECISION_REJECT");
	ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
	if(null == applicationGroup){
		applicationGroup = new ApplicationGroupDataM();
	}
	ArrayList<ApplicationDataM> applications = new ArrayList<ApplicationDataM>();
	if(SystemConstant.lookup("ACTION_TYPE_ENQUIRY",ACTION_TYPE)){
		applications=applicationGroup.filterDisplayEnquiryApplicationsProduct(PRODUCT_K_EXPRESS_CASH, null);
	}else{
		applications= applicationGroup.filterDisplayApplicationsProduct(PRODUCT_K_EXPRESS_CASH,null);

	}
	String CACHE_CARD_INFO = SystemConstant.getConstant("CACHE_CARD_INFO");	
	String FIELD_ID_CARD_TYPE = SystemConstant.getConstant("FIELD_ID_CARD_TYPE");	
	FormEffects formEffect = new FormEffects(subformId,request);
%>
	<%
		if (!Util.empty(applications)) {
			int row = 0;
			int size = applications.size();
			for (ApplicationDataM applicationItem : applications) {
				row++;
// 				if (applicationItem.isDeleteFlag()) {
// 					continue;
// 				}
				CardDataM card = applicationItem.getCard();
				String uniqueId = applicationItem.getApplicationRecordId();
				String cardTypeId = card.getCardType();
				HashMap<String, Object> cardInfo = CardInfoUtil.getCardInfo(cardTypeId);
				String cardCode = (String)cardInfo.get("CARD_CODE");
				String deleteActionJS = "onclick=deleteProductActionJS('"+uniqueId+"')";
// 				String productDesc = CacheControl.getName(CACHE_BUSINESS_CLASS, "BUS_CLASS_ID", applicationItem.getBusinessClassId(), "BUS_CLASS_DESC") ;
				String productDesc = ListBoxControl.getName(FIELD_ID_PRODUCT_TYPE, applicationItem.getProduct());
				logger.debug("cardCode " + cardInfo);
				String finalDecision = applicationItem.getFinalAppDecision();
	%>
	<tr class="kec product<%= row == 1 ? " top" : "" %><%= row == size ? " bottom" : "" %>">
	<% if(!DECISION_FINAL_DECISION_CANCEL.equals(finalDecision) && !DECISION_FINAL_DECISION_REJECT.equals(finalDecision)){  %>
		<td>
			<%=HtmlUtil.icon("DELETE_PRODUCT_KEC","DELETE_PRODUCT_KEC","btnsmall_delete",deleteActionJS,formEffect) %>
		</td>
	<%}else{ %>
		<td></td>
	<%} %>
		
		<td><%= productDesc %></td>
		<td><%=CacheControl.getName(FIELD_ID_CARD_TYPE,cardCode, "DISPLAY_NAME")%></td>
		<td></td>
		<td><%= LabelUtil.getText(request, "PROJECT_CODE") %> : <%= applicationItem.getProjectCode() != null ? applicationItem.getProjectCode() : "" %></td>
		<td></td>
	</tr>
	<%}}%>
