<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.core.ulo.common.util.SessionControl"%>
<%@page import="com.eaf.orig.ulo.model.control.FlowControlDataM"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.common.util.FormEffects"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.CacheControl"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.CardDataM"%>
<script type="text/javascript" src="orig/ulo/product/js/KPersonalLoanSubForm.js"></script>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>
<%	
	String formId = "LIST_PRODUCT_FORM_KPL";
	String subformId = "K_PERSONAL_LOAN_SUBFORM";
	Logger logger = Logger.getLogger(this.getClass());
	FlowControlDataM flowControl = (FlowControlDataM) request.getSession().getAttribute(SessionControl.FlowControl);
	String ACTION_TYPE = flowControl.getActionType();
	
	String APPLICATION_LEVEL = SystemConstant.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
	String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	String CACHE_BUSINESS_CLASS =SystemConstant.getConstant("CACHE_BUSINESS_CLASS");
	
	String PRODUCT_K_PERSONAL_LOAN = SystemConstant.getConstant("PRODUCT_K_PERSONAL_LOAN");
	String DISPLAY_PRODUCT_FINAL_APP_DECISION[] = SystemConstant.getArrayConstant("DISPLAY_PRODUCT_FINAL_APP_DECISION");
	ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
	if(null == applicationGroup){
		applicationGroup = new ApplicationGroupDataM();
	}

	ArrayList<ApplicationDataM> applications = new ArrayList<ApplicationDataM>();
	if(SystemConstant.lookup("ACTION_TYPE_ENQUIRY",ACTION_TYPE)){
		applications=applicationGroup.filterDisplayEnquiryApplicationsProductMaxLifeCycle(PRODUCT_K_PERSONAL_LOAN, null);
	}else{
		applications= applicationGroup.filterDisplayApplicationsProduct(PRODUCT_K_PERSONAL_LOAN,null);

	}
	String CACHE_CARD_INFO = SystemConstant.getConstant("CACHE_CARD_INFO");		
	FormUtil formUtil = new FormUtil(formId,subformId,request);
	FormEffects formEffect = new FormEffects(subformId,request);
	formEffect.setFormId(formId);
%>
<table class="table table-striped table-hover kxproduct">
<%if(!Util.empty(applications)){
	for(ApplicationDataM applicationItem:applications){
// 		if (applicationItem.isDeleteFlag()) {
// 			continue;
// 		}
		String uniqueId = applicationItem.getApplicationRecordId();
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfoRelation(uniqueId,PERSONAL_TYPE_APPLICANT,APPLICATION_LEVEL);
		if(null == personalInfo){
			personalInfo = new PersonalInfoDataM();
		}
		String loadActionJS = "";
		String deleteActionJS ="onclick=deleteKPLInfoActionJS('"+uniqueId+"','"+SystemConstant.getConstant("APPLICATION_CARD_TYPE_BORROWER")+"')";
		CardDataM cardinfo = applicationItem.getCard();
		logger.debug("applicationItem.getBusinessClassId() >> "+applicationItem.getBusinessClassId());
		String KPLDesc = CacheControl.getName(CACHE_BUSINESS_CLASS, "BUS_CLASS_ID", applicationItem.getBusinessClassId(), "BUS_CLASS_DESC") ;
		logger.debug("KPLDesc >> "+KPLDesc);
%>
	<tr>
		<td width="7%">
			<%=HtmlUtil.icon("DEL_CARDINFO", "DEL_CARDINFO", "btnsmall_delete", deleteActionJS, applicationItem, formEffect)%>
		</td>
		<td width="30%"><%=FormatUtil.display(KPLDesc) %></td>
		<td width="20%"></td>
		<td width="25%"><%=HtmlUtil.linkText("LoadKPLInfo","CardPersonName",personalInfo.getThName(),loadActionJS, applicationItem, formEffect) %></td>
		<td width="15%"></td>
		<td width="15%"><%=HtmlUtil.getFinalDecisionText(applicationItem.getFinalAppDecision(), request)%></td>
	</tr>
	<%}%>
<%}%>
</table>