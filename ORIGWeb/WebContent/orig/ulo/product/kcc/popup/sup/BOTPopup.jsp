<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.core.ulo.common.properties.FormControl"%>
<%@page import="com.eaf.orig.ulo.app.view.form.subform.product.manual.CardInfoUtil"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.common.engine.SQLQueryEngine"%>
<%@page import="com.eaf.core.ulo.common.properties.CacheControl"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.LoanDataM"%>
<%@page import="com.eaf.orig.cache.properties.CardTypeProperties"%>
<%@page import="com.eaf.orig.ulo.model.app.CardDataM"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalApplicationInfoDataM"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ModuleForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler"/>
<jsp:useBean id="EntityForm" scope="session"class="com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler" />
<script type="text/javascript" src="orig/ulo/product/kcc/popup/js/KccPopup.js"></script>
<%
	String subformId = "SUP_BOT_POPUP";
	Logger logger = Logger.getLogger(this.getClass());
	String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
	String FIELD_ID_CARD_LEVEL =  SystemConstant.getConstant("FIELD_ID_CARD_LEVEL");
	String FIELD_ID_CARD_TYPE =SystemConstant.getConstant("FIELD_ID_CARD_TYPE");
	String FIELD_ID_PERCENT_LIMIT_MAINCARD = SystemConstant.getConstant("FIELD_ID_PERCENT_LIMIT_MAINCARD");
	String displayMode = HtmlUtil.EDIT;
	String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	ApplicationDataM applicationItem = (ApplicationDataM)ModuleForm.getObjectForm();
	String applicationRecordId = applicationItem.getApplicationRecordId();
	ApplicationGroupDataM applicationGroup = FormControl.getOrigObjectForm(request);
	
	PersonalInfoDataM personalInfo = null;
	Object objectForm = FormControl.getMasterObjectForm(request);
	if(objectForm instanceof ApplicationGroupDataM){
		ApplicationGroupDataM applicationGroupDataM = (ApplicationGroupDataM)objectForm;
		personalInfo = applicationGroupDataM.getPersonalInfo(PERSONAL_TYPE_SUPPLEMENTARY);
	}else if(objectForm instanceof PersonalApplicationInfoDataM){
	PersonalApplicationInfoDataM personalApplicationInfoDataM = (PersonalApplicationInfoDataM)objectForm;
		personalInfo = personalApplicationInfoDataM.getPersonalInfo();
	}
	
// 	PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfoByRelation(applicationRecordId);
// 	PersonalApplicationInfoDataM personalApplicationInfo = (PersonalApplicationInfoDataM) EntityForm.getObjectForm();
// 	PersonalInfoDataM personalInfo = personalApplicationInfo.getPersonalInfo();
	if(null == personalInfo){
		personalInfo = new PersonalInfoDataM();
	}
	LoanDataM loan = applicationItem.getLoan();
	if(null == loan){
		loan = new LoanDataM();
	}
	String productElementId = FormatUtil.getProductElementId(personalInfo,PRODUCT_CRADIT_CARD);	
	
	CardDataM card = applicationItem.getCard();
		HashMap<String,Object> supplemantaryCardInfo = CardInfoUtil.getCardInfo(card.getCardType());	
		String cardCode =(String)supplemantaryCardInfo.get("CARD_CODE");	
		String cardLevel =(String)supplemantaryCardInfo.get("CARD_LEVEL");					
	String supplemantaryCardCode = SQLQueryEngine.display(supplemantaryCardInfo,"CARD_CODE");
	String supplemantaryCardLevel = SQLQueryEngine.display(supplemantaryCardInfo,"CARD_LEVEL");	

	FormUtil formUtil = new FormUtil(subformId,request);
	
	String CARD_TYPE_DESC =	CacheControl.getName(FIELD_ID_CARD_TYPE, cardCode, "DISPLAY_NAME");
	String CARD_LEVEL_DESC = "";
	if(!Util.empty(cardLevel)) {
		CARD_LEVEL_DESC = CacheControl.getName(FIELD_ID_CARD_LEVEL, cardLevel, "DISPLAY_NAME");
	}
	logger.debug("CardCode >> "+cardCode); 	
	logger.debug("CardLevel >> "+cardLevel);
 %>
<div class="panel panel-default">
		 <div class="panel-body">
			<div class="row form-horizontal">
				<div class="col-sm-6">
					<div class="form-group">
						<%=HtmlUtil.getSubFormLabel(request,subformId,"DM_FIRST_NAME","DM_FIRST_NAME","col-sm-4 col-md-5 control-label")%>
<%-- 						<%=HtmlUtil.dropdown("SUB_NAME", PRODUCT_CRADIT_CARD, "SUB_NAME_"+productElementId, "SUB_NAME", "",  --%>
<%-- 							card.getPersonalId(), "","","ALL_ALL_ALL", "", "col-sm-8 col-md-7", applicationItem, formUtil)%> --%>
						<%=HtmlUtil.span("", FormatUtil.display(card.getPersonalId()), "col-sm-8 col-md-7") %>
						<%=FormatUtil.display(personalInfo.getThFirstName())%>&nbsp;<%=FormatUtil.display(personalInfo.getThLastName())%>
					</div>
				</div>
				<div class="col-sm-6">
					<div class="form-group">
						<%=HtmlUtil.getSubFormLabel(request,subformId,"CARD_LEVEL","CARD_LEVEL","col-sm-4 col-md-5 control-label")%>
						<%=HtmlUtil.span("", FormatUtil.display(CARD_LEVEL_DESC), "subform-nopadding-right col-md-7")%>
						<%=HtmlUtil.hidden("CARD_LEVEL_"+PRODUCT_CRADIT_CARD, cardLevel) %>
<%-- 					<%=HtmlUtil.dropdown("CARD_LEVEL", PRODUCT_CRADIT_CARD, "CARD_LEVEL_"+productElementId, "CARD_LEVEL", "",  --%>
<%-- 							cardLevel, "",FIELD_ID_CARD_LEVEL,"ALL_ALL_ALL", "", "col-sm-8 col-md-7", applicationItem, formUtil)%> --%>
					</div>
				</div>
				<div class="clearfix"></div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<%=HtmlUtil.getSubFormLabel(request,applicationItem, subformId,"PERCENT_LIMIT_MAINCARD","PERCENT_LIMIT_MAINCARD","col-sm-4 col-md-5 control-label")%>
						<%=HtmlUtil.dropdown("PERCENT_LIMIT_MAINCARD", PRODUCT_CRADIT_CARD, "PERCENT_LIMIT_MAINCARD_"+productElementId, "PERCENT_LIMIT_MAINCARD", "", 
							card.getPercentLimitMaincard(), "",FIELD_ID_PERCENT_LIMIT_MAINCARD,"ALL_ALL_ALL", "", "col-sm-8 col-md-7", applicationItem, formUtil)%>
					</div>
				</div>
				<div class="col-sm-6">
					<div class="form-group">
						<%=HtmlUtil.getSubFormLabel(request,applicationItem, subformId,"PERCENT_LIMIT","PERCENT_LIMIT","col-sm-4 col-md-5 control-label")%>
						<%=HtmlUtil.currencyBox("PERCENT_LIMIT", "", "PERCENT_LIMIT_"+productElementId, "PERCENT_LIMIT", loan.getRequestLoanAmt(), "", true, "", "", "col-sm-8 col-md-7", applicationItem, formUtil) %>
					</div>
				</div>
				<div class="clearfix"></div>
				<div class="col-sm-6">
					<div class="form-group">
						<%=HtmlUtil.getSubFormLabel(request,applicationItem, subformId,"MEMBERSHIP_NO","MEMBERSHIP_NO","col-sm-4 col-md-5 control-label")%>
						<%=HtmlUtil.textBox("MEMBERSHIP_NO", PRODUCT_CRADIT_CARD,"MEMBERSHIP_NO_"+productElementId,"MEMBERSHIP_NO",
							card.getMembershipNo(),"","50","","col-sm-8 col-md-7", applicationItem, formUtil)%>
					</div>
				</div>
				
			</div>
		</div>
	</div>