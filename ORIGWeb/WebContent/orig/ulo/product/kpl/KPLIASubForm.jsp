<%@page import="com.eaf.core.ulo.common.util.SessionControl"%>
<%@page import="com.eaf.orig.ulo.model.control.FlowControlDataM"%>
<%@page import="com.eaf.core.ulo.common.properties.ListBoxControl"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.CardDataM"%>
<%@page import="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.LoanDataM"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.engine.SQLQueryEngine"%>
<%@page import="com.eaf.core.ulo.common.properties.CacheControl"%>
<%@page import="com.eaf.core.ulo.common.util.FormEffects"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM" />
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler" />
<%
	String subformId = "K_PERSONAL_LOAN_SUBFORM";
	Logger logger = Logger.getLogger(this.getClass());
	FlowControlDataM flowControl = (FlowControlDataM) request.getSession().getAttribute(SessionControl.FlowControl);
	String ACTION_TYPE = flowControl.getActionType();
	
	String PRODUCT_K_PERSONAL_LOAN = SystemConstant.getConstant("PRODUCT_K_PERSONAL_LOAN");
	String DISPLAY_PRODUCT_FINAL_APP_DECISION[] = SystemConstant.getArrayConstant("DISPLAY_PRODUCT_FINAL_APP_DECISION");
	ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
	if (null == applicationGroup) {
		applicationGroup = new ApplicationGroupDataM();
	}
	String APPLICATION_LEVEL = SystemConstant.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
	String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	String CACHE_BUSINESS_CLASS = SystemConstant.getConstant("CACHE_BUSINESS_CLASS");
	
	ArrayList<ApplicationDataM> applications = new ArrayList<ApplicationDataM>();
	if(SystemConstant.lookup("ACTION_TYPE_ENQUIRY",ACTION_TYPE)){
		applications=applicationGroup.filterDisplayEnquiryApplicationsProduct(PRODUCT_K_PERSONAL_LOAN, null);
	}else{
		applications= applicationGroup.filterDisplayApplicationsProduct(PRODUCT_K_PERSONAL_LOAN,null);

	}
	String CACHE_CARD_INFO = SystemConstant.getConstant("CACHE_CARD_INFO");
	String FIELD_ID_PRODUCT_TYPE = SystemConstant.getConstant("FIELD_ID_PRODUCT_TYPE");
	FormUtil formUtil = new FormUtil(subformId, request);
	FormEffects formEffect = new FormEffects(subformId,request);
%>

<%
	if (!Util.empty(applications)) {
		int row = 0;
		int size = applications.size();
		for (ApplicationDataM applicationItem : applications) {
			row++;
// 			if (applicationItem.isDeleteFlag()) {
// 				continue;
// 			}
			String uniqueId = applicationItem.getApplicationRecordId();
			PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfoRelation(uniqueId, PERSONAL_TYPE_APPLICANT,
					APPLICATION_LEVEL);
			if (null == personalInfo) {
				personalInfo = new PersonalInfoDataM();
			}
			String loadActionJS = "";
			String deleteActionJS = "onclick=deleteProductActionJS('" + uniqueId + "')";
			CardDataM cardinfo = applicationItem.getCard();
			logger.debug("applicationItem.getBusinessClassId() >> " + applicationItem.getBusinessClassId());
// 			String KPLDesc = CacheControl.getName(CACHE_BUSINESS_CLASS, "BUS_CLASS_ID", applicationItem.getBusinessClassId(),"BUS_CLASS_DESC");
			String kplProduct = (null != applicationItem.getBusinessClassId())?applicationItem.getBusinessClassId().split("\\_")[1]:null;
			String productDesc = ListBoxControl.getName(FIELD_ID_PRODUCT_TYPE, kplProduct);
%>
	<tr class="kpl product<%= row == 1 ? " top" : "" %><%= row == size ? " bottom" : "" %>">
		<td>
			<%=HtmlUtil.icon("DELETE_PRODUCT_KPL","DELETE_PRODUCT_KPL","btnsmall_delete",deleteActionJS,formEffect) %>
		</td>
		<td><%= productDesc %></td>
		<td></td>
		<td></td>
		<td><%= LabelUtil.getText(request, "PROJECT_CODE") %> : <%= applicationItem.getProjectCode() != null ? applicationItem.getProjectCode() : "" %></td>
		<td></td>
	</tr>
<% }} %>
