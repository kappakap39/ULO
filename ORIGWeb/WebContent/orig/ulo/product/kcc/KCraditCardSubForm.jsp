<%@page import="com.eaf.core.ulo.common.util.SessionControl"%>
<%@page import="com.eaf.orig.ulo.model.control.FlowControlDataM"%>
<%@page import="com.eaf.core.ulo.common.util.FormEffects"%>
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
<%@page import="com.eaf.orig.ulo.app.view.form.subform.product.manual.CardInfoUtil"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler" />
<script type="text/javascript" src="orig/ulo/product/js/KCraditCardSubForm.js"></script>

<%
	String formId = "LIST_PRODUCT_FORM_CC";
	String subformId = "K_CREDIT_CARD_SUBFORM";
	Logger logger = Logger.getLogger(this.getClass());
	FlowControlDataM flowControl = (FlowControlDataM) request.getSession().getAttribute(SessionControl.FlowControl);
	String ACTION_TYPE = flowControl.getActionType();
	String OBJECT_ROLE_ID = flowControl.getRole();
	ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
	if (null == applicationGroup) {
		applicationGroup = new ApplicationGroupDataM();
	}
	String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
	String APPLICATION_LEVEL = SystemConstant.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
	String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	String APPLICATION_CARD_TYPE_BORROWER = SystemConstant.getConstant("APPLICATION_CARD_TYPE_BORROWER");
	String APPLICATION_CARD_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("APPLICATION_CARD_TYPE_SUPPLEMENTARY");
	String CACHE_CARD_INFO = SystemConstant.getConstant("CACHE_CARD_INFO");
	String FIELD_ID_CARD_LEVEL = SystemConstant.getConstant("FIELD_ID_CARD_LEVEL");
	String FIELD_ID_APPLICATION_CARD_TYPE = SystemConstant.getConstant("FIELD_ID_APPLICATION_CARD_TYPE");
	String FIELD_ID_CARD_TYPE = SystemConstant.getConstant("FIELD_ID_CARD_TYPE");
	
	String ROLE_DE1_1 = SystemConstant.getConstant("ROLE_DE1_1");
	String ROLE_DE1_2 = SystemConstant.getConstant("ROLE_DE1_2");
	String BENZ_CARD_TYPE = SystemConstant.getConstant("BENZ_CARD_TYPE");
	String CGA_CARD_TYPE = SystemConstant.getConstant("CGA_CARD_TYPE");
	
	String COMPLETE_FLAG_Y = SystemConstant.getConstant("COMPLETE_FLAG_Y");
	String COMPLETE_FLAG_N = SystemConstant.getConstant("COMPLETE_FLAG_N");
	
	ArrayList<ApplicationDataM> borrowerApplications = new ArrayList<ApplicationDataM>();
	if(SystemConstant.lookup("ACTION_TYPE_ENQUIRY",ACTION_TYPE)){
		borrowerApplications = applicationGroup.filterDisplayEnquiryApplicationsProduct(PRODUCT_CRADIT_CARD, APPLICATION_CARD_TYPE_BORROWER);
	}else{
		borrowerApplications = applicationGroup.filterDisplayApplicationsProduct(PRODUCT_CRADIT_CARD,APPLICATION_CARD_TYPE_BORROWER);
	}
	PersonalInfoDataM supPersonalInfo = applicationGroup.getPersonalInfo(APPLICATION_CARD_TYPE_SUPPLEMENTARY);
	logger.debug("supApplications >>> " + supPersonalInfo);
	FormUtil formUtil = new FormUtil(subformId, request);
	FormEffects formEffect = new FormEffects(subformId,request);
	formEffect.setFormId(formId);
%>
<table class="table table-striped table-hover kcraditcard">
	<%
		if (!Util.empty(borrowerApplications)) {
			for (ApplicationDataM borrowerItem : borrowerApplications) {
// 				if (borrowerItem.isDeleteFlag()) {
// 					continue;
// 				}
				CardDataM borrowerCard = borrowerItem.getCard();
				
				String borrowerApplicationCardType = borrowerCard.getApplicationType();
				String borrowerUniqueId = borrowerItem.getApplicationRecordId();
				String borrowerCardTypeId = borrowerCard.getCardType();
				PersonalInfoDataM borrowerPersonalInfo = applicationGroup.getPersonalInfoRelation(borrowerUniqueId,
						PERSONAL_TYPE_APPLICANT, APPLICATION_LEVEL);
				if (null == borrowerPersonalInfo) {
					borrowerPersonalInfo = new PersonalInfoDataM();
				}
				HashMap<String, Object> borrowerCardInfo = CardInfoUtil.getCardInfo(borrowerCardTypeId);
				logger.debug("borrowerCardInfo >>" + borrowerCardInfo);
				String cardCodeborrower = (String) borrowerCardInfo.get("CARD_CODE");
				String borrowerPopupAction = ListBoxControl.getName(FIELD_ID_CARD_TYPE, cardCodeborrower, "SYSTEM_ID1");
				String borrowerCardCode = SQLQueryEngine.display(borrowerCardInfo, "CARD_CODE");
				String borrowerCardLevel = SQLQueryEngine.display(borrowerCardInfo, "CARD_LEVEL");
				String borrowerLoadCardActionJS = "onclick=loadCraditCardInfoActionJS('" + borrowerPopupAction + "','"
						+ borrowerUniqueId + "',700,300)";
				String borrowerDeleteCardActionJS = "onclick=deleteCraditCardActionJS('" + borrowerUniqueId + "','"
						+ borrowerApplicationCardType + "')";
				String cardCodeChoiceId = CacheControl.getName(FIELD_ID_CARD_TYPE, borrowerCardCode, "CHOICE_ID");
				logger.debug("cardCodeChoiceId " + cardCodeChoiceId);
				//String borrowerCardCodeDisplay = HtmlUtil.linkText("LoadKCraditCardInfo", "CardPersonName", borrowerPersonalInfo.getThName(), borrowerLoadCardActionJS, borrowerItem, formEffect);
				String borrowerCardCodeDisplay = borrowerPersonalInfo.getThName();
				
				
				String cardCompleteFlag =null;
				String btnCompareFlag = null;
				if(!Util.empty(borrowerCard.getCompleteFlag())){
					if(ROLE_DE1_1.equals(OBJECT_ROLE_ID)){
						if(CGA_CARD_TYPE.equals(cardCodeborrower) || BENZ_CARD_TYPE.equals(cardCodeborrower)){
							cardCompleteFlag = COMPLETE_FLAG_Y;
						}else{
							cardCompleteFlag = borrowerCard.getCompleteFlag();
						}
					}else if(ROLE_DE1_2.equals(OBJECT_ROLE_ID)){
						if(CGA_CARD_TYPE.equals(cardCodeborrower)){
							if(Util.empty(borrowerCard.getCgaCode()) || Util.empty(borrowerCard.getCgaApplyChannel())){
								cardCompleteFlag = COMPLETE_FLAG_N;
							}else{
								cardCompleteFlag = borrowerCard.getCompleteFlag();
							}
						}else if(BENZ_CARD_TYPE.equals(cardCodeborrower)){
							if(Util.empty(borrowerCard.getChassisNo())){
								cardCompleteFlag = COMPLETE_FLAG_N;
							}else{
								cardCompleteFlag = borrowerCard.getCompleteFlag();
							}
						}else{
							cardCompleteFlag = borrowerCard.getCompleteFlag();
						}
					}else{
						cardCompleteFlag = borrowerCard.getCompleteFlag();
					}
				}else{
					cardCompleteFlag = COMPLETE_FLAG_N;
				}
				if(cardCompleteFlag.equals(COMPLETE_FLAG_Y)){
					btnCompareFlag="btnsmall_complete_flag";
				}else{
					btnCompareFlag="btnsmall_edit_flag";
				}
	// 				String borrowerCardCodeDisplay = "07".equals(cardCodeChoiceId) ? 
// 				HtmlUtil.linkText("LoadKCraditCardInfo", borrowerPersonalInfo.getThName(), "", borrowerLoadCardActionJS, request) : 
// 					borrowerPersonalInfo.getThName();
				if(Util.empty(borrowerCardCodeDisplay)){
					borrowerCardCodeDisplay = borrowerPersonalInfo.getThName();
				}
	%>
	<tr>
		<td width="7%">
			<%=HtmlUtil.icon("DEL_CARDINFO", "DEL_CARDINFO", "btnsmall_delete", borrowerDeleteCardActionJS, borrowerItem,formEffect)%>
		</td>
		<td width="30%"><%=CacheControl.getName(FIELD_ID_CARD_TYPE, borrowerCardCode, "DISPLAY_NAME")%></td>
		<td width="20%"><%=CacheControl.getName(FIELD_ID_CARD_LEVEL, borrowerCardLevel, "DISPLAY_NAME")%></td>
		<td width="25%"><%=borrowerCardCodeDisplay%></td>
		<td width="15%"><%=CacheControl.displayName(FIELD_ID_APPLICATION_CARD_TYPE, borrowerApplicationCardType)%></td>
		<td width="10%"><%=HtmlUtil.getFinalDecisionText(borrowerItem.getFinalAppDecision(), request)%></td>
		<td width="10%">
			<%=HtmlUtil.icon("LoadKCraditCardInfo", "CardPersonName", btnCompareFlag, borrowerLoadCardActionJS, borrowerItem,formEffect)%>
		</td>				
	</tr>
	<%
		}
			} else {
	%>
	<tr>
 		<td colspan="6" align="center"><%=LabelUtil.getText(request,"NO_RECORD_FOUND")%></td>
 	</tr>
	<%
		
		}
	%>
</table>