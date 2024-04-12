<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.util.SessionControl"%>
<%@page import="com.eaf.orig.ulo.model.control.FlowControlDataM"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.util.FormEffects"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.ListBoxControl"%>
<%@page import="com.eaf.core.ulo.common.properties.CacheControl"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.CardDataM"%>
<%@page import="com.eaf.orig.ulo.app.view.form.subform.product.manual.CardInfoUtil"%>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>
<script type="text/javascript" src="orig/ulo/product/js/KExpressCashSubForm.js"></script>
<%
	FlowControlDataM flowControl = (FlowControlDataM) request.getSession().getAttribute(SessionControl.FlowControl);
	String ACTION_TYPE = flowControl.getActionType();
	String formId = "LIST_PRODUCT_FORM_KEC";
	String subformId = "K_EXPRESS_CACH_SUBFORM";
	Logger logger = Logger.getLogger(this.getClass());
	String PRODUCT_K_EXPRESS_CASH = SystemConstant.getConstant("PRODUCT_K_EXPRESS_CASH");
	String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	String APPLICATION_LEVEL = SystemConstant.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");

	ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
	if(null == applicationGroup){
		applicationGroup = new ApplicationGroupDataM();
	}
	 ArrayList<ApplicationDataM> applications  = new ArrayList<ApplicationDataM>();
	if(SystemConstant.lookup("ACTION_TYPE_ENQUIRY",ACTION_TYPE)){
		applications=applicationGroup.filterDisplayEnquiryApplicationsProduct(PRODUCT_K_EXPRESS_CASH, null);
	}else{
		applications= applicationGroup.filterDisplayApplicationsProduct(PRODUCT_K_EXPRESS_CASH,null);

	}
	/* ArrayList<ApplicationDataM> applications = applicationGroup.getApplicationsProduct(PRODUCT_K_EXPRESS_CASH); */
	String CACHE_CARD_INFO = SystemConstant.getConstant("CACHE_CARD_INFO");	
	String FIELD_ID_CARD_TYPE = SystemConstant.getConstant("FIELD_ID_CARD_TYPE");
	
	FormUtil formUtil = new FormUtil(formId,subformId, request);
	FormEffects formEffect = new FormEffects(subformId,request);
	formEffect.setFormId(formId);
%>
<table class="table table-striped table-hover kxproduct">
	<%
		if (!Util.empty(applications)) {
			for (ApplicationDataM applicationItem : applications) {
				CardDataM card = applicationItem.getCard();
				String uniqueId = applicationItem.getApplicationRecordId();
				String cardTypeId = card.getCardType();
				
				HashMap<String, Object> cardInfo = CardInfoUtil.getCardInfo(cardTypeId);				
				PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfoRelation(uniqueId,PERSONAL_TYPE_APPLICANT,APPLICATION_LEVEL);
				if (null == personalInfo) {
					personalInfo = new PersonalInfoDataM();
				}
				String cardCode = (String)cardInfo.get("CARD_CODE");
				logger.debug("cardCode " + cardCode	);
				String popupAction = ListBoxControl.getName(FIELD_ID_CARD_TYPE,cardCode, "SYSTEM_ID1");
				String loadPopupActionJS = "onclick=CardInfoPopUpKECActionJS('"+popupAction+"','"+uniqueId+"',700,300)";
				String deleteActionJS = "onclick=deleteKECInfoActionJS('"+uniqueId+"')";	
	%>
	<tr>
		<td width="7%">
			<%=HtmlUtil.icon("DEL_CARDINFO", "DEL_CARDINFO", "btnsmall_delete", deleteActionJS, applicationItem, formEffect)%>
		</td>
		<td width="30%"><%=CacheControl.getName(FIELD_ID_CARD_TYPE,cardCode, "DISPLAY_NAME")%></td>
		<td width="20%"></td>
<%-- 		<td width="25%"><%=HtmlUtil.linkText("LOAD_PRODUCT_KEC", "CardPersonName", personalInfo.getThName(), loadPopupActionJS, applicationItem,formEffect)%></td> --%>
		<td width="25%"><%=FormatUtil.replece(personalInfo.getThName())%></td>
		<td width="15%"></td>
		<td width="10%"><%=HtmlUtil.getFinalDecisionText(applicationItem.getFinalAppDecision(), request)%></td>
		<td width="10%"><img src="images\ulo\compareFlag2.png" title="Complete"></td>
	</tr>
	<%
		}
	}%>
</table>