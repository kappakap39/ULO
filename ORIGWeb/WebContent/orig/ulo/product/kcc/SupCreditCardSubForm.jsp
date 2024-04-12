<%@page import="com.eaf.core.ulo.common.util.FormEffects"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Vector"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalApplicationInfoDataM"%>
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
<%@page import="com.eaf.orig.ulo.model.app.ProductGroupDataM"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.message.MessageUtil"%>
<%@page import="com.eaf.orig.ulo.model.control.FlowControlDataM"%>
<%@page
	import="com.eaf.orig.ulo.app.view.form.subform.product.manual.CardInfoUtil"%>
<jsp:useBean id="ORIGUser" scope="session"
	class="com.eaf.orig.profile.model.UserDetailM" />
<jsp:useBean id="EntityForm" scope="session"
	class="com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler" />
<script type="text/javascript"
	src="orig/ulo/product/js/SupCreditCardSubForm.js"></script>
<%
	String subformId = "K_CREDIT_CARD_SUBFORM";
	Logger logger = Logger.getLogger(this.getClass());
	PersonalApplicationInfoDataM personalApplicationInfo = (PersonalApplicationInfoDataM) EntityForm.getObjectForm();
	PersonalInfoDataM personalInfo = personalApplicationInfo.getPersonalInfo();
	
	FlowControlDataM flowControl = (FlowControlDataM)request.getSession().getAttribute("FlowControl");
	String OBJECT_ROLE_ID = flowControl.getRole();
	String ACTION_TYPE = flowControl.getActionType();
	String CACHE_CARD_INFO = SystemConstant.getConstant("CACHE_CARD_INFO");
	String FIELD_ID_CARD_LEVEL = SystemConstant.getConstant("FIELD_ID_CARD_LEVEL");
	String FIELD_ID_APPLICATION_CARD_TYPE = SystemConstant.getConstant("FIELD_ID_APPLICATION_CARD_TYPE");
	String FIELD_ID_CARD_TYPE = SystemConstant.getConstant("FIELD_ID_CARD_TYPE");

	String COMPLETE_FLAG_Y = SystemConstant.getConstant("COMPLETE_FLAG_Y");
	String COMPLETE_FLAG_N = SystemConstant.getConstant("COMPLETE_FLAG_N");
	String ROLE_DE1_1 = SystemConstant.getConstant("ROLE_DE1_1");
	String ROLE_DE1_2 = SystemConstant.getConstant("ROLE_DE1_2");
	
	String BENZ_CARD_TYPE = SystemConstant.getConstant("BENZ_CARD_TYPE");
	String CGA_CARD_TYPE = SystemConstant.getConstant("CGA_CARD_TYPE");
	String DECISION_FINAL_DECISION_CANCEL = SystemConstant.getConstant("DECISION_FINAL_DECISION_CANCEL");
	String DECISION_FINAL_DECISION_REJECT = SystemConstant.getConstant("DECISION_FINAL_DECISION_REJECT");
	
	FormUtil formUtil = new FormUtil(subformId, request);
	FormEffects formEffects = new FormEffects(subformId,request);
	formEffects.setFormId("LIST_SUP_PRODUCT_FORM_CC");
%>
<table class="table table-striped table-hover">
	<%
		ArrayList<ApplicationDataM> supplemantaryApplications = personalApplicationInfo.getApplicationMaxLifeCycle();
		logger.debug("supplemantaryApplications : " + supplemantaryApplications);
		String cardCompleteFlag =null;
		String btnCompareFlag = null;
		if (!Util.empty(supplemantaryApplications)) {
			for (ApplicationDataM supplemantaryItem : supplemantaryApplications) {
// 				if (supplemantaryItem.isDeleteFlag()) {
// 					continue;
// 				}
				String finalDecision = supplemantaryItem.getFinalAppDecision();
				
				CardDataM supplemantaryCard = supplemantaryItem.getCard();
			
				String supplemantaryApplicationCardType = supplemantaryCard.getApplicationType();
				String supplemantaryUniqueId = supplemantaryItem.getApplicationRecordId();
				String supplemantaryCardTypeId = supplemantaryCard.getCardType();
		
				HashMap<String, Object> supplemantaryCardInfo = CardInfoUtil.getCardInfo(supplemantaryCardTypeId);
				String cardCode = (String) supplemantaryCardInfo.get("CARD_CODE");
				String supplemantaryPopupAction = ListBoxControl.getName(FIELD_ID_CARD_TYPE, cardCode, "SYSTEM_ID2");
				String supplemantaryCardCode = SQLQueryEngine.display(supplemantaryCardInfo, "CARD_CODE");
				String supplemantaryCardLevel = SQLQueryEngine.display(supplemantaryCardInfo, "CARD_LEVEL");
				logger.debug("supplemantaryUniqueId >> " + supplemantaryUniqueId);
				String supplemantaryLoadCardActionJS = "onclick=loadCraditCardInfoActionJS('" + supplemantaryPopupAction
						+ "','" + supplemantaryUniqueId + "',800,300)";
				String supplemantaryDeleteCardActionJS = "onclick=deleteCraditCardActionJS('" + supplemantaryUniqueId + "','"
						+ supplemantaryApplicationCardType + "')";
						
						
						
						
				/*	
				if(!Util.empty(supplemantaryCard.getCompleteFlag())){
					cardCompleteFlag = supplemantaryCard.getCompleteFlag();
				}else{
					cardCompleteFlag = COMPLETE_FLAG_N;
				}
				*/
				
				if(!Util.empty(supplemantaryCard.getCompleteFlag())){
					if(ROLE_DE1_1.equals(OBJECT_ROLE_ID)){
						if(BENZ_CARD_TYPE.equals(cardCode)){
							cardCompleteFlag = COMPLETE_FLAG_Y;
						}else{
							cardCompleteFlag = supplemantaryCard.getCompleteFlag();
						}
					}else if(ROLE_DE1_2.equals(OBJECT_ROLE_ID)){
						if(CGA_CARD_TYPE.equals(cardCode)){
							if(Util.empty(supplemantaryCard.getCgaCode()) || Util.empty(supplemantaryCard.getCgaApplyChannel())){
								cardCompleteFlag = COMPLETE_FLAG_N;
							}else{
								cardCompleteFlag = supplemantaryCard.getCompleteFlag();
							}
						}else if(BENZ_CARD_TYPE.equals(cardCode)){
							if(Util.empty(supplemantaryCard.getChassisNo())){
								cardCompleteFlag = COMPLETE_FLAG_N;
							}else{
								cardCompleteFlag = supplemantaryCard.getCompleteFlag();
							}
						}else{
							cardCompleteFlag = supplemantaryCard.getCompleteFlag();
						}
					}else{
						cardCompleteFlag = supplemantaryCard.getCompleteFlag();
					}
				}else{
					cardCompleteFlag = COMPLETE_FLAG_N;
				}	
						
						
						
						
				if(cardCompleteFlag.equals(COMPLETE_FLAG_Y)){
					btnCompareFlag="btnsmall_complete_flag";
				}else{
					btnCompareFlag="btnsmall_edit_flag";
				}		
	%>
	<tr>
		<td width="7%"><%=HtmlUtil.icon("DEL_CARDINFO", "DEL_CARDINFO", "btnsmall_delete", supplemantaryDeleteCardActionJS, supplemantaryItem, formEffects)%></td>
		<td width="25%"><%=CacheControl.getName(FIELD_ID_CARD_TYPE, supplemantaryCardCode, "DISPLAY_NAME")%></td>
		<td width="20%"><%=CacheControl.getName(FIELD_ID_CARD_LEVEL, supplemantaryCardLevel, "DISPLAY_NAME")%></td>
		<td width="25%"><%=HtmlUtil.linkText("LoadKCraditCardInfo", "CardPersonName",personalInfo.getThFirstName() + "  " + personalInfo.getThLastName(),supplemantaryLoadCardActionJS,supplemantaryItem , formEffects)%></td>
		<td width="10%"><%=CacheControl.displayName(FIELD_ID_APPLICATION_CARD_TYPE, supplemantaryApplicationCardType)%></td>
		<td width="15%"><%=HtmlUtil.getFinalDecisionText(supplemantaryItem.getFinalAppDecision(), request)%></td>
		<td width="10%">
			<%=HtmlUtil.icon("LoadKCraditCardInfo", "CardPersonName", btnCompareFlag, supplemantaryLoadCardActionJS, supplemantaryItem,formEffects)%>
		</td>
	</tr>
	<%
		}
		}
	%>
</table>