<%@page import="com.eaf.orig.ulo.model.app.PersonalApplicationInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.core.ulo.common.properties.FormControl"%>
<%@page import="com.eaf.orig.ulo.app.view.form.subform.product.manual.CardInfoUtil"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.common.engine.SQLQueryEngine"%>
<%@page import="com.eaf.core.ulo.common.properties.CacheControl"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.LoanDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.CardDataM"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>

<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<script type="text/javascript" src="orig/ulo/product/js/AdditionalServiceSubForm.js"></script>
<jsp:useBean id="ModuleForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler"/>
<%
	String subformId = "SUPPLEMENTARY_CARD_POPUP_2";
	Logger logger = Logger.getLogger(this.getClass());
// 	logger.debug("1");
// 	String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	ApplicationDataM applicationItem = (ApplicationDataM)ModuleForm.getObjectForm();
// 	int PERSONAL_SEQ = 1;
	CardDataM cardM = applicationItem.getCard();
	if (Util.empty(cardM)) {
		cardM = new CardDataM();
	}
	LoanDataM loan = applicationItem.getLoan();
	if (Util.empty(loan)) {
		loan = new LoanDataM();
	}

	String displayMode = HtmlUtil.EDIT;
	logger.debug("cardM.getCardId() >>>>> " + cardM.getCardType());
	HashMap<String, Object> CardInfo = CardInfoUtil.getCardInfo(cardM.getCardType());
	String CardCode = SQLQueryEngine.display(CardInfo, "CARD_CODE");
	String CardLevel = SQLQueryEngine.display(CardInfo, "CARD_LEVEL");
	logger.debug("CardCode >> " + CardCode);
	logger.debug("CardLevel >> " + CardLevel);

// 	String TAG_SMART_DATA_PERSONAL = FormatUtil.getSmartDataEntryId(PERSONAL_TYPE, PERSONAL_SEQ);
	String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
	String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	String FIELD_ID_CARD_TYPE = SystemConstant.getConstant("FIELD_ID_CARD_TYPE");
	String FIELD_ID_CARD_LEVEL = SystemConstant.getConstant("FIELD_ID_CARD_LEVEL");
	String FIELD_ID_TH_TITLE_CODE = SystemConstant.getConstant("FIELD_ID_TH_TITLE_CODE");

	String CARD_TYPE_DESC = CacheControl.getName(FIELD_ID_CARD_TYPE, CardCode, "DISPLAY_NAME");
	String CARD_LEVEL_DESC = CacheControl.getName(FIELD_ID_CARD_LEVEL, CardLevel, "DISPLAY_NAME");
	String FIELD_ID_PERCENT_LIMIT_MAINCARD = SystemConstant.getConstant("FIELD_ID_PERCENT_LIMIT_MAINCARD");
	
	FormUtil formUtil = new FormUtil(subformId, request);
	PersonalInfoDataM personalInfo = null;
	Object objectForm = FormControl.getMasterObjectForm(request);
	if(objectForm instanceof ApplicationGroupDataM){
		ApplicationGroupDataM applicationGroupDataM = (ApplicationGroupDataM)objectForm;
		personalInfo = applicationGroupDataM.getPersonalInfo(PERSONAL_TYPE_SUPPLEMENTARY);
	}else if(objectForm instanceof PersonalApplicationInfoDataM){
	PersonalApplicationInfoDataM personalApplicationInfoDataM = (PersonalApplicationInfoDataM)objectForm;
		personalInfo = personalApplicationInfoDataM.getPersonalInfo();
	}
	
	String productElementId = FormatUtil.getProductElementId(personalInfo,PRODUCT_CRADIT_CARD);
%>
 
<%=HtmlUtil.hidden("SUFFIX", PRODUCT_CRADIT_CARD) %>
 <div class="panel panel-default">
		 <div class="panel-body">
			<div class="row form-horizontal">
				<div class="col-sm-6">
					<div class="form-group">
						<%=HtmlUtil.getSubFormLabel(request,subformId,"DM_FIRST_NAME","DM_FIRST_NAME", "col-sm-4 col-md-5 control-label")%>
						<%=HtmlUtil.span("", FormatUtil.display(cardM.getPersonalId()), "col-sm-8 col-md-7") %>
						<%=FormatUtil.display(personalInfo.getThFirstName())%>&nbsp;<%=FormatUtil.display(personalInfo.getThLastName())%>
					</div>
				</div>
				<div class="col-sm-6">
					<div class="form-group">
						<%=HtmlUtil.getSubFormLabel(request,subformId,"CARD_LEVEL","CARD_LEVEL", "col-sm-4 col-md-5 control-label")%>
						<%=HtmlUtil.span("", FormatUtil.display(CARD_LEVEL_DESC), "col-sm-8 col-md-7") %>
					</div>
				</div>
				<div class="clearfix"></div>
				<div class="col-sm-6">
					<div class="form-group">
						<%=HtmlUtil.getSubFormLabel(request,applicationItem, subformId,"PERCENT_LIMIT_MAINCARD","PERCENT_LIMIT_MAINCARD", "col-sm-4 col-md-5 control-label")%>
						<%=HtmlUtil.dropdown("PERCENT_LIMIT_MAINCARD","", "PERCENT_LIMIT_MAINCARD_"+productElementId, "PERCENT_LIMIT_MAINCARD","", 
						cardM.getPercentLimitMaincard(), "",FIELD_ID_PERCENT_LIMIT_MAINCARD,"ALL_ALL_ALL","","col-sm-8 col-md-7",applicationItem,formUtil)%>
					</div>
				</div>
				<div class="col-sm-6">
					<div class="form-group">
						<%=HtmlUtil.getSubFormLabel(request,applicationItem, subformId,"PERCENT_LIMIT","PERCENT_LIMIT", "col-sm-4 col-md-5 control-label")%>
						<%=HtmlUtil.currencyBox("PERCENT_LIMIT","", "PERCENT_LIMIT_"+productElementId, "PERCENT_LIMIT", 
							loan.getRequestLoanAmt(), "", true, "", "", "col-sm-8 col-md-7",applicationItem, formUtil)%>
					</div>
				</div>
				<div class="clearfix"></div>
			</div>
		</div>
	</div>

 
 
